package com.xueyi.common.security.feign;

import com.xueyi.common.core.constant.basic.SecurityConstants;
import com.xueyi.common.core.context.SecurityContextHolder;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import com.xueyi.common.core.utils.ip.IpUtil;
import com.xueyi.common.core.utils.servlet.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * feign 请求拦截器
 *
 * @author xueyi
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ServletUtil.getRequest();
        if (ObjectUtil.isNotNull(httpServletRequest)) {
            Map<String, String> headers = ServletUtil.getHeaders(httpServletRequest);
            // 传递用户信息请求头，防止丢失
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.ENTERPRISE_ID);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.ENTERPRISE_NAME);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.IS_LESSOR);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.USER_ID);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.USER_NAME);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.NICK_NAME);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.USER_TYPE);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.SOURCE_NAME);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.STRATEGY_ID);
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.ACCOUNT_TYPE);

            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode());
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.USER_KEY.getCode());
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.AUTHORIZATION_HEADER.getCode());
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.SUPPLY_AUTHORIZATION_HEADER.getCode());
            setHeaderKey(requestTemplate, headers, SecurityConstants.BaseSecurity.TENANT_IGNORE.getCode());

            // 配置客户端IP
            requestTemplate.header("X-Forwarded-For", IpUtil.getIpAddr());
        }
    }

    /**
     * 更替请求头内容
     *
     * @param requestTemplate 请求模板对象
     * @param headers         请求头内容
     * @param security        安全值
     */
    private <T extends SecurityConstants.ISecurityInterface> void setHeaderKey(RequestTemplate requestTemplate, Map<String, String> headers, T security) {
        Optional.ofNullable(SecurityContextHolder.get(security.getCode())).filter(StrUtil::isNotBlank).ifPresentOrElse(info -> requestTemplate.header(security.getCode(), info),
                () -> setHeaderKey(requestTemplate, headers, security.getCode()));
        Optional.ofNullable(SecurityContextHolder.get(security.getBaseCode())).filter(StrUtil::isNotBlank).ifPresentOrElse(info -> requestTemplate.header(security.getBaseCode(), info),
                () -> setHeaderKey(requestTemplate, headers, security.getBaseCode()));
    }

    /**
     * 更替请求头内容
     *
     * @param requestTemplate 请求模板对象
     * @param headers         请求头内容
     * @param headerKey       请求头键值
     */
    private void setHeaderKey(RequestTemplate requestTemplate, Map<String, String> headers, String headerKey) {
        String key = headers.get(headerKey);
        if (StrUtil.isNotBlank(key))
            requestTemplate.header(headerKey, key);
    }
}