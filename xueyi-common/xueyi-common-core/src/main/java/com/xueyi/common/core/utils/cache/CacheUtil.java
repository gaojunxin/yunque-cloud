package com.xueyi.common.core.utils.cache;

import com.xueyi.common.core.constant.basic.SecurityConstants;
import com.xueyi.common.core.context.SecurityContextHolder;
import com.xueyi.common.core.exception.ServiceException;
import com.xueyi.common.core.exception.UtilException;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;

/**
 * 缓存工具类
 *
 * @author xueyi
 */
public class CacheUtil {

    /**
     * 获取缓存键值 | 自定义
     *
     * @param code 缓存编码
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code) {
        return getCusCacheKey(code, Boolean.FALSE, null);
    }

    /**
     * 获取缓存键值 | 自定义
     *
     * @param code         缓存编码
     * @param isTenant     租户级缓存
     * @param enterpriseId 企业Id
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code, Boolean isTenant, Long enterpriseId) {
        String cacheKey;
        if (isTenant) {
            if (ObjectUtil.isNull(enterpriseId)) {
                throw new ServiceException(StrUtil.format("缓存键{}为企业级缓存，企业Id不能为空", code));
            }
            cacheKey = StrUtil.format("{}:{}", code, enterpriseId);
        } else {
            cacheKey = code;
        }
        return cacheKey;
    }
}
