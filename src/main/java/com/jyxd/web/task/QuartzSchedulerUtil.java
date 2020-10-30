package com.jyxd.web.task;

import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.service.basic.QuartzTaskService;
import com.jyxd.web.util.UUIDUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务调度处理
 */
@Configuration
public class QuartzSchedulerUtil {

    private static Logger logger= LoggerFactory.getLogger(QuartzSchedulerUtil.class);

    /*// 任务调度
    @Autowired
    private Scheduler scheduler;*/

    @Autowired
    private QuartzTaskService quartzTaskService;

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void startJob(Scheduler scheduler) throws SchedulerException {
        logger.info("scheduler:"+scheduler.toString());
        //startJob1(scheduler);测试
        //startJob2(scheduler);测试
        startJob3(scheduler);
        scheduler.start();
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name, String group,Scheduler scheduler) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param name
     * @param group
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String time,Scheduler scheduler) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 立即执行某个任务
     *
     * @param name
     * @param group
     * @param time
     * @return
     * @throws SchedulerException
     */
    public boolean beginJob(String name, String group, String time,Scheduler scheduler) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .withSchedule(cronScheduleBuilder).build();
        date = scheduler.rescheduleJob(triggerKey, trigger);
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob(Scheduler scheduler) throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group,Scheduler scheduler) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        System.out.println(jobKey.toString());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob(Scheduler scheduler) throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group,Scheduler scheduler) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group,Scheduler scheduler) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

    /**
     *  动态修改任务执行时间
     * @return
     * @throws Exception
     */
    public String trigger(String name,String group,Scheduler scheduler) throws Exception {
        // 获取任务
        JobKey jobKey = new JobKey(name,group);
        System.out.println("jobKey:"+jobKey);
        // 获取 jobDetail
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        System.out.println("jobDetail:"+jobDetail);
        // 生成 trigger
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();
        // 删除任务，不删除会报错。报任务已存在
        scheduler.deleteJob(jobKey);
        // 启动任务
        scheduler.scheduleJob(jobDetail, trigger);
        return "trigger";
    }

    /**
     * 动态关闭任务
     * @return
     * @throws Exception
     */
    public String pause(String name,String group,Scheduler scheduler) throws Exception {
        JobKey key = new JobKey(name,group);
        scheduler.pauseJob(key);
        return "pause";
    }

    /**
     * 动态启动任务
     * @return
     * @throws Exception
     */
    public void start(String name,String group,Scheduler scheduler) throws Exception {
        JobKey key = new JobKey(name,group);
        scheduler.resumeJob(key);
    }

    private void startJob1(Scheduler scheduler) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class).withIdentity("job1", "group1").build();
        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob2.class).withIdentity("job2", "group2").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job2", "group2")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    private void startJob3(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity("testJob", "testJob").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0/1 * * ? *");//每小时一次
        Map<String,Object> map=new HashMap<>();
        map.put("jobName","testJob");
        map.put("jobGroup","testJob");
        QuartzTask quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
        if(quartzTask==null){
            //如果为空 说明数据库中没有这个任务 需要添加
            QuartzTask data=new QuartzTask();
            data.setId(UUIDUtil.getUUID());
            data.setCreateTime(new Date());
            data.setCron("0 0 0/1 * * ? *");
            data.setDescription("测试定时任务");
            data.setJobGroup("testJob");
            data.setJobName("testJob");
            data.setStatus(1);
            data.setTaskName("testJob");
            data.setType("系统任务");
            quartzTaskService.insert(data);
        }else if(quartzTask.getStatus()==1){
            //转态 1: 正常执行
            cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartzTask.getCron());
        }else{
            return;
        }
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("testJob", "testJob")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

}
