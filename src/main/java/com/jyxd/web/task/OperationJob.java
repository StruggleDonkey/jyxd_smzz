package com.jyxd.web.task;

import com.jyxd.web.data.basic.Operation;
import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.data.basic.QuartzTime;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.basic.OperationService;
import com.jyxd.web.service.basic.QuartzTaskService;
import com.jyxd.web.service.basic.QuartzTimeService;
import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.util.CompareTimeUtil;
import com.jyxd.web.util.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * his同步手术信息定时任务
 * 实现job接口
 */
public class OperationJob implements Job {

    private static Logger logger= LoggerFactory.getLogger(OperationJob.class);

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

            //his手术信息同步定时任务
            if(quartzTask==null){
                //为空时 表明第一次执行该方法 需要查询赋值
                Map<String,Object> map=new HashMap<>();
                map.put("jobName","operationJob");
                map.put("jobGroup","operationJob");
                quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
            }

            //定时任务中具体行为逻辑
            PatientService patientService = (PatientService) cxt.getBean("patientService");
            OperationService operationService = (OperationService) cxt.getBean("operationService");
            Map<String,Object> map1=new HashMap<>();
            Map<String,Object> map2=new HashMap<>();//查询病人信息参数
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //查询本地数据库字典数据
                List<Operation> list=operationService.queryOperationList(map1);
                //从his数据库视图中查询手术信息数据
                List<Map<String,Object>> hisList=operationService.getOperationByHis(map1);
                if(hisList!=null && hisList.size()>0){
                    if(list!=null && list.size()>0){
                        //如果数据库有数据则需要和his中获取的数据做比较再更新

                        ArrayList arrayList= new ArrayList();
                        for (int i = 0; i <list.size(); i++) {
                            arrayList.add(list.get(i).getOperationId());
                        }

                        ArrayList arrayHisList= new ArrayList();
                        for (int i = 0; i < hisList.size(); i++) {
                            arrayHisList.add(hisList.get(i).get("operation_id").toString());
                        }

                        /*for (int i = 0; i <list.size(); i++) {
                            if(!arrayHisList.contains(list.get(i).getOperationId())){
                                //如果his系统中数据 里 没有本地数据 则删除本地数据
                                list.get(i).setStatus(-1);
                                operationService.update(list.get(i));
                            }
                        }*/

                        for (int i = 0; i <hisList.size(); i++) {
                            if(!arrayList.contains(hisList.get(i).get("operation_id").toString())){
                                //如果本地数据不包含 his系统数据 则新增数据
                                Operation operation=new Operation();
                                operation.setStatus(1);
                                operation.setId(UUIDUtil.getUUID());
                                operation.setOperationId(hisList.get(i).get("operation_id").toString());
                                operation.setVisitId(hisList.get(i).get("visit_id").toString());
                                operation.setVisitCode(hisList.get(i).get("visit_code").toString());
                                operation.setCreateTime(new Date());
                                map2.put("visitId",hisList.get(i).get("visit_id").toString());
                                map2.put("visitCode",hisList.get(i).get("visit_code").toString());
                                Patient patient=patientService.getPatientByConditions(map2);
                                if(patient!=null){
                                    operation.setPatientId(patient.getId());
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("operation_time").toString())){
                                    operation.setOperationTime(format.parse(hisList.get(i).get("operation_time").toString()));
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("operation_code").toString())){
                                    operation.setOperationCode(hisList.get(i).get("operation_code").toString());
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("operation_name").toString())){
                                    operation.setOperationName(hisList.get(i).get("operation_name").toString());
                                }
                                operationService.insert(operation);
                            }
                        }

                    }else{
                        //直接将his获取的数据添加到本地数据库
                        for (int i = 0; i < hisList.size(); i++) {
                            Operation operation=new Operation();
                            operation.setStatus(1);
                            operation.setId(UUIDUtil.getUUID());
                            if(StringUtils.isNotEmpty(hisList.get(i).get("operation_id").toString())){
                                operation.setOperationId(hisList.get(i).get("operation_id").toString());
                            }
                            operation.setVisitId(hisList.get(i).get("visit_id").toString());
                            operation.setVisitCode(hisList.get(i).get("visit_code").toString());
                            operation.setCreateTime(new Date());
                            map2.put("visitId",hisList.get(i).get("visit_id").toString());
                            map2.put("visitCode",hisList.get(i).get("visit_code").toString());
                            Patient patient=patientService.getPatientByConditions(map2);
                            if(patient!=null){
                                operation.setPatientId(patient.getId());
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("operation_time").toString())){
                                operation.setOperationTime(format.parse(hisList.get(i).get("operation_time").toString()));
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("operation_code").toString())){
                                operation.setOperationCode(hisList.get(i).get("operation_code").toString());
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("operation_name").toString())){
                                operation.setOperationName(hisList.get(i).get("operation_name").toString());
                            }
                            operationService.insert(operation);
                        }
                    }

                }

            }catch (Exception e){
                logger.info("PatientJob:"+e);
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
