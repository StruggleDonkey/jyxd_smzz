package com.jyxd.web.task;

import com.jyxd.web.data.basic.MedOrderExec;
import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.data.basic.QuartzTime;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.basic.MedOrderExecService;
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
 * his同步医嘱执行信息定时任务
 * 实现job接口
 */
public class MedOrderJob implements Job {

    private static Logger logger= LoggerFactory.getLogger(MedOrderJob.class);

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
                map.put("jobName","medOrderJob");
                map.put("jobGroup","medOrderJob");
                quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
            }

            //定时任务中具体行为逻辑
            MedOrderExecService medOrderExecService = (MedOrderExecService) cxt.getBean("medOrderExecService");
            PatientService patientService = (PatientService) cxt.getBean("patientService");
            try {
                //查询本地数据库字典数据
                Map<String,Object> map=new HashMap<>();
                List<MedOrderExec> list=medOrderExecService.queryMedOrderExecList(map);
                //从his数据库视图中查询医嘱执行信息数据
                List<Map<String,Object>> hisList=medOrderExecService.getMedOrderExecByHis(map);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String,Object> map1=new HashMap<>();//查询病人id参数
                if(hisList!=null && hisList.size()>0){
                    if(list!=null && list.size()>0){
                        //如果数据库有数据则需要和his中获取的数据做比较再更新

                        ArrayList arrayList= new ArrayList();
                        for (int i = 0; i <list.size(); i++) {
                            arrayList.add(list.get(i).getOrderCode());
                        }

                        ArrayList arrayHisList= new ArrayList();
                        for (int i = 0; i < hisList.size(); i++) {
                            arrayHisList.add(hisList.get(i).get("order_code").toString());
                        }

                        for (int i = 0; i <hisList.size(); i++) {
                            if(!arrayList.contains(hisList.get(i).get("order_code").toString())){
                                //如果本地数据不包含 his系统数据 则新增数据
                                MedOrderExec medOrderExec=new MedOrderExec();
                                medOrderExec.setId(UUIDUtil.getUUID());
                                medOrderExec.setVisitId(hisList.get(i).get("visit_id").toString());
                                medOrderExec.setVisitCode(hisList.get(i).get("visit_code").toString());
                                map1.put("visitId",hisList.get(i).get("visit_id").toString());
                                map1.put("visitCode",hisList.get(i).get("visit_code").toString());
                                Patient patient =patientService.getPatientByConditions(map1);
                                if(patient!=null){
                                    medOrderExec.setPatientId(patient.getId());
                                }
                                medOrderExec.setOrderCode(hisList.get(i).get("order_code").toString());//his医嘱执行编码 对应到本地数据 医嘱编码（同步时使用）
                                medOrderExec.setOrderNo(hisList.get(i).get("order_group_no").toString());//his医嘱组号 对应到本地数据 医嘱主键
                                medOrderExec.setOrderSubNo(hisList.get(i).get("order_id ").toString());//his子医嘱 ID 对应到本地数据 子医嘱主键
                                medOrderExec.setOrderName(hisList.get(i).get("order_name ").toString());
                                medOrderExec.setDrugType(Integer.valueOf(hisList.get(i).get("drug_type ").toString()));
                                medOrderExec.setOrderAttr(hisList.get(i).get("order_attr ").toString());
                                if(StringUtils.isNotEmpty(hisList.get(i).get("dosage ").toString())){
                                    medOrderExec.setDosage(hisList.get(i).get("dosage ").toString());//药品用量
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("dosage_units ").toString())){
                                    medOrderExec.setDosageUnits(hisList.get(i).get("dosage_units ").toString());//单位
                                }
                                medOrderExec.setRepeatIndicator((int)hisList.get(i).get("repeat_indicator "));// 医嘱类型，0：临时医嘱；1：长期医嘱
                                if(StringUtils.isNotEmpty(hisList.get(i).get("use_mode ").toString())){
                                    medOrderExec.setUseMode(hisList.get(i).get("use_mode ").toString());//给药途径
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("frequency ").toString())){
                                    medOrderExec.setFrequency(hisList.get(i).get("frequency ").toString());//频次，如：qd,tid等
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("perform_speed ").toString())){
                                    medOrderExec.setPerformSpeed(hisList.get(i).get("perform_speed ").toString());//流速
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("speed_units ").toString())){
                                    medOrderExec.setSpeedUnits(hisList.get(i).get("speed_units ").toString());//流速单位
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("nurse_code ").toString())){
                                    medOrderExec.setNurseCode(hisList.get(i).get("nurse_code ").toString());//执行护士工号
                                }
                                if(StringUtils.isNotEmpty(hisList.get(i).get("remark ").toString())){
                                    medOrderExec.setRemark(hisList.get(i).get("remark ").toString());//医嘱嘱托
                                }
                                medOrderExec.setOrderStatus(0);//执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
                                medOrderExec.setCreateTime(new Date());
                                medOrderExec.setUpdateTime(new Date());
                                medOrderExec.setIsSync(0);//是否已同步到护理单，0：未同步；1：已同步；
                                medOrderExec.setOrderExecNum(1);//医嘱每天执行次数 默认1
                                medOrderExec.setSyncNum(1);//剩余同步次数 默认1
                                medOrderExecService.insert(medOrderExec);
                            }
                        }

                    }else{
                        //直接将his获取的数据添加到本地数据库
                        for (int i = 0; i < hisList.size(); i++) {
                            //如果本地数据不包含 his系统数据 则新增数据
                            MedOrderExec medOrderExec=new MedOrderExec();
                            medOrderExec.setId(UUIDUtil.getUUID());
                            medOrderExec.setVisitId(hisList.get(i).get("visit_id").toString());
                            medOrderExec.setVisitCode(hisList.get(i).get("visit_code").toString());
                            map1.put("visitId",hisList.get(i).get("visit_id").toString());
                            map1.put("visitCode",hisList.get(i).get("visit_code").toString());
                            Patient patient =patientService.getPatientByConditions(map1);
                            if(patient!=null){
                                medOrderExec.setPatientId(patient.getId());
                            }
                            medOrderExec.setOrderCode(hisList.get(i).get("order_code").toString());//his医嘱执行编码 对应到本地数据 医嘱编码（同步时使用）
                            medOrderExec.setOrderNo(hisList.get(i).get("order_group_no").toString());//his医嘱组号 对应到本地数据 医嘱主键
                            medOrderExec.setOrderSubNo(hisList.get(i).get("order_id ").toString());//his子医嘱 ID 对应到本地数据 子医嘱主键
                            medOrderExec.setOrderName(hisList.get(i).get("order_name ").toString());
                            medOrderExec.setDrugType(Integer.valueOf(hisList.get(i).get("drug_type ").toString()));
                            medOrderExec.setOrderAttr(hisList.get(i).get("order_attr ").toString());
                            if(StringUtils.isNotEmpty(hisList.get(i).get("dosage ").toString())){
                                medOrderExec.setDosage(hisList.get(i).get("dosage ").toString());//药品用量
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("dosage_units ").toString())){
                                medOrderExec.setDosageUnits(hisList.get(i).get("dosage_units ").toString());//单位
                            }
                            medOrderExec.setRepeatIndicator((int)hisList.get(i).get("repeat_indicator "));// 医嘱类型，0：临时医嘱；1：长期医嘱
                            if(StringUtils.isNotEmpty(hisList.get(i).get("use_mode ").toString())){
                                medOrderExec.setUseMode(hisList.get(i).get("use_mode ").toString());//给药途径
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("frequency ").toString())){
                                medOrderExec.setFrequency(hisList.get(i).get("frequency ").toString());//频次，如：qd,tid等
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("perform_speed ").toString())){
                                medOrderExec.setPerformSpeed(hisList.get(i).get("perform_speed ").toString());//流速
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("speed_units ").toString())){
                                medOrderExec.setSpeedUnits(hisList.get(i).get("speed_units ").toString());//流速单位
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("nurse_code ").toString())){
                                medOrderExec.setNurseCode(hisList.get(i).get("nurse_code ").toString());//执行护士工号
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("remark ").toString())){
                                medOrderExec.setRemark(hisList.get(i).get("remark ").toString());//医嘱嘱托
                            }
                            medOrderExec.setOrderStatus(0);//执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
                            medOrderExec.setCreateTime(new Date());
                            medOrderExec.setUpdateTime(new Date());
                            medOrderExec.setIsSync(0);//是否已同步到护理单，0：未同步；1：已同步；
                            medOrderExec.setOrderExecNum(1);//医嘱每天执行次数 默认1
                            medOrderExec.setSyncNum(1);//剩余同步次数 默认1
                            medOrderExecService.insert(medOrderExec);
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
