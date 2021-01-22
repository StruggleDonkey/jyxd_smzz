package com.jyxd.web.task;

import com.jyxd.web.data.basic.BedArrange;
import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.data.basic.QuartzTime;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.basic.BedArrangeService;
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
 * his同步病人信息定时任务
 * 实现job接口
 */
public class PatientJob implements Job {

    private static Logger logger= LoggerFactory.getLogger(PatientJob.class);

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
                map.put("jobName","patientJob");
                map.put("jobGroup","patientJob");
                quartzTask =quartzTaskService.queryDataByNameAndGroup(map);
            }

            //定时任务中具体行为逻辑
            PatientService patientService = (PatientService) cxt.getBean("patientService");
            BedArrangeService bedArrangeService = (BedArrangeService) cxt.getBean("bedArrangeService");
            Map<String,Object> map2=new HashMap<>();//床位安排查询所需参数
            System.out.println("111111111111111111111");
            try {
                //查询本地数据库字典数据
                Map<String,Object> map=new HashMap<>();
                List<Patient> list=patientService.queryPatientList(map);
                //从his数据库视图中查询科室字典数据
                List<Map<String,Object>> hisList=patientService.getPatientByHis(map);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("2222222222222222222222");
                if(hisList!=null && hisList.size()>0){
                    System.out.println("333333333333333333333");
                    if(list!=null && list.size()>0){
                        System.out.println("4444444444444444444444");
                        //如果数据库有数据则需要和his中获取的数据做比较再更新

                        ArrayList arrayList= new ArrayList();
                        for (int i = 0; i <list.size(); i++) {
                            System.out.println(list.size()+"-----"+i+"-----"+list.get(i).toString());
                            arrayList.add(list.get(i).getVisitCode());//本次住院号唯一性
                        }

                        ArrayList arrayHisList= new ArrayList();
                        for (int i = 0; i < hisList.size(); i++) {
                            System.out.println(hisList.size()+"-----"+i+"-----"+hisList.get(i).toString());
                            if(hisList.get(i).containsKey("visit_code") && StringUtils.isNotEmpty(hisList.get(i).get("visit_code").toString())){
                                arrayHisList.add(hisList.get(i).get("visit_code").toString());//本次住院号唯一性
                            }
                        }

                        for (int i = 0; i <list.size(); i++) {
                            if(!arrayHisList.contains(list.get(i).getVisitCode())){
                                //如果his系统中数据 里 没有本地数据 则删除本地数据
                                list.get(i).setStatus(-1);
                                patientService.update(list.get(i));
                            }
                        }
                        System.out.println("555555555555555555555");
                        for (int i = 0; i <hisList.size(); i++) {
                            if(hisList.get(i).containsKey("visit_code") && !arrayList.contains(hisList.get(i).get("visit_code").toString())){
                                //如果本地数据不包含 his系统数据 则新增数据
                                System.out.println("6666666666666666");
                                Patient patient=new Patient();
                                patient.setStatus(1);
                                patient.setId(UUIDUtil.getUUID());
                                patient.setCreateTime(new Date());
                                if(hisList.get(i).containsKey("visit_id") && StringUtils.isNotEmpty(hisList.get(i).get("visit_id").toString())){
                                    patient.setVisitId(hisList.get(i).get("visit_id").toString());//病人唯一住院号
                                }
                                if(hisList.get(i).containsKey("visit_code") && StringUtils.isNotEmpty(hisList.get(i).get("visit_code").toString())){
                                    patient.setVisitCode(hisList.get(i).get("visit_code").toString());//本次住院号
                                }
                                if(hisList.get(i).containsKey("visit_times") ){
                                    patient.setVisitNum((int)hisList.get(i).get("visit_times"));//住院次数
                                }
                                if(hisList.get(i).containsKey("unplanned") ){
                                    patient.setUnplanned((int)hisList.get(i).get("unplanned"));//是否非计划入科（0：否 1：是）
                                }
                                if(hisList.get(i).containsKey("enter_visit_time") && StringUtils.isNotEmpty(hisList.get(i).get("enter_visit_time").toString())){
                                    patient.setVisitTime(format.parse(hisList.get(i).get("enter_visit_time").toString()));//住院时间
                                }
                                if(hisList.get(i).containsKey("exit_visit_time") && StringUtils.isNotEmpty(hisList.get(i).get("exit_visit_time").toString())){
                                    patient.setExitTime(format.parse(hisList.get(i).get("exit_visit_time").toString()));//出院时间
                                }
                                if(hisList.get(i).containsKey("enter_time") && StringUtils.isNotEmpty(hisList.get(i).get("enter_time").toString())){
                                    patient.setEnterTime(format.parse(hisList.get(i).get("enter_time").toString()));//入科时间
                                }
                                if(hisList.get(i).containsKey("exit_time") && StringUtils.isNotEmpty(hisList.get(i).get("exit_time").toString())){
                                    patient.setExitTime(format.parse(hisList.get(i).get("exit_time").toString()));//出科时间
                                }
                                if(hisList.get(i).containsKey("death_time") && StringUtils.isNotEmpty(hisList.get(i).get("death_time").toString())){
                                    patient.setDeathTime(format.parse(hisList.get(i).get("death_time").toString()));//死亡时间
                                }
                                if(hisList.get(i).containsKey("name") && StringUtils.isNotEmpty(hisList.get(i).get("name").toString())){
                                    patient.setName(hisList.get(i).get("name").toString());//病人姓名
                                }
                                if(hisList.get(i).containsKey("id_card_no") && StringUtils.isNotEmpty(hisList.get(i).get("id_card_no").toString())){
                                    patient.setIdCard(hisList.get(i).get("id_card_no").toString());//身份证号
                                }
                                patient.setSex((int)hisList.get(i).get("sex"));//性别(0：女 1：男)
                                if(hisList.get(i).containsKey("birthday") && StringUtils.isNotEmpty(hisList.get(i).get("birthday").toString())){
                                    patient.setBirthday(hisList.get(i).get("birthday").toString());//出生日期
                                }
                                if(hisList.get(i).containsKey("age") && StringUtils.isNotEmpty(hisList.get(i).get("age").toString())){
                                    patient.setAge(hisList.get(i).get("age").toString());//年龄
                                }
                                if(hisList.get(i).containsKey("weight") && StringUtils.isNotEmpty(hisList.get(i).get("weight").toString())){
                                    patient.setWeight(hisList.get(i).get("weight").toString());//体重
                                }
                                if(hisList.get(i).containsKey("height") && StringUtils.isNotEmpty(hisList.get(i).get("height").toString())){
                                    patient.setHeight(hisList.get(i).get("height").toString());//身高
                                }
                                if(hisList.get(i).containsKey("race") && StringUtils.isNotEmpty(hisList.get(i).get("race").toString())){
                                    patient.setRace(hisList.get(i).get("race").toString());//民族
                                }
                                if(hisList.get(i).containsKey("marital_state") && StringUtils.isNotEmpty(hisList.get(i).get("marital_state").toString())){
                                    patient.setMaritalState(hisList.get(i).get("marital_state").toString());//婚姻状况编码
                                }
                                if(hisList.get(i).containsKey("blood_type") && StringUtils.isNotEmpty(hisList.get(i).get("blood_type").toString())){
                                    patient.setBloodType(hisList.get(i).get("blood_type").toString());//血型
                                }
                                if(hisList.get(i).containsKey("diagnosis_code") && StringUtils.isNotEmpty(hisList.get(i).get("diagnosis_code").toString())){
                                    patient.setDiagnosisCode(hisList.get(i).get("diagnosis_code").toString());//诊断编码
                                }
                                if(hisList.get(i).containsKey("diagnosis_name") && StringUtils.isNotEmpty(hisList.get(i).get("diagnosis_name").toString())){
                                    patient.setDiagnosisName(hisList.get(i).get("diagnosis_name").toString());//诊断名称
                                }
                                if(hisList.get(i).containsKey("doctor_code") && StringUtils.isNotEmpty(hisList.get(i).get("doctor_code").toString())){
                                    patient.setDoctorCode(hisList.get(i).get("doctor_code").toString());//主管医生工号
                                }
                                if(hisList.get(i).containsKey("nurse_code") && StringUtils.isNotEmpty(hisList.get(i).get("nurse_code").toString())){
                                    patient.setNurseCode(hisList.get(i).get("nurse_code").toString());//主管护士工号
                                }
                                if(hisList.get(i).containsKey("allergies") && StringUtils.isNotEmpty(hisList.get(i).get("allergies").toString())){
                                    patient.setAllergies(hisList.get(i).get("allergies").toString());//过敏史
                                }
                                if(hisList.get(i).containsKey("nursing_level") && StringUtils.isNotEmpty(hisList.get(i).get("nursing_level").toString())){
                                    patient.setNursingLevel(hisList.get(i).get("nursing_level").toString());//护理级别编码
                                }
                                if(hisList.get(i).containsKey("illness_state") && StringUtils.isNotEmpty(hisList.get(i).get("illness_state").toString())){
                                    patient.setIllnessState(hisList.get(i).get("illness_state").toString());//病情编码
                                }
                                if(hisList.get(i).containsKey("positive") && StringUtils.isNotEmpty(hisList.get(i).get("positive").toString())){
                                    patient.setPositive(hisList.get(i).get("positive").toString());//阳性
                                }
                                if(hisList.get(i).containsKey("department_code") && StringUtils.isNotEmpty(hisList.get(i).get("department_code").toString())){
                                    patient.setDepartmentCode(hisList.get(i).get("department_code").toString());//当前科室编号
                                }
                                if(hisList.get(i).containsKey("from_department_code") && StringUtils.isNotEmpty(hisList.get(i).get("from_department_code").toString())){
                                    patient.setToDepartmentCode(hisList.get(i).get("from_department_code").toString());//原科室编号
                                }
                                if(hisList.get(i).containsKey("ward_code") && StringUtils.isNotEmpty(hisList.get(i).get("ward_code").toString())){
                                    patient.setWardCode(hisList.get(i).get("ward_code").toString());//当前病区编号
                                }
                                if(hisList.get(i).containsKey("bed_code") && StringUtils.isNotEmpty(hisList.get(i).get("bed_code").toString())){
                                    patient.setBedCode(hisList.get(i).get("bed_code").toString());//当前床号
                                    //添加床位安排记录
                                    map2.put("bedCode",hisList.get(i).get("bed_code").toString());
                                    BedArrange bedArrange =bedArrangeService.queryDataByCode(map2);
                                    if(bedArrange!=null){
                                        //不为空则编辑
                                        bedArrange.setPatientId(patient.getId());
                                        bedArrangeService.update(bedArrange);
                                    }else{
                                        //为空则新增
                                        BedArrange data=new BedArrange();
                                        data.setId(UUIDUtil.getUUID());
                                        data.setPatientId(patient.getId());
                                        data.setBedCode(hisList.get(i).get("bed_code").toString());
                                        bedArrangeService.insert(data);
                                    }
                                }
                                if(hisList.get(i).containsKey("contact_man") && StringUtils.isNotEmpty(hisList.get(i).get("contact_man").toString())){
                                    patient.setContactMan(hisList.get(i).get("contact_man").toString());//紧急联系人
                                }
                                if(hisList.get(i).containsKey("contact_phone") && StringUtils.isNotEmpty(hisList.get(i).get("contact_phone").toString())){
                                    patient.setContactPhone(hisList.get(i).get("contact_phone").toString());//紧急联系电话
                                }
                                if(hisList.get(i).containsKey("remark") && StringUtils.isNotEmpty(hisList.get(i).get("remark").toString())){
                                    patient.setRemark(hisList.get(i).get("remark").toString());//备注
                                }
                                patient.setFlag(1);//在院标志（0：出科；1：在科）
                                patient.setExitType("");//出科方式 (出院、转科、死亡、放弃、转院)
                                System.out.println(hisList.size()+"----"+i+"----"+patient.toString());
                                patientService.insert(patient);
                                System.out.println("====="+i+1+"=====");
                            }
                        }
                    }else{
                        //直接将his获取的数据添加到本地数据库
                        System.out.println("777777777777777777777777777");
                        for (int i = 0; i < hisList.size(); i++) {
                            System.out.println("88888888888888888888888888888888888888888");
                            Patient patient=new Patient();
                            patient.setStatus(1);
                            patient.setId(UUIDUtil.getUUID());
                            patient.setCreateTime(new Date());
                            if(hisList.get(i).containsKey("visit_id") && StringUtils.isNotEmpty(hisList.get(i).get("visit_id").toString())){
                                patient.setVisitId(hisList.get(i).get("visit_id").toString());//病人唯一住院号
                            }
                            if(hisList.get(i).containsKey("visit_code") && StringUtils.isNotEmpty(hisList.get(i).get("visit_code").toString())){
                                patient.setVisitCode(hisList.get(i).get("visit_code").toString());//本次住院号
                            }
                            if(hisList.get(i).containsKey("visit_times") ){
                                patient.setVisitNum((int)hisList.get(i).get("visit_times"));//住院次数
                            }
                            if(hisList.get(i).containsKey("unplanned") ){
                                patient.setUnplanned((int)hisList.get(i).get("unplanned"));//是否非计划入科（0：否 1：是）
                            }
                            if(hisList.get(i).containsKey("enter_visit_time") && StringUtils.isNotEmpty(hisList.get(i).get("enter_visit_time").toString())){
                                patient.setVisitTime(format.parse(hisList.get(i).get("enter_visit_time").toString()));//住院时间
                            }
                            if(hisList.get(i).containsKey("exit_visit_time") && StringUtils.isNotEmpty(hisList.get(i).get("exit_visit_time").toString())){
                                patient.setExitTime(format.parse(hisList.get(i).get("exit_visit_time").toString()));//出院时间
                            }
                            if(hisList.get(i).containsKey("enter_time") && StringUtils.isNotEmpty(hisList.get(i).get("enter_time").toString())){
                                patient.setEnterTime(format.parse(hisList.get(i).get("enter_time").toString()));//入科时间
                            }
                            if(hisList.get(i).containsKey("exit_time") && StringUtils.isNotEmpty(hisList.get(i).get("exit_time").toString())){
                                patient.setExitTime(format.parse(hisList.get(i).get("exit_time").toString()));//出科时间
                            }
                            if(hisList.get(i).containsKey("death_time") && StringUtils.isNotEmpty(hisList.get(i).get("death_time").toString())){
                                patient.setDeathTime(format.parse(hisList.get(i).get("death_time").toString()));//死亡时间
                            }
                            if(hisList.get(i).containsKey("name") && StringUtils.isNotEmpty(hisList.get(i).get("name").toString())){
                                patient.setName(hisList.get(i).get("name").toString());//病人姓名
                            }
                            if(hisList.get(i).containsKey("id_card_no") && StringUtils.isNotEmpty(hisList.get(i).get("id_card_no").toString())){
                                patient.setIdCard(hisList.get(i).get("id_card_no").toString());//身份证号
                            }
                            patient.setSex((int)hisList.get(i).get("sex"));//性别(0：女 1：男)
                            if(hisList.get(i).containsKey("birthday") && StringUtils.isNotEmpty(hisList.get(i).get("birthday").toString())){
                                patient.setBirthday(hisList.get(i).get("birthday").toString());//出生日期
                            }
                            if(hisList.get(i).containsKey("age") && StringUtils.isNotEmpty(hisList.get(i).get("age").toString())){
                                patient.setAge(hisList.get(i).get("age").toString());//年龄
                            }
                            if(hisList.get(i).containsKey("weight") && StringUtils.isNotEmpty(hisList.get(i).get("weight").toString())){
                                patient.setWeight(hisList.get(i).get("weight").toString());//体重
                            }
                            if(hisList.get(i).containsKey("height") && StringUtils.isNotEmpty(hisList.get(i).get("height").toString())){
                                patient.setHeight(hisList.get(i).get("height").toString());//身高
                            }
                            if(hisList.get(i).containsKey("race") && StringUtils.isNotEmpty(hisList.get(i).get("race").toString())){
                                patient.setRace(hisList.get(i).get("race").toString());//民族
                            }
                            if(hisList.get(i).containsKey("marital_state") && StringUtils.isNotEmpty(hisList.get(i).get("marital_state").toString())){
                                patient.setMaritalState(hisList.get(i).get("marital_state").toString());//婚姻状况编码
                            }
                            if(hisList.get(i).containsKey("blood_type") && StringUtils.isNotEmpty(hisList.get(i).get("blood_type").toString())){
                                patient.setBloodType(hisList.get(i).get("blood_type").toString());//血型
                            }
                            if(hisList.get(i).containsKey("diagnosis_code") && StringUtils.isNotEmpty(hisList.get(i).get("diagnosis_code").toString())){
                                patient.setDiagnosisCode(hisList.get(i).get("diagnosis_code").toString());//诊断编码
                            }
                            if(hisList.get(i).containsKey("diagnosis_name") && StringUtils.isNotEmpty(hisList.get(i).get("diagnosis_name").toString())){
                                patient.setDiagnosisName(hisList.get(i).get("diagnosis_name").toString());//诊断名称
                            }
                            if(hisList.get(i).containsKey("doctor_code") && StringUtils.isNotEmpty(hisList.get(i).get("doctor_code").toString())){
                                patient.setDoctorCode(hisList.get(i).get("doctor_code").toString());//主管医生工号
                            }
                            if(hisList.get(i).containsKey("nurse_code") && StringUtils.isNotEmpty(hisList.get(i).get("nurse_code").toString())){
                                patient.setNurseCode(hisList.get(i).get("nurse_code").toString());//主管护士工号
                            }
                            if(hisList.get(i).containsKey("allergies") && StringUtils.isNotEmpty(hisList.get(i).get("allergies").toString())){
                                patient.setAllergies(hisList.get(i).get("allergies").toString());//过敏史
                            }
                            if(hisList.get(i).containsKey("nursing_level") && StringUtils.isNotEmpty(hisList.get(i).get("nursing_level").toString())){
                                patient.setNursingLevel(hisList.get(i).get("nursing_level").toString());//护理级别编码
                            }
                            if(hisList.get(i).containsKey("illness_state") && StringUtils.isNotEmpty(hisList.get(i).get("illness_state").toString())){
                                patient.setIllnessState(hisList.get(i).get("illness_state").toString());//病情编码
                            }
                            if(hisList.get(i).containsKey("positive") && StringUtils.isNotEmpty(hisList.get(i).get("positive").toString())){
                                patient.setPositive(hisList.get(i).get("positive").toString());//阳性
                            }
                            if(hisList.get(i).containsKey("department_code") && StringUtils.isNotEmpty(hisList.get(i).get("department_code").toString())){
                                patient.setDepartmentCode(hisList.get(i).get("department_code").toString());//当前科室编号
                            }
                            if(hisList.get(i).containsKey("from_department_code") && StringUtils.isNotEmpty(hisList.get(i).get("from_department_code").toString())){
                                patient.setToDepartmentCode(hisList.get(i).get("from_department_code").toString());//原科室编号
                            }
                            if(hisList.get(i).containsKey("ward_code") && StringUtils.isNotEmpty(hisList.get(i).get("ward_code").toString())){
                                patient.setWardCode(hisList.get(i).get("ward_code").toString());//当前病区编号
                            }
                            if(hisList.get(i).containsKey("bed_code") && StringUtils.isNotEmpty(hisList.get(i).get("bed_code").toString())){
                                patient.setBedCode(hisList.get(i).get("bed_code").toString());//当前床号
                                //添加床位安排记录
                                map2.put("bedCode",hisList.get(i).get("bed_code").toString());
                                BedArrange bedArrange =bedArrangeService.queryDataByCode(map2);
                                if(bedArrange!=null){
                                    //不为空则编辑
                                    bedArrange.setPatientId(patient.getId());
                                    bedArrangeService.update(bedArrange);
                                }else{
                                    //为空则新增
                                    BedArrange data=new BedArrange();
                                    data.setId(UUIDUtil.getUUID());
                                    data.setPatientId(patient.getId());
                                    data.setBedCode(hisList.get(i).get("bed_code").toString());
                                    bedArrangeService.insert(data);
                                }
                            }
                            if(hisList.get(i).containsKey("contact_man") && StringUtils.isNotEmpty(hisList.get(i).get("contact_man").toString())){
                                patient.setContactMan(hisList.get(i).get("contact_man").toString());//紧急联系人
                            }
                            if(hisList.get(i).containsKey("contact_phone") && StringUtils.isNotEmpty(hisList.get(i).get("contact_phone").toString())){
                                patient.setContactPhone(hisList.get(i).get("contact_phone").toString());//紧急联系电话
                            }
                            if(hisList.get(i).containsKey("remark") && StringUtils.isNotEmpty(hisList.get(i).get("remark").toString())){
                                patient.setRemark(hisList.get(i).get("remark").toString());//备注
                            }
                            patient.setFlag(1);//在院标志（0：出科；1：在科）
                            patient.setExitType("");//出科方式 (出院、转科、死亡、放弃、转院)
                            System.out.println("----"+i+"----"+patient.toString());
                            patientService.insert(patient);
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
