package com.yunque.system.monitor.service.impl;

import com.yunque.common.web.entity.service.impl.BaseServiceImpl;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.query.SysLoginLogQuery;
import com.yunque.system.monitor.domain.correlate.SysLoginLogCorrelate;
import com.yunque.system.monitor.manager.ISysLoginLogManager;
import com.yunque.system.monitor.service.ISysLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 系统服务 | 监控模块 | 访问日志管理 服务层处理
 *
 * @author xueyi
 */
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogQuery, SysLoginLogDto, SysLoginLogCorrelate, ISysLoginLogManager> implements ISysLoginLogService {

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginLog() {
        baseManager.cleanLoginLog();
    }
}
