package com.yunque.system.dict.service.impl;

import com.yunque.common.cache.model.CacheModel;
import com.yunque.common.core.constant.basic.OperateConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.exception.ServiceException;
import com.yunque.common.core.utils.cache.CacheUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.redis.constant.RedisConstants;
import com.yunque.common.security.utils.SecurityUserUtils;
import com.yunque.common.security.utils.SecurityUtils;
import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.dict.constant.ConfigConstants;
import com.yunque.system.api.dict.domain.dto.SysDictDataDto;
import com.yunque.system.api.dict.domain.dto.SysDictTypeDto;
import com.yunque.system.api.dict.domain.query.SysDictDataQuery;
import com.yunque.system.dict.domain.correlate.SysDictDataCorrelate;
import com.yunque.system.dict.manager.ISysDictDataManager;
import com.yunque.system.dict.service.ISysDictDataService;
import com.yunque.system.dict.service.ISysDictTypeService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统服务 | 字典模块 | 字典数据管理 业务层处理
 *
 * @author xueyi
 */
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataQuery, SysDictDataDto, SysDictDataCorrelate, ISysDictDataManager> implements ISysDictDataService {

    /** 缓存定义 */
    @Override
    public CacheModel getCacheModel() {
        return new CacheModel(ConfigConstants.CacheType.SYS_DICT_KEY.getCode(), ConfigConstants.CacheType.SYS_DICT_KEY.getIsTenant());
    }

    /**
     * 单条操作 - 开始处理
     *
     * @param operate 服务层 - 操作类型
     * @param newDto  新数据对象（删除时不存在）
     * @param id      Id集合（非删除时不存在）
     */
    @Override
    protected SysDictDataDto startHandle(OperateConstants.ServiceType operate, SysDictDataDto newDto, Serializable id) {
        SysDictDataDto originDto = super.startHandle(operate, newDto, id);
        switch (operate) {
            case ADD, EDIT, EDIT_STATUS -> {
                if (ObjectUtil.isNotNull(newDto.getDictTypeId())) {
                    SysDictTypeDto dictType = SpringUtil.getBean(ISysDictTypeService.class).selectByIdIgnore(newDto.getDictTypeId());
                    newDto.setDictTypeInfo(dictType);
                }
            }
        }
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
    protected void endHandle(OperateConstants.ServiceType operate, int row, SysDictDataDto originDto, SysDictDataDto newDto) {
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
    protected List<SysDictDataDto> startBatchHandle(OperateConstants.ServiceType operate, Collection<SysDictDataDto> newList, Collection<? extends Serializable> idList) {
        List<SysDictDataDto> originList = super.startBatchHandle(operate, newList, idList);
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
    protected void endBatchHandle(OperateConstants.ServiceType operate, int rows, Collection<SysDictDataDto> originList, Collection<SysDictDataDto> newList) {
        super.endBatchHandle(operate, rows, originList, newList);
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
    public void refreshCache(OperateConstants.ServiceType operate, RedisConstants.OperateType operateCache, SysDictDataDto dto, Collection<SysDictDataDto> dtoList,
                             String cacheKey, Boolean isTenant, Function<? super SysDictDataDto, String> cacheKeyFun, Function<? super SysDictDataDto, Object> cacheValueFun) {
        switch (operateCache) {
            case REFRESH, REMOVE -> {
                Collection<SysDictDataDto> list = operate.isBatch() ? dtoList : new ArrayList<>() {{
                    add(dto);
                }};
                List<String> enterpriseCaches = list
                        .stream().map(item -> CacheUtil.getCusCacheKey(cacheKey)).toList();
                redisService.deleteObject(enterpriseCaches);
            }
        }
    }
}