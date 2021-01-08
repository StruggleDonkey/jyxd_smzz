package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.NursingPlan;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.NursingPlanService;
import com.jyxd.web.service.log.LogService;
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
@RequestMapping(value = "/nursingPlan")
public class NursingPlanController {

    private static Logger logger= LoggerFactory.getLogger(NursingPlanController.class);

    @Autowired
    private NursingPlanService nursingPlanService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条重症护理计划表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody NursingPlan nursingPlan, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        nursingPlan.setId(UUIDUtil.getUUID());
        nursingPlan.setCreateTime(new Date());
        User user=(User)session.getAttribute("user");
        if(user!=null){
            nursingPlan.setOperatorCode(user.getLoginName());
        }
        nursingPlanService.insert(nursingPlan);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新重症护理计划表记录状态
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
            NursingPlan nursingPlan=nursingPlanService.queryData(map.get("id").toString());
            if(nursingPlan!=null){
                nursingPlan.setStatus((int)map.get("status"));
                nursingPlanService.update(nursingPlan);
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
     * 编辑重症护理计划表
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        try {
            if(map!=null && map.containsKey("id") ){
                NursingPlan nursingPlan=nursingPlanService.queryData(map.get("id").toString());
                if(nursingPlan!=null){
                    if(map.containsKey("operatorCode") && StringUtils.isNotEmpty(map.get("operatorCode").toString())){
                        nursingPlan.setOperatorCode(map.get("operatorCode").toString());
                    }
                    if(map.containsKey("content") && StringUtils.isNotEmpty(map.get("content").toString())){
                        nursingPlan.setContent(map.get("content").toString());
                    }
                    if(map.containsKey("dateTime") && StringUtils.isNotEmpty(map.get("dateTime").toString())){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        nursingPlan.setDataTime(sdf.parse(map.get("dataTime").toString()));
                    }
                    nursingPlanService.update(nursingPlan);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }else{
                    json.put("msg","编辑失败，没有这个对象。");
                    return json.toString();
                }
            }
        }catch (Exception e){
            logger.info("编辑护理记录表记录单:"+e);
        }
        return json.toString();
    }

