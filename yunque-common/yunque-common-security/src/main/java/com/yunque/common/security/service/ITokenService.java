package com.yunque.common.security.service;

import com.yunque.common.core.constant.basic.SecurityConstants;
import com.yunque.common.core.constant.basic.TokenConstants;
import com.yunque.common.core.utils.JwtUtil;
import com.yunque.common.core.utils.core.CollUtil;
import com.yunque.common.core.utils.core.MapUtil;
import com.yunque.common.core.utils.core.NumberUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.utils.ip.IpUtil;
import com.yunque.common.core.utils.servlet.ServletUtil;
import com.yunque.common.core.web.model.BaseLoginUser;
import com.yunque.common.redis.service.RedisService;
import com.yunque.common.security.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.yunque.common.cache.constant.CacheConstants.ACCESS_TIME;
import static com.yunque.common.cache.constant.CacheConstants.EXPIRATION;
import static com.yunque.common.cache.constant.CacheConstants.REFRESH_TIME;

/**
 * token控制器
 *
 * @author xueyi
 */
@SuppressWarnings(value = {"unchecked"})
public interface ITokenService<User, LoginUser extends BaseLoginUser<User>> extends Ordered {

    RedisService getRedisService();

    /**
     * 判断账户类型
     *
     * @param accountType 账户类型
     * @return 结果
     */
    boolean support(String accountType);

    /**
     * 构建令牌缓存路径
     *
     * @param type         密钥类型
     * @param userId       用户名Id
     * @param tokenValue   token值
     * @return 令牌缓存路径
     */
    String getTokenAddress(String type, Long userId, String tokenValue);

    /**
     * 排序值 | 默认取最大的
     *
     * @return 排序值
     */
    @Override
    default int getOrder() {
        return NumberUtil.Zero;
    }

    default long getAccessExpireTime() {
        return ACCESS_TIME;
    }

    default long getRefreshExpireTime() {
        return EXPIRATION;
    }

    default long getMillisMinuteTen() {
        return REFRESH_TIME * 60L;
    }

    /**
     * 创建令牌
     *
     * @param loginUser 登录信息
     * @return JWT令牌
     */
    default Map<String, Object> createToken(LoginUser loginUser) {
        Map<String, Object> claimsMap = buildToken(loginUser, new HashMap<>());
        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<>();
        rspMap.put("access_token", JwtUtil.createToken(claimsMap));
        rspMap.put("expires_in", getRefreshExpireTime());
        return rspMap;
    }

    /**
     * 创建令牌缓存
     *
     * @param authorization 用户认证信息
     */
    default void createTokenCache(OAuth2Authorization authorization) {
        Map<String, Object> loginMap = buildTokenCache(authorization, new HashMap<>());
        createTokenCache(authorization, (LoginUser) loginMap.get(SecurityConstants.BaseSecurity.USER_INFO.getCode()), loginMap);
    }

    /**
     * 创建令牌缓存 | 定义缓存
     *
     * @param authorization 用户认证信息
     * @param loginUser     用户认证信息
     * @param loginMap      缓存存储信息
     */
    default void createTokenCache(OAuth2Authorization authorization, LoginUser loginUser, Map<String, Object> loginMap) {
        if (ObjectUtil.isNotNull(loginUser.getAccessToken())) {
            getRedisService().setJavaCacheObject(loginUser.getAccessToken(), authorization, getAccessExpireTime(), TimeUnit.MINUTES);
        }
        if (ObjectUtil.isNotNull(loginUser.getRefreshToken())) {
            getRedisService().setCacheMap(loginUser.getRefreshToken(), loginMap, getRefreshExpireTime(), TimeUnit.MINUTES);
        }
        if (ObjectUtil.isNotNull(loginUser.getStateToken())) {
            getRedisService().setJavaCacheObject(loginUser.getStateToken(), authorization, getAccessExpireTime(), TimeUnit.MINUTES);
        }
        if (ObjectUtil.isNotNull(loginUser.getCodeToken())) {
            getRedisService().setJavaCacheObject(loginUser.getCodeToken(), authorization, getAccessExpireTime(), TimeUnit.MINUTES);
        }
    }

