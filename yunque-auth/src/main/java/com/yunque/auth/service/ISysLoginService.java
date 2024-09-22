package com.yunque.auth.service;

import com.yunque.auth.form.RegisterBody;

/**
 * 登录校验 服务层
 *
 * @author xueyi
 */
public interface ISysLoginService {

    /**
     * 注册
     */
    void register(RegisterBody registerBody);

}
