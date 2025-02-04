package com.yunque.system.monitor.service;

import com.yunque.common.web.entity.service.IBaseService;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.query.SysLoginLogQuery;

/**
 * 系统服务 | 监控模块 | 访问日志管理 服务层
 *
 * @author xueyi
 */
public interface ISysLoginLogService extends IBaseService<SysLoginLogQuery, SysLoginLogDto> {

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
