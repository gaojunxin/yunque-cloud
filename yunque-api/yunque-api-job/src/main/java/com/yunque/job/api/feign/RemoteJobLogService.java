package com.yunque.job.api.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.result.R;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.feign.factory.RemoteJobLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 调度日志服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteJobLogService", path = "/inner/job/log", value = ServiceConstants.JOB_SERVICE, fallbackFactory = RemoteJobLogFallbackFactory.class)
public interface RemoteJobLogService {

    /**
     * 保存调度日志
     *
     * @param jobLog       调度日志实体
     * @return 结果
     */

    @PostMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveJobLog(@RequestBody SysJobLogDto jobLog);
}