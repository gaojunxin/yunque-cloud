package com.xueyi.mqtt.client;

import cn.hutool.extra.spring.SpringUtil;
import com.xueyi.mqtt.basicClient.MqttBaseSendClient;
import com.xueyi.mqtt.client.callback.MqttSendCallBack;
import com.xueyi.mqtt.config.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MQTT发送客户端 | 默认
 *
 * @author xueyi
 */
@Slf4j
@Component
public class MqttSendClient extends MqttBaseSendClient<MqttSendCallBack, MqttProperties> {

    @Override
    protected MqttSendCallBack setCallback() {
        return super.setCallback(SpringUtil.getBean(MqttSendCallBack.class));
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
