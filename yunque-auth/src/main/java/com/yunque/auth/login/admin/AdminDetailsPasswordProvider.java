package com.yunque.auth.login.admin;

import com.yunque.auth.login.base.IUserDetailsService;
import com.yunque.auth.service.ISysLogService;
import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.core.constant.basic.Constants;
import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.utils.core.ConvertUtil;
import com.yunque.common.core.utils.core.MapUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.servlet.ServletUtil;
import com.yunque.common.core.web.result.R;
import com.yunque.system.api.authority.feign.RemoteAdminLoginService;
import com.yunque.system.api.model.LoginUser;
import com.yunque.system.api.organize.constant.OrganizeConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证处理器 | 密码模式 | 后台账户
 *
 * @author xueyi
 */
@Component
public class AdminDetailsPasswordProvider implements IUserDetailsService {

    @Autowired
    private ISysLogService logService;

    @Autowired
    private RemoteAdminLoginService remoteLoginService;

    /**
     * 校验授权类型与账户类型
     *
     * @param grantType   授权类型
     * @param accountType 账户类型
     * @return 结果
     */
    @Override
    public boolean support(String grantType, String accountType) {
        return StrUtil.equals(SecurityConstants.GrantType.PASSWORD.getCode(), grantType) && StrUtil.equals(SecurityConstants.AccountType.ADMIN.getCode(), accountType);
    }

    /**
     * 参数校验
     *
     * @param request 请求体
     */
    @Override
    @SneakyThrows
    public void checkParams(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = ServletUtil.getParameters(request);

        String userName = parameters.getFirst(SecurityConstants.LoginParam.USER_NAME.getCode());
        String password = parameters.getFirst(SecurityConstants.LoginParam.PASSWORD.getCode());

        // 员工账号不在指定范围内 错误
        if (userName != null && (userName.length() < OrganizeConstants.USERNAME_MIN_LENGTH
                || userName.length() > OrganizeConstants.USERNAME_MAX_LENGTH)) {
            SpringUtil.getBean(ISysLogService.class).recordLoginInfo(userName, Constants.LOGIN_FAIL, "员工账号不在指定范围");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "员工账号不在指定范围", null));
        }

        // 密码如果不在指定范围内 错误
        if (password != null && (password.length() < OrganizeConstants.PASSWORD_MIN_LENGTH
                || password.length() > OrganizeConstants.PASSWORD_MAX_LENGTH)) {
            SpringUtil.getBean(ISysLogService.class).recordLoginInfo(userName, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new OAuth2AuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, "用户密码不在指定范围", null));
        }
    }

    /**
     * 登录信息构建
     *
     * @param reqParameters 请求参数
     * @return 用户信息
     */
    public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
        String userName = (String) reqParameters.get(SecurityConstants.LoginParam.USER_NAME.getCode());
        String password = (String) reqParameters.get(SecurityConstants.LoginParam.PASSWORD.getCode());
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put(SecurityConstants.BaseSecurity.USER_NAME.getCode(), userName);
        loginMap.put(SecurityConstants.BaseSecurity.PASSWORD.getCode(), password);
        return new UsernamePasswordAuthenticationToken(loginMap, password);
    }

    /**
     * 登录验证
     *
     * @param principal 登录信息
     * @return 用户信息
     */
    @Override
    @SneakyThrows
    public UserDetails loadUser(Object principal) {
        Map<String, String> loginMap = ConvertUtil.toMap(String.class, String.class, principal);
        if (MapUtil.isEmpty(loginMap)) {
            loginMap = new HashMap<>();
        }
        String userName = loginMap.get(SecurityConstants.BaseSecurity.USER_NAME.getCode());
        String password = loginMap.get(SecurityConstants.BaseSecurity.PASSWORD.getCode());
        return loadUser(userName, password);
    }

    /**
     * 登录验证
     *
     * @param userName       用户名
     * @param password       密码
     * @return 用户信息
     */
    @SneakyThrows
    public LoginUser loadUser(String userName, String password) {
        // 查询登录信息
        R<LoginUser> loginInfoResult = remoteLoginService.getLoginInfoInner(userName, password);
        if (loginInfoResult.isFail()) {
            throw new UsernameNotFoundException("服务调用失败，请稍后再试！");
        } else if (ObjectUtil.isNull(loginInfoResult.getData())) {
            logService.recordLoginInfo(userName, Constants.LOGIN_FAIL, loginInfoResult.getMsg());
            throw new UsernameNotFoundException("员工账号/密码错误，请检查！");
        }
        LoginUser loginUser = loginInfoResult.getData();
        if (BaseConstants.Status.DISABLE.getCode().equals(loginUser.getUser().getStatus())) {
            logService.recordLoginInfo(loginUser, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new UsernameNotFoundException("对不起，您的账号：" + userName + " 已停用！");
        }
        loginUser.setPassword(SecurityConstants.BCRYPT + loginUser.getPassword());
        return loginUser;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