    /**
     * 删除重症护理计划表
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
            NursingPlan nursingPlan=nursingPlanService.queryData(map.get("id").toString());
            if(nursingPlan!=null){
                nursingPlan.setStatus(-1);
                nursingPlanService.update(nursingPlan);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","删除失败，没有这个对象。");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询重症护理计划表
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
            NursingPlan nursingPlan=nursingPlanService.queryData(map.get("id").toString());
            if(nursingPlan!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(nursingPlan));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询重症护理计划表记录列表（也可以不分页）
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
            int totalCount =nursingPlanService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<NursingPlan> list =nursingPlanService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理计划单--保存数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/add",method= RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","保存失败");
        String assessmentId=UUIDUtil.getUUID();
        if(map!=null && map.containsKey("startDate_0") && StringUtils.isNotEmpty(map.get("startDate_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_0");
            nursingPlan.setContent(map.get("startDate_0").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_1") && StringUtils.isNotEmpty(map.get("startDate_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_1");
            nursingPlan.setContent(map.get("startDate_1").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_2") && StringUtils.isNotEmpty(map.get("startDate_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_2");
            nursingPlan.setContent(map.get("startDate_2").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_3") && StringUtils.isNotEmpty(map.get("startDate_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_3");
            nursingPlan.setContent(map.get("startDate_3").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_4") && StringUtils.isNotEmpty(map.get("startDate_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_4");
            nursingPlan.setContent(map.get("startDate_4").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_5") && StringUtils.isNotEmpty(map.get("startDate_5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_5");
            nursingPlan.setContent(map.get("startDate_5").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_6") && StringUtils.isNotEmpty(map.get("startDate_6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_6");
            nursingPlan.setContent(map.get("startDate_6").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_7") && StringUtils.isNotEmpty(map.get("startDate_7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_7");
            nursingPlan.setContent(map.get("startDate_7").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_8") && StringUtils.isNotEmpty(map.get("startDate_8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_8");
            nursingPlan.setContent(map.get("startDate_8").toString());
            nursingPlanService.insert(nursingPlan);
        }

        if(map!=null && map.containsKey("startDate_9") && StringUtils.isNotEmpty(map.get("startDate_9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_9");
            nursingPlan.setContent(map.get("startDate_9").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate_10") && StringUtils.isNotEmpty(map.get("startDate_10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_10");
            nursingPlan.setContent(map.get("startDate_10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_11") && StringUtils.isNotEmpty(map.get("startDate_11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_11");
            nursingPlan.setContent(map.get("startDate_11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_12") && StringUtils.isNotEmpty(map.get("startDate_12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_12");
            nursingPlan.setContent(map.get("startDate_12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_13") && StringUtils.isNotEmpty(map.get("startDate_13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_13");
            nursingPlan.setContent(map.get("startDate_13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_14") && StringUtils.isNotEmpty(map.get("startDate_14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_14");
            nursingPlan.setContent(map.get("startDate_14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_15") && StringUtils.isNotEmpty(map.get("startDate_15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_15");
            nursingPlan.setContent(map.get("startDate_15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_16") && StringUtils.isNotEmpty(map.get("startDate_16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_16");
            nursingPlan.setContent(map.get("startDate_16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_17") && StringUtils.isNotEmpty(map.get("startDate_17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_17");
            nursingPlan.setContent(map.get("startDate_17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_18") && StringUtils.isNotEmpty(map.get("startDate_18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_18");
            nursingPlan.setContent(map.get("startDate_18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_19") && StringUtils.isNotEmpty(map.get("startDate_19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_19");
            nursingPlan.setContent(map.get("startDate_19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_20") && StringUtils.isNotEmpty(map.get("startDate_20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_20");
            nursingPlan.setContent(map.get("startDate_20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_21") && StringUtils.isNotEmpty(map.get("startDate_21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_21");
            nursingPlan.setContent(map.get("startDate_21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_22") && StringUtils.isNotEmpty(map.get("startDate_22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_22");
            nursingPlan.setContent(map.get("startDate_22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_23") && StringUtils.isNotEmpty(map.get("startDate_23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_23");
            nursingPlan.setContent(map.get("startDate_23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_24") && StringUtils.isNotEmpty(map.get("startDate_24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_24");
            nursingPlan.setContent(map.get("startDate_24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_25") && StringUtils.isNotEmpty(map.get("startDate_25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_25");
            nursingPlan.setContent(map.get("startDate_25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_26") && StringUtils.isNotEmpty(map.get("startDate_26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_26");
            nursingPlan.setContent(map.get("startDate_26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate_27") && StringUtils.isNotEmpty(map.get("startDate_27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate_27");
            nursingPlan.setContent(map.get("startDate_27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startTime_0") && StringUtils.isNotEmpty(map.get("startTime_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_0");
            nursingPlan.setContent(map.get("startTime_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_1") && StringUtils.isNotEmpty(map.get("startTime_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_1");
            nursingPlan.setContent(map.get("startTime_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_2") && StringUtils.isNotEmpty(map.get("startTime_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_2");
            nursingPlan.setContent(map.get("startTime_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_3") && StringUtils.isNotEmpty(map.get("startTime_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_3");
            nursingPlan.setContent(map.get("startTime_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_4") && StringUtils.isNotEmpty(map.get("startTime_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_4");
            nursingPlan.setContent(map.get("startTime_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_5") && StringUtils.isNotEmpty(map.get("startTime_5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_5");
            nursingPlan.setContent(map.get("startTime_5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_6") && StringUtils.isNotEmpty(map.get("startTime_6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_6");
            nursingPlan.setContent(map.get("startTime_6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_7") && StringUtils.isNotEmpty(map.get("startTime_7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_7");
            nursingPlan.setContent(map.get("startTime_7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_8") && StringUtils.isNotEmpty(map.get("startTime_8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_8");
            nursingPlan.setContent(map.get("startTime_8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_9") && StringUtils.isNotEmpty(map.get("startTime_9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_9");
            nursingPlan.setContent(map.get("startTime_9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_10") && StringUtils.isNotEmpty(map.get("startTime_10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_10");
            nursingPlan.setContent(map.get("startTime_10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_11") && StringUtils.isNotEmpty(map.get("startTime_11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_11");
            nursingPlan.setContent(map.get("startTime_11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_12") && StringUtils.isNotEmpty(map.get("startTime_12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_12");
            nursingPlan.setContent(map.get("startTime_12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_13") && StringUtils.isNotEmpty(map.get("startTime_13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_13");
            nursingPlan.setContent(map.get("startTime_13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_14") && StringUtils.isNotEmpty(map.get("startTime_14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_14");
            nursingPlan.setContent(map.get("startTime_14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_15") && StringUtils.isNotEmpty(map.get("startTime_15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_15");
            nursingPlan.setContent(map.get("startTime_15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_16") && StringUtils.isNotEmpty(map.get("startTime_16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_16");
            nursingPlan.setContent(map.get("startTime_16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_17") && StringUtils.isNotEmpty(map.get("startTime_17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_17");
            nursingPlan.setContent(map.get("startTime_17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_18") && StringUtils.isNotEmpty(map.get("startTime_18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_18");
            nursingPlan.setContent(map.get("startTime_18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_19") && StringUtils.isNotEmpty(map.get("startTime_19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_19");
            nursingPlan.setContent(map.get("startTime_19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_20") && StringUtils.isNotEmpty(map.get("startTime_20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_20");
            nursingPlan.setContent(map.get("startTime_20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_21") && StringUtils.isNotEmpty(map.get("startTime_21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_21");
            nursingPlan.setContent(map.get("startTime_21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_22") && StringUtils.isNotEmpty(map.get("startTime_22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_22");
            nursingPlan.setContent(map.get("startTime_22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_23") && StringUtils.isNotEmpty(map.get("startTime_23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_23");
            nursingPlan.setContent(map.get("startTime_23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_24") && StringUtils.isNotEmpty(map.get("startTime_24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_24");
            nursingPlan.setContent(map.get("startTime_24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_25") && StringUtils.isNotEmpty(map.get("startTime_25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_25");
            nursingPlan.setContent(map.get("startTime_25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_26") && StringUtils.isNotEmpty(map.get("startTime_26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_26");
            nursingPlan.setContent(map.get("startTime_26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime_27") && StringUtils.isNotEmpty(map.get("startTime_27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime_27");
            nursingPlan.setContent(map.get("startTime_27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("plan_0_0") && StringUtils.isNotEmpty(map.get("plan_0_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_0_0");
            nursingPlan.setContent(map.get("plan_0_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_1_0") && StringUtils.isNotEmpty(map.get("plan_1_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_1_0");
            nursingPlan.setContent(map.get("plan_1_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_2_0") && StringUtils.isNotEmpty(map.get("plan_2_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_2_0");
            nursingPlan.setContent(map.get("plan_2_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_3_0") && StringUtils.isNotEmpty(map.get("plan_3_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_3_0");
            nursingPlan.setContent(map.get("plan_3_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_4_0") && StringUtils.isNotEmpty(map.get("plan_4_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_4_0");
            nursingPlan.setContent(map.get("plan_4_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_5_0") && StringUtils.isNotEmpty(map.get("plan_5_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_5_0");
            nursingPlan.setContent(map.get("plan_5_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_6_0") && StringUtils.isNotEmpty(map.get("plan_6_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_6_0");
            nursingPlan.setContent(map.get("plan_6_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_7_0") && StringUtils.isNotEmpty(map.get("plan_7_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_7_0");
            nursingPlan.setContent(map.get("plan_7_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_7_1") && StringUtils.isNotEmpty(map.get("plan_7_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_7_1");
            nursingPlan.setContent(map.get("plan_7_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_8_0") && StringUtils.isNotEmpty(map.get("plan_8_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_8_0");
            nursingPlan.setContent(map.get("plan_8_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_9_0") && StringUtils.isNotEmpty(map.get("plan_9_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_9_0");
            nursingPlan.setContent(map.get("plan_9_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_10_0") && StringUtils.isNotEmpty(map.get("plan_10_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_10_0");
            nursingPlan.setContent(map.get("plan_10_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_11_0") && StringUtils.isNotEmpty(map.get("plan_11_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_11_0");
            nursingPlan.setContent(map.get("plan_11_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_11_1") && StringUtils.isNotEmpty(map.get("plan_11_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_11_1");
            nursingPlan.setContent(map.get("plan_11_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_11_2") && StringUtils.isNotEmpty(map.get("plan_11_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_11_2");
            nursingPlan.setContent(map.get("plan_11_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_12_0") && StringUtils.isNotEmpty(map.get("plan_12_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_12_0");
            nursingPlan.setContent(map.get("plan_12_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_12_1") && StringUtils.isNotEmpty(map.get("plan_12_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_12_1");
            nursingPlan.setContent(map.get("plan_12_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_12_2") && StringUtils.isNotEmpty(map.get("plan_12_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_12_2");
            nursingPlan.setContent(map.get("plan_12_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_13_0") && StringUtils.isNotEmpty(map.get("plan_13_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_13_0");
            nursingPlan.setContent(map.get("plan_13_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_14_0") && StringUtils.isNotEmpty(map.get("plan_14_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_14_0");
            nursingPlan.setContent(map.get("plan_14_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_15_0") && StringUtils.isNotEmpty(map.get("plan_15_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_15_0");
            nursingPlan.setContent(map.get("plan_15_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_16_0") && StringUtils.isNotEmpty(map.get("plan_16_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_16_0");
            nursingPlan.setContent(map.get("plan_16_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_17_0") && StringUtils.isNotEmpty(map.get("plan_17_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_17_0");
            nursingPlan.setContent(map.get("plan_17_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_17_1") && StringUtils.isNotEmpty(map.get("plan_17_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_17_1");
            nursingPlan.setContent(map.get("plan_17_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_17_2") && StringUtils.isNotEmpty(map.get("plan_17_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_17_2");
            nursingPlan.setContent(map.get("plan_17_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_17_3") && StringUtils.isNotEmpty(map.get("plan_17_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_17_3");
            nursingPlan.setContent(map.get("plan_17_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_17_4") && StringUtils.isNotEmpty(map.get("plan_17_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_17_4");
            nursingPlan.setContent(map.get("plan_17_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_0") && StringUtils.isNotEmpty(map.get("plan_18_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_0");
            nursingPlan.setContent(map.get("plan_18_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_1") && StringUtils.isNotEmpty(map.get("plan_18_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_1");
            nursingPlan.setContent(map.get("plan_18_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_2") && StringUtils.isNotEmpty(map.get("plan_18_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_2");
            nursingPlan.setContent(map.get("plan_18_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_3") && StringUtils.isNotEmpty(map.get("plan_18_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_3");
            nursingPlan.setContent(map.get("plan_18_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_4") && StringUtils.isNotEmpty(map.get("plan_18_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_4");
            nursingPlan.setContent(map.get("plan_18_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_5") && StringUtils.isNotEmpty(map.get("plan_18_5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_5");
            nursingPlan.setContent(map.get("plan_18_5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_18_6") && StringUtils.isNotEmpty(map.get("plan_18_6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_18_6");
            nursingPlan.setContent(map.get("plan_18_6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_19_0") && StringUtils.isNotEmpty(map.get("plan_19_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_19_0");
            nursingPlan.setContent(map.get("plan_19_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_19_1") && StringUtils.isNotEmpty(map.get("plan_19_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_19_1");
            nursingPlan.setContent(map.get("plan_19_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_19_2") && StringUtils.isNotEmpty(map.get("plan_19_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_19_2");
            nursingPlan.setContent(map.get("plan_19_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_19_3") && StringUtils.isNotEmpty(map.get("plan_19_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_19_3");
            nursingPlan.setContent(map.get("plan_19_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_20_0") && StringUtils.isNotEmpty(map.get("plan_20_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_20_0");
            nursingPlan.setContent(map.get("plan_20_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_20_1") && StringUtils.isNotEmpty(map.get("plan_20_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_20_1");
            nursingPlan.setContent(map.get("plan_20_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_21_0") && StringUtils.isNotEmpty(map.get("plan_21_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_21_0");
            nursingPlan.setContent(map.get("plan_21_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_21_1") && StringUtils.isNotEmpty(map.get("plan_21_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_21_1");
            nursingPlan.setContent(map.get("plan_21_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_21_2") && StringUtils.isNotEmpty(map.get("plan_21_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_21_2");
            nursingPlan.setContent(map.get("plan_21_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_21_3") && StringUtils.isNotEmpty(map.get("plan_21_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_21_3");
            nursingPlan.setContent(map.get("plan_21_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_21_4") && StringUtils.isNotEmpty(map.get("plan_21_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_21_4");
            nursingPlan.setContent(map.get("plan_21_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_22_0") && StringUtils.isNotEmpty(map.get("plan_22_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_22_0");
            nursingPlan.setContent(map.get("plan_22_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_23_0") && StringUtils.isNotEmpty(map.get("plan_23_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_23_0");
            nursingPlan.setContent(map.get("plan_23_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_24_0") && StringUtils.isNotEmpty(map.get("plan_24_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_24_0");
            nursingPlan.setContent(map.get("plan_24_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_24_1") && StringUtils.isNotEmpty(map.get("plan_24_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_24_1");
            nursingPlan.setContent(map.get("plan_24_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_25_0") && StringUtils.isNotEmpty(map.get("plan_25_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_25_0");
            nursingPlan.setContent(map.get("plan_25_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_26_0") && StringUtils.isNotEmpty(map.get("plan_26_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_26_0");
            nursingPlan.setContent(map.get("plan_26_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan_27_0") && StringUtils.isNotEmpty(map.get("plan_27_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan_27_0");
            nursingPlan.setContent(map.get("plan_27_0").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("signature_0") && StringUtils.isNotEmpty(map.get("signature_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_0");
            nursingPlan.setContent(map.get("signature_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_1") && StringUtils.isNotEmpty(map.get("signature_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_1");
            nursingPlan.setContent(map.get("signature_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_2") && StringUtils.isNotEmpty(map.get("signature_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_2");
            nursingPlan.setContent(map.get("signature_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_3") && StringUtils.isNotEmpty(map.get("signature_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_3");
            nursingPlan.setContent(map.get("signature_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_4") && StringUtils.isNotEmpty(map.get("signature_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_4");
            nursingPlan.setContent(map.get("signature_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_5") && StringUtils.isNotEmpty(map.get("signature_5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_5");
            nursingPlan.setContent(map.get("signature_5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_6") && StringUtils.isNotEmpty(map.get("signature_6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_6");
            nursingPlan.setContent(map.get("signature_6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_7") && StringUtils.isNotEmpty(map.get("signature_7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_7");
            nursingPlan.setContent(map.get("signature_7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_8") && StringUtils.isNotEmpty(map.get("signature_8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_8");
            nursingPlan.setContent(map.get("signature_8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_9") && StringUtils.isNotEmpty(map.get("signature_9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_9");
            nursingPlan.setContent(map.get("signature_9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_10") && StringUtils.isNotEmpty(map.get("signature_10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_10");
            nursingPlan.setContent(map.get("signature_10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_11") && StringUtils.isNotEmpty(map.get("signature_11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_11");
            nursingPlan.setContent(map.get("signature_11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_12") && StringUtils.isNotEmpty(map.get("signature_12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_12");
            nursingPlan.setContent(map.get("signature_12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_13") && StringUtils.isNotEmpty(map.get("signature_13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_13");
            nursingPlan.setContent(map.get("signature_13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_14") && StringUtils.isNotEmpty(map.get("signature_14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_14");
            nursingPlan.setContent(map.get("signature_14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_15") && StringUtils.isNotEmpty(map.get("signature_15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_15");
            nursingPlan.setContent(map.get("signature_15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_16") && StringUtils.isNotEmpty(map.get("signature_16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_16");
            nursingPlan.setContent(map.get("signature_16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_17") && StringUtils.isNotEmpty(map.get("signature_17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_17");
            nursingPlan.setContent(map.get("signature_17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_18") && StringUtils.isNotEmpty(map.get("signature_18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_18");
            nursingPlan.setContent(map.get("signature_18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_19") && StringUtils.isNotEmpty(map.get("signature_19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_19");
            nursingPlan.setContent(map.get("signature_19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_20") && StringUtils.isNotEmpty(map.get("signature_20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_20");
            nursingPlan.setContent(map.get("signature_20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_21") && StringUtils.isNotEmpty(map.get("signature_21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_21");
            nursingPlan.setContent(map.get("signature_21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_22") && StringUtils.isNotEmpty(map.get("signature_22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_22");
            nursingPlan.setContent(map.get("signature_22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_23") && StringUtils.isNotEmpty(map.get("signature_23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_23");
            nursingPlan.setContent(map.get("signature_23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_24") && StringUtils.isNotEmpty(map.get("signature_24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_24");
            nursingPlan.setContent(map.get("signature_24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_25") && StringUtils.isNotEmpty(map.get("signature_25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_25");
            nursingPlan.setContent(map.get("signature_25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_26") && StringUtils.isNotEmpty(map.get("signature_26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_26");
            nursingPlan.setContent(map.get("signature_26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_27") && StringUtils.isNotEmpty(map.get("signature_27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature_27");
            nursingPlan.setContent(map.get("signature_27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("stopDate_0") && StringUtils.isNotEmpty(map.get("stopDate_0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_0");
            nursingPlan.setContent(map.get("stopDate_0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_1") && StringUtils.isNotEmpty(map.get("stopDate_1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_1");
            nursingPlan.setContent(map.get("stopDate_1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_2") && StringUtils.isNotEmpty(map.get("stopDate_2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_2");
            nursingPlan.setContent(map.get("stopDate_2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_3") && StringUtils.isNotEmpty(map.get("stopDate_3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_3");
            nursingPlan.setContent(map.get("stopDate_3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_4") && StringUtils.isNotEmpty(map.get("stopDate_4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_4");
            nursingPlan.setContent(map.get("stopDate_4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_5") && StringUtils.isNotEmpty(map.get("stopDate_5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_5");
            nursingPlan.setContent(map.get("stopDate_5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_6") && StringUtils.isNotEmpty(map.get("stopDate_6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_6");
            nursingPlan.setContent(map.get("stopDate_6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_7") && StringUtils.isNotEmpty(map.get("stopDate_7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_7");
            nursingPlan.setContent(map.get("stopDate_7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_8") && StringUtils.isNotEmpty(map.get("stopDate_8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_8");
            nursingPlan.setContent(map.get("stopDate_8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_9") && StringUtils.isNotEmpty(map.get("stopDate_9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_9");
            nursingPlan.setContent(map.get("stopDate_9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_10") && StringUtils.isNotEmpty(map.get("stopDate_10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_10");
            nursingPlan.setContent(map.get("stopDate_10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_11") && StringUtils.isNotEmpty(map.get("stopDate_11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_11");
            nursingPlan.setContent(map.get("stopDate_11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_12") && StringUtils.isNotEmpty(map.get("stopDate_12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_12");
            nursingPlan.setContent(map.get("stopDate_12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_13") && StringUtils.isNotEmpty(map.get("stopDate_13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_13");
            nursingPlan.setContent(map.get("stopDate_13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature_14") && StringUtils.isNotEmpty(map.get("signature_14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_14");
            nursingPlan.setContent(map.get("stopDate_14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_15") && StringUtils.isNotEmpty(map.get("stopDate_15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_15");
            nursingPlan.setContent(map.get("stopDate_15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_16") && StringUtils.isNotEmpty(map.get("stopDate_16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_16");
            nursingPlan.setContent(map.get("stopDate_16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_17") && StringUtils.isNotEmpty(map.get("stopDate_17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_17");
            nursingPlan.setContent(map.get("stopDate_17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_18") && StringUtils.isNotEmpty(map.get("stopDate_18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_18");
            nursingPlan.setContent(map.get("stopDate_18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_19") && StringUtils.isNotEmpty(map.get("stopDate_19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_19");
            nursingPlan.setContent(map.get("stopDate_19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_20") && StringUtils.isNotEmpty(map.get("stopDate_20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_20");
            nursingPlan.setContent(map.get("stopDate_20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_21") && StringUtils.isNotEmpty(map.get("stopDate_21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_21");
            nursingPlan.setContent(map.get("stopDate_21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_22") && StringUtils.isNotEmpty(map.get("stopDate_22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_22");
            nursingPlan.setContent(map.get("stopDate_22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_23") && StringUtils.isNotEmpty(map.get("stopDate_23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_23");
            nursingPlan.setContent(map.get("stopDate_23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_24") && StringUtils.isNotEmpty(map.get("stopDate_24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_24");
            nursingPlan.setContent(map.get("stopDate_24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_25") && StringUtils.isNotEmpty(map.get("stopDate_25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_25");
            nursingPlan.setContent(map.get("stopDate_25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_26") && StringUtils.isNotEmpty(map.get("stopDate_26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_26");
            nursingPlan.setContent(map.get("stopDate_26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate_27") && StringUtils.isNotEmpty(map.get("stopDate_27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate_27");
            nursingPlan.setContent(map.get("stopDate_27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        json.put("msg","保存成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 新建一个对象
     * @return
     */
    private NursingPlan newNursingPlan(Map<String,Object> map,HttpSession session){
        NursingPlan nursingPlan=new NursingPlan();
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            nursingPlan.setId(UUIDUtil.getUUID());
            nursingPlan.setStatus(1);
            nursingPlan.setCreateTime(new Date());
            if(map!=null && map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                nursingPlan.setDataTime(sdf.parse(map.get("dataTime").toString()));
            }
            if(map!=null && map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                nursingPlan.setPatientId(map.get("patientId").toString());
            }
            if(map!=null && map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString())){
                nursingPlan.setVisitCode(map.get("visitCode").toString());
            }
            if(map!=null && map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString())){
                nursingPlan.setVisitId(map.get("visitId").toString());
            }
            User user=(User)session.getAttribute("user");
            if(user!=null){
                nursingPlan.setOperatorCode(user.getLoginName());
            }
        }catch (Exception e){
            logger.info("新建一个对象-newNursingPlan:"+e);
        }
       return  nursingPlan;
    }

    /**
     * 护理文书--护理计划单--历史列表--分页查询列表
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
        if(map!=null && map.containsKey("start")){
            int totalCount =nursingPlanService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =nursingPlanService.getList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理计划单--历史列表--删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData",method= RequestMethod.POST)
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","删除失败");
        if(map!=null && map.containsKey("assessmentId")){
            List<NursingPlan> list =nursingPlanService.queryListByAssessmentId(map.get("assessmentId").toString());
            if(list!=null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setStatus(-1);
                    nursingPlanService.update(list.get(i));
                }
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 护理文书--护理计划单--历史列表--选择
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryDataByAssessmentId",method= RequestMethod.POST)
    @ResponseBody
    public String queryDataByAssessmentId(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("assessmentId")){
            Map<String,Object> data =nursingPlanService.queryDataByAssessmentId(map);
            if(data!=null){
                json.put("data",JSONObject.fromObject(data));
                json.put("msg","查询成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

}
