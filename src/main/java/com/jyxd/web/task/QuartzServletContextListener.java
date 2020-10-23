package com.jyxd.web.task;

import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * 解决定时任务quartz的job中注入service为null
 */
@Component
public class QuartzServletContextListener extends QuartzInitializerListener{

    public static final String MY_CONTEXT_NAME = "servletContext";

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        super.contextDestroyed(sce);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        super.contextInitialized(sce);
        ServletContext servletContext = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
        try {
            factory.getScheduler().getContext()
                    .put(QuartzServletContextListener.MY_CONTEXT_NAME, servletContext);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
