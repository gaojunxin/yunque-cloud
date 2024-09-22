package com.yunque.system.organize.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.domain.po.SysPostPo;
import com.yunque.system.api.organize.domain.query.SysPostQuery;

/**
 * 系统服务 | 组织模块 | 岗位管理 数据层
 *
 * @author xueyi
 */
public interface SysPostMapper extends BaseMapper<SysPostQuery, SysPostDto, SysPostPo> {
}
