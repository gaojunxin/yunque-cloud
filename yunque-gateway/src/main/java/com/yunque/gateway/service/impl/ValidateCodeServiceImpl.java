package com.yunque.gateway.service.impl;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.yunque.common.core.constant.basic.Constants;
import com.yunque.common.core.exception.CaptchaException;
import com.yunque.common.core.utils.core.IdUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.redis.constant.RedisConstants;
import com.yunque.common.redis.service.RedisService;
import com.yunque.gateway.config.properties.CaptchaProperties;
import com.yunque.gateway.service.ValidateCodeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 *
 * @author xueyi
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaProperties captchaProperties;

    /**
     * 生成验证码
     */
    @Override
    public AjaxResult createCaptcha() throws IOException, CaptchaException {
        boolean captchaOnOff = captchaProperties.getEnabled();
        HashMap<String, Object> ajax = new HashMap<>();
        ajax.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff) {
            return AjaxResult.success(ajax);
        }

        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = RedisConstants.CacheKey.CAPTCHA_CODE_KEY.getCode() + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return AjaxResult.success(ajax);
    }

    /**
     * 校验验证码
     */
    @Override
    public void checkCaptcha(String code, String uuid) throws CaptchaException {
        if (StrUtil.isEmpty(code)) {
            throw new CaptchaException("验证码不能为空");
        }
        if (StrUtil.isEmpty(uuid)) {
            throw new CaptchaException("验证码已失效");
        }
        String verifyKey = RedisConstants.CacheKey.CAPTCHA_CODE_KEY.getCode() + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }
}
