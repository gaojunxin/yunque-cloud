package com.yunque.system.dict.service.impl;

import com.yunque.common.cache.model.CacheModel;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.DictConstants;
import com.yunque.common.core.constant.basic.OperateConstants;
import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.TenantConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.exception.ServiceException;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.entity.base.BasisEntity;
import com.yunque.common.redis.constant.RedisConstants;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.security.utils.SecurityUtils;
import com.yunque.common.web.correlate.contant.CorrelateConstants;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.dict.constant.ConfigConstants;
import com.yunque.system.api.dict.domain.dto.SysDictDataDto;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.po.SysDictTypePo;
import com.yunque.system.api.dict.domain.query.SysDictTypeQuery;
import com.yunque.system.dict.domain.correlate.SysDictTypeCorrelate;
import com.yunque.system.dict.manager.ISysDictTypeManager;
import com.yunque.system.dict.service.ISysDictDataService;
import com.yunque.system.dict.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统服务 | 字典模块 | 字典类型管理 业务层处理
 *
 * @author xueyi
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeQuery, SysDictTypeDto, SysDictTypeCorrelate, ISysDictTypeManager> implements ISysDictTypeService {

    @Autowired
    private ISysDictDataService dictDataService;

    /** 缓存定义 */
    @Override
    public CacheModel getCacheModel() {
        return new CacheModel(ConfigConstants.CacheType.SYS_DICT_KEY.getCode(), ConfigConstants.CacheType.SYS_DICT_KEY.getIsTenant());
    }

    /**
     * 默认方法关联配置定义
     */
    @Override
    protected Map<CorrelateConstants.ServiceType, SysDictTypeCorrelate> defaultCorrelate() {
        return new HashMap<>() {{
            put(CorrelateConstants.ServiceType.CACHE_REFRESH, SysDictTypeCorrelate.CACHE_REFRESH);
            put(CorrelateConstants.ServiceType.DELETE, SysDictTypeCorrelate.BASE_DEL);
        }};
    }

    /**
     * 查询数据对象列表 | 数据权限 | 附加数据
     *
     * @param query 数据查询对象
     * @return 数据对象集合
     */
    @Override
    public List<SysDictTypeDto> selectListScope(SysDictTypeQuery query) {
        return selectList(query);
    }

    /**
     * 根据Id查询单条数据对象
     *
     * @param id Id
     * @return 数据对象
     */
    @Override
    public SysDictTypeDto selectById(Serializable id) {
        return baseManager.selectById(id);
    }

    /**
     * 根据Id查询单条数据对象 | 全局
     *
     * @param id Id
     * @return 数据对象
     */
    @Override
    public SysDictTypeDto selectByIdIgnore(Serializable id) {
        return selectById(id);
    }

    /**
     * 更新缓存数据
     */
    @Override
    public Boolean syncCache() {
        List<SysDictTypeDto> enterpriseTypeList = baseManager.selectList(null);
        subCorrelates(enterpriseTypeList, getBasicCorrelate(CorrelateConstants.ServiceType.CACHE_REFRESH));
        List<SysDictTypeDto> commonTypeList = baseManager.selectList(null);
        subCorrelates(commonTypeList, getBasicCorrelate(CorrelateConstants.ServiceType.CACHE_REFRESH));
        Map<String, SysDictTypeDto> enterpriseTypeMap = enterpriseTypeList.stream().collect(Collectors.toMap(SysDictTypePo::getCode, Function.identity()));
        List<SysDictTypeDto> addTypeList = new ArrayList<>();
        List<SysDictDataDto> addDataList = new ArrayList<>();
        Set<Long> delIdList = new HashSet<>();
        commonTypeList.forEach(type -> {
            if (StrUtil.equals(DictConstants.DicCacheType.OVERALL.getCode(), type.getCacheType())) {
                return;
            }
            SysDictTypeDto enterpriseType = enterpriseTypeMap.get(type.getCode());
            if (ObjectUtil.isNull(type.getSubList())) {
                type.setSubList(new ArrayList<>());
            }
            if (ObjectUtil.isNull(enterpriseType)) {
                addTypeList.add(type);
                addDataList.addAll(type.getSubList());
            } else {
                if (ObjectUtil.isNull(enterpriseType.getSubList())) {
                    enterpriseType.setSubList(new ArrayList<>());
                }
                Set<String> enterpriseValues = enterpriseType.getSubList().stream().map(SysDictDataDto::getValue).collect(Collectors.toSet());
                Set<String> commonValues = type.getSubList().stream().map(SysDictDataDto::getValue).collect(Collectors.toSet());
                if (StrUtil.equals(DictConstants.DicDataType.READ.getCode(), type.getDataType()) || StrUtil.equals(DictConstants.DicDataType.INCREASE.getCode(), type.getDataType())) {
                    Set<String> increaseValues = new HashSet<>(commonValues);
                    increaseValues.removeAll(enterpriseValues);
                    Map<String, SysDictDataDto> commonDataMap = type.getSubList().stream().collect(Collectors.toMap(SysDictDataDto::getValue, Function.identity()));
                    increaseValues.forEach(item -> addDataList.add(commonDataMap.get(item)));
                }
                if (StrUtil.equals(DictConstants.DicDataType.READ.getCode(), type.getDataType()) || StrUtil.equals(DictConstants.DicDataType.SUBTRACT.getCode(), type.getDataType())) {
                    Map<String, Long> commonDataMap = enterpriseType.getSubList().stream().collect(Collectors.toMap(SysDictDataDto::getValue, SysDictDataDto::getId));
                    Set<String> subtractValues = new HashSet<>(enterpriseValues);
                    subtractValues.removeAll(commonValues);
                    subtractValues.forEach(item -> delIdList.add(commonDataMap.get(item)));
                }
            }
        });
        if (CollUtil.isNotEmpty(addTypeList)) {
            addTypeList.forEach(BasisEntity::initId);
            baseManager.insertBatch(addTypeList);
        }
        if (CollUtil.isNotEmpty(addDataList)) {
            addDataList.forEach(BasisEntity::initId);
            dictDataService.insertBatch(addDataList);
        }
        if (CollUtil.isNotEmpty(delIdList)) {
            dictDataService.deleteByIds(delIdList);
        }
        return Boolean.TRUE;
    }

    /**
     * 校验字典编码是否唯一
     *
     * @param Id       字典类型Id
     * @param dictCode 字典类型编码
     * @return 结果 | true/false 唯一/不唯一
     */
    @Override
    public boolean checkDictCodeUnique(Long Id, String dictCode) {
        return ObjectUtil.isNotNull(baseManager.checkDictCodeUnique(ObjectUtil.isNull(Id) ? BaseConstants.NONE_ID : Id, dictCode));
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysDictTypeDto startHandle(OperateConstants.ServiceType operate, SysDictTypeDto newDto, Serializable id) {
        SysDictTypeDto originDto = super.startHandle(operate, newDto, id);
        return originDto;
    }

    /**
     * 单条操作 - 结束处理
     *
     * @param operate   服务层 - 操作类型
     * @param row       操作数据条数
     * @param originDto 源数据对象（新增时不存在）
     * @param newDto    新数据对象（删除时不存在）
     */
    @Override
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysDictTypeDto originDto, SysDictTypeDto newDto) {
        super.endHandle(operate, row, originDto, newDto);
    }

    /**
     * 批量操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newList 新数据对象集合（删除时不存在）
     * @param idList  Id集合（非删除时不存在）
     */
    @Override
    protected List<SysDictTypeDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysDictTypeDto> newList, Collection<? extends Serializable> idList) {
        List<SysDictTypeDto> originList = super.startBatchHandle(operate, newList, idList);
        return originList;
    }

    /**
     * 批量操作 - 结束处理
     *
     * @param operate    服务层 - 操作类型
     * @param rows       操作数据条数
     * @param originList 源数据对象集合（新增时不存在）
     * @param newList    新数据对象集合（删除时不存在）
     */
    @Override
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysDictTypeDto> originList, Collection<SysDictTypeDto> newList) {
        super.endBatchHandle(operate, rows, originList, newList);
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        Collection<String> keys = redisService.keys(RedisConstants.CacheKey.DICT_KEY.getCacheName(StrUtil.ASTERISK));
        if (CollUtil.isNotEmpty(keys)) {
            redisService.deleteObject(keys);
        }
    }

    /**
     * 缓存更新
     *
     * @param operate       服务层 - 操作类型
     * @param operateCache  缓存操作类型
     * @param dto           数据对象
     * @param dtoList       数据对象集合
     * @param cacheKey      缓存编码
     * @param isTenant      租户级缓存
     * @param cacheKeyFun   缓存键定义方法
     * @param cacheValueFun 缓存值定义方法
     */
    @Override
    public void refreshCache(OperateConstants.ServiceType operate, RedisConstants.OperateType operateCache, SysDictTypeDto dto, Collection<SysDictTypeDto> dtoList,
                             String cacheKey, Boolean isTenant, Function<? super SysDictTypeDto, String> cacheKeyFun, Function<? super SysDictTypeDto, Object> cacheValueFun) {
        // 默认缓存管理方法
        super.refreshCache(operate, operateCache, dto, dtoList, cacheKey, isTenant, SysDictTypeDto::getCode, SysDictTypeDto::getSubList);
        // 路由缓存管理方法
        ConfigConstants.CacheType routeCacheKey = ConfigConstants.CacheType.ROUTE_DICT_KEY;
        super.refreshCache(operate, operateCache, dto, dtoList, routeCacheKey.getCode(), routeCacheKey.getIsTenant(), SysDictTypeDto::getCode, Function.identity());
    }
}