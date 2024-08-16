package com.xueyi.system.dict.controller.inner;

import com.xueyi.common.core.constant.basic.BaseConstants;
import com.xueyi.common.core.constant.basic.SecurityConstants;
import com.xueyi.common.core.context.SecurityContextHolder;
import com.xueyi.common.core.web.result.R;
import com.xueyi.common.core.web.validate.V_E;
import com.xueyi.common.log.annotation.Log;
import com.xueyi.common.log.enums.BusinessType;
import com.xueyi.common.security.annotation.InnerAuth;
import com.xueyi.system.api.dict.domain.dto.SysConfigDto;
import com.xueyi.system.dict.controller.base.BSysConfigController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务 | 字典模块 | 参数管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/config")
public class ISysConfigController extends BSysConfigController {

    @InnerAuth(isAnonymous = true)
    @GetMapping(value = "/sync")
    @Operation(summary = "同步参数缓存 | 租户数据")
    public R<Boolean> syncCacheInner() {
        return R.ok(baseService.syncCache());
    }

    @Override
    @InnerAuth(isAnonymous = true)
    @GetMapping("/refresh")
    @Operation(summary = "刷新参数缓存 | 租户数据")
    @Log(title = "参数管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCacheInner() {
        return super.refreshCacheInner();
    }

    @InnerAuth(isAnonymous = true)
    @GetMapping("/common/refresh")
    @Operation(summary = "刷新参数缓存 | 默认数据")
    @Log(title = "参数管理", businessType = BusinessType.REFRESH)
    public R<Boolean> refreshCommonCacheInner() {
        SecurityContextHolder.setEnterpriseId(SecurityConstants.COMMON_TENANT_ID.toString());
        return super.refreshCacheInner();
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "查询参数对象")
    public R<SysConfigDto> getConfigByCodeInner(@PathVariable("code") String code) {
        return R.ok(baseService.selectConfigByCode(code));
    }

    @PutMapping
    @Operation(summary = "参数修改")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    public R<Boolean> editInner(@Validated({V_E.class}) @RequestBody SysConfigDto config) {
        config.initOperate(BaseConstants.Operate.EDIT);
        AEHandle(config.getOperate(), config);
        return R.success(baseService.update(config));
    }
}
