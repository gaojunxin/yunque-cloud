package com.yunque.job.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.yunque.common.core.constant.basic.DictConstants;
import com.yunque.common.core.utils.ExceptionUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.domain.dto.SysJobLogDto;
import com.yunque.job.api.feign.RemoteJobLogService;
import com.yunque.job.constant.ScheduleConstants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 抽象quartz调用
 *
 * @author xueyi
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJobDto sysJob = new SysJobDto();
        BeanUtil.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
        try {
            before(context, sysJob);
            if (ObjectUtil.isNotNull(sysJob))
                doExecute(context, sysJob);
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJobDto sysJob) {
        threadLocal.set(LocalDateTime.now());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param job     系统计划任务
     */
    protected void after(JobExecutionContext context, SysJobDto job, Exception e) {
        LocalDateTime startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLogDto jobLog = new SysJobLogDto();
        jobLog.setJobId(job.getId());
        jobLog.setName(job.getName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setStopTime(LocalDateTime.now());
        Duration between = LocalDateTimeUtil.between(jobLog.getStartTime(), jobLog.getStopTime());
        jobLog.setJobMessage(jobLog.getName() + " 总共耗时：" + between.toMillis() + "毫秒");
        if (e != null) {
            jobLog.setStatus(DictConstants.DicStatus.FAIL.getCode());
            String errorMsg = StrUtil.sub(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            jobLog.setExceptionInfo(errorMsg);
        } else {
            jobLog.setStatus(DictConstants.DicStatus.NORMAL.getCode());
        }
        // 写入数据库当中
        SpringUtil.getBean(RemoteJobLogService.class).saveJobLog(jobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJobDto sysJob) throws Exception;
}