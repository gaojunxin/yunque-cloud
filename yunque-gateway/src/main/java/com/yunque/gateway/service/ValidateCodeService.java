package com.yunque.gateway.service;

import com.yunque.common.core.exception.CaptchaException;
import com.yunque.common.core.web.result.AjaxResult;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author xueyi
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     */
    AjaxResult createCaptcha() throws IOException, CaptchaException;

    /**
     * 校验验证码
     */
    void checkCaptcha(String key, String value) throws CaptchaException;
}
