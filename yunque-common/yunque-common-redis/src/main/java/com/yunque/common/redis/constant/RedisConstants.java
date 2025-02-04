package com.yunque.common.redis.constant;

import com.yunque.common.core.utils.core.EnumUtil;
import com.yunque.common.core.utils.core.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存 通用常量
 *
 * @author xueyi
 */
public class RedisConstants {

    /** 缓存键 */
    @Getter
    @AllArgsConstructor
    public enum CacheKey {

        CLIENT_DETAILS_KEY("client:details", "oauth 客户端信息"),
        CAPTCHA_CODE_KEY("captcha_codes:", "验证码"),
        SYS_CORRELATE_KEY("system:correlate:{}.{}", "数据关联工具"),
        SYS_CORRELATE_IMPL_KEY("system:correlate:service:{}", "数据关联工具"),
        DICT_KEY("system:dict:{}", "字典缓存"),
        CONFIG_KEY("system:config:{}", "参数缓存"),
        IM_EX_KEY("system:im_ex:{}", "导入导出配置缓存"),
        SNOWFLAKE_CREATOR_KEY("core:snowflake:creatorId", "核心|雪花生成|标识排序值");

        private final String code;
        private final String info;

        public String getCacheName(Object... cacheNames) {
            return StrUtil.format(getCode(), cacheNames);
        }
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
