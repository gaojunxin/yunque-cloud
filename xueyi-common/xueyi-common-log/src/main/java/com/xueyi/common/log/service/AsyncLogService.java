package com.xueyi.common.log.service;

import com.xueyi.common.core.context.SecurityContextHolder;
import com.xueyi.system.api.log.domain.dto.SysOperateLogDto;
import com.xueyi.system.api.log.feign.RemoteLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author xueyi
 */
@Slf4j
@Service
public class AsyncLogService {

    @Resource
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveOperateLog(SysOperateLogDto operateLog) {
        try {
            remoteLogService.saveOperateLog(operateLog);
        } catch (Exception e) {
            log.error("操作日志记录异常", e);
        }
    }
}