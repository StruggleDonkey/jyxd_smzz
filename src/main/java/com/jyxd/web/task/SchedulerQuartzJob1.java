package com.jyxd.web.task;

import com.jyxd.web.service.log.LogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 实现job接口
 */
public class SchedulerQuartzJob1 implements Job {

    private static Logger logger= LoggerFactory.getLogger(SchedulerQuartzJob1.class);


    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            ServletContext context = null;
            // TODO 业务

            //获取service
            context = (ServletContext) arg0.getScheduler().getContext()
                    .get(QuartzServletContextListener.MY_CONTEXT_NAME);
            WebApplicationContext cxt = (WebApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            LogService logService = (LogService) cxt.getBean("logService");


            logger.info("Job key:"+arg0.getJobDetail().getKey());



           /* Log log=new Log();
            log.setId(UUIDUtil.getUUID());
            log.setOperateTime(new Date());
            log.setContent("定时任务测试");*/
            logger.info("hotelService:成功");
        }catch (Exception e){
            logger.info("异常："+e);
        }

    }

}
