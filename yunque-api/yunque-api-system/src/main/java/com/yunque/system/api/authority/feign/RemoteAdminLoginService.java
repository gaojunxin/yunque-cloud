package com.yunque.system.api.authority.feign;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.ServiceConstants;
import com.yunque.common.core.web.result.R;
import com.yunque.system.api.authority.feign.factory.RemoteAdminLoginFallbackFactory;
import com.yunque.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录服务 | 管理端
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteLoginService", path = "/inner/login/admin", value = ServiceConstants.SYSTEM_SERVICE, fallbackFactory = RemoteAdminLoginFallbackFactory.class)
public interface RemoteAdminLoginService {

    /**
     * 查询登录登录信息
     *
     * @param userName       员工账号
     * @param password       密码
     * @return 结果
     */
    @GetMapping(headers = SecurityConstants.FROM_SOURCE_INNER)
    R<LoginUser> getLoginInfoInner(@RequestParam("userName") String userName, @RequestParam("password") String password);

}
