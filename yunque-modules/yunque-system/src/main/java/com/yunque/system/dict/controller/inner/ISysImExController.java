package com.yunque.system.dict.controller.inner;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.web.result.R;
import com.yunque.common.log.annotation.Log;
import com.yunque.common.log.enums.BusinessType;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.system.dict.controller.base.BSysImExController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入导出配置管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/imExConfig")
public class ISysImExController extends BSysImExController {

    @InnerAuth(isAnonymous = true)
    @GetMapping(value = "/sync")
    @Operation(summary = "同步配置缓存 | 租户数据")
    public R<Boolean> syncCacheInner() {
        return R.ok(baseService.syncCache());
    }

    @Override
    @GetMapping("/refresh")
    @InnerAuth(isAnonymous = true)
    @Operation(summary = "刷新配置缓存 | 租户数据")
    @Log(title = "配置管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }

    @InnerAuth(isAnonymous = true)
    @GetMapping("/common/refresh")
    @Operation(summary = "刷新配置缓存 | 默认数据")
    @Log(title = "导入导出配置管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCommonCacheInner() {
        return super.refreshCacheInner();
    }
}
