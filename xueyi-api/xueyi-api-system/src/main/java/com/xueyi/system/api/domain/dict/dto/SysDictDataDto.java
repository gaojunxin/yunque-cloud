package com.xueyi.system.api.domain.dict.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xueyi.system.api.domain.dict.po.SysDictDataPo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字典数据 数据传输对象
 *
 * @author xueyi
 */
@TableName(value = "sys_dict_data", excludeProperty = {"name", "delFlag"})
public class SysDictDataDto extends SysDictDataPo {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("value", getValue())
                .append("label", getLabel())
                .append("cssClass", getCssClass())
                .append("listClass", getListClass())
                .append("isDefault", getIsDefault())
                .append("status", getStatus())
                .append("sort", getSort())
                .append("createBy", getCreateBy())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}