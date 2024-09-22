package com.yunque.mqtt.basicClient;

import com.yunque.mqtt.basicClient.callback.MqttBaseSendCallBack;
import com.yunque.mqtt.config.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.core.Ordered;

import java.util.Optional;
import java.util.UUID;

/**
 * MQTT发送客户端 | 基类
 *
 * @author xueyi
 */
@Slf4j
public abstract class MqttBaseSendClient<Callback extends MqttBaseSendCallBack, Properties extends MqttProperties> implements Ordered {

    protected Callback callback;

    protected Properties properties;

    protected Callback getCallback() {
        return Optional.ofNullable(callback).orElse(setCallback());
    }

    protected Properties getProperties() {
        return Optional.ofNullable(properties).orElse(setProperties());
    }

    protected abstract Callback setCallback();

    protected abstract Properties setProperties();

    protected Callback setCallback(Callback callback) {
        this.callback = callback;
        return this.callback;
    }

    protected Properties setProperties(Properties properties) {
        this.properties = properties;
        return this.properties;
    }

    /**
     * 关闭连接
     *
     * @param mqttClient 连接信息
     */
    public static void disconnect(MqttClient mqttClient) {
        try {
            if (mqttClient != null)
                mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("MqttSendClient disconnect error,message:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param mqttClient 连接信息
     */
    public static void close(MqttClient mqttClient) {
        try {
            if (mqttClient != null)
                mqttClient.close();
        } catch (MqttException e) {
            log.error("MqttSendClient close error,message:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public MqttClient connect() {
        MqttClient client = null;
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            client = new MqttClient(getProperties().getHostUrl(), uuid, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(getProperties().getUsername());
            options.setPassword(getProperties().getPassword().toCharArray());
            options.setConnectionTimeout(getProperties().getTimeout());
            options.setKeepAliveInterval(getProperties().getKeepAlive());
            options.setCleanSession(true);
            options.setAutomaticReconnect(false);
            // 设置回调
            client.setCallback(getCallback());
            client.connect(options);
        } catch (Exception e) {
            log.error("MqttSendClient connect error,message:{}", e.getMessage());
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 发布消息
     *
     * @param retained 是否保留
     * @param content  消息内容
     */
    public void publish(boolean retained, String content) {
        cusTopicPublish(retained, getProperties().getServerTopic(), content);
    }

    /**
     * 发布消息
     *
     * @param retained 是否保留
     * @param topic    主题，格式： server:${env}:report:${topic}
     * @param content  消息内容
     */
    public void publish(boolean retained, String topic, String content) {
        cusTopicPublish(retained, getProperties().getServerTopic(topic), content);
    }

    /**
     * 自定义主题发布消息
     *
     * @param topic   主题
     * @param content 消息内容
     */
    public void cusTopicPublish(String topic, String content) {
        cusTopicPublish(Boolean.FALSE, topic, content);
    }

    /**
     * 自定义主题发布消息
     *
     * @param retained 是否保留
     * @param topic    主题
     * @param content  消息内容
     */
    public void cusTopicPublish(boolean retained, String topic, String content) {
        MqttMessage message = new MqttMessage();
        message.setQos(getProperties().getQos());
        message.setRetained(retained);
        message.setPayload(content.getBytes());
        MqttDeliveryToken token;
        MqttClient mqttClient = connect();
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            log.error("MqttSendClient publish error,message:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            disconnect(mqttClient);
            close(mqttClient);
        }
    }

    /**
     * 处理优先级 | 从大到小
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
