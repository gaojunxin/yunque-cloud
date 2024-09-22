package com.yunque.system.monitor.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.po.SysLoginLogPo;
import com.yunque.system.api.log.domain.query.SysLoginLogQuery;

/**
 * 系统服务 | 监控模块 | 访问日志管理 数据层
 *
 * @author xueyi
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLogQuery, SysLoginLogDto, SysLoginLogPo> {
}
