package com.yunque.system.organize.mapper;

import com.yunque.common.web.entity.mapper.TreeMapper;
import com.yunque.system.api.organize.domain.dto.SysDeptDto;
import com.yunque.system.api.organize.domain.po.SysDeptPo;
import com.yunque.system.api.organize.domain.query.SysDeptQuery;

/**
 * 系统服务 | 组织模块 | 部门管理 数据层
 *
 * @author xueyi
 */
public interface SysDeptMapper extends TreeMapper<SysDeptQuery, SysDeptDto, SysDeptPo> {
}
