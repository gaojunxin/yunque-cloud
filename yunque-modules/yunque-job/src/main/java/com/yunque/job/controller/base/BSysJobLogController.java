package com.yunque.job.controller.base;

import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.query.SysJobLogQuery;
import com.yunque.job.service.ISysJobLogService;

/**
 * 定时任务 | 调度日志管理 | 通用 业务处理
 *
 * @author xueyi
 */
public class BSysJobLogController extends BaseController<SysJobLogQuery, SysJobLogDto, ISysJobLogService> {

    /** 定义节点名称 */
    @Override
    protected String getNodeName() {
        return "调度日志";
    }
}
