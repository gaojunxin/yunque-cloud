package com.yunque.system.authority.domain.merge;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunque.common.core.web.entity.base.BasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 系统服务 | 权限模块 | 企业权限组和模块关联 持久化对象
 *
 * @author xueyi
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_auth_group_module_merge")
public class SysAuthGroupModuleMerge extends BasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId
    protected Long id;

    /** 模块Id */
    protected Long moduleId;

    /** 企业权限组Id */
    protected Long authGroupId;

}