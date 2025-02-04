package com.yunque.system.authority.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.authority.domain.dto.SysClientDto;
import com.yunque.system.api.authority.domain.po.SysClientPo;
import com.yunque.system.api.authority.domain.query.SysClientQuery;

/**
 * 系统服务 | 权限模块 | 客户端管理 数据层
 *
 * @author xueyi
 */
public interface SysClientMapper extends BaseMapper<SysClientQuery, SysClientDto, SysClientPo> {
}
