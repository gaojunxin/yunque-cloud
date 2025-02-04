package com.yunque.common.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作人类别
 *
 * @author xueyi
 */
@Getter
@AllArgsConstructor
public enum OperatorType {

    /** 其它 */
    OTHER("00", "其它"),

    /** 后台用户 */
    MANAGE("01", "后台用户"),

    /** 手机端用户 */
    MOBILE("02", "手机端用户");

    private final String code;
    private final String info;

}
