package com.xueyi.common.core.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * entity Po基类
 *
 * @author xueyi
 */
public class BasisEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据源名称 */
    @TableField(exist = false)
    private String sourceName;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Map<String, Object> getParams() {
        return params == null ? new HashMap<>() : params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
