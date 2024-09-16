package com.xueyi.common.core.utils.core;

import java.util.Map;

/**
 * Map工具类
 *
 * @author xueyi
 */
public class MapUtil extends cn.hutool.core.map.MapUtil {

    /**
     * 非空判断
     *
     * @param map Map
     * @return 结果
     */
    public static boolean isNotNull(Map<?, ?> map) {
        return !isNull(map);
    }

    /**
     * 空判断
     *
     * @param map Map
     * @return 结果
     */
    public static boolean isNull(Map<?, ?> map) {
        return null == map;
    }

    /**
     * 是否不存在指定键判断
     *
     * @param map Map
     * @return 结果
     */
    public static <T> boolean notContainsKey(Map<T, ?> map, T key) {
        return !containsKey(map, key);
    }

    /**
     * 是否存在指定键判断
     *
     * @param map Map
     * @return 结果
     */
    public static <T> boolean containsKey(Map<T, ?> map, T key) {
        return isNotEmpty(map) && map.containsKey(key);
    }
}