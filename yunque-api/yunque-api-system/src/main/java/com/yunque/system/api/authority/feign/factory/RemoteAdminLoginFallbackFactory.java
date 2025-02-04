package com.yunque.system.api.authority.feign.factory;

import com.yunque.common.core.web.result.R;
import com.yunque.system.api.authority.feign.RemoteAdminLoginService;
import com.yunque.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 登录服务 | 管理端 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteAdminLoginFallbackFactory implements FallbackFactory<RemoteAdminLoginService> {

    @Override
    public RemoteAdminLoginService create(Throwable throwable) {
        log.error("登录服务调用失败:{}", throwable.getMessage());
        return new RemoteAdminLoginService() {
            @Override
            public R<LoginUser> getLoginInfoInner(String userName, String password) {
                return R.fail("获取登录信息失败:" + throwable.getMessage());
            }
        };
    }
}
