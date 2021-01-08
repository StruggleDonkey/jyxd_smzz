package com.jyxd.web.controller.assessment;

import com.jyxd.web.data.assessment.HospitalizedAssessment;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.assessment.HospitalizedAssessmentService;
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
@RequestMapping(value = "/hospitalizedAssessment")
public class HospitalizedAssessmentController {

    private static Logger logger= LoggerFactory.getLogger(HospitalizedAssessmentController.class);

    @Autowired
    private HospitalizedAssessmentService hospitalizedAssessmentService;

    /**
     * 增加一条入院护理评估单记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody HospitalizedAssessment hospitalizedAssessment){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        hospitalizedAssessment.setId(UUIDUtil.getUUID());
        hospitalizedAssessment.setCreateTime(new Date());
        hospitalizedAssessmentService.insert(hospitalizedAssessment);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新入院护理评估单记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus((int)map.get("status"));
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 编辑入院护理评估单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus((int)map.get("status"));
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 删除入院护理评估单记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map.containsKey("id")){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus(-1);
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 根据主键id查询入院护理评估单记录
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
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(hospitalizedAssessment));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询入院护理评估单记录列表（也可以不分页）
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
        List<HospitalizedAssessment> list =hospitalizedAssessmentService.queryList(map);
        if(map!=null && map.containsKey("start")){
            int totalCount =hospitalizedAssessmentService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        if(list!=null && list.size()>0){
            json.put("msg","成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理评估--入院护理评估单--保存
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
                List<HospitalizedAssessment> list=hospitalizedAssessmentService.getListByAssessmentIdAndPatientId(map);
                if(list!=null && list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setStatus(-1);
                        //删除
                        hospitalizedAssessmentService.update(list.get(i));
                    }
                }
            }else {
                //首先根据dataTime和病人id 查询 对象列表 如果有则删除后重新添加 没有则直接新增添加
                List<HospitalizedAssessment> list=hospitalizedAssessmentService.getListByDataTimeAndPatientId(map);
                if(list!=null && list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setStatus(-1);
                        //删除
                        hospitalizedAssessmentService.update(list.get(i));
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

            //联系人及电话：contactPerson
            if(map.containsKey("contactPerson") && StringUtils.isNotEmpty(map.get("contactPerson").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("contactPerson");
                hospitalizedAssessment.setContent(map.get("contactPerson").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //民族：nation  （通用字典）
            if(map.containsKey("nation") && StringUtils.isNotEmpty(map.get("nation").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("nation");
                hospitalizedAssessment.setContent(map.get("nation").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //职业：occupation   （通用字典）
            if(map.containsKey("occupation") && StringUtils.isNotEmpty(map.get("occupation").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("occupation");
                hospitalizedAssessment.setContent(map.get("occupation").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //婚姻状况：maritalStatus  （通用字典）
            if(map.containsKey("maritalStatus") && StringUtils.isNotEmpty(map.get("maritalStatus").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("maritalStatus");
                hospitalizedAssessment.setContent(map.get("maritalStatus").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //文化程度：education  （通用字典）
            if(map.containsKey("education") && StringUtils.isNotEmpty(map.get("education").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("education");
                hospitalizedAssessment.setContent(map.get("education").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院时间：visitTime
            if(map.containsKey("visitTime") && StringUtils.isNotEmpty(map.get("visitTime").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitTime");
                hospitalizedAssessment.setContent(map.get("visitTime").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院诊断：visitDiagnosis
            if(map.containsKey("visitDiagnosis") && StringUtils.isNotEmpty(map.get("visitDiagnosis").toString()))
            //入院方式：visitMethod  （1:步行 2：轮椅 3：平车 4：其他）
            if(map.containsKey("visitMethod") && StringUtils.isNotEmpty(map.get("visitMethod").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitMethod");
                hospitalizedAssessment.setContent(map.get("visitMethod").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院方式-其他-内容：visitMethodOther
            if(map.containsKey("visitMethodOther") && StringUtils.isNotEmpty(map.get("visitMethodOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitMethodOther");
                hospitalizedAssessment.setContent(map.get("visitMethodOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史：pastHistory （0：无  1：有）
            if(map.containsKey("pastHistory") && StringUtils.isNotEmpty(map.get("pastHistory").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pastHistory");
                hospitalizedAssessment.setContent(map.get("pastHistory").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-高血压：hypertension （选中传 1）
            if(map.containsKey("hypertension") && StringUtils.isNotEmpty(map.get("hypertension").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("hypertension");
                hospitalizedAssessment.setContent(map.get("hypertension").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-心脏病：heartDisease    （选中传 1）
            if(map.containsKey("heartDisease") && StringUtils.isNotEmpty(map.get("heartDisease").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("heartDisease");
                hospitalizedAssessment.setContent(map.get("heartDisease").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-糖尿病：diabetes    （选中传 1）
            if(map.containsKey("diabetes") && StringUtils.isNotEmpty(map.get("diabetes").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("diabetes");
                hospitalizedAssessment.setContent(map.get("diabetes").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-脑血管病：cerebralVascularDisease    （选中传 1）
            if(map.containsKey("cerebralVascularDisease") && StringUtils.isNotEmpty(map.get("cerebralVascularDisease").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("cerebralVascularDisease");
                hospitalizedAssessment.setContent(map.get("cerebralVascularDisease").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-精神病：mentalDisease   （选中传 1）
            if(map.containsKey("mentalDisease") && StringUtils.isNotEmpty(map.get("mentalDisease").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("mentalDisease");
                hospitalizedAssessment.setContent(map.get("mentalDisease").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-手术史：operationHistory    （选中传 1）
            if(map.containsKey("operationHistory") && StringUtils.isNotEmpty(map.get("operationHistory").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("operationHistory");
                hospitalizedAssessment.setContent(map.get("operationHistory").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-手术史-内容：operationHistoryContent
            if(map.containsKey("operationHistoryContent") && StringUtils.isNotEmpty(map.get("operationHistoryContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("operationHistoryContent");
                hospitalizedAssessment.setContent(map.get("operationHistoryContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-其他：pastHistoryOther
            if(map.containsKey("pastHistoryOther") && StringUtils.isNotEmpty(map.get("pastHistoryOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pastHistoryOther");
                hospitalizedAssessment.setContent(map.get("pastHistoryOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //既往史-其他-内容：pastHistoryOtherContent
            if(map.containsKey("pastHistoryOtherContent") && StringUtils.isNotEmpty(map.get("pastHistoryOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pastHistoryOtherContent");
                hospitalizedAssessment.setContent(map.get("pastHistoryOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //过敏史：allergyHistory （0：无  1：有）
            if(map.containsKey("allergyHistory") && StringUtils.isNotEmpty(map.get("allergyHistory").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("allergyHistory");
                hospitalizedAssessment.setContent(map.get("allergyHistory").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //过敏史-药物：allergyDrug
            if(map.containsKey("allergyDrug") && StringUtils.isNotEmpty(map.get("allergyDrug").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("allergyDrug");
                hospitalizedAssessment.setContent(map.get("allergyDrug").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //过敏史-食物：allergyFood
            if(map.containsKey("allergyFood") && StringUtils.isNotEmpty(map.get("allergyFood").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("allergyFood");
                hospitalizedAssessment.setContent(map.get("allergyFood").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //过敏史-其他：allergyOther
            if(map.containsKey("allergyOther") && StringUtils.isNotEmpty(map.get("allergyOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("allergyOther");
                hospitalizedAssessment.setContent(map.get("allergyOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-时间：measureTime
            if(map.containsKey("measureTime") && StringUtils.isNotEmpty(map.get("measureTime").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("measureTime");
                hospitalizedAssessment.setContent(map.get("measureTime").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-T：visitT
            if(map.containsKey("visitT") && StringUtils.isNotEmpty(map.get("visitT").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitT");
                hospitalizedAssessment.setContent(map.get("visitT").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-P：visitP
            if(map.containsKey("visitP") && StringUtils.isNotEmpty(map.get("visitP").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitP");
                hospitalizedAssessment.setContent(map.get("visitP").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-R：visitR
            if(map.containsKey("visitR") && StringUtils.isNotEmpty(map.get("visitR").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitR");
                hospitalizedAssessment.setContent(map.get("visitR").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-BP：visitBP
            if(map.containsKey("visitBP") && StringUtils.isNotEmpty(map.get("visitBP").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitBP");
                hospitalizedAssessment.setContent(map.get("visitBP").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理评估-体重：visitWeight
            if(map.containsKey("visitWeight") && StringUtils.isNotEmpty(map.get("visitWeight").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitWeight");
                hospitalizedAssessment.setContent(map.get("visitWeight").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //神志：mind （1：清楚 2：嗜睡 3：意识模糊 4：昏睡 5：浅昏迷 6：中昏迷 7：深昏迷 8：痴呆 ）
            if(map.containsKey("mind") && StringUtils.isNotEmpty(map.get("mind").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("mind");
                hospitalizedAssessment.setContent(map.get("mind").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //沟通方式-语言：communicationLanguage （选中传 1）
            if(map.containsKey("communicationLanguage") && StringUtils.isNotEmpty(map.get("communicationLanguage").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("communicationLanguage");
                hospitalizedAssessment.setContent(map.get("communicationLanguage").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //沟通方式-文字：communicationFont （选中传 1）
            if(map.containsKey("communicationFont") && StringUtils.isNotEmpty(map.get("communicationFont").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("communicationFont");
                hospitalizedAssessment.setContent(map.get("communicationFont").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //沟通方式-手势：communicationGesture （选中传 1）
            if(map.containsKey("communicationGesture") && StringUtils.isNotEmpty(map.get("communicationGesture").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("communicationGesture");
                hospitalizedAssessment.setContent(map.get("communicationGesture").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //沟通方式-无法评估：communicationNo （选中传 1）
            if(map.containsKey("communicationNo") && StringUtils.isNotEmpty(map.get("communicationNo").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("communicationNo");
                hospitalizedAssessment.setContent(map.get("communicationNo").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //理解能力-良好：comprehensionAbilityGood （选中传 1）
            if(map.containsKey("comprehensionAbilityGood") && StringUtils.isNotEmpty(map.get("comprehensionAbilityGood").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("comprehensionAbilityGood");
                hospitalizedAssessment.setContent(map.get("comprehensionAbilityGood").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //理解能力-一般：comprehensionAbilityCommonly （选中传 1）
            if(map.containsKey("comprehensionAbilityCommonly") && StringUtils.isNotEmpty(map.get("comprehensionAbilityCommonly").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("comprehensionAbilityCommonly");
                hospitalizedAssessment.setContent(map.get("comprehensionAbilityCommonly").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //理解能力-差：comprehensionAbilityBad （选中传 1）
            if(map.containsKey("comprehensionAbilityBad") && StringUtils.isNotEmpty(map.get("comprehensionAbilityBad").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("comprehensionAbilityBad");
                hospitalizedAssessment.setContent(map.get("comprehensionAbilityBad").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //理解能力-无法评估：comprehensionAbilityNo （选中传 1）
            if(map.containsKey("comprehensionAbilityNo") && StringUtils.isNotEmpty(map.get("comprehensionAbilityNo").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("comprehensionAbilityNo");
                hospitalizedAssessment.setContent(map.get("comprehensionAbilityNo").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //视力：vision （0：异常 1：正常）
            if(map.containsKey("vision") && StringUtils.isNotEmpty(map.get("vision").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("vision");
                hospitalizedAssessment.setContent(map.get("vision").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //视力-异常-内容：visionAbnormal
            if(map.containsKey("visionAbnormal") && StringUtils.isNotEmpty(map.get("visionAbnormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visionAbnormal");
                hospitalizedAssessment.setContent(map.get("visionAbnormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //听力：hearing（0：异常 1：正常）
            if(map.containsKey("hearing") && StringUtils.isNotEmpty(map.get("hearing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("hearing");
                hospitalizedAssessment.setContent(map.get("hearing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //听力-异常-内容：hearingAbnormal
            if(map.containsKey("hearingAbnormal") && StringUtils.isNotEmpty(map.get("hearingAbnormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("hearingAbnormal");
                hospitalizedAssessment.setContent(map.get("hearingAbnormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //义齿：falseTooth （0：无 1：有）
            if(map.containsKey("falseTooth") && StringUtils.isNotEmpty(map.get("falseTooth").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("falseTooth");
                hospitalizedAssessment.setContent(map.get("falseTooth").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //口腔黏膜：oralMucosa （0：破损 1：完整 2：其他）
            if(map.containsKey("oralMucosa") && StringUtils.isNotEmpty(map.get("oralMucosa").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("oralMucosa");
                hospitalizedAssessment.setContent(map.get("oralMucosa").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //口腔黏膜-其他-内容：oralMucosaOther
            if(map.containsKey("oralMucosaOther") && StringUtils.isNotEmpty(map.get("oralMucosaOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("oralMucosaOther");
                hospitalizedAssessment.setContent(map.get("oralMucosaOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-正常：skinNormal    （选中传 1）
            if(map.containsKey("skinNormal") && StringUtils.isNotEmpty(map.get("skinNormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinNormal");
                hospitalizedAssessment.setContent(map.get("skinNormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-水肿：skinEdema    （选中传 1）
            if(map.containsKey("skinEdema") && StringUtils.isNotEmpty(map.get("skinEdema").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinEdema");
                hospitalizedAssessment.setContent(map.get("skinEdema").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-黄疸：skinJaundice    （选中传 1）
            if(map.containsKey("skinJaundice") && StringUtils.isNotEmpty(map.get("skinJaundice").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinJaundice");
                hospitalizedAssessment.setContent(map.get("skinJaundice").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-苍白：skinPale    （选中传 1）
            if(map.containsKey("skinPale") && StringUtils.isNotEmpty(map.get("skinPale").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinPale");
                hospitalizedAssessment.setContent(map.get("skinPale").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-发绀：skinCyanosis    （选中传 1）
            if(map.containsKey("skinCyanosis") && StringUtils.isNotEmpty(map.get("skinCyanosis").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinCyanosis");
                hospitalizedAssessment.setContent(map.get("skinCyanosis").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-皮疹：skinRash    （选中传 1）
            if(map.containsKey("skinRash") && StringUtils.isNotEmpty(map.get("skinRash").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinRash");
                hospitalizedAssessment.setContent(map.get("skinRash").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-瘀斑：skinEcchymosis    （选中传 1）
            if(map.containsKey("skinEcchymosis") && StringUtils.isNotEmpty(map.get("skinEcchymosis").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinEcchymosis");
                hospitalizedAssessment.setContent(map.get("skinEcchymosis").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-破损：skinDamaged    （选中传 1）
            if(map.containsKey("skinDamaged") && StringUtils.isNotEmpty(map.get("skinDamaged").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinDamaged");
                hospitalizedAssessment.setContent(map.get("skinDamaged").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-其他：skinOther    （选中传 1）
            if(map.containsKey("skinOther") && StringUtils.isNotEmpty(map.get("skinOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinOther");
                hospitalizedAssessment.setContent(map.get("skinOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤-其他-内容：skinOtherContent
            if(map.containsKey("skinOtherContent") && StringUtils.isNotEmpty(map.get("skinOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinOtherContent");
                hospitalizedAssessment.setContent(map.get("skinOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //压疮：pressureSore （0：无 1：有）
            if(map.containsKey("pressureSore") && StringUtils.isNotEmpty(map.get("pressureSore").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pressureSore");
                hospitalizedAssessment.setContent(map.get("pressureSore").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //压疮-部位：pressureSorePosition
            if(map.containsKey("pressureSorePosition") && StringUtils.isNotEmpty(map.get("pressureSorePosition").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pressureSorePosition");
                hospitalizedAssessment.setContent(map.get("pressureSorePosition").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //压疮-分期：pressureSoreLevel
            if(map.containsKey("pressureSoreLevel") && StringUtils.isNotEmpty(map.get("pressureSoreLevel").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pressureSoreLevel");
                hospitalizedAssessment.setContent(map.get("pressureSoreLevel").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //压疮-范围：pressureSoreRange
            if(map.containsKey("pressureSoreRange") && StringUtils.isNotEmpty(map.get("pressureSoreRange").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("pressureSoreRange");
                hospitalizedAssessment.setContent(map.get("pressureSoreRange").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-正常：urineNormal （选中传 1）
            if(map.containsKey("urineNormal") && StringUtils.isNotEmpty(map.get("urineNormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineNormal");
                hospitalizedAssessment.setContent(map.get("urineNormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-失禁：urineIncontinence （选中传 1）
            if(map.containsKey("urineIncontinence") && StringUtils.isNotEmpty(map.get("urineIncontinence").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineIncontinence");
                hospitalizedAssessment.setContent(map.get("urineIncontinence").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-尿频：urineFrequent （选中传 1）
            if(map.containsKey("urineFrequent") && StringUtils.isNotEmpty(map.get("urineFrequent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineFrequent");
                hospitalizedAssessment.setContent(map.get("urineFrequent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-尿急：urineUrgency （选中传 1）
            if(map.containsKey("urineUrgency") && StringUtils.isNotEmpty(map.get("urineUrgency").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineUrgency");
                hospitalizedAssessment.setContent(map.get("urineUrgency").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-尿痛：urinePain （选中传 1）
            if(map.containsKey("urinePain") && StringUtils.isNotEmpty(map.get("urinePain").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urinePain");
                hospitalizedAssessment.setContent(map.get("urinePain").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-尿少：urineLittle （选中传 1）
            if(map.containsKey("urineLittle") && StringUtils.isNotEmpty(map.get("urineLittle").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineLittle");
                hospitalizedAssessment.setContent(map.get("urineLittle").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-尿滞留：urineRetention （选中传 1）
            if(map.containsKey("urineRetention") && StringUtils.isNotEmpty(map.get("urineRetention").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineRetention");
                hospitalizedAssessment.setContent(map.get("urineRetention").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-血尿：urineBlood （选中传 1）
            if(map.containsKey("urineBlood") && StringUtils.isNotEmpty(map.get("urineBlood").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineBlood");
                hospitalizedAssessment.setContent(map.get("urineBlood").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-蛋白尿：urineProtein （选中传 1）
            if(map.containsKey("urineProtein") && StringUtils.isNotEmpty(map.get("urineProtein").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineProtein");
                hospitalizedAssessment.setContent(map.get("urineProtein").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-保留尿管：urineRetentionCatheter （选中传 1）
            if(map.containsKey("urineRetentionCatheter") && StringUtils.isNotEmpty(map.get("urineRetentionCatheter").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineRetentionCatheter");
                hospitalizedAssessment.setContent(map.get("urineRetentionCatheter").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-造口：urineStoma （选中传 1）
            if(map.containsKey("urineStoma") && StringUtils.isNotEmpty(map.get("urineStoma").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineStoma");
                hospitalizedAssessment.setContent(map.get("urineStoma").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-其他：urineOther （选中传 1）
            if(map.containsKey("urineOther") && StringUtils.isNotEmpty(map.get("urineOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineOther");
                hospitalizedAssessment.setContent(map.get("urineOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //小便情况-其他-内容：urineOtherContent
            if(map.containsKey("urineOtherContent") && StringUtils.isNotEmpty(map.get("urineOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("urineOtherContent");
                hospitalizedAssessment.setContent(map.get("urineOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-正常：shitNormal （选中传 1）
            if(map.containsKey("shitNormal") && StringUtils.isNotEmpty(map.get("shitNormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitNormal");
                hospitalizedAssessment.setContent(map.get("shitNormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-失禁：shitIncontinence （选中传 1）
            if(map.containsKey("shitIncontinence") && StringUtils.isNotEmpty(map.get("shitIncontinence").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitIncontinence");
                hospitalizedAssessment.setContent(map.get("shitIncontinence").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-便秘：shitConstipation （选中传 1）
            if(map.containsKey("shitConstipation") && StringUtils.isNotEmpty(map.get("shitConstipation").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitConstipation");
                hospitalizedAssessment.setContent(map.get("shitConstipation").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-便血：shitBlood （选中传 1）
            if(map.containsKey("shitBlood") && StringUtils.isNotEmpty(map.get("shitBlood").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitBlood");
                hospitalizedAssessment.setContent(map.get("shitBlood").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-腹泻：shitDiarrhea （选中传 1）
            if(map.containsKey("shitDiarrhea") && StringUtils.isNotEmpty(map.get("shitDiarrhea").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitDiarrhea");
                hospitalizedAssessment.setContent(map.get("shitDiarrhea").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-腹泻-内容：shitDiarrheaNum
            if(map.containsKey("shitDiarrheaNum") && StringUtils.isNotEmpty(map.get("shitDiarrheaNum").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitDiarrheaNum");
                hospitalizedAssessment.setContent(map.get("shitDiarrheaNum").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-造口：shitStoma （选中传 1）
            if(map.containsKey("shitStoma") && StringUtils.isNotEmpty(map.get("shitStoma").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitStoma");
                hospitalizedAssessment.setContent(map.get("shitStoma").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-其他：shitOther （选中传 1）
            if(map.containsKey("shitOther") && StringUtils.isNotEmpty(map.get("shitOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitOther");
                hospitalizedAssessment.setContent(map.get("shitOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //大便-其他-内容：shitOtherContent
            if(map.containsKey("shitOtherContent") && StringUtils.isNotEmpty(map.get("shitOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("shitOtherContent");
                hospitalizedAssessment.setContent(map.get("shitOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-稳定：emotionStable   （选中传 1）
            if(map.containsKey("emotionStable") && StringUtils.isNotEmpty(map.get("emotionStable").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionStable");
                hospitalizedAssessment.setContent(map.get("emotionStable").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-焦虑：emotionAnxious   （选中传 1）
            if(map.containsKey("emotionAnxious") && StringUtils.isNotEmpty(map.get("emotionAnxious").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionAnxious");
                hospitalizedAssessment.setContent(map.get("emotionAnxious").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-紧张：emotionNervous   （选中传 1）
            if(map.containsKey("emotionNervous") && StringUtils.isNotEmpty(map.get("emotionNervous").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionNervous");
                hospitalizedAssessment.setContent(map.get("emotionNervous").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-恐惧：emotionFear   （选中传 1）
            if(map.containsKey("emotionFear") && StringUtils.isNotEmpty(map.get("emotionFear").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionFear");
                hospitalizedAssessment.setContent(map.get("emotionFear").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-其他：emotionOther   （选中传 1）
            if(map.containsKey("emotionOther") && StringUtils.isNotEmpty(map.get("emotionOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionOther");
                hospitalizedAssessment.setContent(map.get("emotionOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //情绪状态-其他-内容：emotionOtherContent
            if(map.containsKey("emotionOtherContent") && StringUtils.isNotEmpty(map.get("emotionOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("emotionOtherContent");
                hospitalizedAssessment.setContent(map.get("emotionOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //吸烟：smoke    （0：否 1：是）
            if(map.containsKey("smoke") && StringUtils.isNotEmpty(map.get("smoke").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("smoke");
                hospitalizedAssessment.setContent(map.get("smoke").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //吸烟-内容：smokeContent
            if(map.containsKey("smokeContent") && StringUtils.isNotEmpty(map.get("smokeContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("smokeContent");
                hospitalizedAssessment.setContent(map.get("smokeContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮酒：drink （0：否 1：是）
            if(map.containsKey("drink") && StringUtils.isNotEmpty(map.get("drink").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("drink");
                hospitalizedAssessment.setContent(map.get("drink").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮酒-内容：drinkContent
            if(map.containsKey("drinkContent") && StringUtils.isNotEmpty(map.get("drinkContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("drinkContent");
                hospitalizedAssessment.setContent(map.get("drinkContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-咸：dietSalty （选中传 1）
            if(map.containsKey("dietSalty") && StringUtils.isNotEmpty(map.get("dietSalty").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietSalty");
                hospitalizedAssessment.setContent(map.get("dietSalty").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-甜：dietSweet （选中传 1）
            if(map.containsKey("dietSweet") && StringUtils.isNotEmpty(map.get("dietSweet").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietSweet");
                hospitalizedAssessment.setContent(map.get("dietSweet").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-辛辣：dietPungent （选中传 1）
            if(map.containsKey("dietPungent") && StringUtils.isNotEmpty(map.get("dietPungent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietPungent");
                hospitalizedAssessment.setContent(map.get("dietPungent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-油腻：dietGreasy （选中传 1）
            if(map.containsKey("dietGreasy") && StringUtils.isNotEmpty(map.get("dietGreasy").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietGreasy");
                hospitalizedAssessment.setContent(map.get("dietGreasy").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-清淡：dietLight （选中传 1）
            if(map.containsKey("dietLight") && StringUtils.isNotEmpty(map.get("dietLight").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietLight");
                hospitalizedAssessment.setContent(map.get("dietLight").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-其他：dietOther （选中传 1）
            if(map.containsKey("dietOther") && StringUtils.isNotEmpty(map.get("dietOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietOther");
                hospitalizedAssessment.setContent(map.get("dietOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食习惯-其他-内容：dietOtherContent
            if(map.containsKey("dietOtherContent") && StringUtils.isNotEmpty(map.get("dietOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietOtherContent");
                hospitalizedAssessment.setContent(map.get("dietOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //忌食：dietAvoid
            if(map.containsKey("dietAvoid") && StringUtils.isNotEmpty(map.get("dietAvoid").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietAvoid");
                hospitalizedAssessment.setContent(map.get("dietAvoid").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-正常：sleepNormal   （选中传 1）
            if(map.containsKey("sleepNormal") && StringUtils.isNotEmpty(map.get("sleepNormal").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepNormal");
                hospitalizedAssessment.setContent(map.get("sleepNormal").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-多梦：sleepLotDream   （选中传 1）
            if(map.containsKey("sleepLotDream") && StringUtils.isNotEmpty(map.get("sleepLotDream").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepLotDream");
                hospitalizedAssessment.setContent(map.get("sleepLotDream").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-易醒：sleepToWakeUp   （选中传 1）
            if(map.containsKey("sleepToWakeUp") && StringUtils.isNotEmpty(map.get("sleepToWakeUp").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepToWakeUp");
                hospitalizedAssessment.setContent(map.get("sleepToWakeUp").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-入睡困难：sleepHard   （选中传 1）
            if(map.containsKey("sleepHard") && StringUtils.isNotEmpty(map.get("sleepHard").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepHard");
                hospitalizedAssessment.setContent(map.get("sleepHard").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-每日睡眠：sleepHour
            if(map.containsKey("sleepHour") && StringUtils.isNotEmpty(map.get("sleepHour").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepHour");
                hospitalizedAssessment.setContent(map.get("sleepHour").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-药物辅助：sleepDrugAssisted （0：无 1：有）
            if(map.containsKey("sleepDrugAssisted") && StringUtils.isNotEmpty(map.get("sleepDrugAssisted").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepDrugAssisted");
                hospitalizedAssessment.setContent(map.get("sleepDrugAssisted").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //睡眠-药物辅助-内容：sleepDrugAssistedContent （0：无 1：有）
            if(map.containsKey("sleepDrugAssistedContent") && StringUtils.isNotEmpty(map.get("sleepDrugAssistedContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("sleepDrugAssistedContent");
                hospitalizedAssessment.setContent(map.get("sleepDrugAssistedContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //评分时间：scoreTime
            if(map.containsKey("scoreTime") && StringUtils.isNotEmpty(map.get("scoreTime").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("scoreTime");
                hospitalizedAssessment.setContent(map.get("scoreTime").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //Braden评分：bradenScore
            if(map.containsKey("bradenScore") && StringUtils.isNotEmpty(map.get("bradenScore").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("bradenScore");
                hospitalizedAssessment.setContent(map.get("bradenScore").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //Morse评分：morseScore
            if(map.containsKey("morseScore") && StringUtils.isNotEmpty(map.get("morseScore").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("morseScore");
                hospitalizedAssessment.setContent(map.get("morseScore").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //Autar-DVT评分：autarDvtScore
            if(map.containsKey("autarDvtScore") && StringUtils.isNotEmpty(map.get("autarDvtScore").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("autarDvtScore");
                hospitalizedAssessment.setContent(map.get("autarDvtScore").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //ADL评分：adlScore
            if(map.containsKey("adlScore") && StringUtils.isNotEmpty(map.get("adlScore").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("adlScore");
                hospitalizedAssessment.setContent(map.get("adlScore").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //家属态度：familyAttitude （1：关心 2：不关心 3：过于关心 4：无人照顾 5：不配合）
            if(map.containsKey("familyAttitude") && StringUtils.isNotEmpty(map.get("familyAttitude").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("familyAttitude");
                hospitalizedAssessment.setContent(map.get("familyAttitude").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食状况：dietaryStatus （1：一般 2：良好 3：较差）
            if(map.containsKey("dietaryStatus") && StringUtils.isNotEmpty(map.get("dietaryStatus").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietaryStatus");
                hospitalizedAssessment.setContent(map.get("dietaryStatus").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //自理能力：selfCareAbility （1：完全自理 2：部分自理 3：不能自理）
            if(map.containsKey("selfCareAbility") && StringUtils.isNotEmpty(map.get("selfCareAbility").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("selfCareAbility");
                hospitalizedAssessment.setContent(map.get("selfCareAbility").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //隔离状况：isolationStatus （通用字典）
            if(map.containsKey("isolationStatus") && StringUtils.isNotEmpty(map.get("isolationStatus").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("isolationStatus");
                hospitalizedAssessment.setContent(map.get("isolationStatus").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //有无呕吐：vomit  （0：无 1：有）
            if(map.containsKey("vomit") && StringUtils.isNotEmpty(map.get("vomit").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("vomit");
                hospitalizedAssessment.setContent(map.get("vomit").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //发育程度：developmentalDegree （通用字典）
            if(map.containsKey("developmentalDegree") && StringUtils.isNotEmpty(map.get("developmentalDegree").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("developmentalDegree");
                hospitalizedAssessment.setContent(map.get("developmentalDegree").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //健康状况：health （0：不健康 1：健康）
            if(map.containsKey("health") && StringUtils.isNotEmpty(map.get("health").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("health");
                hospitalizedAssessment.setContent(map.get("health").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //传染性疾病：infectiousDiseases （0：无 1：有）
            if(map.containsKey("infectiousDiseases") && StringUtils.isNotEmpty(map.get("infectiousDiseases").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("infectiousDiseases");
                hospitalizedAssessment.setContent(map.get("infectiousDiseases").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-床位医生：bedsideDoctor （选中传 1）
            if(map.containsKey("bedsideDoctor") && StringUtils.isNotEmpty(map.get("bedsideDoctor").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("bedsideDoctor");
                hospitalizedAssessment.setContent(map.get("bedsideDoctor").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-责任护士：responsibleNurse （选中传 1）
            if(map.containsKey("responsibleNurse") && StringUtils.isNotEmpty(map.get("responsibleNurse").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("responsibleNurse");
                hospitalizedAssessment.setContent(map.get("responsibleNurse").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-病房环境：wardEnvironment （选中传 1）
            if(map.containsKey("wardEnvironment") && StringUtils.isNotEmpty(map.get("wardEnvironment").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("wardEnvironment");
                hospitalizedAssessment.setContent(map.get("wardEnvironment").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-病房制度：wardSystem （选中传 1）
            if(map.containsKey("wardSystem") && StringUtils.isNotEmpty(map.get("wardSystem").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("wardSystem");
                hospitalizedAssessment.setContent(map.get("wardSystem").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-探视规定：visitingRegulations （选中传 1）
            if(map.containsKey("visitingRegulations") && StringUtils.isNotEmpty(map.get("visitingRegulations").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("visitingRegulations");
                hospitalizedAssessment.setContent(map.get("visitingRegulations").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-膳食安排：dietaryArrangement （选中传 1）
            if(map.containsKey("dietaryArrangement") && StringUtils.isNotEmpty(map.get("dietaryArrangement").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietaryArrangement");
                hospitalizedAssessment.setContent(map.get("dietaryArrangement").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-心里疏导：psychologicalCounseling （选中传 1）
            if(map.containsKey("psychologicalCounseling") && StringUtils.isNotEmpty(map.get("psychologicalCounseling").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("psychologicalCounseling");
                hospitalizedAssessment.setContent(map.get("psychologicalCounseling").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-禁止外出：noGoingOut （选中传 1）
            if(map.containsKey("noGoingOut") && StringUtils.isNotEmpty(map.get("noGoingOut").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("noGoingOut");
                hospitalizedAssessment.setContent(map.get("noGoingOut").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-腕带佩戴：wristStrap （选中传 1）
            if(map.containsKey("wristStrap") && StringUtils.isNotEmpty(map.get("wristStrap").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("wristStrap");
                hospitalizedAssessment.setContent(map.get("wristStrap").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-其他：inHospitalOther （选中传 1）
            if(map.containsKey("inHospitalOther") && StringUtils.isNotEmpty(map.get("inHospitalOther").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("inHospitalOther");
                hospitalizedAssessment.setContent(map.get("inHospitalOther").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //入院宣教-其他-内容：inHospitalOtherContent
            if(map.containsKey("inHospitalOtherContent") && StringUtils.isNotEmpty(map.get("inHospitalOtherContent").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("inHospitalOtherContent");
                hospitalizedAssessment.setContent(map.get("inHospitalOtherContent").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //皮肤护理：skinNursing
            if(map.containsKey("skinNursing") && StringUtils.isNotEmpty(map.get("skinNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("skinNursing");
                hospitalizedAssessment.setContent(map.get("skinNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //营养护理：nutritionNursing
            if(map.containsKey("nutritionNursing") && StringUtils.isNotEmpty(map.get("nutritionNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("nutritionNursing");
                hospitalizedAssessment.setContent(map.get("nutritionNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //体位护理：postureNursing
            if(map.containsKey("postureNursing") && StringUtils.isNotEmpty(map.get("postureNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("postureNursing");
                hospitalizedAssessment.setContent(map.get("postureNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //心里护理：mentalNursing  （通用字典）
            if(map.containsKey("mentalNursing") && StringUtils.isNotEmpty(map.get("mentalNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("mentalNursing");
                hospitalizedAssessment.setContent(map.get("mentalNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //饮食护理：dietNursing  （通用字典）
            if(map.containsKey("dietNursing") && StringUtils.isNotEmpty(map.get("dietNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("dietNursing");
                hospitalizedAssessment.setContent(map.get("dietNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //安全护理：safetyNursing  （通用字典）
            if(map.containsKey("safetyNursing") && StringUtils.isNotEmpty(map.get("safetyNursing").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("safetyNursing");
                hospitalizedAssessment.setContent(map.get("safetyNursing").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //异常情况描述：abnormalDescription
            if(map.containsKey("abnormalDescription") && StringUtils.isNotEmpty(map.get("abnormalDescription").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("abnormalDescription");
                hospitalizedAssessment.setContent(map.get("abnormalDescription").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //通知医生时间：informDoctorTime
            if(map.containsKey("informDoctorTime") && StringUtils.isNotEmpty(map.get("informDoctorTime").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("informDoctorTime");
                hospitalizedAssessment.setContent(map.get("informDoctorTime").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护士签名：nurseSignature
            if(map.containsKey("nurseSignature") && StringUtils.isNotEmpty(map.get("nurseSignature").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("nurseSignature");
                hospitalizedAssessment.setContent(map.get("nurseSignature").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //审核护士签名：examineNurseSignature
            if(map.containsKey("examineNurseSignature") && StringUtils.isNotEmpty(map.get("examineNurseSignature").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("examineNurseSignature");
                hospitalizedAssessment.setContent(map.get("examineNurseSignature").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            //护理计划及措施-时间：examineTime
            if(map.containsKey("examineTime") && StringUtils.isNotEmpty(map.get("examineTime").toString())){
                HospitalizedAssessment hospitalizedAssessment=newHospitalizedAssessment(map,operatorCode,assessmentId,createTime,dataTime);
                hospitalizedAssessment.setCode("examineTime");
                hospitalizedAssessment.setContent(map.get("examineTime").toString());
                hospitalizedAssessmentService.insert(hospitalizedAssessment);
            }
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }catch (Exception e){
            logger.info("护理评估--入院护理评估单--保存:"+e);
        }
        return json.toString();
    }

    private HospitalizedAssessment newHospitalizedAssessment(Map<String,Object> map,String operatorCode,String assessmentId,Date createTime,Date dataTime){
        HospitalizedAssessment hospitalizedAssessment=new HospitalizedAssessment();
        try {
            hospitalizedAssessment.setId(UUIDUtil.getUUID());
            hospitalizedAssessment.setStatus(1);
            hospitalizedAssessment.setCreateTime(createTime);
            hospitalizedAssessment.setAssessmentId(assessmentId);
            hospitalizedAssessment.setOperatorCode(operatorCode);
            hospitalizedAssessment.setDataTime(dataTime);
            if(map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString())){
                hospitalizedAssessment.setVisitId(map.get("visitId").toString());
            }
            if(map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString())){
                hospitalizedAssessment.setVisitCode(map.get("visitCode").toString());
            }
            if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                hospitalizedAssessment.setPatientId(map.get("patientId").toString());
            }
        }catch (Exception e){
            logger.info("newHospitalizedAssessment:"+e);
        }
        return hospitalizedAssessment;
    }

    /**
     * 护理评估--入院护理评估单--历史列表--分页查询列表
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
        List<Map<String,Object>> list =hospitalizedAssessmentService.getList(map);
        if(map!=null && map.containsKey("start")){
            int totalCount =hospitalizedAssessmentService.getNum(map);
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
     * 护理评估--入院护理评估单--历史列表--删除
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
        List<HospitalizedAssessment> list =hospitalizedAssessmentService.getListByAssessmentIdAndPatientId(map);
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setStatus(-1);
                hospitalizedAssessmentService.update(list.get(i));
            }
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 护理评估--入院护理评估单--历史列表--选择
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
        Map<String,Object> data =hospitalizedAssessmentService.getDataByAssessmentId(map);
        if(data!=null){
            json.put("data",JSONObject.fromObject(data));
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

}
