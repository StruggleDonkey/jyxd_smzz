package com.jyxd.web.his.controller;


import com.jyxd.web.data.basic.MedOrderExec;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.department.ChangeDepartmentRequest;
import com.jyxd.web.his.data.medOrderExec.AddOrdersRtRequest;
import com.jyxd.web.his.data.patient.CancelHospitallRequest;
import com.jyxd.web.his.data.patient.InHospitalRequest;
import com.jyxd.web.his.data.patient.PatientRequest;
import com.jyxd.web.service.basic.MedOrderExecService;
import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.util.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HisController {

    private static Logger logger= LoggerFactory.getLogger(HisController.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedOrderExecService medOrderExecService;

    /**
     * 患者基本信息接收
     * @param action
     * @param patientRequest
     * @return
     */
    @PostMapping(value = "/patientRegistry", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse patientRegistry(@RequestBody String action, @RequestBody PatientRequest patientRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(patientRequest!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
                PatientRequest.PatientRegistryRt patientRegistryRt =patientRequest.getBody().getPatientRegistryRt();
                Patient patient=new Patient();
                patient.setId(UUIDUtil.getUUID());
                patient.setCreateTime(new Date());
                patient.setVisitId(patientRegistryRt.getPATPatientID());//患者主索引
                patient.setName(patientRegistryRt.getPATPatientName());//患者姓名
                if(patientRegistryRt.getPATDob()!=null){
                    patient.setBirthday(sdf.format(patientRegistryRt.getPATDob()));//患者出生日期
                    patient.setAge(getAge(patientRegistryRt.getPATDob())+"");//年龄
                }
                patient.setSex(Integer.valueOf(patientRegistryRt.getPATSexCode()));//患者性别代码
                if(StringUtils.isNotEmpty(patientRegistryRt.getPATMaritalStatusCode())){
                    patient.setMaritalState(patientRegistryRt.getPATMaritalStatusCode());//患者婚姻状况代码
                }
                if(StringUtils.isNotEmpty(patientRegistryRt.getPATNationCode())){
                    patient.setRace(patientRegistryRt.getPATNationCode());//患者民族代码
                }
                if(patientRegistryRt.getPATDeceasedDate()!=null && patientRegistryRt.getPATDeceasedTime() !=null){
                    String deceasedDate=sdfd.format(patientRegistryRt.getPATDeceasedDate());
                    String deceasedTinme=sdft.format(patientRegistryRt.getPATDeceasedTime());
                    patient.setDeathTime(sdfd.parse(deceasedDate+" "+deceasedTinme));//患者死亡日期时间
                }

                PatientRequest.PATIdentity patIdentity=patientRequest.getBody().getPatientRegistryRt().getPATAddressList().getPATIdentity();
                if(StringUtils.isNotEmpty(patIdentity.getPATIdentityNum())){
                    patient.setIdCard(patIdentity.getPATIdentityNum());//患者证件号码
                    patient.setAge(IdNOToAge(patIdentity.getPATIdentityNum())+"");//年龄
                }

                PatientRequest.PATRelation patRelation=patientRequest.getBody().getPatientRegistryRt().getPATRelation();
                if(StringUtils.isNotEmpty(patRelation.getPATRelationName())){
                    patient.setContactMan(patRelation.getPATRelationName());//患者联系人姓名
                }
                if(StringUtils.isNotEmpty(patRelation.getPATRelationPhone())){
                    patient.setContactPhone(patRelation.getPATRelationPhone());//患者联系人电话
                }
                patientService.insert(patient);
            }

            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);


            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("patientRequest=======================================:"+patientRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 住院登记
     * @param action
     * @param inHospitalRequest
     * @return
     */
    @PostMapping(value = "/inpatientEncounterStarted", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse inpatientEncounterStarted(@RequestBody String action, @RequestBody InHospitalRequest inHospitalRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(inHospitalRequest!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
                InHospitalRequest.InpatientEncounterStartedRt inpatientEncounterStartedRt=inHospitalRequest.getBody().getInpatientEncounterStartedRt();
                Patient patient=patientService.getPatientByVisitId(inpatientEncounterStartedRt.getPATPatientID());//根据患者住索引查询病人
                patient.setVisitCode(inpatientEncounterStartedRt.getPAADMVisitNumber());//就诊号码 每次就诊一个号
                patient.setFlag(1);//在院标志（0：出科；1：在科）
                patient.setWardCode(inpatientEncounterStartedRt.getPAADMAdmWardCode());//入院病区代码
                patient.setBedCode(inpatientEncounterStartedRt.getPAADMCurBedNo());//病床号
                patient.setDepartmentCode(inpatientEncounterStartedRt.getPAADMAdmDeptCode());//入院就诊科室代码
                String date=sdfd.format(inpatientEncounterStartedRt.getPAADMStartDate());//入院日期
                String time=sdft.format(inpatientEncounterStartedRt.getPAADMStartTime());//入院时间
                patient.setVisitTime(sdf.parse(date+" "+time));//入院日期时间
                patient.setEnterTime(sdf.parse(date+" "+time));//入科时间

                InHospitalRequest.PAADMDiagnose paadmDiagnose=inHospitalRequest.getBody().getInpatientEncounterStartedRt().getPAADMDiagnoseList().getPAADMDiagnose();
                patient.setDiagnosisCode(paadmDiagnose.getPADDiagCode());//诊断代码
                patient.setDiagnosisName(paadmDiagnose.getPADDiagDesc());//诊断描述
                patient.setDoctorCode(paadmDiagnose.getPADDiagDocCode());//诊断医生代码

                patientService.update(patient);
            }

            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);


            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("inHospitalRequest=======================================:"+inHospitalRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 取消住院登记
     * @param action
     * @param cancelHospitallRequest
     * @return
     */
    @PostMapping(value = "/inpatientEncounterCancel", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse inpatientEncounterCancel(@RequestBody String action, @RequestBody CancelHospitallRequest cancelHospitallRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(cancelHospitallRequest!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
                CancelHospitallRequest.InpatientEncounterCancelRt inpatientEncounterCancelRt=cancelHospitallRequest.getBody().getInpatientEncounterCancelRt();
                Map<String,Object> map=new HashMap<>();
                map.put("visitId",inpatientEncounterCancelRt.getPATPatientID());//患者主索引
                map.put("visitCode",inpatientEncounterCancelRt.getPAADMVisitNumber());//就诊号码

                Patient patient=patientService.getPatientByConditions(map);
                patientService.update(patient);
            }

            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);


            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("cancelHospitallRequest=======================================:"+cancelHospitallRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 接收转科信息 病人转科成功后调用
     * @param action
     * @param changeDepartmentRequest
     * @return
     */
    @PostMapping(value = "/admTransaction", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse admTransaction(@RequestBody String action, @RequestBody ChangeDepartmentRequest changeDepartmentRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(changeDepartmentRequest!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
                ChangeDepartmentRequest.AdmTransactionRt admTransactionRt=changeDepartmentRequest.getBody().getAdmTransactionRt();
                Map<String,Object> map=new HashMap<>();
                map.put("visitId",admTransactionRt.getPATPatientID());//患者主索引
                map.put("visitCode",admTransactionRt.getPAADMVisitNumber());//就诊号码
                Patient patient=patientService.getPatientByConditions(map);

                String exitDate=sdfd.format(admTransactionRt.getPAADMTStartDate());//转出日期
                String exitTime=sdft.format(admTransactionRt.getPAADMTStartTime());//转出时间
                patient.setExitTime(sdf.parse(exitDate+" "+exitTime));//出科时间
                patient.setToDepartmentCode(admTransactionRt.getPAADMTOrigDeptCode());//转出科室代码

                String enterDate=sdfd.format(admTransactionRt.getPAADMTEndDate());//转入日期
                String enterTime=sdft.format(admTransactionRt.getPAADMTEndTime());//转入时间
                patient.setEnterTime(sdf.parse(enterDate+" "+enterTime));//入科时间
                patient.setDepartmentCode(admTransactionRt.getPAADMTTargDeptCode());//转入科室代码
                patient.setWardCode(admTransactionRt.getPAADMTTargWardCode());//转入病区代码
                patient.setBedCode(admTransactionRt.getPAADMTTargBedCode());//转入床位代码
                patient.setDoctorCode(admTransactionRt.getPAADMTTargDocCode());//转入医生代码

                switch (admTransactionRt.getPAADMTState()){
                    //01转出02转入03换床04换医生
                    case "01":
                        patient.setFlag(0);//在院标志（0：出科；1：在科）
                        patient.setExitType("转科");//出科方式 (出院、转科、死亡、放弃、转院)
                        break;
                    case "02":
                        patient.setFlag(1);//在院标志（0：出科；1：在科）
                        break;
                    case "03":
                        patient.setFlag(1);//在院标志（0：出科；1：在科）
                        break;
                    case "04":
                        patient.setFlag(1);//在院标志（0：出科；1：在科）
                        break;
                }

                patientService.update(patient);
            }

            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);


            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("changeDepartmentRequest=======================================:"+changeDepartmentRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 接收医嘱信息 医生开完医嘱保存成功后调用
     * @param action
     * @param addOrdersRtRequest
     * @return
     */
    @PostMapping(value = "/addOrders", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse addOrders(@RequestBody String action, @RequestBody AddOrdersRtRequest addOrdersRtRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(addOrdersRtRequest!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
                AddOrdersRtRequest.AddOrdersRt addOrdersRt=addOrdersRtRequest.getBody().getAddOrdersRt();
                Map<String,Object> map=new HashMap<>();
                map.put("visitId",addOrdersRt.getPATPatientID());//患者主索引
                map.put("visitCode",addOrdersRt.getPAADMVisitNumber());//就诊号码
                Patient patient=patientService.getPatientByConditions(map);

                MedOrderExec medOrderExec =new MedOrderExec();
                medOrderExec.setId(UUIDUtil.getUUID());
                medOrderExec.setVisitId(addOrdersRt.getPATPatientID());//addOrdersRt
                medOrderExec.setVisitCode(addOrdersRt.getPAADMVisitNumber());//就诊号码
                medOrderExec.setPatientId(patient.getId());
                medOrderExec.setCreateTime(new Date());





            }

            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);


            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("addOrdersRtRequest=======================================:"+addOrdersRtRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 根据出生日期计算年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
     public int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        }
            return age;
    }

    /**
     * 通过身份证号计算年龄
     * @param IdNO
     * @return
     */
    public int IdNOToAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }
}
