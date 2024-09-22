package com.yunque.system.organize.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.organize.domain.dto.SysUserDto;
import com.yunque.system.api.organize.domain.po.SysUserPo;
import com.yunque.system.api.organize.domain.query.SysUserQuery;

/**
 * 系统服务 | 组织模块 | 用户管理 数据层
 *
 * @author xueyi
 */
public interface SysUserMapper extends BaseMapper<SysUserQuery, SysUserDto, SysUserPo> {
}
