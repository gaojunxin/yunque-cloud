package com.xueyi.tenant.api.tenant.constant;

import com.xueyi.common.core.utils.core.SpringUtil;
import com.xueyi.tenant.api.tenant.feign.RemoteTenantService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 租户通用常量
 *
 * @author xueyi
 */
public class TenantConstants {

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum CacheType {

        TE_TENANT_KEY("system:tenant", false, "租户", () -> SpringUtil.getBean(RemoteTenantService.class).refreshCacheInner());

        private final String code;
        private final Boolean isTenant;
        private final String info;
        private final Supplier<Object> consumer;
    }
}