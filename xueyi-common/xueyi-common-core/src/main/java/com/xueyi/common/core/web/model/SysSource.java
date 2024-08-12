package com.xueyi.common.core.web.model;

import com.alibaba.fastjson2.JSONObject;
import com.xueyi.common.core.web.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 源策略 基础数据对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysSource extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 源策略组Id */
    protected Long strategyId;

    /** 数据源Id */
    protected  Long sourceId;

    /** 主数据源 */
    protected String master;

    /** 策略组类型配置信息 */
    protected JSONObject sourceTypeInfo;

}