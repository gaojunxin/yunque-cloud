package com.xueyi.common.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xueyi.common.core.constant.basic.TenantConstants;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.xueyi.common.core.constant.basic.TenantConstants.ISOLATE;

/**
 * 租户策略源
 *
 * @author xueyi
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@DS(ISOLATE)
public @interface Isolate {

    /** 策略组类型 */
    TenantConstants.strategyType strategyType() default TenantConstants.strategyType.DEFAULT;
}