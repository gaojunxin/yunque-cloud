package com.yunque.system.monitor.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.domain.po.SysOperateLogPo;
import com.yunque.system.api.log.domain.query.SysOperateLogQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 系统服务 | 监控模块 | 操作日志 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysOperateLogConverter extends BaseConverter<SysOperateLogQuery, SysOperateLogDto, SysOperateLogPo> {
}
