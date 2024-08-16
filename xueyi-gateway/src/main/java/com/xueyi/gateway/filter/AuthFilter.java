package com.xueyi.gateway.filter;

import com.xueyi.common.core.constant.basic.SecurityConstants;
import com.xueyi.common.core.constant.basic.TokenConstants;
import com.xueyi.common.core.utils.JwtUtil;
import com.xueyi.common.core.utils.core.ObjectUtil;
import com.xueyi.common.core.utils.core.StrUtil;
import com.xueyi.common.core.utils.jwt.JwtMemberUtil;
import com.xueyi.common.core.utils.jwt.JwtPlatformUtil;
import com.xueyi.common.core.utils.servlet.ServletUtil;
import com.xueyi.common.redis.service.RedisService;
import com.xueyi.gateway.config.properties.IgnoreWhiteProperties;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author xueyi
 */
@Slf4j
public class AuthFilter implements WebFilter {

    private final RedisService redisService;

    private final IgnoreWhiteProperties ignoreWhite;

    public AuthFilter(RedisService redisService, IgnoreWhiteProperties ignoreWhite) {
        this.redisService = redisService;
        this.ignoreWhite = ignoreWhite;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();

        boolean isWhites = StrUtil.matches(url, ignoreWhite.getWhites());

        String token = ServletUtil.getToken(request);
        if (StrUtil.isEmpty(token)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌不能为空");
        }
        Claims claims;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        if (ObjectUtil.isNull(claims)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }

        String accessTokenPrefix = JwtUtil.getAccessKey(claims);
        if (StrUtil.isBlank(accessTokenPrefix) || !StrUtil.startWith(accessTokenPrefix, TokenConstants.PREFIX)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        String accessToken = StrUtil.replaceFirst(accessTokenPrefix, TokenConstants.PREFIX, StrUtil.EMPTY);
        String userKey = JwtUtil.getUserKey(claims);
        if (StrUtil.isBlank(userKey)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }
        SecurityConstants.AccountType accountType = SecurityConstants.AccountType.getByCodeElseNull(JwtUtil.getAccountType(claims));
        if (ObjectUtil.isNull(accountType)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌已过期或验证不正确");
        }

        boolean hasLogin = redisService.hasKey(accessToken) && redisService.hasKey(userKey);
        if (!hasLogin) {
            return unauthorizedResponse(exchange, chain, isWhites, "登录状态已过期");
        }

        // JWT解析信息
        String baseEnterpriseId = JwtUtil.getEnterpriseId(claims);
        String baseEnterpriseName = JwtUtil.getEnterpriseName(claims);
        String baseIsLessor = JwtUtil.getIsLessor(claims);
        String baseStrategyId = JwtUtil.getStrategyId(claims);
        String baseSourceName = JwtUtil.getSourceName(claims);
        String baseUserId = JwtUtil.getUserId(claims);
        String baseUserName = JwtUtil.getUserName(claims);
        String baseNickName = JwtUtil.getNickName(claims);
        String baseUserType = JwtUtil.getUserType(claims);

        if (StrUtil.hasBlank(baseEnterpriseId, baseEnterpriseName, baseIsLessor, baseSourceName)) {
            return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
        }

        switch (accountType) {
            case ADMIN, EXTERNAL -> {
                if (StrUtil.hasBlank(baseUserId, baseUserName, baseUserType)) {
                    return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
                }
            }
            case MEMBER -> {
                String applicationId = JwtMemberUtil.getApplicationId(claims);
                String appId = JwtMemberUtil.getAppId(claims);

                if (StrUtil.hasBlank(appId)) {
                    return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
                }

                ServletUtil.addHeader(mutate, SecurityConstants.MemberSecurity.APPLICATION_ID.getBaseCode(), applicationId);
                ServletUtil.addHeader(mutate, SecurityConstants.MemberSecurity.APP_ID.getBaseCode(), appId);
            }
            case PLATFORM -> {
                String appId = JwtPlatformUtil.getAppId(claims);

                if (StrUtil.hasBlank(appId)) {
                    return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
                }

                ServletUtil.addHeader(mutate, SecurityConstants.PlatformSecurity.APP_ID.getBaseCode(), appId);
            }
            default -> {
                return unauthorizedResponse(exchange, chain, isWhites, "令牌验证失败");
            }
        }

        // 设置Token解析信息到请求
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.ENTERPRISE_ID.getBaseCode(), baseEnterpriseId);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.ENTERPRISE_NAME.getBaseCode(), baseEnterpriseName);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getBaseCode(), accountType.getCode());
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.IS_LESSOR.getBaseCode(), baseIsLessor);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.STRATEGY_ID.getBaseCode(), baseStrategyId);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.SOURCE_NAME.getBaseCode(), baseSourceName);

        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.USER_ID.getBaseCode(), baseUserId);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.USER_NAME.getBaseCode(), baseUserName);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.NICK_NAME.getBaseCode(), baseNickName);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.USER_TYPE.getBaseCode(), baseUserType);

        // 令牌信息
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode(), accessTokenPrefix);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.REFRESH_TOKEN.getCode(), userKey);
        ServletUtil.addHeader(mutate, SecurityConstants.BaseSecurity.USER_KEY.getCode(), userKey);

        // 内部请求来源参数清除
        ServletUtil.removeHeader(mutate, SecurityConstants.BaseSecurity.FROM_SOURCE.getCode());
        if (!isWhites) {
            ServletUtil.removeHeader(mutate, TokenConstants.SUPPLY_AUTHORIZATION);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, WebFilterChain chain, boolean isWhites, String msg) {
        return isWhites ? chain.filter(exchange) : ServletUtil.unauthorizedResponse(exchange, msg);
    }
}