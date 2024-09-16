package com.xueyi.common.mq.rabbit.service.impl;

import com.xueyi.common.mq.rabbit.config.properties.RabbitProperties;
import com.xueyi.common.mq.rabbit.service.RabbitService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * RabbitMQ消息 工具类
 *
 * @author xueyi
 **/
@Slf4j
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RabbitServiceImpl implements RabbitService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RabbitProperties rabbitProperties;

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    @Bean("rabbitMQEvn")
    public String rabbitMQEvn() {
        return getEvnPrefix();
    }

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    private String getEvnPrefix() {
        return rabbitProperties.getEvnPrefix();
    }

    /**
     * 获取系统环境前缀
     *
     * @return 结果
     */
    private String getEvnPrefix(String key) {
        return getEvnPrefix() + key;
    }

    /**
     * 发送消息
     *
     * @param routingKey 路由键
     * @param object     消息内容
     */
    @Override
    @SneakyThrows
    public void convertAndSend(String routingKey, Object object) {
        rabbitTemplate.convertAndSend(getEvnPrefix(routingKey), object);
    }

    /**
     * 发送消息
     *
     * @param exchangeName 交换机名称
     * @param routingKey   路由键
     * @param object       消息内容
     */
    @Override
    @SneakyThrows
    public void convertAndSend(String exchangeName, String routingKey, Object object) {
        rabbitTemplate.convertAndSend(getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object);
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
    @Override
    @SneakyThrows
    public void convertAndSendDelayed(String exchangeName, String routingKey, Object object, long delayInSeconds, long expirationSeconds) {
        log.info("发送延时消息：交换机名称：{}，路由键：{}，消息内容：{}，延时时间：{}秒", getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object, delayInSeconds);
        rabbitTemplate.convertAndSend(getEvnPrefix(exchangeName), getEvnPrefix(routingKey), object,
                message -> {
                    // 设置延时时间（毫秒）
                    message.getMessageProperties().setDelayLong(delayInSeconds * 1000);
                    // 设置消息持续时间（毫秒）
                    message.getMessageProperties().setExpiration(String.valueOf(expirationSeconds * 1000));
                    return message;
                });
    }
}
