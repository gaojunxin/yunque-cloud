package com.yunque.common.core.web.result;

import com.yunque.common.core.constant.basic.HttpConstants;
import com.yunque.common.core.exception.ServiceException;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.StrUtil;

import java.io.Serial;
import java.util.HashMap;
import java.util.Objects;

/**
 * 操作消息提醒
 *
 * @author xueyi
 */
public class AjaxResult extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 返回类型 */
    public static final String TYPE_TAG = "type";

    /** 数据对象 */
    public static final String RESULT_TAG = "data";

    /** 图片地址 */
    public static final String URL_TAG = "url";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param type 返回类型
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String type, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(TYPE_TAG, type);
        super.put(MSG_TAG, msg);
        if (ObjectUtil.isNotNull(data)) {
            super.put(RESULT_TAG, data);
        }
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param type 返回类型
     * @param msg  返回内容
     * @param url  返回地址
     */
    public AjaxResult(int code, String type, String msg, String url) {
        super.put(CODE_TAG, code);
        super.put(TYPE_TAG, type);
        super.put(MSG_TAG, msg);
        if (StrUtil.isNotEmpty(url)) {
            super.put(URL_TAG, url);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success(HttpConstants.ResultType.SUCCESS.getInfo());
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success(HttpConstants.ResultType.SUCCESS.getInfo(), data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpConstants.Status.SUCCESS.getCode(), HttpConstants.ResultType.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param url 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, String url) {
        return new AjaxResult(HttpConstants.Status.SUCCESS.getCode(), HttpConstants.ResultType.SUCCESS.getCode(), msg, url);
    }

    /**
     * 返回错误消息
     *
     * @return 错误内容
     */
    public static AjaxResult error() {
        return AjaxResult.error(HttpConstants.ResultType.ERROR.getInfo());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpConstants.Status.ERROR.getCode(), HttpConstants.ResultType.ERROR.getCode(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, HttpConstants.ResultType.ERROR.getCode(), msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg) {
        throw new ServiceException(msg);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param code 错误编码
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Integer code) {
        throw new ServiceException(msg, code);
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public boolean isSuccess() {
        return !isError();
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public boolean isError() {
        return Objects.equals(HttpConstants.ResultType.ERROR.getCode(), this.get(CODE_TAG));
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
