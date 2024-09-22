package com.yunque.system.authority.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.authority.domain.dto.SysRoleDto;
import com.yunque.system.api.authority.domain.po.SysRolePo;
import com.yunque.system.api.authority.domain.query.SysRoleQuery;

/**
 * 岗位管理 数据层
 *
 * @author xueyi
 */
public interface SysRoleMapper extends BaseMapper<SysRoleQuery, SysRoleDto, SysRolePo> {
}
