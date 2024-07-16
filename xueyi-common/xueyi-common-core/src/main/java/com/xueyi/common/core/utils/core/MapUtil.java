package com.xueyi.common.core.utils.core;

import java.util.Map;

/**
 * Map工具类
 *
 * @author xueyi
 */
public class MapUtil extends cn.hutool.core.map.MapUtil {

    public static boolean isNotNull(Map<?, ?> map) {
        return !isNull(map);
    }

    public static boolean isNull(Map<?, ?> map) {
        return null == map;
    }

    public static <T> boolean notContainsKey(Map<T, ?> map, T key) {
        return !containsKey(map, key);
    }

    public static <T> boolean containsKey(Map<T, ?> map, T key) {
        return isNotEmpty(map) && map.containsKey(key);
    }
}