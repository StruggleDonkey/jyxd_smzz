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
        if(map!=null && map.containsKey("startDate-0") && StringUtils.isNotEmpty(map.get("startDate-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-0");
            nursingPlan.setContent(map.get("startDate-0").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-1") && StringUtils.isNotEmpty(map.get("startDate-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-1");
            nursingPlan.setContent(map.get("startDate-1").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-2") && StringUtils.isNotEmpty(map.get("startDate-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-2");
            nursingPlan.setContent(map.get("startDate-2").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-3") && StringUtils.isNotEmpty(map.get("startDate-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-3");
            nursingPlan.setContent(map.get("startDate-3").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-4") && StringUtils.isNotEmpty(map.get("startDate-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-4");
            nursingPlan.setContent(map.get("startDate-4").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-5") && StringUtils.isNotEmpty(map.get("startDate-5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-5");
            nursingPlan.setContent(map.get("startDate-5").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-6") && StringUtils.isNotEmpty(map.get("startDate-6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-6");
            nursingPlan.setContent(map.get("startDate-6").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-7") && StringUtils.isNotEmpty(map.get("startDate-7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-7");
            nursingPlan.setContent(map.get("startDate-7").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-8") && StringUtils.isNotEmpty(map.get("startDate-8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-8");
            nursingPlan.setContent(map.get("startDate-8").toString());
            nursingPlanService.insert(nursingPlan);
        }

        if(map!=null && map.containsKey("startDate-9") && StringUtils.isNotEmpty(map.get("startDate-9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-9");
            nursingPlan.setContent(map.get("startDate-9").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startDate-10") && StringUtils.isNotEmpty(map.get("startDate-10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-10");
            nursingPlan.setContent(map.get("startDate-10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-11") && StringUtils.isNotEmpty(map.get("startDate-11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-11");
            nursingPlan.setContent(map.get("startDate-11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-12") && StringUtils.isNotEmpty(map.get("startDate-12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-12");
            nursingPlan.setContent(map.get("startDate-12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-13") && StringUtils.isNotEmpty(map.get("startDate-13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-13");
            nursingPlan.setContent(map.get("startDate-13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-14") && StringUtils.isNotEmpty(map.get("startDate-14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-14");
            nursingPlan.setContent(map.get("startDate-14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-15") && StringUtils.isNotEmpty(map.get("startDate-15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-15");
            nursingPlan.setContent(map.get("startDate-15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-16") && StringUtils.isNotEmpty(map.get("startDate-16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-16");
            nursingPlan.setContent(map.get("startDate-16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-17") && StringUtils.isNotEmpty(map.get("startDate-17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-17");
            nursingPlan.setContent(map.get("startDate-17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-18") && StringUtils.isNotEmpty(map.get("startDate-18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-18");
            nursingPlan.setContent(map.get("startDate-18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-19") && StringUtils.isNotEmpty(map.get("startDate-19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-19");
            nursingPlan.setContent(map.get("startDate-19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-20") && StringUtils.isNotEmpty(map.get("startDate-20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-20");
            nursingPlan.setContent(map.get("startDate-20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-21") && StringUtils.isNotEmpty(map.get("startDate-21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-21");
            nursingPlan.setContent(map.get("startDate-21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-22") && StringUtils.isNotEmpty(map.get("startDate-22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-22");
            nursingPlan.setContent(map.get("startDate-22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-23") && StringUtils.isNotEmpty(map.get("startDate-23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-23");
            nursingPlan.setContent(map.get("startDate-23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-24") && StringUtils.isNotEmpty(map.get("startDate-24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-24");
            nursingPlan.setContent(map.get("startDate-24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-25") && StringUtils.isNotEmpty(map.get("startDate-25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-25");
            nursingPlan.setContent(map.get("startDate-25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-26") && StringUtils.isNotEmpty(map.get("startDate-26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-26");
            nursingPlan.setContent(map.get("startDate-26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startDate-27") && StringUtils.isNotEmpty(map.get("startDate-27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startDate-27");
            nursingPlan.setContent(map.get("startDate-27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("startTime-0") && StringUtils.isNotEmpty(map.get("startTime-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-0");
            nursingPlan.setContent(map.get("startTime-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-1") && StringUtils.isNotEmpty(map.get("startTime-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-1");
            nursingPlan.setContent(map.get("startTime-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-2") && StringUtils.isNotEmpty(map.get("startTime-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-2");
            nursingPlan.setContent(map.get("startTime-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-3") && StringUtils.isNotEmpty(map.get("startTime-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-3");
            nursingPlan.setContent(map.get("startTime-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-4") && StringUtils.isNotEmpty(map.get("startTime-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-4");
            nursingPlan.setContent(map.get("startTime-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-5") && StringUtils.isNotEmpty(map.get("startTime-5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-5");
            nursingPlan.setContent(map.get("startTime-5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-6") && StringUtils.isNotEmpty(map.get("startTime-6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-6");
            nursingPlan.setContent(map.get("startTime-6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-7") && StringUtils.isNotEmpty(map.get("startTime-7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-7");
            nursingPlan.setContent(map.get("startTime-7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-8") && StringUtils.isNotEmpty(map.get("startTime-8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-8");
            nursingPlan.setContent(map.get("startTime-8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-9") && StringUtils.isNotEmpty(map.get("startTime-9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-9");
            nursingPlan.setContent(map.get("startTime-9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-10") && StringUtils.isNotEmpty(map.get("startTime-10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-10");
            nursingPlan.setContent(map.get("startTime-10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-11") && StringUtils.isNotEmpty(map.get("startTime-11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-11");
            nursingPlan.setContent(map.get("startTime-11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-12") && StringUtils.isNotEmpty(map.get("startTime-12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-12");
            nursingPlan.setContent(map.get("startTime-12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-13") && StringUtils.isNotEmpty(map.get("startTime-13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-13");
            nursingPlan.setContent(map.get("startTime-13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-14") && StringUtils.isNotEmpty(map.get("startTime-14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-14");
            nursingPlan.setContent(map.get("startTime-14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-15") && StringUtils.isNotEmpty(map.get("startTime-15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-15");
            nursingPlan.setContent(map.get("startTime-15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-16") && StringUtils.isNotEmpty(map.get("startTime-16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-16");
            nursingPlan.setContent(map.get("startTime-16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-17") && StringUtils.isNotEmpty(map.get("startTime-17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-17");
            nursingPlan.setContent(map.get("startTime-17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-18") && StringUtils.isNotEmpty(map.get("startTime-18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-18");
            nursingPlan.setContent(map.get("startTime-18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-19") && StringUtils.isNotEmpty(map.get("startTime-19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-19");
            nursingPlan.setContent(map.get("startTime-19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-20") && StringUtils.isNotEmpty(map.get("startTime-20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-20");
            nursingPlan.setContent(map.get("startTime-20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-21") && StringUtils.isNotEmpty(map.get("startTime-21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-21");
            nursingPlan.setContent(map.get("startTime-21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-22") && StringUtils.isNotEmpty(map.get("startTime-22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-22");
            nursingPlan.setContent(map.get("startTime-22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-23") && StringUtils.isNotEmpty(map.get("startTime-23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-23");
            nursingPlan.setContent(map.get("startTime-23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-24") && StringUtils.isNotEmpty(map.get("startTime-24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-24");
            nursingPlan.setContent(map.get("startTime-24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-25") && StringUtils.isNotEmpty(map.get("startTime-25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-25");
            nursingPlan.setContent(map.get("startTime-25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-26") && StringUtils.isNotEmpty(map.get("startTime-26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-26");
            nursingPlan.setContent(map.get("startTime-26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("startTime-27") && StringUtils.isNotEmpty(map.get("startTime-27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("startTime-27");
            nursingPlan.setContent(map.get("startTime-27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("plan-0-0") && StringUtils.isNotEmpty(map.get("plan-0-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-0-0");
            nursingPlan.setContent(map.get("plan-0-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-1-0") && StringUtils.isNotEmpty(map.get("plan-1-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-1-0");
            nursingPlan.setContent(map.get("plan-1-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-2-0") && StringUtils.isNotEmpty(map.get("plan-2-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-2-0");
            nursingPlan.setContent(map.get("plan-2-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-3-0") && StringUtils.isNotEmpty(map.get("plan-3-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-3-0");
            nursingPlan.setContent(map.get("plan-3-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-4-0") && StringUtils.isNotEmpty(map.get("plan-4-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-4-0");
            nursingPlan.setContent(map.get("plan-4-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-5-0") && StringUtils.isNotEmpty(map.get("plan-5-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-5-0");
            nursingPlan.setContent(map.get("plan-5-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-6-0") && StringUtils.isNotEmpty(map.get("plan-6-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-6-0");
            nursingPlan.setContent(map.get("plan-6-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-7-0") && StringUtils.isNotEmpty(map.get("plan-7-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-7-0");
            nursingPlan.setContent(map.get("plan-7-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-7-1") && StringUtils.isNotEmpty(map.get("plan-7-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-7-1");
            nursingPlan.setContent(map.get("plan-7-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-8-0") && StringUtils.isNotEmpty(map.get("plan-8-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-8-0");
            nursingPlan.setContent(map.get("plan-8-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-9-0") && StringUtils.isNotEmpty(map.get("plan-9-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-9-0");
            nursingPlan.setContent(map.get("plan-9-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-10-0") && StringUtils.isNotEmpty(map.get("plan-10-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-10-0");
            nursingPlan.setContent(map.get("plan-10-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-11-0") && StringUtils.isNotEmpty(map.get("plan-11-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-11-0");
            nursingPlan.setContent(map.get("plan-11-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-11-1") && StringUtils.isNotEmpty(map.get("plan-11-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-11-1");
            nursingPlan.setContent(map.get("plan-11-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-11-2") && StringUtils.isNotEmpty(map.get("plan-11-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-11-2");
            nursingPlan.setContent(map.get("plan-11-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-12-0") && StringUtils.isNotEmpty(map.get("plan-12-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-12-0");
            nursingPlan.setContent(map.get("plan-12-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-12-1") && StringUtils.isNotEmpty(map.get("plan-12-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-12-1");
            nursingPlan.setContent(map.get("plan-12-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-12-2") && StringUtils.isNotEmpty(map.get("plan-12-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-12-2");
            nursingPlan.setContent(map.get("plan-12-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-13-0") && StringUtils.isNotEmpty(map.get("plan-13-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-13-0");
            nursingPlan.setContent(map.get("plan-13-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-14-0") && StringUtils.isNotEmpty(map.get("plan-14-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-14-0");
            nursingPlan.setContent(map.get("plan-14-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-15-0") && StringUtils.isNotEmpty(map.get("plan-15-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-15-0");
            nursingPlan.setContent(map.get("plan-15-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-16-0") && StringUtils.isNotEmpty(map.get("plan-16-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-16-0");
            nursingPlan.setContent(map.get("plan-16-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-17-0") && StringUtils.isNotEmpty(map.get("plan-17-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-17-0");
            nursingPlan.setContent(map.get("plan-17-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-17-1") && StringUtils.isNotEmpty(map.get("plan-17-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-17-1");
            nursingPlan.setContent(map.get("plan-17-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-17-2") && StringUtils.isNotEmpty(map.get("plan-17-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-17-2");
            nursingPlan.setContent(map.get("plan-17-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-17-3") && StringUtils.isNotEmpty(map.get("plan-17-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-17-3");
            nursingPlan.setContent(map.get("plan-17-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-17-4") && StringUtils.isNotEmpty(map.get("plan-17-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-17-4");
            nursingPlan.setContent(map.get("plan-17-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-0") && StringUtils.isNotEmpty(map.get("plan-18-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-0");
            nursingPlan.setContent(map.get("plan-18-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-1") && StringUtils.isNotEmpty(map.get("plan-18-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-1");
            nursingPlan.setContent(map.get("plan-18-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-2") && StringUtils.isNotEmpty(map.get("plan-18-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-2");
            nursingPlan.setContent(map.get("plan-18-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-3") && StringUtils.isNotEmpty(map.get("plan-18-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-3");
            nursingPlan.setContent(map.get("plan-18-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-4") && StringUtils.isNotEmpty(map.get("plan-18-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-4");
            nursingPlan.setContent(map.get("plan-18-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-5") && StringUtils.isNotEmpty(map.get("plan-18-5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-5");
            nursingPlan.setContent(map.get("plan-18-5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-18-6") && StringUtils.isNotEmpty(map.get("plan-18-6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-18-6");
            nursingPlan.setContent(map.get("plan-18-6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-19-0") && StringUtils.isNotEmpty(map.get("plan-19-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-19-0");
            nursingPlan.setContent(map.get("plan-19-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-19-1") && StringUtils.isNotEmpty(map.get("plan-19-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-19-1");
            nursingPlan.setContent(map.get("plan-19-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-19-2") && StringUtils.isNotEmpty(map.get("plan-19-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-19-2");
            nursingPlan.setContent(map.get("plan-19-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-19-3") && StringUtils.isNotEmpty(map.get("plan-19-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-19-3");
            nursingPlan.setContent(map.get("plan-19-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-20-0") && StringUtils.isNotEmpty(map.get("plan-20-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-20-0");
            nursingPlan.setContent(map.get("plan-20-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-20-1") && StringUtils.isNotEmpty(map.get("plan-20-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-20-1");
            nursingPlan.setContent(map.get("plan-20-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-21-0") && StringUtils.isNotEmpty(map.get("plan-21-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-21-0");
            nursingPlan.setContent(map.get("plan-21-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-21-1") && StringUtils.isNotEmpty(map.get("plan-21-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-21-1");
            nursingPlan.setContent(map.get("plan-21-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-21-2") && StringUtils.isNotEmpty(map.get("plan-21-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-21-2");
            nursingPlan.setContent(map.get("plan-21-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-21-3") && StringUtils.isNotEmpty(map.get("plan-21-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-21-3");
            nursingPlan.setContent(map.get("plan-21-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-21-4") && StringUtils.isNotEmpty(map.get("plan-21-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-21-4");
            nursingPlan.setContent(map.get("plan-21-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-22-0") && StringUtils.isNotEmpty(map.get("plan-22-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-22-0");
            nursingPlan.setContent(map.get("plan-22-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-23-0") && StringUtils.isNotEmpty(map.get("plan-23-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-23-0");
            nursingPlan.setContent(map.get("plan-23-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-24-0") && StringUtils.isNotEmpty(map.get("plan-24-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-24-0");
            nursingPlan.setContent(map.get("plan-24-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-24-1") && StringUtils.isNotEmpty(map.get("plan-24-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-24-1");
            nursingPlan.setContent(map.get("plan-24-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-25-0") && StringUtils.isNotEmpty(map.get("plan-25-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-25-0");
            nursingPlan.setContent(map.get("plan-25-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-26-0") && StringUtils.isNotEmpty(map.get("plan-26-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-26-0");
            nursingPlan.setContent(map.get("plan-26-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("plan-27-0") && StringUtils.isNotEmpty(map.get("plan-27-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("plan-27-0");
            nursingPlan.setContent(map.get("plan-27-0").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("signature-0") && StringUtils.isNotEmpty(map.get("signature-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-0");
            nursingPlan.setContent(map.get("signature-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-1") && StringUtils.isNotEmpty(map.get("signature-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-1");
            nursingPlan.setContent(map.get("signature-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-2") && StringUtils.isNotEmpty(map.get("signature-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-2");
            nursingPlan.setContent(map.get("signature-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-3") && StringUtils.isNotEmpty(map.get("signature-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-3");
            nursingPlan.setContent(map.get("signature-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-4") && StringUtils.isNotEmpty(map.get("signature-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-4");
            nursingPlan.setContent(map.get("signature-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-5") && StringUtils.isNotEmpty(map.get("signature-5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-5");
            nursingPlan.setContent(map.get("signature-5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-6") && StringUtils.isNotEmpty(map.get("signature-6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-6");
            nursingPlan.setContent(map.get("signature-6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-7") && StringUtils.isNotEmpty(map.get("signature-7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-7");
            nursingPlan.setContent(map.get("signature-7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-8") && StringUtils.isNotEmpty(map.get("signature-8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-8");
            nursingPlan.setContent(map.get("signature-8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-9") && StringUtils.isNotEmpty(map.get("signature-9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-9");
            nursingPlan.setContent(map.get("signature-9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-10") && StringUtils.isNotEmpty(map.get("signature-10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-10");
            nursingPlan.setContent(map.get("signature-10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-11") && StringUtils.isNotEmpty(map.get("signature-11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-11");
            nursingPlan.setContent(map.get("signature-11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-12") && StringUtils.isNotEmpty(map.get("signature-12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-12");
            nursingPlan.setContent(map.get("signature-12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-13") && StringUtils.isNotEmpty(map.get("signature-13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-13");
            nursingPlan.setContent(map.get("signature-13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-14") && StringUtils.isNotEmpty(map.get("signature-14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-14");
            nursingPlan.setContent(map.get("signature-14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-15") && StringUtils.isNotEmpty(map.get("signature-15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-15");
            nursingPlan.setContent(map.get("signature-15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-16") && StringUtils.isNotEmpty(map.get("signature-16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-16");
            nursingPlan.setContent(map.get("signature-16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-17") && StringUtils.isNotEmpty(map.get("signature-17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-17");
            nursingPlan.setContent(map.get("signature-17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-18") && StringUtils.isNotEmpty(map.get("signature-18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-18");
            nursingPlan.setContent(map.get("signature-18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-19") && StringUtils.isNotEmpty(map.get("signature-19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-19");
            nursingPlan.setContent(map.get("signature-19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-20") && StringUtils.isNotEmpty(map.get("signature-20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-20");
            nursingPlan.setContent(map.get("signature-20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-21") && StringUtils.isNotEmpty(map.get("signature-21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-21");
            nursingPlan.setContent(map.get("signature-21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-22") && StringUtils.isNotEmpty(map.get("signature-22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-22");
            nursingPlan.setContent(map.get("signature-22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-23") && StringUtils.isNotEmpty(map.get("signature-23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-23");
            nursingPlan.setContent(map.get("signature-23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-24") && StringUtils.isNotEmpty(map.get("signature-24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-24");
            nursingPlan.setContent(map.get("signature-24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-25") && StringUtils.isNotEmpty(map.get("signature-25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-25");
            nursingPlan.setContent(map.get("signature-25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-26") && StringUtils.isNotEmpty(map.get("signature-26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-26");
            nursingPlan.setContent(map.get("signature-26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-27") && StringUtils.isNotEmpty(map.get("signature-27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("signature-27");
            nursingPlan.setContent(map.get("signature-27").toString());
            nursingPlanService.insert(nursingPlan);
        }
        if(map!=null && map.containsKey("stopDate-0") && StringUtils.isNotEmpty(map.get("stopDate-0").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-0");
            nursingPlan.setContent(map.get("stopDate-0").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-1") && StringUtils.isNotEmpty(map.get("stopDate-1").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-1");
            nursingPlan.setContent(map.get("stopDate-1").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-2") && StringUtils.isNotEmpty(map.get("stopDate-2").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-2");
            nursingPlan.setContent(map.get("stopDate-2").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-3") && StringUtils.isNotEmpty(map.get("stopDate-3").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-3");
            nursingPlan.setContent(map.get("stopDate-3").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-4") && StringUtils.isNotEmpty(map.get("stopDate-4").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-4");
            nursingPlan.setContent(map.get("stopDate-4").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-5") && StringUtils.isNotEmpty(map.get("stopDate-5").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-5");
            nursingPlan.setContent(map.get("stopDate-5").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-6") && StringUtils.isNotEmpty(map.get("stopDate-6").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-6");
            nursingPlan.setContent(map.get("stopDate-6").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-7") && StringUtils.isNotEmpty(map.get("stopDate-7").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-7");
            nursingPlan.setContent(map.get("stopDate-7").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-8") && StringUtils.isNotEmpty(map.get("stopDate-8").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-8");
            nursingPlan.setContent(map.get("stopDate-8").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-9") && StringUtils.isNotEmpty(map.get("stopDate-9").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-9");
            nursingPlan.setContent(map.get("stopDate-9").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-10") && StringUtils.isNotEmpty(map.get("stopDate-10").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-10");
            nursingPlan.setContent(map.get("stopDate-10").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-11") && StringUtils.isNotEmpty(map.get("stopDate-11").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-11");
            nursingPlan.setContent(map.get("stopDate-11").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-12") && StringUtils.isNotEmpty(map.get("stopDate-12").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-12");
            nursingPlan.setContent(map.get("stopDate-12").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-13") && StringUtils.isNotEmpty(map.get("stopDate-13").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-13");
            nursingPlan.setContent(map.get("stopDate-13").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("signature-14") && StringUtils.isNotEmpty(map.get("signature-14").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-14");
            nursingPlan.setContent(map.get("stopDate-14").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-15") && StringUtils.isNotEmpty(map.get("stopDate-15").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-15");
            nursingPlan.setContent(map.get("stopDate-15").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-16") && StringUtils.isNotEmpty(map.get("stopDate-16").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-16");
            nursingPlan.setContent(map.get("stopDate-16").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-17") && StringUtils.isNotEmpty(map.get("stopDate-17").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-17");
            nursingPlan.setContent(map.get("stopDate-17").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-18") && StringUtils.isNotEmpty(map.get("stopDate-18").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-18");
            nursingPlan.setContent(map.get("stopDate-18").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-19") && StringUtils.isNotEmpty(map.get("stopDate-19").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-19");
            nursingPlan.setContent(map.get("stopDate-19").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-20") && StringUtils.isNotEmpty(map.get("stopDate-20").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-20");
            nursingPlan.setContent(map.get("stopDate-20").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-21") && StringUtils.isNotEmpty(map.get("stopDate-21").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-21");
            nursingPlan.setContent(map.get("stopDate-21").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-22") && StringUtils.isNotEmpty(map.get("stopDate-22").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-22");
            nursingPlan.setContent(map.get("stopDate-22").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-23") && StringUtils.isNotEmpty(map.get("stopDate-23").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-23");
            nursingPlan.setContent(map.get("stopDate-23").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-24") && StringUtils.isNotEmpty(map.get("stopDate-24").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-24");
            nursingPlan.setContent(map.get("stopDate-24").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-25") && StringUtils.isNotEmpty(map.get("stopDate-25").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-25");
            nursingPlan.setContent(map.get("stopDate-25").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-26") && StringUtils.isNotEmpty(map.get("stopDate-26").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-26");
            nursingPlan.setContent(map.get("stopDate-26").toString());
            nursingPlanService.insert(nursingPlan);
        }if(map!=null && map.containsKey("stopDate-27") && StringUtils.isNotEmpty(map.get("stopDate-27").toString())){
            NursingPlan nursingPlan=newNursingPlan(map,session);
            nursingPlan.setAssessmentId(assessmentId);
            nursingPlan.setCode("stopDate-27");
            nursingPlan.setContent(map.get("stopDate-27").toString());
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
