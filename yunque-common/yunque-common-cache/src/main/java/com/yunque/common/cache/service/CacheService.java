package com.yunque.common.cache.service;

import cn.hutool.core.map.MapUtil;
import com.yunque.common.core.utils.cache.CacheUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 缓存管理服务
 *
 * @author xueyi
 **/
@Component
public class CacheService {

    @Autowired
    private RedisService redisService;

    /**
     * 获取指定缓存数据对象 | 自定义
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return 数据对象
     */
    public <T> T getCacheObject(String cacheCode, Boolean isTenant, Supplier<Object> consumer) {
        String key = CacheUtil.getCusCacheKey(cacheCode);
        T object = redisService.getCacheObject(key);
        if (ObjectUtil.isNull(object)) {
            refreshCache(consumer);
            return redisService.getCacheObject(key);
        }
        return object;
    }

    /**
     * 获取指定缓存数据对象 | 自定义
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return 数据对象
     */
    public <T> Set<T> getCacheSet(String cacheCode, Boolean isTenant, Supplier<Object> consumer) {
        String key = CacheUtil.getCusCacheKey(cacheCode);
        Set<T> cacheSet = redisService.getCacheSet(key);
        if (ObjectUtil.isNull(cacheSet)) {
            refreshCache(consumer);
            return redisService.getCacheSet(key);
        }
        return cacheSet;
    }

    /**
     * 获取指定缓存数据对象 | 自定义
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return 数据对象
     */
    public <T> List<T> getCacheList(String cacheCode, Boolean isTenant, Supplier<Object> consumer) {
        String key = CacheUtil.getCusCacheKey(cacheCode);
        List<T> cacheSet = redisService.getCacheList(key);
        if (ObjectUtil.isNull(cacheSet)) {
            refreshCache(consumer);
            return redisService.getCacheList(key);
        }
        return cacheSet;
    }

    /**
     * 获取指定缓存数据对象 | 自定义
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @return 数据对象
     */
    public <T> Map<String, T> getCacheMap(String cacheCode, Boolean isTenant, Supplier<Object> consumer) {
        String key = CacheUtil.getCusCacheKey(cacheCode);
        Map<String, T> map = redisService.getCacheMap(key);
        if (MapUtil.isEmpty(map)) {
            refreshCache(consumer);
            return redisService.getCacheMap(key);
        }
        return map;
    }

    /**
     * 获取指定缓存数据对象 | 自定义
     *
     * @param cacheCode 缓存编码
     * @param isTenant  租户级缓存
     * @param consumer  缓存更新方法
     * @param code      缓存编码
     * @return 数据对象
     */
    public <T> T getCacheObject(String cacheCode, Boolean isTenant, Supplier<Object> consumer, String code) {
        String key = CacheUtil.getCusCacheKey(cacheCode);
        T object = redisService.getCacheMapValue(key, code);
        if (ObjectUtil.isNull(object)) {
            refreshCache(consumer);
            return redisService.getCacheMapValue(key, code);
        }
        return object;
    }

    /**
     * 更新指定类型的缓存对象 | 自定义
     *
     * @param consumer 缓存更新方法
     */
    public void refreshCache(Supplier<Object> consumer) {
        consumer.get();
    }
}
