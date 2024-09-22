package com.yunque.mqtt.client.callback;

import cn.hutool.extra.spring.SpringUtil;
import com.yunque.mqtt.basicClient.callback.MqttBaseAcceptCallback;
import com.yunque.mqtt.client.MqttAcceptClient;
import com.yunque.mqtt.config.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MQTT接受服务的回调 | 默认
 *
 * @author xueyi
 */
@Slf4j
@Component
public class MqttAcceptCallback extends MqttBaseAcceptCallback<MqttAcceptClient, MqttProperties> {

    @Override
    protected MqttAcceptClient setClient() {
        return super.setClient(SpringUtil.getBean(MqttAcceptClient.class));
    }

    @Override
    protected MqttProperties setProperties() {
        return super.setProperties(SpringUtil.getBean(MqttProperties.class));
    }
}
