package com.yunque.system.authority.controller.admin;

import com.yunque.common.core.utils.TreeUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.common.web.entity.controller.BasisController;
import com.yunque.system.authority.service.ISysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务 | 权限模块 | 权限管理 | 管理端 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/auth")
public class ASysAuthController extends BasisController {

    @Autowired
    private ISysAuthService authService;

    /**
     * 获取全部公共模块 | 菜单权限树
     */
    @GetMapping(value = "/common/authScope")
    @PreAuthorize("@ss.hasAnyAuthority(@Auth.TE_TENANT_ADD, @Auth.TE_TENANT_AUTH)")
    public AjaxResult getCommonAuthScope() {
        return success(TreeUtil.buildTree(authService.selectCommonAuthScope()));
    }
    
}
