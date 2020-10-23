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
        after();
    }

    private void after(){
        logger.info("Job2任务开始执行");
    }

}
