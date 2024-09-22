package com.yunque.system.dict.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.domain.po.SysConfigPo;
import com.yunque.system.api.dict.domain.query.SysConfigQuery;

/**
 * 系统服务 | 字典模块 | 参数管理 数据层
 *
 * @author xueyi
 */
public interface SysConfigMapper extends BaseMapper<SysConfigQuery, SysConfigDto, SysConfigPo> {
}