    /**
     * 删除令牌缓存
     *
     * @param loginUser 用户认证信息
     */
    default void removeTokenCache(LoginUser loginUser) {
        List<String> keys = new ArrayList<>();
        if (ObjectUtil.isNotNull(loginUser.getAccessToken())) {
            keys.add(loginUser.getAccessToken());
        }
        if (ObjectUtil.isNotNull(loginUser.getRefreshToken())) {
            keys.add(loginUser.getRefreshToken());
        }
        if (ObjectUtil.isNotNull(loginUser.getStateToken())) {
            keys.add(loginUser.getStateToken());
        }
        if (ObjectUtil.isNotNull(loginUser.getCodeToken())) {
            keys.add(loginUser.getCodeToken());
        }
        if (CollUtil.isNotEmpty(keys)) {
            getRedisService().deleteObject(keys);
        }
    }

    /**
     * 构建令牌
     *
     * @param loginUser 登录信息
     * @param claimsMap 令牌存储信息
     * @return JWT令牌
     */
    default Map<String, Object> buildToken(LoginUser loginUser, Map<String, Object> claimsMap) {
        if (MapUtil.isNull(claimsMap)) {
            claimsMap = new HashMap<>();
        }

        claimsMap.put(SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode(), TokenConstants.PREFIX + loginUser.getAccessToken());
        claimsMap.put(SecurityConstants.BaseSecurity.REFRESH_TOKEN.getCode(), TokenConstants.PREFIX + loginUser.getRefreshToken());
        claimsMap.put(SecurityConstants.BaseSecurity.IS_LESSOR.getCode(), loginUser.getIsLessor());
        claimsMap.put(SecurityConstants.BaseSecurity.USER_ID.getCode(), loginUser.getUserId());
        claimsMap.put(SecurityConstants.BaseSecurity.USER_NAME.getCode(), loginUser.getUserName());
        claimsMap.put(SecurityConstants.BaseSecurity.NICK_NAME.getCode(), loginUser.getNickName());
        claimsMap.put(SecurityConstants.BaseSecurity.USER_TYPE.getCode(), loginUser.getUserType());
        claimsMap.put(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getCode(), loginUser.getAccountType().getCode());

        return claimsMap;
    }

    /**
     * 构建令牌缓存
     *
     * @param authorization 用户认证信息
     * @param loginMap      缓存存储信息
     * @return 令牌缓存
     */
    default Map<String, Object> buildTokenCache(OAuth2Authorization authorization, Map<String, Object> loginMap) {
        LoginUser loginUser = Optional.ofNullable(authorization)
                .map(item -> (UsernamePasswordAuthenticationToken) authorization.getAttribute(Principal.class.getName()))
                .map(item -> (LoginUser) item.getPrincipal())
                .orElseThrow(() -> new NullPointerException("authorization principal cannot be null"));

        if (MapUtil.isNull(loginMap)) {
            loginMap = new HashMap<>();
        }

        loginUser.setIpaddr(IpUtil.getIpAddr(ServletUtil.getRequest()));
        loginUser.setLoginTime(System.currentTimeMillis());

        // 自定义构建
        loginMap = buildTokenCache(loginUser, loginMap);

        // 默认构建
        loginMap.put(SecurityConstants.BaseSecurity.ACCESS_TOKEN.getCode(), loginUser.getAccessToken());
        loginMap.put(SecurityConstants.BaseSecurity.REFRESH_TOKEN.getCode(), loginUser.getRefreshToken());
        loginMap.put(SecurityConstants.BaseSecurity.USER.getCode(), loginUser.getUser());
        loginMap.put(SecurityConstants.BaseSecurity.EXPIRE_TIME.getCode(), getExpireTime(loginUser.getLoginTime()));
        loginMap.put(SecurityConstants.BaseSecurity.USER_INFO.getCode(), loginUser);
        loginMap.put(SecurityConstants.BaseSecurity.ACCOUNT_TYPE.getCode(), loginUser.getAccountType().getCode());

        return loginMap;
    }

