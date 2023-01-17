package com.xueyi.common.core.exception.auth;

import com.xueyi.common.core.utils.core.ArrayUtil;
import com.xueyi.common.core.utils.core.StrUtil;

import java.io.Serial;

/**
 * 未能通过的权限认证异常
 *
 * @author xueyi
 */
public class NotPermissionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotPermissionException(String permission) {
        super(permission);
    }

    public NotPermissionException(String[] permissions) {
        super(ArrayUtil.join(permissions, StrUtil.COMMA));
    }
}
