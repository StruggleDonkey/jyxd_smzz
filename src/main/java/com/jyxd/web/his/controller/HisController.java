package com.jyxd.web.his.controller;


import com.jyxd.web.data.basic.MedOrderExec;
import com.jyxd.web.data.dictionary.BedDictionary;
import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.data.dictionary.WardDictionary;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.data.user.User;
import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.department.ChangeDepartmentRequest;
import com.jyxd.web.his.data.dictionary.ReceiveBedRequest;
import com.jyxd.web.his.data.dictionary.ReceiveDepartmentRequest;
import com.jyxd.web.his.data.dictionary.ReceiveUserRequest;
import com.jyxd.web.his.data.dictionary.ReceiveWardRequest;
import com.jyxd.web.his.data.medOrderExec.AddOrdersRtRequest;
import com.jyxd.web.his.data.patient.CancelHospitallRequest;
import com.jyxd.web.his.data.patient.InHospitalRequest;
import com.jyxd.web.his.data.patient.PatientRequest;
import com.jyxd.web.service.basic.MedOrderExecService;
import com.jyxd.web.service.dictionary.BedDictionaryService;
import com.jyxd.web.service.dictionary.DepartmentDictionaryService;
import com.jyxd.web.service.dictionary.WardDictionaryService;
import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.service.user.UserService;
import com.jyxd.web.util.MD5Util;
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

    @Autowired
    private BedDictionaryService bedDictionaryService;

    @Autowired
    private WardDictionaryService wardDictionaryService;

    @Autowired
    private DepartmentDictionaryService departmentDictionaryService;

    @Autowired
    private UserService userService;

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

                AddOrdersRtRequest.OEORIInfoList oeoriInfoList=addOrdersRt.getOEORIInfoList();
                AddOrdersRtRequest.OEORIInfo oeoriInfo=oeoriInfoList.getOEORIInfo();

                medOrderExec.setOrderName(oeoriInfo.getOEORIARCItmMastDesc());//医嘱项目描述
                medOrderExec.setSpecs(oeoriInfo.getOEORISpecification());//医嘱规格
                switch (oeoriInfo.getOEORIClass()){//医嘱类别代码 检查类 西药类 中药类
                    case "检查类":
                        medOrderExec.setDrugType(0);//是否为药嘱（0：否  1：是）
                     break;
                    case "西药类":
                        medOrderExec.setDrugType(1);//是否为药嘱（0：否  1：是）
                        break;
                    case "中药类":
                        medOrderExec.setDrugType(1);//是否为药嘱（0：否  1：是）
                        break;
                }
                medOrderExec.setOrderAttr(oeoriInfo.getOEORIDoseFormsDesc());//剂型描述 补液类型（如：晶体液、胶体液等）
                String defaultStart=sdfd.format(oeoriInfo.getOEORIRequireExecDate());//要求执行日期 YYYY-MM-DD
                String defaultEnd=sdft.format(oeoriInfo.getOEORIRequireExecTime());//要求执行时间 hh:mm:ss
                medOrderExec.setDefaultTimePoint(sdf.parse(defaultStart+" "+defaultEnd));//计划执行时间
                String completeStart=sdfd.format(oeoriInfo.getOEORIStopDate());//医嘱停止日期 YYYY-MM-DD
                String completeEnd=sdft.format(oeoriInfo.getOEORIStopTime());//医嘱停止时间 hh:mm:ss
                medOrderExec.setCompleteTimePoint(sdf.parse(completeStart+""+completeEnd));//执行完成时间
                if(StringUtils.isNotEmpty(oeoriInfo.getOEORIDoseQty())){
                    medOrderExec.setDosage(oeoriInfo.getOEORIDoseQty());//单次剂量
                }
                if(StringUtils.isNotEmpty(oeoriInfo.getOEORIDoseUnitDesc())){
                    medOrderExec.setDosageUnits(oeoriInfo.getOEORIDoseUnitDesc());//单次剂量单位描述
                }
                medOrderExec.setAllDosage(oeoriInfo.getOEORIOrderQty());//医嘱数量
                switch (oeoriInfo.getOEORIPriorityDesc()){//医嘱类型描述 长期、临时、自备长期、自备临时、出院带药
                    case "长期":
                        medOrderExec.setRepeatIndicator(1);//医嘱类型，0：临时医嘱；1：长期医嘱
                        break;
                    case "临时":
                        medOrderExec.setRepeatIndicator(0);//医嘱类型，0：临时医嘱；1：长期医嘱
                        break;
                    case "自备长期":
                        medOrderExec.setRepeatIndicator(1);//医嘱类型，0：临时医嘱；1：长期医嘱
                        break;
                    case "自备临时":
                        medOrderExec.setRepeatIndicator(0);//医嘱类型，0：临时医嘱；1：长期医嘱
                        break;
                    case "出院带药":
                        medOrderExec.setRepeatIndicator(1);//医嘱类型，0：临时医嘱；1：长期医嘱
                         break;

                }
                if(StringUtils.isNotEmpty(oeoriInfo.getOEORIInstrCode())){
                    medOrderExec.setUseMode(oeoriInfo.getOEORIInstrCode());//用药途径代码
                }
                medOrderExec.setClassType(oeoriInfo.getOEORIClass());//医嘱类别代码 检查类 西药类 中药类
                medOrderExec.setFrequency(oeoriInfo.getOEORIFreqDesc());//频次描述
                medOrderExec.setPerformSpeed("");//流速
                medOrderExec.setOrderStatus(Integer.valueOf(oeoriInfo.getOEORIStatusCode()));//医嘱状态代码  执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
                if(StringUtils.isNotEmpty(oeoriInfo.getOEORIRemarks())){
                    medOrderExec.setRemark(oeoriInfo.getOEORIRemarks());//医嘱备注信息
                }
                medOrderExec.setUpdateTime(new Date());//记录最后修改时间
                medOrderExec.setIsSync(0);//是否已同步到护理单，0：未同步；1：已同步；
                medOrderExec.setOrderExecNum(1);//医嘱每天执行次数 默认1
                medOrderExec.setSyncNum(1);//剩余同步次数 默认1
                //medOrderExec.setRecentSyncTime();//最新同步时间
                medOrderExecService.insert(medOrderExec);
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
     * 接收床位字典信息
     * @param action
     * @param receiveBedRequest
     * @return
     */
    @PostMapping(value = "/receiveBed", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse receiveBed(@RequestBody String action, @RequestBody ReceiveBedRequest receiveBedRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(receiveBedRequest!=null){
                ReceiveBedRequest.CT_Bed ct_bed=receiveBedRequest.getBody().getCT_BedList().getCT_Bed();
                BedDictionary bedDictionary=new BedDictionary();
                bedDictionary.setId(UUIDUtil.getUUID());
                switch (ct_bed.getCTB_Status()){//1启用0停用-1删除
                    case "1":
                        bedDictionary.setStatus(1);
                        break;
                    case "0":
                        bedDictionary.setStatus(0);
                        break;
                    case "-1":
                        bedDictionary.setStatus(-1);
                        break;
                }
                bedDictionary.setBedCode(ct_bed.getCTB_Code());//床位代码
                if(StringUtils.isNotEmpty(ct_bed.getCTB_Desc())){
                    bedDictionary.setBedName(ct_bed.getCTB_Desc());//床位描述
                }
                bedDictionaryService.insert(bedDictionary);
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
            logger.info("receiveBedRequest=======================================:"+receiveBedRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 接收病区字典信息
     * @param action
     * @param receiveWardRequest
     * @return
     */
    @PostMapping(value = "/receiveWard", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse receiveWard(@RequestBody String action, @RequestBody ReceiveWardRequest receiveWardRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(receiveWardRequest!=null){
                ReceiveWardRequest.CT_Ward ct_ward=receiveWardRequest.getBody().getCT_WardList().getCT_Ward();
                WardDictionary wardDictionary=new WardDictionary();
                wardDictionary.setId(UUIDUtil.getUUID());
                wardDictionary.setStatus(1);
                wardDictionary.setWardCode(ct_ward.getCTW_Code());
                wardDictionary.setWardName(ct_ward.getCTW_Desc());
                switch (ct_ward.getCTW_Status()){//1启用0停用-1删除
                    case "1":
                        wardDictionary.setStatus(1);
                        break;
                    case "0":
                        wardDictionary.setStatus(0);
                        break;
                    case "-1":
                        wardDictionary.setStatus(-1);
                        break;
                }
                wardDictionaryService.insert(wardDictionary);
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
            logger.info("receiveWardRequest=======================================:"+receiveWardRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 接收科室字典信息
     * @param action
     * @param receiveDepartmentRequest
     * @return
     */
    @PostMapping(value = "/receiveDepartment", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse receiveDepartment(@RequestBody String action, @RequestBody ReceiveDepartmentRequest receiveDepartmentRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(receiveDepartmentRequest!=null){
                ReceiveDepartmentRequest.CT_Dept ct_dept=receiveDepartmentRequest.getBody().getCT_DeptList().getCT_Dept();
                DepartmentDictionary departmentDictionary=new DepartmentDictionary();
                departmentDictionary.setId(UUIDUtil.getUUID());
                departmentDictionary.setDepartmentCode(ct_dept.getCTD_Code());
                departmentDictionary.setDepartmentName(ct_dept.getCTD_Desc());
                switch (ct_dept.getCTD_Status()){//1启用0停用-1删除
                    case "1":
                        departmentDictionary.setStatus(1);
                        break;
                    case "0":
                        departmentDictionary.setStatus(0);
                        break;
                    case "-1":
                        departmentDictionary.setStatus(-1);
                        break;
                }
                departmentDictionaryService.insert(departmentDictionary);
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
            logger.info("receiveDepartmentRequest=======================================:"+receiveDepartmentRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }

    /**
     * 接收医护人员字典信息
     * @param action
     * @param receiveUserRequest
     * @return
     */
    @PostMapping(value = "/receiveUser", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse receiveUser(@RequestBody String action, @RequestBody ReceiveUserRequest receiveUserRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            if(receiveUserRequest!=null){
                ReceiveUserRequest.CT_CareProv ct_careProv=receiveUserRequest.getBody().getCT_CareProvList().getCT_CareProv();
                User user=new User();
                user.setId(UUIDUtil.getUUID());
                user.setCreateTime(new Date());
                user.setUserName(ct_careProv.getCTCP_Desc());//职工姓名
                user.setLoginName(ct_careProv.getCTCP_Desc());//职工姓名
                if(StringUtils.isNotEmpty(ct_careProv.getCTCP_SexCode()) && "1".equals(ct_careProv.getCTCP_SexCode())){//1 男  2女
                    user.setSex(1);
                }else {
                    user.setSex(0);
                }
                SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
                if(ct_careProv.getCTCP_StartDate()!=null){
                    user.setEnterTime(ct_careProv.getCTCP_StartDate());//有效开始日期
                }
                if(ct_careProv.getCTCP_EndDate()!=null){
                    user.setExitTime(ct_careProv.getCTCP_EndDate());//有效结束日期
                }
                switch (ct_careProv.getCTCP_Status()){//1启用0停用-1删除
                    case "1":
                        user.setStatus(1);
                        break;
                    case "0":
                        user.setStatus(0);
                        break;
                    case "-1":
                        user.setStatus(-1);
                        break;
                }
                if(StringUtils.isNotEmpty(ct_careProv.getCTCP_PassWord())){
                   user.setPassword( MD5Util.string2MD5(ct_careProv.getCTCP_PassWord()));
                }else {
                    user.setPassword("e10adc3949ba59abbe56e057f20f883e");//123456
                }
                user.setWorkNumber(ct_careProv.getCTCP_Code());//职工代码
                if(StringUtils.isNotEmpty(ct_careProv.getCTCP_Name())){
                    user.setSimplicity(ct_careProv.getCTCP_Name());//姓名
                }
                if(StringUtils.isNotEmpty(ct_careProv.getCTCP_StaffType()) && "NURSE".equals(ct_careProv.getCTCP_StaffType())){
                    user.setIsShedual(1);//是否参与排班（0：不参与 1：参与）
                }else {
                    user.setIsShedual(0);//是否参与排班（0：不参与 1：参与）
                }
                userService.insert(user);

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
            logger.info("receiveUserRequest=======================================:"+receiveUserRequest.toString());
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
