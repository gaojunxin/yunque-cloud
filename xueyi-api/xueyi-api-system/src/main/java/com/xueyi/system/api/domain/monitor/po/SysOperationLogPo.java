package com.xueyi.system.api.domain.monitor.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xueyi.common.core.annotation.Excel;
import com.xueyi.common.core.web.tenant.TBaseEntity;

import java.util.Date;

/**
 * 操作日志 持久化对象
 *
 * @author xueyi
 */
public class SysOperationLogPo extends TBaseEntity {

    private static final long serialVersionUID = 1L;

    /** 操作模块 */
    @Excel(name = "操作模块")
    @TableField("title")
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    @TableField("business_type")
    private String businessType;

    /** 请求方法 */
    @Excel(name = "请求方法")
    @TableField("method")
    private String method;

    /** 请求方式 */
    @Excel(name = "请求方式")
    @TableField("request_method")
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    @TableField("operation_type")
    private String operationType;

    /** 操作Id */
    @TableField("user_id")
    private Long userId;

    /** 请求url */
    @Excel(name = "请求地址")
    @TableField("url")
    private String url;

    /** 操作地址 */
    @Excel(name = "操作地址")
    @TableField("ip")
    private String ip;

    /** 请求参数 */
    @Excel(name = "请求参数")
    @TableField("param")
    private String param;

    /** 请求参数 */
    @Excel(name = "操作地点")
    @TableField("location")
    private String location;
    
    /** 返回参数 */
    @Excel(name = "返回参数")
    @TableField("json_result")
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    @TableField("status")
    private String status;

    /** 错误消息 */
    @Excel(name = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @TableField("operate_time")
    private Date operateTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
