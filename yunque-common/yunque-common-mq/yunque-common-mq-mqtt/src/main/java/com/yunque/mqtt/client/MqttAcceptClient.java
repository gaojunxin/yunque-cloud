package com.yunque.mqtt.client;

import cn.hutool.extra.spring.SpringUtil;
import com.yunque.mqtt.basicClient.MqttBaseAcceptClient;
import com.yunque.mqtt.client.callback.MqttAcceptCallback;
import com.yunque.mqtt.config.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MQTT接受服务的客户端 | 默认
 *
 * @author xueyi
 */
@Slf4j
@Component
public class MqttAcceptClient extends MqttBaseAcceptClient<MqttAcceptCallback, MqttProperties> {

    @Override
    protected MqttAcceptCallback setCallback() {
        return super.setCallback(SpringUtil.getBean(MqttAcceptCallback.class));
    }

    @Override
    protected MqttProperties setProperties() {
        return super.setProperties(SpringUtil.getBean(MqttProperties.class));
    }

    /**
     * 处理优先级 | 从大到小
     *
     * @return 优先级
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
