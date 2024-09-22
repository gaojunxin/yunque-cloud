package com.yunque.system.monitor.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.domain.po.SysOperateLogPo;
import com.yunque.system.api.log.domain.query.SysOperateLogQuery;

/**
 * 系统服务 | 监控模块 | 操作日志管理 数据层
 *
 * @author xueyi
 */
public interface SysOperateLogMapper extends BaseMapper<SysOperateLogQuery, SysOperateLogDto, SysOperateLogPo> {
}
