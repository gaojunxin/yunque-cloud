package com.yunque.system.monitor.service.impl;

import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.domain.query.SysOperateLogQuery;
import com.yunque.system.monitor.domain.correlate.SysOperateLogCorrelate;
import com.yunque.system.monitor.manager.ISysOperateLogManager;
import com.yunque.system.monitor.service.ISysOperateLogService;
import org.springframework.stereotype.Service;

/**
 * 系统服务 | 监控模块 | 操作日志管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysOperateLogServiceImpl extends BaseServiceImpl<SysOperateLogQuery, SysOperateLogDto, SysOperateLogCorrelate, ISysOperateLogManager> implements ISysOperateLogService {

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperateLog() {
        baseManager.cleanOperateLog();
    }
}
