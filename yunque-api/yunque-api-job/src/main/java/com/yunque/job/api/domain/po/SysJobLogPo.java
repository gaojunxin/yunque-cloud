package com.yunque.job.api.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yunque.common.core.annotation.Excel;
import com.yunque.common.core.web.tenant.base.TBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 调度日志 持久化对象
 *
 * @author xueyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job_log", excludeProperty = {"createBy", "updateBy", "remark", "updateTime", "sort"})
public class SysJobLogPo extends TBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 任务Id */
    protected Long jobId;

    /** 任务组名 */
    @Excel(name = "任务组名")
    protected String jobGroup;

    /** 调用目标字符串 */
    @Excel(name = "调用目标字符串")
    protected String invokeTarget;

    /** 日志信息 */
    @Excel(name = "日志信息")
    protected String jobMessage;

    /** 执行状态（0正常 1失败） */
    @Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
    protected String status;

    /** 异常信息 */
    @Excel(name = "异常信息")
    protected String exceptionInfo;

}
