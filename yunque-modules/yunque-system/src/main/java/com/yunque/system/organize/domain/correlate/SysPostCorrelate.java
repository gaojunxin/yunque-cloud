package com.yunque.system.organize.domain.correlate;

import com.yunque.common.web.correlate.domain.BaseCorrelate;
import com.yunque.common.web.correlate.domain.Direct;
import com.yunque.common.web.correlate.domain.Indirect;
import com.yunque.common.web.correlate.service.CorrelateService;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.organize.domain.merge.SysOrganizeRoleMerge;
import com.yunque.system.organize.domain.merge.SysRolePostMerge;
import com.yunque.system.organize.domain.merge.SysUserPostMerge;
import com.yunque.system.organize.mapper.merge.SysOrganizeRoleMergeMapper;
import com.yunque.system.organize.mapper.merge.SysRolePostMergeMapper;
import com.yunque.system.organize.mapper.merge.SysUserPostMergeMapper;
import com.yunque.system.organize.service.ISysDeptService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.DELETE;
import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.EDIT;
import static com.yunque.common.web.correlate.contant.CorrelateConstants.SubOperate.SELECT;

/**
 * 系统服务 | 组织模块 | 岗位 关联映射
 */
@Getter
@AllArgsConstructor
public enum SysPostCorrelate implements CorrelateService {

    ROLE_SEL("角色组查询|关联（组织-角色）", new ArrayList<>() {{
        // 岗位 | 组织-角色
        add(new Indirect<>(SELECT, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getPostId, SysOrganizeRoleMerge::getRoleId, SysPostDto::getId, SysPostDto::getRoleIds));
    }}),
    ROLE_EDIT("角色组查询|关联（组织-角色）", new ArrayList<>() {{
        // 岗位 | 组织-角色
        add(new Indirect<>(EDIT, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getPostId, SysOrganizeRoleMerge::getRoleId, SysPostDto::getId, SysPostDto::getRoleIds));
    }}),
    BASE_LIST("默认列表|（归属部门）", new ArrayList<>() {{
        // 岗位 | 部门
        add(new Direct<>(SELECT, ISysDeptService.class, SysPostDto::getDeptId, SysDeptDto::getId, SysPostDto::getDept));
    }}),
    BASE_DEL("默认删除|（岗位）", new ArrayList<>() {{
        // 岗位 | 组织-角色
        add(new Indirect<>(DELETE, SysOrganizeRoleMergeMapper.class, SysOrganizeRoleMerge::getPostId, SysPostDto::getId));
        // 岗位 | 角色-岗位
        add(new Indirect<>(DELETE, SysRolePostMergeMapper.class, SysRolePostMerge::getPostId, SysPostDto::getId));
        // 岗位 | 岗位-用户
        add(new Indirect<>(DELETE, SysUserPostMergeMapper.class, SysUserPostMerge::getPostId, SysPostDto::getId));
    }});

    private final String info;
    private final List<? extends BaseCorrelate<?>> correlates;

}