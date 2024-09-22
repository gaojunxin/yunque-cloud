package com.yunque.job.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.domain.query.SysJobQuery;

import java.util.List;

/**
 * 调度任务管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysJobManager extends IBaseManager<SysJobQuery, SysJobDto> {

    /**
     * 项目启动时
     */
    List<SysJobDto> initScheduler();
}
