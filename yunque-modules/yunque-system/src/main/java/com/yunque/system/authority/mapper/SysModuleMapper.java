package com.yunque.system.authority.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.authority.domain.dto.SysModuleDto;
import com.yunque.system.api.authority.domain.po.SysModulePo;
import com.yunque.system.api.authority.domain.query.SysModuleQuery;

/**
 * 系统服务 | 权限模块 | 角色管理 数据层
 *
 * @author xueyi
 */
public interface SysModuleMapper extends BaseMapper<SysModuleQuery, SysModuleDto, SysModulePo> {
}
