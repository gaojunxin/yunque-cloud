package com.xueyi.common.cache.utils;

import com.xueyi.common.cache.service.CacheService;
import com.xueyi.common.core.utils.core.SpringUtil;
import com.xueyi.common.core.web.model.SysEnterprise;
import com.xueyi.tenant.api.tenant.constant.TenantConstants;

/**
 * 企业缓存管理工具类
 *
 * @author xueyi
 */
public class EnterpriseUtil {

    /**
     * 获取企业缓存信息
     *
     * @param id 企业Id
     * @return 企业信息对象
     */
    public static SysEnterprise getEnterpriseCache(Long id) {
        TenantConstants.CacheType cacheType = TenantConstants.CacheType.TE_TENANT_KEY;
        return SpringUtil.getBean(CacheService.class).getCacheObject(cacheType.getCode(), cacheType.getIsTenant(), cacheType.getConsumer(), id.toString());
    }
}
