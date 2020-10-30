package com.jyxd.web.task;

import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.data.basic.QuartzTime;
import com.jyxd.web.service.basic.QuartzTaskService;
import com.jyxd.web.service.basic.QuartzTimeService;
import com.jyxd.web.util.CompareTimeUtil;
import com.jyxd.web.util.UUIDUtil;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现job接口
 */
public class TestJob implements Job {

    private static Logger logger= LoggerFactory.getLogger(TestJob.class);

    //定义一个成员变量
    private static QuartzTask quartzTask;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            QuartzTime quartzTime=new QuartzTime();
            quartzTime.setStartTime(new Date());

            ServletContext context = null;
            //获取service
            context = (ServletContext) arg0.getScheduler().getContext()
                    .get(QuartzServletContextListener.MY_CONTEXT_NAME);
            WebApplicationContext cxt = (WebApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            QuartzTimeService quartzTimeService = (QuartzTimeService) cxt.getBean("quartzTimeService");
            QuartzTaskService quartzTaskService = (QuartzTaskService) cxt.getBean("quartzTaskService");

            if(quartzTask==null){
                //为空时 表明第一次执行该方法 需要查询赋值
                Map<String,Object> map=new HashMap<>();
                map.put("jobName","testJob");
                map.put("jobGroup","testJob");
                quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
            }
            CronExpression expression = new CronExpression(quartzTask.getCron());
            quartzTime.setNextTime(expression.getNextValidTimeAfter(quartzTime.getStartTime()));
            quartzTime.setQuartzTaskId(quartzTask.getId());
            quartzTime.setId(UUIDUtil.getUUID());
            quartzTime.setEndTime(new Date());
            quartzTime.setTimeLength(String.valueOf(CompareTimeUtil.calculatetimeGapSecond(quartzTime.getStartTime(),quartzTime.getEndTime())));
            quartzTime.setStatus(1);
            quartzTimeService.insert(quartzTime);
        }catch (Exception e){
            logger.info("异常："+e);
        }

    }

}
