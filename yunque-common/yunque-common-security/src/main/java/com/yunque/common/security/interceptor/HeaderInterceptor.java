package com.yunque.common.security.interceptor;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.context.SecurityContextHolder;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.servlet.ServletUtil;
import com.yunque.common.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import java.util.Optional;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author xueyi
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor {

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            setSecurityContext(request, SecurityConstants.BaseSecurity.IS_LESSOR);
            setSecurityContext(request, SecurityConstants.BaseSecurity.USER_ID);
            setSecurityContext(request, SecurityConstants.BaseSecurity.USER_NAME);
            setSecurityContext(request, SecurityConstants.BaseSecurity.NICK_NAME);
            setSecurityContext(request, SecurityConstants.BaseSecurity.USER_TYPE);
            setSecurityContext(request, SecurityConstants.BaseSecurity.ACCOUNT_TYPE);

            SecurityContextHolder.setAccessToken(ServletUtil.getHeader(request, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode()));
            SecurityContextHolder.setUserKey(ServletUtil.getHeader(request, SecurityConstants.BaseSecurity.USER_KEY.getCode()));

            String accountType = SecurityContextHolder.getAccountType();

            // 刷新用户有效期
            if (StrUtil.isNotBlank(accountType)) {
                Optional.of(SecurityUtils.getTokenService(accountType)).ifPresent(service -> service.refreshToken(request));
            }
        }
        return true;
    }

    @Override
    @SneakyThrows
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.remove();
    }

    /**
     * 设置安全上下文
     *
     * @param request  请求
     * @param security 安全值
     */
    private <T extends SecurityConstants.ISecurityInterface> void setSecurityContext(HttpServletRequest request, T security) {
        SecurityContextHolder.set(security.getCode(), ServletUtil.getHeader(request, security.getCode()));
        SecurityContextHolder.set(security.getBaseCode(), ServletUtil.getHeader(request, security.getBaseCode()));
    }
}