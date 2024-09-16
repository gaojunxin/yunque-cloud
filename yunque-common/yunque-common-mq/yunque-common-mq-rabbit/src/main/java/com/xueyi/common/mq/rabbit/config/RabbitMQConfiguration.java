package com.xueyi.common.mq.rabbit.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xueyi.common.core.exception.UtilException;
import com.xueyi.common.core.utils.core.CollUtil;
import com.xueyi.common.core.utils.core.MapUtil;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import com.xueyi.common.mq.rabbit.config.properties.RabbitProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Optional;

/**
 * RabbitMQ 配置类
 *
 * @author xueyi
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
public class RabbitMQConfiguration {

    @Resource
    private RabbitProperties rabbitProperties;

    /**
     * RabbitMQ连接初始化
     */
    @Bean
    public void rabbitMQConnectionFactory() {
        Optional.ofNullable(rabbitProperties).map(RabbitProperties::getExchangeInfos).filter(CollUtil::isNotEmpty).ifPresent(list -> {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            list.forEach(item -> {
                try {
                    String host = StrUtil.isNotBlank(item.getHost()) ? item.getHost() : rabbitProperties.getHost();
                    Integer port = ObjectUtil.isNotNull(item.getPort()) ? item.getPort() : rabbitProperties.getPort();
                    String username = StrUtil.isNotBlank(item.getUsername()) ? item.getUsername() : rabbitProperties.getUsername();
                    String password = StrUtil.isNotBlank(item.getPassword()) ? item.getPassword() : rabbitProperties.getPassword();
                    if (StrUtil.isBlank(host)) {
                        throw new UtilException("地址（host）为空");
                    } else if (StrUtil.isBlank(item.getType())) {
                        throw new UtilException("交换机类型（type）为空");
                    } else if (StrUtil.isBlank(item.getName())) {
                        throw new UtilException("交换机名称（name）为空");
                    }
                    String evnName = rabbitProperties.getEvnPrefix();
                    // 设置RabbitMQ服务器信息
                    factory.setHost(host);
                    factory.setPort(port);
                    factory.setUsername(username);
                    factory.setPassword(password);
                    // 创建一个新的连接
                    Connection connection = factory.newConnection();
                    // 创建一个通道
                    Channel channel = connection.createChannel();

                    try (connection; channel) {
                        // 声明一个Exchange
                        String exchangeName = StrUtil.format("{}{}", evnName, item.getName());
                        Map<String, Object> exchangeArguments = MapUtil.isNotEmpty(item.getParams()) ? item.getParams() : null;
                        channel.exchangeDeclare(exchangeName, item.getType(), item.getDurable(), item.getAutoDelete(), item.getExclusive(), exchangeArguments);
                        Optional.ofNullable(item.getQueueInfos()).filter(CollUtil::isNotEmpty).ifPresent(queueInfos -> queueInfos.forEach(queueInfo -> {
                            try {
                                // 声明一个Queue
                                String queueName = StrUtil.format("{}{}", evnName, queueInfo.getName());
                                Map<String, Object> arguments = MapUtil.isNotEmpty(queueInfo.getParams()) ? queueInfo.getParams() : null;
                                channel.queueDeclare(queueName, queueInfo.getDurable(), queueInfo.getExclusive(), queueInfo.getAutoDelete(), arguments);
                                // 将Queue绑定到Exchange
                                String routingKeyName = StrUtil.format("{}{}", evnName, queueInfo.getRoutingKey());
                                channel.queueBind(queueName, exchangeName, routingKeyName);
                            } catch (Exception e) {
                                log.error("RabbitMQ连接创建失败，原因：{}\n", e.getMessage(), e);
                            }
                        }));
                    }
                } catch (Exception e) {
                    log.error("RabbitMQ连接创建失败，原因：{}\n", e.getMessage(), e);
                }
            });
        });
    }

    /**
     * 消息序列化消息
     */
    @Bean
    public MessageConverter createMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
