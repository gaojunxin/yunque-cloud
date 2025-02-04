package com.yunque.system.api.log.feign.factory;

import com.yunque.common.core.web.result.R;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.feign.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务 | 监控模块 | 日志服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService> {

    @Override
    public RemoteLogService create(Throwable throwable) {
        log.error("日志服务调用失败:", throwable);
        return new RemoteLogService() {
            @Override
            public R<Boolean> saveOperateLog(SysOperateLogDto operateLog) {
                return R.fail("保存操作日志失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> saveLoginInfo(SysLoginLogDto loginInfo) {
                return R.fail("保存登录日志失败:" + throwable.getMessage());
            }
        };
    }
}