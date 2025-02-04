package com.yunque.common.security.utils;

import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.security.service.TokenUserService;
import com.yunque.system.api.model.DataScope;

/**
 * 管理端 - 权限获取工具类
 *
 * @author xueyi
 */
public class SecurityUserUtils extends SecurityUtils {

    /**
     * 获取数据权限信息
     */
    public static DataScope getDataScope() {
        return SpringUtil.getBean(TokenUserService.class).getDataScope();
    }
}
