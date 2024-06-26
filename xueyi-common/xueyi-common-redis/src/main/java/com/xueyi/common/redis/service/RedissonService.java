package com.xueyi.common.redis.service;

import com.xueyi.common.core.exception.UtilException;
import com.xueyi.common.core.utils.core.NumberUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson 工具类
 *
 * @author xueyi
 */
@Getter
@Slf4j
@Component
public class RedissonService {

    @Autowired
    public RedissonClient client;

    public static String lockStr = "lock:";
    /**
     * 限流
     *
     * @param key          限流key
     * @param rateType     限流类型
     * @param rate         速率
     * @param rateInterval 速率间隔
     * @return -1 表示失败
     */
    public long rateLimiter(String key, RateType rateType, int rate, int rateInterval) {
        RRateLimiter rateLimiter = client.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, rate, rateInterval, RateIntervalUnit.SECONDS);
        if (rateLimiter.tryAcquire()) {
            return rateLimiter.availablePermits();
        } else {
            return -1L;
        }
    }

    /**
     * 分布式锁方法 | Supplier
     *
     * @param lockCode 锁编码
     * @param supplier SupplierFun
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier) {
        return lockFun(lockCode, supplier, NumberUtil.Five, NumberUtil.ThreeHundred, TimeUnit.MINUTES);
    }

    /**
     * 指定租户方法执行 | Supplier
     *
     * @param lockCode    锁编码
     * @param supplier    SupplierFun
     * @param executeTime 单次执行时间
     * @param waitTime    最长等待时间
     * @param timeUnit    时间类型
     */
    public <T> T lockFun(String lockCode, Supplier<T> supplier, long executeTime, long waitTime, TimeUnit timeUnit) {
        RLock lock = null;
        try {
            lock = client.getLock(lockStr + lockCode);
            boolean tryLock = lock.tryLock(executeTime, waitTime, timeUnit);
            if (tryLock) {
                return supplier.get();
            }
        } catch (Exception e) {
            log.error("分布式锁Supplier方法执行失败：\n锁编码：{};\n错误原因：{};", lockCode, e.getMessage());
            throw new UtilException(e);
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    /**
     * 分布式锁方法 | Runnable
     *
     * @param lockCode 锁编码
     * @param runnable RunnableFun
     */
    public void lockFun(String lockCode, Runnable runnable) {
        lockFun(lockCode, runnable, NumberUtil.Five, NumberUtil.ThreeHundred, TimeUnit.MINUTES);
    }

    /**
     * 指定租户方法执行 | Runnable
     *
     * @param lockCode    锁编码
     * @param runnable    RunnableFun
     * @param executeTime 单次执行时间
     * @param waitTime    最长等待时间
     * @param timeUnit    时间类型
     */
    public void lockFun(String lockCode, Runnable runnable, long executeTime, long waitTime, TimeUnit timeUnit) {
        RLock lock = null;
        try {
            lock = client.getLock(lockStr + lockCode);
            boolean tryLock = lock.tryLock(executeTime, waitTime, timeUnit);
            if (tryLock) {
                runnable.run();
            }
        } catch (Exception e) {
            log.error("分布式锁Runnable方法执行失败：\n锁编码：{};\n错误原因：{};", lockCode, e.getMessage());
            throw new UtilException(e);
        } finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
