package com.xueyi.common.cache.model;

import com.xueyi.common.core.utils.cache.CacheUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 缓存定义 基础数据对象
 *
 * @author xueyi
 */
@Data
@NoArgsConstructor
public class CacheModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 缓存键名 */
    private String code;

    /** 是否租户为租户数据 */
    private Boolean isTenant = Boolean.FALSE;

    public CacheModel(String code) {
        this.code = code;
    }

    public CacheModel(String code, Boolean isTenant) {
        this.code = code;
        this.isTenant = isTenant;
    }

    /**
     * 获取缓存键值
     *
     * @return 缓存键值
     */
    public String getCacheKey() {
        return CacheUtil.getCusCacheKey(getCode(), getIsTenant());
    }

    /**
     * 获取缓存键值 | 指定企业Id
     *
     * @param enterpriseId 企业Id
     * @return 缓存键值
     */
    public String getCacheKey(Long enterpriseId) {
        return CacheUtil.getCusCacheKey(getCode(), getIsTenant(), enterpriseId);
    }
}
