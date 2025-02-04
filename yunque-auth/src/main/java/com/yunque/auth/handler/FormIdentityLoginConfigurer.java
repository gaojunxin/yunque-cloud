package com.yunque.auth.handler;

import com.yunque.auth.service.impl.FormEventHandlerImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * 统一认证登录 - 授权码模式
 *
 * @author xueyi
 */
public final class FormIdentityLoginConfigurer extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.formLogin(formLogin -> {
                    formLogin.loginPage("/token/login");
                    formLogin.loginProcessingUrl("/token/form");
                    formLogin.failureHandler(new FormEventHandlerImpl());
                })
                // SSO登出成功处理
                .logout(logout -> logout.logoutSuccessHandler(new FormEventHandlerImpl())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable);
    }
}
