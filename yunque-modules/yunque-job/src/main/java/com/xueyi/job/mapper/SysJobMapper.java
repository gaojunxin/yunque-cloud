package com.xueyi.job.mapper;

import com.xueyi.common.web.entity.mapper.BaseMapper;
import com.xueyi.job.api.domain.dto.SysJobDto;
import com.xueyi.job.api.domain.po.SysJobPo;
import com.xueyi.job.api.domain.query.SysJobQuery;

/**
 * 调度任务管理 数据层
 *
 * @author xueyi
 */
public interface SysJobMapper extends BaseMapper<SysJobQuery, SysJobDto, SysJobPo> {
}
