package com.yunque.system.api.organize.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.result.R;
import com.yunque.system.api.organize.domain.dto.SysPostDto;
import com.yunque.system.api.organize.feign.factory.RemotePostFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 系统服务 | 组织模块 | 岗位服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remotePostService", path = "/inner/post", value = ServiceConstants.SYSTEM_SERVICE, fallbackFactory = RemotePostFallbackFactory.class)
public interface RemotePostService {

    /**
     * 新增岗位
     *
     * @param post         岗位对象
     * @param enterpriseId 企业Id
     * @param sourceName   策略源
     * @return 结果
     */
    @PostMapping(value = "/add", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysPostDto> addInner(@RequestBody SysPostDto post, @RequestHeader(SecurityConstants.ENTERPRISE_ID) Long enterpriseId, @RequestHeader(SecurityConstants.SOURCE_NAME) String sourceName);
}