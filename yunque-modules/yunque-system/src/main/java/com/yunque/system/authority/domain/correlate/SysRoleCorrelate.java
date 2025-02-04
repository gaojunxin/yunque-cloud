package com.yunque.system.authority.domain.correlate;

import com.yunque.common.web.correlate.domain.BaseCorrelate;
import com.yunque.common.web.correlate.domain.Indirect;
import com.yunque.common.web.correlate.service.CorrelateService;
import com.yunque.system.api.authority.domain.dto.SysRoleDto;
import com.yunque.system.authority.domain.merge.SysRoleMenuMerge;
import com.yunque.system.authority.domain.merge.SysRoleModuleMerge;
import com.yunque.system.authority.mapper.merge.SysRoleMenuMergeMapper;
import com.yunque.system.authority.mapper.merge.SysRoleModuleMergeMapper;
import com.yunque.system.organize.domain.merge.SysOrganizeRoleMerge;
import com.yunque.system.organize.domain.merge.SysRoleDeptMerge;
import com.yunque.system.organize.domain.merge.SysRolePostMerge;
import com.yunque.system.organize.mapper.merge.SysOrganizeRoleMergeMapper;
import com.yunque.system.organize.mapper.merge.SysRoleDeptMergeMapper;
import com.yunque.system.organize.mapper.merge.SysRolePostMergeMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.ADD;
import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.DELETE;
import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.EDIT;
import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

/**
 * 系统服务 | 权限模块 | 角色 关联映射
 *
 * @author xueyi
 */
@Getter
@AllArgsConstructor
public enum SysRoleCorrelate implements CorrelateService {

    AUTH_SEL("功能权限查询|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-模块关联
        add(new Indirect<>(SELECT, SysRoleModuleMergeMapper.class, SysRoleModuleMerge::getRoleId, SysRoleModuleMerge::getModuleId, SysRoleDto::getId, SysRoleDto::getModuleIds));
        // 角色 | 角色-菜单关联
        add(new Indirect<>(SELECT, SysRoleMenuMergeMapper.class, SysRoleMenuMerge::getRoleId, SysRoleMenuMerge::getMenuId, SysRoleDto::getId, SysRoleDto::getMenuIds));
    }}),
    AUTH_EDIT("功能权限更新|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-模块关联
        add(new Indirect<>(EDIT, SysRoleModuleMergeMapper.class, SysRoleModuleMerge::getRoleId, SysRoleModuleMerge::getModuleId, SysRoleDto::getId, SysRoleDto::getModuleIds));
        // 角色 | 角色-菜单关联
        add(new Indirect<>(EDIT, SysRoleMenuMergeMapper.class, SysRoleMenuMerge::getRoleId, SysRoleMenuMerge::getMenuId, SysRoleDto::getId, SysRoleDto::getMenuIds));
    }}),

    DATA_SEL("数据权限查询|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-部门关联（权限范围）
        add(new Indirect<>(SELECT, SysRoleDeptMergeMapper.class, SysRoleDeptMerge::getRoleId, SysRoleDeptMerge::getDeptId, SysRoleDto::getId, SysRoleDto::getOrgDeptIds));
        // 角色 | 角色-岗位关联（权限范围）
        add(new Indirect<>(SELECT, SysRolePostMergeMapper.class, SysRolePostMerge::getRoleId, SysRolePostMerge::getPostId, SysRoleDto::getId, SysRoleDto::getOrgPostIds));
    }}),
    DATA_EDIT("数据权限更新|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-部门关联（权限范围）
        add(new Indirect<>(EDIT, SysRoleDeptMergeMapper.class, SysRoleDeptMerge::getRoleId, SysRoleDeptMerge::getDeptId, SysRoleDto::getId, SysRoleDto::getOrgDeptIds));
        // 角色 | 角色-岗位关联（权限范围）
        add(new Indirect<>(EDIT, SysRolePostMergeMapper.class, SysRolePostMerge::getRoleId, SysRolePostMerge::getPostId, SysRoleDto::getId, SysRoleDto::getOrgPostIds));
    }}),

    BASE_ADD("默认新增|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-模块关联
        add(new Indirect<>(ADD, SysRoleModuleMergeMapper.class, SysRoleModuleMerge::getRoleId, SysRoleModuleMerge::getModuleId, SysRoleDto::getId, SysRoleDto::getModuleIds));
        // 角色 | 角色-菜单关联
        add(new Indirect<>(ADD, SysRoleMenuMergeMapper.class, SysRoleMenuMerge::getRoleId, SysRoleMenuMerge::getMenuId, SysRoleDto::getId, SysRoleDto::getMenuIds));
        // 角色 | 角色-部门关联（权限范围）
        add(new Indirect<>(ADD, SysRoleDeptMergeMapper.class, SysRoleDeptMerge::getRoleId, SysRoleDeptMerge::getDeptId, SysRoleDto::getId, SysRoleDto::getOrgDeptIds));
        // 角色 | 角色-岗位关联（权限范围）
        add(new Indirect<>(ADD, SysRolePostMergeMapper.class, SysRolePostMerge::getRoleId, SysRolePostMerge::getPostId, SysRoleDto::getId, SysRoleDto::getOrgPostIds));
    }}),
    BASE_DEL("默认删除|关联（角色）", new ArrayList<>() {{
        // 角色 | 角色-模块关联
        add(new Indirect<>(DELETE, SysRoleModuleMergeMapper.class, SysRoleModuleMerge::getRoleId, SysRoleDto::getId));
        // 角色 | 角色-菜单关联
        add(new Indirect<>(DELETE, SysRoleMenuMergeMapper.class, SysRoleMenuMerge::getRoleId, SysRoleDto::getId));
        // 角色 | 角色-部门关联（权限范围）
        add(new Indirect<>(DELETE, SysRoleDeptMergeMapper.class, SysRoleDeptMerge::getRoleId, SysRoleDto::getId));
        // 角色 | 角色-岗位关联（权限范围）
        add(new Indirect<>(DELETE, SysRolePostMergeMapper.class, SysRolePostMerge::getRoleId, SysRoleDto::getId));
        // 角色 | 组织-角色关联（角色绑定）
        add(new Indirect<>(DELETE, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getRoleId, SysRoleDto::getId));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}