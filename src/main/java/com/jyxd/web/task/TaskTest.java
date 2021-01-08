package com.jyxd.web.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class TaskTest {



    private static Logger logger= LoggerFactory.getLogger(TaskTest.class);

    /*public static void main(String[] args) {
        try {
            //QuartzSchedulerUtil quartzScheduler=new QuartzSchedulerUtil();
            //logger.info(quartzScheduler.pause("job1","group1"));
            //quartzScheduler.resumeAllJob();
            //getAllJobs();
            Date curTime = new Date();

            System.out.println(curTime);
            CronExpression  expression = new CronExpression("0 30 15 * * ?");
            Date newDate = expression.getNextValidTimeAfter(curTime);
            //getNextValidTimeAfter（Date date）是根据cron表达式，来获得传入时间之后的第一个执行时间
            //如上例中：当前时间为6月24日19:11:52，cron表示每天的15:30:00来执行，那么返回的结果就是6月25日15:30:00

            System.out.println(newDate);
        }catch (Exception e){
            logger.info("TaskTest:"+e);
        }
    }*/

    public static void getAllJobs() throws SchedulerException {
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
    }

    /*public static void main(String[] args) {
        String msg="A|告知患儿及家属跌倒坠床相关因素和预防措施B|床栏保护C|保护性约束D|挂警示标识E|保持地面干燥、通道通畅F|保持光线适宜";
        JSONArray array=new JSONArray();

            String[] biaozhun=msg.split("\\|");
            for (int i = 0; i < biaozhun.length; i++) {
                if(i>0){
                    array.add(biaozhun[i-1].substring(biaozhun[i-1].length()-1,biaozhun[i-1].length()) +biaozhun[i].substring(0,biaozhun[i].length()-1));
                }

            }
        System.out.println(array.toString());
    }*/

}
