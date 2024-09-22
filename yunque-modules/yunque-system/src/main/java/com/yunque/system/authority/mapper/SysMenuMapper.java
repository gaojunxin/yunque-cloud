package com.yunque.system.authority.mapper;

import com.yunque.common.web.entity.mapper.TreeMapper;
import com.yunque.system.api.authority.domain.dto.SysMenuDto;
import com.yunque.system.api.authority.domain.po.SysMenuPo;
import com.yunque.system.api.authority.domain.query.SysMenuQuery;

/**
 * 系统服务 | 权限模块 | 菜单管理 数据层
 *
 * @author xueyi
 */
public interface SysMenuMapper extends TreeMapper<SysMenuQuery, SysMenuDto, SysMenuPo> {
}
