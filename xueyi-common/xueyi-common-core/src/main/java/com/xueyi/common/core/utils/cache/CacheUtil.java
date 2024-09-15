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
     * @param code         缓存编码
     * @return 缓存键值
     */
    public static String getCusCacheKey(String code) {

        return code;
    }
}
