package com.yunque.system.api.dict.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.feign.RemoteCacheService;
import com.yunque.common.core.web.result.R;
import com.yunque.system.api.dict.domain.dto.SysConfigDto;
import com.yunque.system.api.dict.feign.factory.RemoteConfigFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务 | 字典模块 | 参数服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteConfigService", path = "/inner/config", value = ServiceConstants.SYSTEM_SERVICE, fallbackFactory = RemoteConfigFallbackFactory.class)
public interface RemoteConfigService extends RemoteCacheService {

    /**
     * 同步参数缓存 | 租户数据
     *
     * @return 结果
     */
    @GetMapping(value = "/sync", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> syncCacheInner();

    /**
     * 刷新参数缓存 | 默认数据
     *
     * @return 结果
     */
    @GetMapping(value = "/common/refresh", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> refreshCommonCacheInner();

    /**
     * 查询参数详情
     *
     * @param code 参数编码
     * @return 参数对象
     */
    @GetMapping(value = "/code/{code}", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<SysConfigDto> getInfoInner(@PathVariable("code") String code);

    /**
     * 修改参数缓存
     *
     * @param config 参数对象
     * @return 结果
     */
    @PutMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> editInner(@RequestBody SysConfigDto config);
}