package com.jyxd.web.controller.assessment;

import com.jyxd.web.data.assessment.TransferAssessment;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.assessment.TransferAssessmentService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/transferAssessment")
public class TransferAssessmentController {

    private static Logger logger= LoggerFactory.getLogger(TransferAssessmentController.class);

    @Autowired
    private TransferAssessmentService transferAssessmentService;


    /**
     * 增加一条转科交接表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody TransferAssessment transferAssessment){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        transferAssessment.setId(UUIDUtil.getUUID());
        transferAssessment.setCreateTime(new Date());
        transferAssessmentService.insert(transferAssessment);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新转科交接表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            TransferAssessment transferAssessment=transferAssessmentService.queryData(map.get("id").toString());
            if(transferAssessment!=null){
                transferAssessment.setStatus((int)map.get("status"));
                transferAssessmentService.update(transferAssessment);
                json.put("msg","更新成功");
            }else{
                json.put("msg","更新失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑转科交接表记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            TransferAssessment transferAssessment=transferAssessmentService.queryData(map.get("id").toString());
            if(transferAssessment!=null){
                transferAssessment.setStatus((int)map.get("status"));
                transferAssessmentService.update(transferAssessment);
                json.put("msg","编辑成功");
            }else{
                json.put("msg","编辑失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());

        return json.toString();
    }

    /**
     * 删除转科交接表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map.containsKey("id")){
            TransferAssessment transferAssessment=transferAssessmentService.queryData(map.get("id").toString());
            if(transferAssessment!=null){
                transferAssessment.setStatus(-1);
                transferAssessmentService.update(transferAssessment);
                json.put("msg","删除成功");
            }else{
                json.put("msg","删除失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询转科交接表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map !=null && map.containsKey("id")){
            TransferAssessment transferAssessment=transferAssessmentService.queryData(map.get("id").toString());
            if(transferAssessment!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(transferAssessment));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询转科交接表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =transferAssessmentService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<TransferAssessment> list =transferAssessmentService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理评估-转科交接-保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveData",method= RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
       try {
           //如果包含assessmentId 说明是编辑时保存  不包含则新增
           if(map!=null && map.containsKey("assessmentId") && StringUtils.isNotEmpty(map.get("assessmentId").toString())){
               //首先根据如果包含assessmentId和病人id 查询 对象列表 如果有则删除后重新添加 没有则直接新增添加
               List<TransferAssessment> list=transferAssessmentService.getListByAssessmentIdAndPatientId(map);
               if(list!=null && list.size()>0){
                   for (int i = 0; i < list.size(); i++) {
                       list.get(i).setStatus(-1);
                       //删除
                       transferAssessmentService.update(list.get(i));
                   }
               }
           }else {
               //首先根据dataTime和病人id 查询 对象列表 如果有则删除后重新添加 没有则直接新增添加
               List<TransferAssessment> list=transferAssessmentService.getListByDataTimeAndPatientId(map);
               if(list!=null && list.size()>0){
                   for (int i = 0; i < list.size(); i++) {
                       list.get(i).setStatus(-1);
                       //删除
                       transferAssessmentService.update(list.get(i));
                   }
               }
           }
           String assessmentId=UUIDUtil.getUUID();
           User user=(User)session.getAttribute("user");
           String operatorCode="";
           if(user!=null){
               operatorCode=user.getLoginName();
           }
           Date createTime=new Date();
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date dataTime=null;
           if(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
               dataTime=format.parse(map.get("dataTime").toString());
           }

           //转出科室：sourceDepartment
           if(map.containsKey("sourceDepartment") && StringUtils.isNotEmpty(map.get("sourceDepartment").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("sourceDepartment");
               transferAssessment.setContent(map.get("sourceDepartment").toString());
               transferAssessmentService.insert(transferAssessment);

               //添加病人出入科记录表
               /*Map<String,Object> m=new HashMap<>();
               if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                   m.put("patientId",map.get("patientId").toString());
               }

               inOutIcuRecordService.geList(m);
               if(map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString())){
                   transferAssessment.setVisitId(map.get("visitId").toString());
               }
               if(map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString())){
                   transferAssessment.setVisitCode(map.get("visitCode").toString());
               }
               if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                   transferAssessment.setPatientId(map.get("patientId").toString());
               }*/
           }

           //床号：bedName
           if(map.containsKey("bedName") && StringUtils.isNotEmpty(map.get("bedName").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("bedName");
               transferAssessment.setContent(map.get("bedName").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //姓名：name
           if(map.containsKey("name") && StringUtils.isNotEmpty(map.get("name").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("name");
               transferAssessment.setContent(map.get("name").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //年龄：age
           if(map.containsKey("age") && StringUtils.isNotEmpty(map.get("age").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("age");
               transferAssessment.setContent(map.get("age").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //性别：sex
           if(map.containsKey("sex") && StringUtils.isNotEmpty(map.get("sex").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("sex");
               transferAssessment.setContent(map.get("sex").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //诊断：diagnosisName
           if(map.containsKey("diagnosisName") && StringUtils.isNotEmpty(map.get("diagnosisName").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("diagnosisName");
               transferAssessment.setContent(map.get("diagnosisName").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //转入科室：targetDepartment
           if(map.containsKey("targetDepartment") && StringUtils.isNotEmpty(map.get("targetDepartment").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("targetDepartment");
               transferAssessment.setContent(map.get("targetDepartment").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //佩戴腕带：wristTape （0：否 1：是）
           if(map.containsKey("wristTape") && StringUtils.isNotEmpty(map.get("wristTape").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("wristTape");
               transferAssessment.setContent(map.get("wristTape").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-体征监测时间：syncTime
           if(map.containsKey("syncTime") && StringUtils.isNotEmpty(map.get("syncTime").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("syncTime");
               transferAssessment.setContent(map.get("syncTime").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-T：t
           if(map.containsKey("t") && StringUtils.isNotEmpty(map.get("t").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("t");
               transferAssessment.setContent(map.get("t").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-P：p
           if(map.containsKey("p") && StringUtils.isNotEmpty(map.get("p").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("p");
               transferAssessment.setContent(map.get("p").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-R：r
           if(map.containsKey("r") && StringUtils.isNotEmpty(map.get("r").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("r");
               transferAssessment.setContent(map.get("r").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-BP：bp
           if(map.containsKey("bp") && StringUtils.isNotEmpty(map.get("bp").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("bp");
               transferAssessment.setContent(map.get("bp").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-spo2：spo2
           if(map.containsKey("spo2") && StringUtils.isNotEmpty(map.get("spo2").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("spo2");
               transferAssessment.setContent(map.get("spo2").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-意识：consciousness  （1：清醒 2：嗜睡 3：谵妄 4：浅昏迷 5：神昏迷 6：其他）
           if(map.containsKey("consciousness") && StringUtils.isNotEmpty(map.get("consciousness").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("consciousness");
               transferAssessment.setContent(map.get("consciousness").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //病情-意识-其他-内容：otherConsciousness
           if(map.containsKey("otherConsciousness") && StringUtils.isNotEmpty(map.get("otherConsciousness").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("otherConsciousness");
               transferAssessment.setContent(map.get("otherConsciousness").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-类型：infusionPunctureType  （1：周围静脉钢针 2：周围静脉置管 3：深静脉置管 4：PICC）
           if(map.containsKey("infusionPunctureType") && StringUtils.isNotEmpty(map.get("infusionPunctureType").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("infusionPunctureType");
               transferAssessment.setContent(map.get("infusionPunctureType").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-通畅：infustionClear  （1：是  2：否）
           if(map.containsKey("infustionClear") && StringUtils.isNotEmpty(map.get("infustionClear").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("infustionClear");
               transferAssessment.setContent(map.get("infustionClear").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-静脉输液：isInfustioning  （1：无  2：有）
           if(map.containsKey("isInfustioning") && StringUtils.isNotEmpty(map.get("isInfustioning").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("isInfustioning");
               transferAssessment.setContent(map.get("isInfustioning").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-输液通路：infustionPathNum
           if(map.containsKey("infustionPathNum") && StringUtils.isNotEmpty(map.get("infustionPathNum").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("infustionPathNum");
               transferAssessment.setContent(map.get("infustionPathNum").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-输液中药液：infustionDrug
           if(map.containsKey("infustionDrug") && StringUtils.isNotEmpty(map.get("infustionDrug").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("infustionDrug");
               transferAssessment.setContent(map.get("infustionDrug").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //静脉输液-剩余约：infustionLeftOver
           if(map.containsKey("infustionLeftOver") && StringUtils.isNotEmpty(map.get("infustionLeftOver").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("infustionLeftOver");
               transferAssessment.setContent(map.get("infustionLeftOver").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //皮肤完整性：skinComplete  （1：完整 2：压疮 3：破损 4：其他）
           if(map.containsKey("skinComplete") && StringUtils.isNotEmpty(map.get("skinComplete").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("skinComplete");
               transferAssessment.setContent(map.get("skinComplete").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //皮肤完整性-情况描述：skinOtherDescription
           if(map.containsKey("skinOtherDescription") && StringUtils.isNotEmpty(map.get("skinOtherDescription").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("skinOtherDescription");
               transferAssessment.setContent(map.get("skinOtherDescription").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //各种管路-人工气道：hasArtificialAirway（1 ：有 2：无）
           if(map.containsKey("hasArtificialAirway") && StringUtils.isNotEmpty(map.get("hasArtificialAirway").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("hasArtificialAirway");
               transferAssessment.setContent(map.get("hasArtificialAirway").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //各种管路-人工气道-有：artificialAirwayType（1 ：气管插管 2：气管切开）
           if(map.containsKey("artificialAirwayType") && StringUtils.isNotEmpty(map.get("artificialAirwayType").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("artificialAirwayType");
               transferAssessment.setContent(map.get("artificialAirwayType").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //各种管路-引流管：hasDrainageTube（1 ：无 2：有）
           if(map.containsKey("hasDrainageTube") && StringUtils.isNotEmpty(map.get("hasDrainageTube").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("hasDrainageTube");
               transferAssessment.setContent(map.get("hasDrainageTube").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //各种管路-引流管-有：drainageTubeList  （1：导尿管 2：盆腔引流管 3：皮下引流管 4：T管 5：头部引流管 6：颈部引流管 7：胸腔引流管 8：腹腔引流管 9：其他   传参时传 以逗号分隔的字符串，如 1,2,4,5）
           if(map.containsKey("drainageTubeList") && StringUtils.isNotEmpty(map.get("drainageTubeList").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("drainageTubeList");
               transferAssessment.setContent(map.get("drainageTubeList").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //各种管路-引流管-有-内容：otherDrainageTube
           if(map.containsKey("otherDrainageTube") && StringUtils.isNotEmpty(map.get("otherDrainageTube").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("otherDrainageTube");
               transferAssessment.setContent(map.get("otherDrainageTube").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //其他-病例：hasMedicalRecord （1：无 2：有）
           if(map.containsKey("hasMedicalRecord") && StringUtils.isNotEmpty(map.get("hasMedicalRecord").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("hasMedicalRecord");
               transferAssessment.setContent(map.get("hasMedicalRecord").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //其他-病人特殊物品：hasSpecialThing （1：无 2：有）
           if(map.containsKey("hasSpecialThing") && StringUtils.isNotEmpty(map.get("hasSpecialThing").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("hasSpecialThing");
               transferAssessment.setContent(map.get("hasSpecialThing").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //其他-病人特殊物品-内容：specialThing
           if(map.containsKey("specialThing") && StringUtils.isNotEmpty(map.get("specialThing").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("specialThing");
               transferAssessment.setContent(map.get("specialThing").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //其他-特殊交接：specialTransfer
           if(map.containsKey("specialTransfer") && StringUtils.isNotEmpty(map.get("specialTransfer").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("specialTransfer");
               transferAssessment.setContent(map.get("specialTransfer").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //转出科护士签字：transferNurseSignature
           if(map.containsKey("transferNurseSignature") && StringUtils.isNotEmpty(map.get("transferNurseSignature").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("transferNurseSignature");
               transferAssessment.setContent(map.get("transferNurseSignature").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //出科时间：transferTime
           if(map.containsKey("transferTime") && StringUtils.isNotEmpty(map.get("transferTime").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("transferTime");
               transferAssessment.setContent(map.get("transferTime").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //转入科护士签字：inNurseSignature
           if(map.containsKey("inNurseSignature") && StringUtils.isNotEmpty(map.get("inNurseSignature").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("inNurseSignature");
               transferAssessment.setContent(map.get("inNurseSignature").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           //入科时间：inTime
           if(map.containsKey("inTime") && StringUtils.isNotEmpty(map.get("inTime").toString())){
               TransferAssessment transferAssessment=newTransferAssessment(map,operatorCode,assessmentId,createTime,dataTime);
               transferAssessment.setCode("inTime");
               transferAssessment.setContent(map.get("inTime").toString());
               transferAssessmentService.insert(transferAssessment);
           }

           json.put("msg","成功");
           json.put("code",HttpCode.OK_CODE.getCode());

       }catch (Exception e){
           logger.info(("护理评估-转科交接-保存:")+e);
       }
        return json.toString();
    }

    private TransferAssessment newTransferAssessment(Map<String,Object> map, String operatorCode, String assessmentId, Date createTime, Date dataTime){
        TransferAssessment transferAssessment=new TransferAssessment();
        try {
            transferAssessment.setId(UUIDUtil.getUUID());
            transferAssessment.setStatus(1);
            transferAssessment.setCreateTime(createTime);
            transferAssessment.setAssessmentId(assessmentId);
            transferAssessment.setOperatorCode(operatorCode);
            transferAssessment.setDataTime(dataTime);
            if(map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString())){
                transferAssessment.setVisitId(map.get("visitId").toString());
            }
            if(map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString())){
                transferAssessment.setVisitCode(map.get("visitCode").toString());
            }
            if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                transferAssessment.setPatientId(map.get("patientId").toString());
            }
        }catch (Exception e){
            logger.info("newTransferAssessment:"+e);
        }
        return transferAssessment;
    }

    /**
     * 护理评估--转科交接--历史列表--分页查询列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list =transferAssessmentService.getList(map);
        if(map!=null && map.containsKey("start")){
            int totalCount =transferAssessmentService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理评估--转科交接--历史列表--删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData",method= RequestMethod.POST)
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        List<TransferAssessment> list =transferAssessmentService.getListByAssessmentIdAndPatientId(map);
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setStatus(-1);
                transferAssessmentService.update(list.get(i));
            }
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 护理评估--转科交接--历史列表--选择
     * @param map
     * @return
     */
    @RequestMapping(value = "/chooseData",method= RequestMethod.POST)
    @ResponseBody
    public String chooseData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        Map<String,Object> data =transferAssessmentService.getDataByAssessmentId(map);
        if(data!=null){
            json.put("data",JSONObject.fromObject(data));
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

}
