package com.yunque.common.security.handler;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.TokenConstants;
import com.yunque.common.core.utils.core.ArrayUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.servlet.ServletUtil;
import com.yunque.common.security.config.properties.PermitAllUrlProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Token解析器
 *
 * @author xueyi
 */
public class BearerTokenHandler implements BearerTokenResolver {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    private boolean allowFormEncodedBodyParameter = false;

    private boolean allowUriQueryParameter = true;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final PermitAllUrlProperties urlProperties;

    public BearerTokenHandler(PermitAllUrlProperties urlProperties) {
        this.urlProperties = urlProperties;
    }

    @Override
    public String resolve(HttpServletRequest request) {
        AtomicBoolean match = new AtomicBoolean(urlProperties.getRoutine().stream().anyMatch(url ->
                pathMatcher.match(url, request.getRequestURI())));

        if (!match.get()) {
            Optional.ofNullable(urlProperties.getCustom().get(RequestMethod.valueOf(request.getMethod())))
                    .ifPresent(list -> match.set(list.stream().anyMatch(url -> pathMatcher.match(url, request.getRequestURI()))));
        }
        if (match.get()) {
            return null;
        }

        final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
        final String parameterToken = isParameterTokenSupportedForRequest(request) ? resolveFromRequestParameters(request) : null;
        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                final BearerTokenError error = BearerTokenErrors
                        .invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
        }
        if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
            return parameterToken;
        }
        return null;
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = ServletUtil.getHeader(request, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode());
        if (!StrUtil.startWith(authorization, TokenConstants.PREFIX))
            return null;
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }

    private static String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues(SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode());
        if (ArrayUtil.isEmpty(values)) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        }
        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }

    private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
        return (("POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || "GET".equals(request.getMethod()));
    }

    private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
        return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
                && MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
                || (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
    }
}