package com.yunque.job.service.impl;

import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.domain.query.SysJobLogQuery;
import com.yunque.job.domain.correlate.SysJobLogCorrelate;
import com.yunque.job.manager.ISysJobLogManager;
import com.yunque.job.service.ISysJobLogService;
import org.springframework.stereotype.Service;

/**
 * 调度日志管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysJobLogServiceImpl extends BaseServiceImpl<SysJobLogQuery, SysJobLogDto, SysJobLogCorrelate, ISysJobLogManager> implements ISysJobLogService {

    /**
     * 清空任务日志
     */
    @Override
    public void cleanLog() {
        baseManager.cleanLog();
    }
}
