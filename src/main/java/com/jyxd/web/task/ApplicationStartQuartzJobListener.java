package com.jyxd.web.task;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Scheduler 注入
 * 这里采用监听spring容器加载完毕后事件，启动任务调用
 * 将Scheduler交给spring初始化管理
 */
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzSchedulerUtil quartzScheduler;

//    @Bean(name="SchedulerFactory")
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//           SchedulerFactoryBean factory = new SchedulerFactoryBean();
//           factory.setQuartzProperties(quartzProperties());
//           return factory;
//         }
//
//    /**
//      * 加载Quartz配置
//     *
//      */
//    @Bean
//    public Properties quartzProperties() throws IOException {
//         //使用Spring的PropertiesFactoryBean对属性配置文件进行管理
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        Properties properties = propertiesFactoryBean.getObject();
//        return properties;
//    }

    /**
     * 初始启动quartz
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            quartzScheduler.startJob(scheduler());
            System.out.println("任务已经启动...");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始注入scheduler
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException{
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }
}
