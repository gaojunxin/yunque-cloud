package com.yunque.job.service;

import com.yunque.common.web.entity.service.IBaseService;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.query.SysJobLogQuery;

/**
 * 调度日志管理 服务层
 *
 * @author xueyi
 */
public interface ISysJobLogService extends IBaseService<SysJobLogQuery, SysJobLogDto> {

    /**
     * 清空任务日志
     */
    public void cleanLog();
}
