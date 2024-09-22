package com.yunque.job.domain.model;

import com.yunque.common.core.web.entity.model.BaseConverter;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.domain.po.SysJobPo;
import com.yunque.job.api.domain.query.SysJobQuery;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 调度任务 对象映射器
 *
 * @author xueyi
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobConverter extends BaseConverter<SysJobQuery, SysJobDto, SysJobPo> {
}
