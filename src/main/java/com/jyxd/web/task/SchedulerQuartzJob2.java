package com.jyxd.web.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现job接口
 */
public class SchedulerQuartzJob2 implements Job {

    private static Logger logger= LoggerFactory.getLogger(SchedulerQuartzJob2.class);

    private void before(){
        logger.info("Job2任务开始执行");
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        before();
        // TODO 业务
        logger.info("Job key:"+arg0.getJobDetail().getKey());
        try {
            /*SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
            QuartzSchedulerUtil quartzSchedulerUtil=new QuartzSchedulerUtil();
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            quartzSchedulerUtil.modifyJob("job2", "group2","0/1 * * * * ?",scheduler);
            logger.info("修改Job2任务");*/
        }catch (Exception e){
            logger.info("Job2任务异常："+e);
        }

        //getAllJobs();
    }

    /*public static void getAllJobs() {
        try {
            SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            System.out.println("-------------");
            for (String groupName : scheduler.getJobGroupNames()) {
                System.out.println("+_++++++++++++++++++");
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    System.out.println("=============");
                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();
                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
