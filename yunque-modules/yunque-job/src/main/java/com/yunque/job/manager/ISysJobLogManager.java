package com.yunque.job.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.query.SysJobLogQuery;

/**
 * 调度任务日志管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysJobLogManager extends IBaseManager<SysJobLogQuery, SysJobLogDto> {

    /**
     * 清空任务日志
     */
    void cleanLog();
}
