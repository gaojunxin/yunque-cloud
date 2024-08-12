package com.xueyi.common.datasource.processor;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.datasource.annotation.Isolate;
import com.xueyi.common.datasource.utils.DSUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.xueyi.common.core.constant.basic.TenantConstants.ISOLATE;

/**
 * 租户库源策略
 *
 * @author xueyi
 */
@Component
public class DsIsolateExpressionProcessor extends DsProcessor {

    @Override
    public boolean matches(String key) {
        return key.startsWith(ISOLATE);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        return Optional.ofNullable(invocation.getThis()).map(Object::getClass)
                .map(clazz -> AnnotationUtils.findAnnotation(clazz, Isolate.class))
                .filter(ObjectUtil::isNotNull).map(DSUtil::loadDs).orElse(null);
    }
}