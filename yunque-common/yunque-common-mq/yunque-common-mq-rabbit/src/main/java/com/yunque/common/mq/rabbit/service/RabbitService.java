package com.yunque.common.mq.rabbit.service;

/**
 * RabbitMQ消息 工具类
 *
 * @author xueyi
 **/
public interface RabbitService {

    /**
     * 发送消息
     *
     * @param routingKey 路由键
     * @param object     消息内容
     */
    void convertAndSend(String routingKey, Object object);

    /**
     * 发送消息
     *
     * @param exchangeName 交换机名称
     * @param routingKey   路由键
     * @param object       消息内容
     */
    void convertAndSend(String exchangeName, String routingKey, Object object);

    /**
     * 发送延时消息
     * <p>
     * 注意：
     * 1.队列已创建
     * 2.路由队列是否为延时队列
     *
     * @param exchangeName   交换机名称
     * @param routingKey     路由键
     * @param object         消息内容
     * @param delayInSeconds 延时时间（单位/秒）
     */
    default void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInSeconds) {
        convertAndSendDelayed(exchangeName, routingKey, object, delayInSeconds, 10);
    }

    /**
     * 发送延时消息
     * <p>
     * 注意：
     * 1.队列已创建
     * 2.路由队列是否为延时队列
     *
     * @param exchangeName      交换机名称
     * @param routingKey        路由键
     * @param object            消息内容
     * @param delayInSeconds    延时时间（单位/秒）
     * @param expirationSeconds 消息有效时间（单位/秒）
     */
    void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInSeconds, long expirationSeconds);
}
