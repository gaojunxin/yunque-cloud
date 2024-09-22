package com.yunque.system.monitor.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.query.SysLoginLogQuery;

/**
 * 系统服务 | 监控模块 | 访问日志管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysLoginLogManager extends IBaseManager<SysLoginLogQuery, SysLoginLogDto> {

    /**
     * 清空系统登录日志
     */
    void cleanLoginLog();
}
