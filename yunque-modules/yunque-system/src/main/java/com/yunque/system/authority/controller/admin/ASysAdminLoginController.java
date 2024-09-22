package com.yunque.system.authority.controller.admin;

import com.yunque.common.security.annotation.AdminAuth;
import com.yunque.common.web.entity.controller.BasisController;
import com.yunque.system.authority.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端登录 | 内部调用 业务处理
 *
 * @author xueyi
 */
@AdminAuth
@RestController
@RequestMapping("/admin/login/admin")
public class ASysAdminLoginController extends BasisController {

    @Autowired
    private ISysLoginService loginService;

}
