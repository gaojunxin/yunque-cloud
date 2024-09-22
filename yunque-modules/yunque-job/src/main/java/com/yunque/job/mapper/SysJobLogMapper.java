package com.yunque.job.mapper;

import com.yunque.common.web.entity.mapper.BaseMapper;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.po.SysJobLogPo;
import com.yunque.job.api.domain.query.SysJobLogQuery;


/**
 * 调度日志管理 数据层
 *
 * @author xueyi
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLogQuery, SysJobLogDto, SysJobLogPo> {
}
