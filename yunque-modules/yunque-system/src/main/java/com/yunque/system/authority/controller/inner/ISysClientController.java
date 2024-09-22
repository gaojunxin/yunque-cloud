package com.yunque.system.authority.controller.inner;

import com.yunque.common.core.web.result.R;
import com.yunque.common.security.annotation.InnerAuth;
import com.yunque.system.api.authority.domain.dto.SysClientDto;
import com.yunque.system.authority.controller.base.BSysClientController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务 | 权限模块 | 客户端管理 | 内部调用 业务处理
 *
 * @author xueyi
 */
@InnerAuth
@RestController
@RequestMapping("/inner/client")
public class ISysClientController extends BSysClientController {

    @GetMapping("/clientId")
    @InnerAuth(isAnonymous = true)
    @Operation(summary = "获取登录信息")
    public R<SysClientDto> getInfoByClientIdInner(@RequestParam String clientId) {
        return R.ok(baseService.selectByClientId(clientId));
    }
}
