package com.yunque.job.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.domain.po.SysJobPo;
import com.yunque.job.api.domain.query.SysJobQuery;

/**
 * 调度任务管理 数据层
 *
 * @author xueyi
 */
public interface SysJobMapper extends BaseMapper<SysJobQuery, SysJobDto, SysJobPo> {
}
