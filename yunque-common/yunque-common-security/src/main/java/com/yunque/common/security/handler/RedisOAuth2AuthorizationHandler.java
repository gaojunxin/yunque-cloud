package com.yunque.common.security.handler;

import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.web.model.BaseLoginUser;
import com.yunque.common.redis.service.RedisService;
import com.yunque.common.security.service.ITokenService;
import com.yunque.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * Redis授权
 *
 * @author xueyi
 */
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisOAuth2AuthorizationHandler implements OAuth2AuthorizationService {

    @Autowired
    private RedisService redisService;

    @Override
    public void save(OAuth2Authorization authorization) {
        // check loginUser info
        BaseLoginUser loginUser = SecurityUtils.getLoginUser(authorization);
        // check token service
        ITokenService tokenService = SecurityUtils.getTokenService(loginUser.getAccountType().getCode());

        // build refresh token
        if (isRefreshToken(authorization)) {
            String refreshToken = Optional.ofNullable(authorization.getRefreshToken())
                    .map(OAuth2Authorization.Token::getToken)
                    .map(OAuth2RefreshToken::getTokenValue)
                    .orElseThrow(() -> new NullPointerException("refreshToken cannot be null"));
            loginUser.setRefreshToken(tokenService.getTokenAddress(OAuth2ParameterNames.REFRESH_TOKEN, loginUser.getUserId(), refreshToken));
        }

        // build access token
        if (isAccessToken(authorization)) {
            String accessToken = Optional.ofNullable(authorization.getAccessToken())
                    .map(OAuth2Authorization.Token::getToken)
                    .map(OAuth2AccessToken::getTokenValue)
                    .orElseThrow(() -> new NullPointerException("accessToken cannot be null"));
            loginUser.setAccessToken(tokenService.getTokenAddress(OAuth2ParameterNames.ACCESS_TOKEN, loginUser.getUserId(), accessToken));
        }

        // build state token
        if (isState(authorization)) {
            String token = authorization.getAttribute(OAuth2ParameterNames.STATE);
            loginUser.setStateToken(tokenService.getTokenAddress(OAuth2ParameterNames.STATE, loginUser.getUserId(), token));
        }

        // build code token
        if (isCode(authorization)) {
            OAuth2AuthorizationCode authorizationCodeToken = Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class))
                    .map(OAuth2Authorization.Token::getToken)
                    .orElseThrow(() -> new NullPointerException("authorizationCodeToken cannot be null"));
            loginUser.setCodeToken(tokenService.getTokenAddress(OAuth2ParameterNames.CODE, loginUser.getUserId(), authorizationCodeToken.getTokenValue()));
        }

        // build redis cache
        tokenService.createTokenCache(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        // check loginUser info
        BaseLoginUser loginUser = SecurityUtils.getLoginUser(authorization);
        // check token service
        ITokenService tokenService = SecurityUtils.getTokenService(loginUser.getAccountType().getCode());
        tokenService.removeTokenCache(loginUser);
    }

    @Override
    @Nullable
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be empty");
        return ObjectUtil.equals(OAuth2TokenType.ACCESS_TOKEN, tokenType) ? redisService.getJavaCacheObject(token) : ObjectUtil.equals(OAuth2TokenType.REFRESH_TOKEN, tokenType) ?  redisService.getCacheObject(token) : null;
    }

    private static boolean isState(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAttribute(OAuth2ParameterNames.STATE));
    }

    private static boolean isCode(OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        return ObjectUtil.isNotNull(authorizationCode);
    }

    private static boolean isRefreshToken(OAuth2Authorization authorization) {
        return ObjectUtil.isNotNull(authorization.getRefreshToken());
    }

    private static boolean isAccessToken(OAuth2Authorization authorization) {
        return ObjectUtil.isNotNull(authorization.getAccessToken());
    }
}
