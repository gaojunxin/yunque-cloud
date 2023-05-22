package com.xueyi.common.redis.constant;

import com.xueyi.common.core.utils.core.EnumUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存的key通用常量
 *
 * @author xueyi
 */
public class RedisConstants {

    /** 缓存有效期，默认720（分钟） */
    public final static long EXPIRATION = 720;

    /** 缓存刷新时间，默认120（分钟） */
    public final static long REFRESH_TIME = 120;

    /** 缓存键 */
    @Getter
    @AllArgsConstructor
    public enum CacheKey {

        LOGIN_TOKEN_KEY("login_tokens:", "权限缓存前缀"),
        CAPTCHA_CODE_KEY("captcha_codes:", "验证码"),
        SYS_CORRELATE_KEY("system:correlate:{}.{}", "数据关联工具");

        private final String code;
        private final String info;

        public String getCacheName(Object... cacheNames) {
            return StrUtil.format(getCode(), cacheNames);
        }
    }

    /** 登录缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum LoginTokenType {

        ADMIN("login_tokens:", "管理端");

        private final String code;
        private final String info;

    }

    /** 缓存类型 */
    @Getter
    @AllArgsConstructor
    public enum OperateType {

        REFRESH_ALL("refresh_all", "更新全部"),
        REFRESH("refresh", "更新"),
        REMOVE("remove", "删除");

        private final String code;
        private final String info;

        public static OperateType getByCode(String code) {
            return EnumUtil.getByCode(OperateType.class, code);
        }

    }
}
