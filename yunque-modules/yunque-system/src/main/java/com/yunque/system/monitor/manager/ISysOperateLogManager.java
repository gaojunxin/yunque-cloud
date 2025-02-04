package com.yunque.system.monitor.manager;

import com.yunque.common.web.entity.manager.IBaseManager;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.domain.query.SysOperateLogQuery;

/**
 * 系统服务 | 监控模块 | 操作日志管理 数据封装层
 *
 * @author xueyi
 */
public interface ISysOperateLogManager extends IBaseManager<SysOperateLogQuery, SysOperateLogDto> {

    /**
     * 清空系统操作日志
     */
    void cleanOperateLog();
}
