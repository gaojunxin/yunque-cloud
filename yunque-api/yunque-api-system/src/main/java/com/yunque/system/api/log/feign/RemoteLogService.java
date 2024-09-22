package com.yunque.system.api.log.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.result.R;
import com.yunque.system.api.log.domain.dto.SysLoginLogDto;
import com.yunque.system.api.log.domain.dto.SysOperateLogDto;
import com.yunque.system.api.log.feign.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
/**
 * 系统服务 | 监控模块 | 日志服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteLogService", value = ServiceConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {

    /**
     * 保存系统日志
     *
     * @param operateLog   日志实体
     * @return 结果
     */
    @PostMapping(value = "/inner/operateLog", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveOperateLog(@RequestBody SysOperateLogDto operateLog) throws Exception;

    /**
     * 保存访问记录
     *
     * @param loginInfo    访问实体
     * @return 结果
     */
    @PostMapping(value = "/inner/loginLog", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> saveLoginInfo(@RequestBody SysLoginLogDto loginInfo);
}