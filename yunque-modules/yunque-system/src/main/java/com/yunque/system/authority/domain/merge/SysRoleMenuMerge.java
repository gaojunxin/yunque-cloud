package com.yunque.system.authority.domain.merge;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yunque.common.core.web.tenant.base.TBasisEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 系统服务 | 权限模块 | 角色-菜单关联 持久化对象
 *
 * @author xueyi
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu_merge")
public class SysRoleMenuMerge extends TBasisEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 角色Id */
    private Long roleId;

    /** 菜单Id */
    private Long menuId;

    public SysRoleMenuMerge(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

}
