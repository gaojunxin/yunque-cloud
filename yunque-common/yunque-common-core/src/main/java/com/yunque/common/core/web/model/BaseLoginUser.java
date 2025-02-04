package com.yunque.common.core.web.model;

import com.yunque.common.core.constant.basic.SecurityConstants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认用户信息
 *
 * @author xueyi
 */
@Data
public class BaseLoginUser<User> implements UserDetails, OAuth2AuthenticatedPrincipal {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户唯一标识 - 访问令牌 */
    protected String accessToken;

    /** 用户唯一标识 - 刷新令牌 */
    protected String refreshToken;

    /** 用户唯一标识 - 令牌 */
    protected String stateToken;

    /** 用户唯一标识 - 令牌 */
    protected String codeToken;

    /** 用户名Id */
    protected Long userId;

    /** 用户名 */
    protected String userName;

    /** 密码 */
    protected String password;

    /** 用户昵称 */
    protected String nickName;

    /** 租户标识 */
    protected String isLessor;

    /** 用户标识 */
    protected String userType;

    /** 登录时间 */
    protected Long loginTime;

    /** 登录IP地址 */
    protected String ipaddr;

    /** 用户信息 */
    protected User user;

    /** 账户类型 */
    protected SecurityConstants.AccountType accountType;

    private Set<GrantedAuthority> authorities;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public String getUserName() {
        return userName;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return userName;
    }

}
