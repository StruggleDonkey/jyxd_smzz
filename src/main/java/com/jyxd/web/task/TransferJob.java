package com.jyxd.web.task;

import com.jyxd.web.data.basic.BedArrange;
import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.data.basic.QuartzTime;
import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.basic.BedArrangeService;
import com.jyxd.web.service.basic.QuartzTaskService;
import com.jyxd.web.service.basic.QuartzTimeService;
import com.jyxd.web.service.dictionary.DepartmentDictionaryService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * his同步病人转移信息定时任务
 * 实现job接口
 */
public class TransferJob implements Job {

    private static Logger logger= LoggerFactory.getLogger(TransferJob.class);

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

            //his病人信息同步定时任务
            if(quartzTask==null){
                //为空时 表明第一次执行该方法 需要查询赋值
                Map<String,Object> map=new HashMap<>();
                map.put("jobName","transferJob");
                map.put("jobGroup","transferJob");
                quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
            }

            //定时任务中具体行为逻辑
            PatientService patientService = (PatientService) cxt.getBean("patientService");
            BedArrangeService bedArrangeService = (BedArrangeService) cxt.getBean("bedArrangeService");
            String departmentCode="";//重症科室编码
            DepartmentDictionaryService departmentDictionaryService = (DepartmentDictionaryService) cxt.getBean("departmentDictionaryService");
            Map<String,Object> map1=new HashMap<>();//查询重症科室编码参数
            map1.put("departmentName","重症");
            List<DepartmentDictionary> departmentDictionaryList=departmentDictionaryService.queryDataList(map1);
            if(departmentDictionaryList!=null && departmentDictionaryList.size()>0){
                departmentCode=departmentDictionaryList.get(0).getDepartmentCode();
            }
            Map<String,Object> map2=new HashMap<>();//床位安排查询所需参数
            try {
                //查询本地数据库参数
                Map<String,Object> map=new HashMap<>();
                Map<String,Object> map3=new HashMap<>();//查询病人所需参数  只放visit_id visit_code
                //从his数据库视图中查询病人转移信息数据
                List<Map<String,Object>> hisList=patientService.getTransferByHis(map);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < hisList.size(); i++) {
                    if(hisList.get(i).containsKey("in_office") && StringUtils.isNotEmpty(hisList.get(i).get("in_office").toString())){
                        //转入科室编号
                        map.put("departmentCode",hisList.get(i).get("in_office").toString());
                        //转入科室编号和重症科室编码一样代表是入科
                        if(hisList.get(i).get("in_office").toString().equals(departmentCode)
                        && hisList.get(i).containsKey("transfer_time") && StringUtils.isNotEmpty(hisList.get(i).get("transfer_time").toString())){
                            map.put("enterTime",hisList.get(i).get("transfer_time").toString());
                        }
                    }
                    if(hisList.get(i).containsKey("out_office") && StringUtils.isNotEmpty(hisList.get(i).get("out_office").toString())){
                        //转出科室编号
                        map.put("toDepartmentCode",hisList.get(i).get("out_office").toString());
                        //转出科室编号和重症科室编码一样代表是出科
                        if(hisList.get(i).get("out_office").toString().equals(departmentCode)
                                && hisList.get(i).containsKey("transfer_time") && StringUtils.isNotEmpty(hisList.get(i).get("transfer_time").toString())){
                            map.put("exitTime",hisList.get(i).get("transfer_time").toString());
                        }
                    }
                    if(hisList.get(i).containsKey("visit_id") && StringUtils.isNotEmpty(hisList.get(i).get("visit_id").toString())){
                        //转出科室编号
                        map.put("visitId",hisList.get(i).get("visit_id").toString());
                        map3.put("visitId",hisList.get(i).get("visit_id").toString());
                    }
                    if(hisList.get(i).containsKey("visit_code") && StringUtils.isNotEmpty(hisList.get(i).get("visit_code").toString())){
                        //转出科室编号
                        map.put("visitCode",hisList.get(i).get("visit_code").toString());
                        map3.put("visitCode",hisList.get(i).get("visit_code").toString());
                    }
                    if(hisList.get(i).containsKey("bed_code") && StringUtils.isNotEmpty(hisList.get(i).get("bed_code").toString())){
                        //转出科室编号
                        map.put("bedCode",hisList.get(i).get("bed_code").toString());
                    }
                    if(patientService.getPatientByConditions(map)==null){
                        //根据以上参数查询本地数据库 如果对象为空 则表明是新的转移消息
                        Patient patient =patientService.getPatientByConditions(map3);
                        if(patient!=null){
                            if(hisList.get(i).containsKey("in_office") && StringUtils.isNotEmpty(hisList.get(i).get("in_office").toString())){
                                //转入科室编号
                                patient.setDepartmentCode(hisList.get(i).get("in_office").toString());
                                //转入科室编号和重症科室编码一样代表是入科
                                if(hisList.get(i).get("in_office").toString().equals(departmentCode)
                                        && hisList.get(i).containsKey("transfer_time") && StringUtils.isNotEmpty(hisList.get(i).get("transfer_time").toString())){
                                    patient.setEnterTime(format.parse(hisList.get(i).get("transfer_time").toString()));
                                }
                                //在院标志（0：出科；1：在科）
                                patient.setFlag(1);

                            }
                            if(hisList.get(i).containsKey("out_office") && StringUtils.isNotEmpty(hisList.get(i).get("out_office").toString())){
                                //转出科室编号
                                patient.setToDepartmentCode(hisList.get(i).get("out_office").toString());
                                //转出科室编号和重症科室编码一样代表是出科
                                if(hisList.get(i).get("out_office").toString().equals(departmentCode)
                                        && hisList.get(i).containsKey("transfer_time") && StringUtils.isNotEmpty(hisList.get(i).get("transfer_time").toString())){
                                    patient.setExitTime(format.parse(hisList.get(i).get("transfer_time").toString()));
                                }
                                //在院标志（0：出科；1：在科）
                                patient.setFlag(0);
                            }
                            if(hisList.get(i).containsKey("visit_id") && StringUtils.isNotEmpty(hisList.get(i).get("visit_id").toString())){
                                //转出科室编号
                                patient.setVisitId(hisList.get(i).get("visit_id").toString());
                            }
                            if(hisList.get(i).containsKey("visit_code") && StringUtils.isNotEmpty(hisList.get(i).get("visit_code").toString())){
                                //转出科室编号
                                patient.setVisitCode(hisList.get(i).get("visit_code").toString());
                            }
                            if(hisList.get(i).containsKey("bed_code") && StringUtils.isNotEmpty(hisList.get(i).get("bed_code").toString())){
                                //转出科室编号
                                patient.setBedCode(hisList.get(i).get("bed_code").toString());
                            }
                            patientService.update(patient);
                            map2.put("patientId",patient.getId());
                            //根据并人id查询床位安排对象
                            BedArrange bedArrange =bedArrangeService.queryDataByCode(map2);
                            if(bedArrange!=null){
                                bedArrange.setBedCode(hisList.get(i).get("bed_code").toString());
                            }
                            bedArrangeService.update(bedArrange);
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
