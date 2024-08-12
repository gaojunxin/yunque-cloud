package com.xueyi.common.cache.utils;

import com.xueyi.common.cache.service.CacheService;
import com.xueyi.common.core.utils.core.SpringUtil;
import com.xueyi.common.core.web.model.SysEnterprise;
import com.xueyi.common.core.web.model.SysSource;
import com.xueyi.tenant.api.source.constant.SourceConstants;
import com.xueyi.tenant.api.source.domain.dto.TeSourceDto;
import com.xueyi.tenant.api.source.domain.dto.TeStrategyDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 源策略组缓存管理工具类
 *
 * @author xueyi
 */
@Slf4j
public class SourceUtil {

    /**
     * 通过企业Id查询数据源信息
     *
     * @param enterpriseId 企业Id
     * @return 数据源信息对象
     */
    public static SysSource getSourceCacheByEnterpriseId(Long enterpriseId) {
        return Optional.ofNullable(EnterpriseUtil.getEnterpriseCache(enterpriseId)).map(SysEnterprise::getStrategyId).map(SourceUtil::getSourceCache).orElseGet(() -> {
            log.error("【源策略组缓存】企业Id：{}对应的企业信息不存在", enterpriseId);
            return null;
        });
    }

    /**
     * 通过源策略组Id查询数据源信息
     *
     * @param strategyId 源策略组Id
     * @return 数据源信息对象
     */
    public static SysSource getSourceCache(Long strategyId) {
        return Optional.ofNullable(getStrategyCache(strategyId)).map(strategyInfo -> {
            SysSource source = new SysSource();
            source.setId(strategyInfo.getId());
            source.setStrategyId(strategyInfo.getId());
            source.setSourceId(strategyInfo.getSourceId());
            source.setMaster(strategyInfo.getSourceSlave());
            source.setSourceTypeInfo(strategyInfo.getSourceTypeInfo());
            return source;
        }).orElseGet(() -> {
            log.error("【源策略组缓存】源策略组Id：{}对应的源策略信息不存在", strategyId);
            return null;
        });
    }

    /**
     * 通过企业Id查询源策略缓存信息
     *
     * @param enterpriseId 企业Id
     * @return 源策略信息对象
     */
    public static TeStrategyDto getStrategyByEnterpriseId(Long enterpriseId) {
        return Optional.ofNullable(EnterpriseUtil.getEnterpriseCache(enterpriseId)).map(SysEnterprise::getStrategyId).map(SourceUtil::getStrategyCache).orElseGet(() -> {
            log.error("【源策略组缓存】企业Id：{}对应的企业/源策略信息不存在", enterpriseId);
            return null;
        });
    }

    /**
     * 获取源策略缓存信息
     *
     * @param id 源策略组Id
     * @return 源策略信息对象
     */
    public static TeStrategyDto getStrategyCache(Long id) {
        SourceConstants.CacheType cacheType = SourceConstants.CacheType.TE_STRATEGY_KEY;
        return SpringUtil.getBean(CacheService.class).getCacheObject(cacheType.getCode(), cacheType.getIsTenant(), cacheType.getConsumer(), id.toString());
    }

    /**
     * 获取数据源缓存信息
     *
     * @param slave 数据源编码
     * @return 数据源信息对象
     */
    public static TeSourceDto getSourceCache(String slave) {
        SourceConstants.CacheType cacheType = SourceConstants.CacheType.TE_SOURCE_KEY;
        return SpringUtil.getBean(CacheService.class).getCacheObject(cacheType.getCode(), cacheType.getIsTenant(), cacheType.getConsumer(), slave);
    }
}
