package com.yunque.job.util;

import com.yunque.common.core.exception.job.TaskException;
import com.yunque.common.core.exception.job.TaskException.Code;
import com.yunque.common.core.utils.core.NumberUtil;
import com.yunque.common.core.utils.core.ObjectUtil;
import com.yunque.common.core.utils.core.SpringUtil;
import com.yunque.common.core.utils.core.StrUtil;
import com.yunque.job.api.domain.dto.SysJobDto;
import com.yunque.job.api.utils.CronUtils;
import com.yunque.job.constant.ScheduleConstants;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * 定时任务工具类
 *
 * @author xueyi
 */
public class ScheduleUtil {

    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysJobDto sysJob) {
        boolean isConcurrent = "0".equals(sysJob.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJobDto job) throws SchedulerException, TaskException {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        // 构建job信息
        Long jobId = job.getId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup)).withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        // 判断任务是否过期
        if (ObjectUtil.isNotNull(CronUtils.getNextExecution(job.getCronExpression()))) {
            // 执行调度任务
            scheduler.scheduleJob(jobDetail, trigger);
        }

        // 暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getCode())) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJobDto job, CronScheduleBuilder cb) throws TaskException {
        switch (ScheduleConstants.Misfire.getByCode(job.getMisfirePolicy())) {
            case DEFAULT:
                return cb;
            case IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks", Code.CONFIG_ERROR);
        }
    }

    /**
     * 检查包名是否为白名单配置
     *
     * @param invokeTarget 目标字符串
     * @return 结果
     */
    public static boolean whiteList(String invokeTarget) {
        String packageName = StrUtil.subBefore(invokeTarget, StrUtil.PARENTHESES_START);
        int count = StrUtil.count(packageName, StrUtil.DOT);
        if (count > NumberUtil.One) {
            return StrUtil.containsAnyIgnoreCase(invokeTarget, ScheduleConstants.JOB_WHITELIST_STR);
        }
        Object obj = SpringUtil.getBean(StrUtil.splitToArray(invokeTarget, StrUtil.DOT)[NumberUtil.Zero]);
        String beanPackageName = obj.getClass().getPackage().getName();
        return StrUtil.containsAnyIgnoreCase(beanPackageName, ScheduleConstants.JOB_WHITELIST_STR)
                && !StrUtil.containsAnyIgnoreCase(beanPackageName, ScheduleConstants.JOB_ERROR_STR);
    }
}