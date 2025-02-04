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
import com.yunque.common.redis.constant.RedisConstants;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.security.utils.SecurityUtils;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.dict.constant.ConfigConstants;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;
import com.yunque.system.dict.domain.correlate.SysConfigCorrelate;
import com.yunque.system.dict.manager.ISysConfigManager;
import com.yunque.system.dict.service.ISysConfigService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统服务 | 字典模块 | 参数管理 服务层实现
 *
 * @author xueyi
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigQuery, SysConfigDto, SysConfigCorrelate, ISysConfigManager> implements ISysConfigService {

    /** 缓存定义 */
    @Override
    public CacheModel getCacheModel() {
        return new CacheModel(ConfigConstants.CacheType.SYS_CONFIG_KEY.getCode(), ConfigConstants.CacheType.SYS_CONFIG_KEY.getIsTenant());
    }

    /**
     * 查询数据对象列表 | 数据权限 | 附加数据
     *
     * @param query 数据查询对象
     * @return 数据对象集合
     */
    @Override
    public List<SysConfigDto> selectListScope(SysConfigQuery query) {
        return selectList(query);
    }

    /**
     * 根据Id查询单条数据对象
     *
     * @param id Id
     * @return 数据对象
     */
    @Override
    public SysConfigDto selectById(Serializable id) {
        SysConfigDto dto = baseManager.selectById(id);
        return dto;
    }

    /**
     * 根据参数编码查询参数值
     *
     * @param code 参数编码
     * @return 参数对象
     */
    @Override
    public SysConfigDto selectConfigByCode(String code) {
        SysConfigDto config = baseManager.selectConfigByCode(code);
        if (ObjectUtil.isNull(config)) {
            syncCache();
            config = baseManager.selectConfigByCode(code);
        }
        return config;
    }

    /**
     * 更新缓存数据
     */
    @Override
    public Boolean syncCache() {
        List<SysConfigDto> enterpriseTypeList = baseManager.selectList(null);
        List<SysConfigDto> commonTypeList = baseManager.selectList(null);
        Map<String, SysConfigDto> enterpriseConfigMap = enterpriseTypeList.stream().collect(Collectors.toMap(SysConfigDto::getCode, Function.identity()));
        List<SysConfigDto> addConfigList = new ArrayList<>();
        commonTypeList.forEach(config -> {
            if (StrUtil.equals(DictConstants.DicCacheType.OVERALL.getCode(), config.getCacheType())) {
                return;
            }
            SysConfigDto enterpriseType = enterpriseConfigMap.get(config.getCode());
            if (ObjectUtil.isNull(enterpriseType)) {
                addConfigList.add(config);
            }
        });
        if (CollUtil.isNotEmpty(addConfigList)) {
            addConfigList.forEach(SysConfigDto::initId);
            baseManager.insertBatch(addConfigList);
        }
        return Boolean.TRUE;
    }

    /**
     * 校验参数编码是否唯一
     *
     * @param id         参数Id
     * @param configCode 参数编码
     * @return 结果 | true/false 唯一/不唯一
     */
    @Override
    public boolean checkConfigCodeUnique(Long id, String configCode) {
        return ObjectUtil.isNotNull(baseManager.checkConfigCodeUnique(ObjectUtil.isNull(id) ? BaseConstants.NONE_ID : id, configCode));
    }

    /**
     * 校验是否为内置参数
     *
     * @param id 参数Id
     * @return 结果 | true/false 是/否
     */
    @Override
    public boolean checkIsBuiltIn(Long id) {
        return ObjectUtil.isNotNull(baseManager.checkIsBuiltIn(ObjectUtil.isNull(id) ? BaseConstants.NONE_ID : id));
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysConfigDto startHandle(OperateConstants.ServiceType operate, SysConfigDto newDto, Serializable id) {
        SysConfigDto originDto = super.startHandle(operate, newDto, id);
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
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysConfigDto originDto, SysConfigDto newDto) {
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
    protected List<SysConfigDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysConfigDto> newList, Collection<? extends Serializable> idList) {
        List<SysConfigDto> originList = super.startBatchHandle(operate, newList, idList);
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
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysConfigDto> originList, Collection<SysConfigDto> newList) {
        super.endBatchHandle(operate, rows, originList, newList);
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache() {
        Collection<String> keys = redisService.keys(RedisConstants.CacheKey.CONFIG_KEY.getCacheName(StrUtil.ASTERISK));
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
    public void refreshCache(OperateConstants.ServiceType operate, RedisConstants.OperateType operateCache, SysConfigDto dto, Collection<SysConfigDto> dtoList,
                             String cacheKey, Boolean isTenant, Function<? super SysConfigDto, String> cacheKeyFun, Function<? super SysConfigDto, Object> cacheValueFun) {
        // 默认缓存管理方法
        super.refreshCache(operate, operateCache, dto, dtoList, cacheKey, isTenant, SysConfigDto::getCode, SysConfigDto::getValue);
        // 路由缓存管理方法
        ConfigConstants.CacheType routeCacheKey = ConfigConstants.CacheType.ROUTE_CONFIG_KEY;
        super.refreshCache(operate, operateCache, dto, dtoList, routeCacheKey.getCode(), routeCacheKey.getIsTenant(), SysConfigDto::getCode, Function.identity());
    }
}