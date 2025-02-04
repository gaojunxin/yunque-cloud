package com.yunque.auth.support.password;

import com.yunque.auth.support.base.AuthenticationBaseToken;
import com.yunque.common.core.constant.basic.SecurityConstants;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * 自定义授权模式 | 密码模式
 *
 * @author xueyi
 */
public class AuthenticationPasswordToken extends AuthenticationBaseToken {

    public AuthenticationPasswordToken(Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(SecurityConstants.GrantType.PASSWORD, clientPrincipal, scopes, additionalParameters);
    }
}
