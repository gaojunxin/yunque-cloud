package com.yunque.auth.service.impl;

import com.yunque.auth.form.RegisterBody;
import com.yunque.auth.service.ISysLoginService;
import com.yunque.common.core.web.result.AjaxResult;
import com.yunque.common.core.web.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录校验 服务层处理
 *
 * @author xueyi
 */
@Component
public class SysLoginServiceImpl implements ISysLoginService {


    /**
     * 注册
     */
    @Override
    public void register(RegisterBody registerBody) {
        // 注册租户信息
//        R<?> registerResult = remoteTenantService.registerTenantInfo(registerBody.buildJson());
//        if (R.FAIL == registerResult.getCode()) {
//            AjaxResult.warn(registerResult.getMsg());
//        }
        // 注册逻辑补充完整后再增加日志
//        logService.recordLoginInfo(TenantConstants.Source.SLAVE.getCode(), SecurityConstants.EMPTY_TENANT_ID, registerBody.getTenant().getName(), SecurityConstants.EMPTY_USER_ID, registerBody.getUser().getUserName(), Constants.REGISTER, "注册成功");
    }
}