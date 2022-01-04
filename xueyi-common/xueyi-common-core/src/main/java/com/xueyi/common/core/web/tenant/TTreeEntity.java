package com.xueyi.common.core.web.tenant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xueyi.common.core.web.entity.TreeEntity;

/**
 * Tree 租户基类
 *
 * @param <D> Dto
 * @author xueyi
 */
public class TTreeEntity<D> extends TreeEntity<D> {

    private static final long serialVersionUID = 1L;

    /** 租户Id */
    @TableField(value = "tenant_id", select = false)
    private Long enterpriseId;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