    /**
     * 构建令牌缓存 | 自定义构建
     *
     * @param loginUser 用户登录信息
     * @param loginMap  缓存存储信息
     * @return 令牌缓存
     */
    default Map<String, Object> buildTokenCache(LoginUser loginUser, Map<String, Object> loginMap) {
        return loginMap;
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    default User getUser() {
        return getUser(ServletUtil.getRequest());
    }

    /**
     * 获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    default User getUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getUser(token);
    }

    /**
     * 获取用户信息
     *
     * @param token 令牌
     * @return 用户信息
     */
    default User getUser(String token) {
        try {
            if (StrUtil.isNotBlank(token)) {
                return getRedisService().getCacheMapValue(JwtUtil.getUserKey(token), SecurityConstants.BaseSecurity.USER.getCode());
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取登陆身份信息
     *
     * @return 登陆信息
     */
    default LoginUser getLoginUser() {
        return getLoginUser(ServletUtil.getRequest());
    }

    /**
     * 获取登陆身份信息
     *
     * @param request 请求
     * @return 登陆信息
     */
    default LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取登陆身份信息
     *
     * @param token 令牌
     * @return 登陆信息
     */
    default LoginUser getLoginUser(String token) {
        return getLoginUserSingle(token);
    }

    /**
     * 设置用户身份信息
     */
    default void setLoginUser(LoginUser loginUser) {
        if (ObjectUtil.isNotNull(loginUser)) {
            Map<String, Object> loginMap = new HashMap<>();
            loginMap.put(SecurityConstants.BaseSecurity.USER.getCode(), loginUser.getUser());
            loginMap.put(SecurityConstants.BaseSecurity.USER_INFO.getCode(), loginUser);
            if (StrUtil.isNotBlank(loginUser.getRefreshToken())) {
                getRedisService().setCacheMap(loginUser.getRefreshToken(), loginMap);
            }
        }
    }

    /**
     * 删除用户缓存信息
     *
     * @param token 令牌
     */
    default void delLogin(String token) {
        if (StrUtil.isNotBlank(token)) {
            getRedisService().deleteObject(JwtUtil.getUserKey(token));
        }
    }

    /**
     * 验证令牌有效期，自动刷新缓存
     *
     * @param request 请求
     */
    default void refreshToken(HttpServletRequest request) {
        refreshToken(SecurityUtils.getToken(request));
    }

    /**
     * 刷新令牌有效期
     *
     * @param token 令牌
     */
    default void refreshToken(String token) {
        Optional.ofNullable(token).map(JwtUtil::parseToken).ifPresent(claims -> {
            // 获取过期时间信息
            String accessToken = Optional.ofNullable(JwtUtil.getAccessKey(claims)).map(key -> StrUtil.replaceFirst(key, TokenConstants.PREFIX, StrUtil.EMPTY)).orElse(null);
            Optional.ofNullable(accessToken).map(key -> getRedisService().getExpire(key))
                    .filter(expireTime -> NumberUtil.compare(getMillisMinuteTen(), expireTime) >= NumberUtil.Zero)
                    .ifPresent(t -> getRedisService().expire(accessToken, getAccessExpireTime(), TimeUnit.MINUTES));
            String refreshToken = JwtUtil.getUserKey(claims);
            Optional.ofNullable(refreshToken).map(key -> getRedisService().getExpire(key))
                    .filter(expireTime -> NumberUtil.compare(getMillisMinuteTen(), expireTime) >= NumberUtil.Zero)
                    .ifPresent(t -> getRedisService().expire(refreshToken, getRefreshExpireTime(), TimeUnit.MINUTES));
        });
    }

    /**
     * 获取UserKey
     *
     * @return UserKey
     */
    default String getUserKey() {
        return Optional.ofNullable(ServletUtil.getRequest()).map(SecurityUtils::getToken)
                .filter(StrUtil::isNotBlank).map(JwtUtil::getUserKey).orElse(null);
    }

    /**
     * 获取过期时间
     *
     * @param loginTime 登录时间
     * @return 过期时间
     */
    default long getExpireTime(long loginTime) {
        return loginTime + getRefreshExpireTime();
    }

    /**
     * 校验是否已登录
     *
     * @return 结果
     */
    default boolean hasLogin() {
        try {
            String token = SecurityUtils.getToken(Objects.requireNonNull(ServletUtil.getRequest()));
            return getRedisService().hasKey(JwtUtil.getUserKey(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登陆身份信息
     *
     * @param token 令牌
     * @return 登陆信息
     */
    default LoginUser getLoginUserSingle(String token) {
        try {
            if (StrUtil.isNotBlank(token)) {
                return getRedisService().getCacheMapValue(JwtUtil.getUserKey(token), SecurityConstants.BaseSecurity.USER_INFO.getCode());
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
