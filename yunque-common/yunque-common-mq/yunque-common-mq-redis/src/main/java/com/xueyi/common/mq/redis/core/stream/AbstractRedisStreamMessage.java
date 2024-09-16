package com.xueyi.common.mq.redis.core.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xueyi.common.mq.redis.core.message.AbstractRedisMessage;

/**
 * Redis Stream Message 抽象类
 *
 * @author xueyi
 */
public abstract class AbstractRedisStreamMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Stream Key，默认使用类名
     */
    @JsonIgnore // 避免序列化
    public String getStreamKey() {
        return getClass().getSimpleName();
    }

}
