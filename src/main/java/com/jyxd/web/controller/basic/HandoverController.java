package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.Handover;
import com.jyxd.web.data.basic.HandoverRecord;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.*;
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
@RequestMapping(value = "/handover")
public class HandoverController {

    private static Logger logger= LoggerFactory.getLogger(HandoverController.class);

    @Autowired
    private HandoverService handoverService;

    @Autowired
    private HandoverRecordService handoverRecordService;

    @Autowired
    private VitalSignService vitalSignService;

    @Autowired
    private OutputAmountService outputAmountService;

    @Autowired
    private InputAmountService inputAmountService;

    @Autowired
    private BasicNursingService basicNursingService;

    /**
     * 增加一条交班记录主表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody Handover handover){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        handover.setId(UUIDUtil.getUUID());
        handover.setCreateTime(new Date());
        handoverService.insert(handover);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新交班记录主表记录状态
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
            Handover handover=handoverService.queryData(map.get("id").toString());
            if(handover!=null){
                handover.setStatus((int)map.get("status"));
                handoverService.update(handover);
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
     * 编辑交班记录主表记录单
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
            Handover handover=handoverService.queryData(map.get("id").toString());
            if(handover!=null){
                handover.setStatus((int)map.get("status"));
                handoverService.update(handover);
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
     * 删除交班记录主表记录
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
            Handover handover=handoverService.queryData(map.get("id").toString());
            if(handover!=null){
                handover.setStatus(-1);
                handoverService.update(handover);
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
     * 根据主键id查询交班记录主表记录
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
            Handover handover=handoverService.queryData(map.get("id").toString());
            if(handover!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(handover));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询交班记录主表记录列表（也可以不分页）
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
            int totalCount =handoverService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Handover> list =handoverService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 交接班--新增交班报告--一键生成交班报告
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPatientMessage")
    @ResponseBody
    public String getPatientMessage(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","暂无数据");
        //某个时间的生命体征信息
        JSONObject nowJson=vitalSignService.getNowVitalSign(map);
        json.put("vital",nowJson);
        //某个时间段的最大生命体征信息
        JSONObject maxJson=vitalSignService.getMaxVitalSign(map);
        json.put("maxVital",maxJson);
        //某个时间段的最小生命体征信息
        JSONObject minJson=vitalSignService.getMinVitalSign(map);
        json.put("minVital",minJson);
        //某个时间段的出量信息汇总
        JSONObject outJson=outputAmountService.getOutAmount(map);
        json.put("outPutAmount",outJson);
        //某个时间段的入量信息汇总
        JSONObject inJson=inputAmountService.getInAmount(map);
        json.put("inPutAmount",inJson);
        //某个时间段的人工气道方式
        JSONObject airJson=vitalSignService.getAirWay(map);
        json.put("airWay",airJson);
        //某个时间段的静脉置管
        JSONArray veinArray=basicNursingService.getVeinDrainage(map);
        json.put("veinDrainage",veinArray);
        //某个时间段的静脉置管
        JSONArray arteryArray=basicNursingService.getArtery(map);
        json.put("artery",arteryArray);
        //某个时间段的引流管
        JSONArray drainageArray=basicNursingService.getDrainage(map);
        json.put("drainage",drainageArray);

        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 交接班--新增交班报告--确认交班/存为草稿
     * @param map
     * @return
     */
    @RequestMapping(value = "/confirmHandover")
    @ResponseBody
    public String confirmHandover(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        try {
            String operatorCode="";
            User user=(User)session.getAttribute("user");
            Handover handover=new Handover();
            handover.setId(UUIDUtil.getUUID());
            handover.setStatus((int)map.get("status"));//状态（-1:删除，0:草稿箱，1:已交班，2:已接班）
            handover.setCreateTime(new Date());
            handover.setShift((int)map.get("shift"));
            handover.setVisitId(map.get("visitId").toString());
            handover.setVisitCode(map.get("visitCode").toString());
            handover.setPatientId(map.get("patientId").toString());
            handover.setSendOperator(map.get("sendOperator").toString());
            handover.setSendDescription(map.get("sendDescription").toString());
            if(user!=null){
                operatorCode=user.getLoginName();
                handover.setOperatorCode(user.getLoginName());
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            handover.setHandoverBeginTime(format.parse(map.get("beginTime").toString()));
            handover.setHandoverEndTime(format.parse(map.get("endTime").toString()));
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            handover.setInfoDate(formatDate.parse(map.get("infoDate").toString()));
            handoverService.insert(handover);

            //添加交接班记录明细表记录
            Date date=new Date();
            String handoverId=handover.getId();

            //床号：bedName
            if(map.containsKey("bedName") && StringUtils.isNotEmpty(map.get("bedName").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("bedName");
                handoverRecord.setContent(map.get("bedName").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //姓名：name
            if(map.containsKey("name") && StringUtils.isNotEmpty(map.get("name").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("name");
                handoverRecord.setContent(map.get("name").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //性别（男 女）：sex
            if(map.containsKey("sex") && StringUtils.isNotEmpty(map.get("sex").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("sex");
                handoverRecord.setContent(map.get("sex").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //年龄：age
            if(map.containsKey("age") && StringUtils.isNotEmpty(map.get("age").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("age");
                handoverRecord.setContent(map.get("age").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //来源科室：department
            if(map.containsKey("department") && StringUtils.isNotEmpty(map.get("department").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("department");
                handoverRecord.setContent(map.get("department").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //入科时间：visitTime
            if(map.containsKey("visitTime") && StringUtils.isNotEmpty(map.get("visitTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("visitTime");
                handoverRecord.setContent(map.get("visitTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //手术时间：operationTime
            if(map.containsKey("operationTime") && StringUtils.isNotEmpty(map.get("operationTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("operationTime");
                handoverRecord.setContent(map.get("operationTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //过敏史：allergies
            if(map.containsKey("allergies") && StringUtils.isNotEmpty(map.get("allergies").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("allergies");
                handoverRecord.setContent(map.get("allergies").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //诊断：diagnosisName
            if(map.containsKey("diagnosisName") && StringUtils.isNotEmpty(map.get("diagnosisName").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("diagnosisName");
                handoverRecord.setContent(map.get("diagnosisName").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //生命体征时间：vitalSignTime
            if(map.containsKey("vitalSignTime") && StringUtils.isNotEmpty(map.get("vitalSignTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("vitalSignTime");
                handoverRecord.setContent(map.get("vitalSignTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-体温：temperature
            if(map.containsKey("temperature") && StringUtils.isNotEmpty(map.get("temperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("temperature");
                handoverRecord.setContent(map.get("temperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-脉搏：pulse
            if(map.containsKey("pulse") && StringUtils.isNotEmpty(map.get("pulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pulse");
                handoverRecord.setContent(map.get("pulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-心率：hr
            if(map.containsKey("hr") && StringUtils.isNotEmpty(map.get("hr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("hr");
                handoverRecord.setContent(map.get("hr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-呼吸：br
            if(map.containsKey("br") && StringUtils.isNotEmpty(map.get("br").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("br");
                handoverRecord.setContent(map.get("br").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-SpO₂：spo2
            if(map.containsKey("spo2") && StringUtils.isNotEmpty(map.get("spo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("spo2");
                handoverRecord.setContent(map.get("spo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-有创血压：ibp
            if(map.containsKey("ibp") && StringUtils.isNotEmpty(map.get("ibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("ibp");
                handoverRecord.setContent(map.get("ibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-无创血压：bp
            if(map.containsKey("bp") && StringUtils.isNotEmpty(map.get("bp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("bp");
                handoverRecord.setContent(map.get("bp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-cvp：cvp
            if(map.containsKey("cvp") && StringUtils.isNotEmpty(map.get("cvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvp");
                handoverRecord.setContent(map.get("cvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-cvp（mmHg₂O）：cvpmmHg
            if(map.containsKey("cvpmmHg") && StringUtils.isNotEmpty(map.get("cvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvpmmHg");
                handoverRecord.setContent(map.get("cvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-体温：maxtemperature
            if(map.containsKey("maxtemperature") && StringUtils.isNotEmpty(map.get("maxtemperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxtemperature");
                handoverRecord.setContent(map.get("maxtemperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-脉搏：maxpulse
            if(map.containsKey("maxpulse") && StringUtils.isNotEmpty(map.get("maxpulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxpulse");
                handoverRecord.setContent(map.get("maxpulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-心率：maxhr
            if(map.containsKey("maxhr") && StringUtils.isNotEmpty(map.get("maxhr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxhr");
                handoverRecord.setContent(map.get("maxhr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-呼吸：maxbr
            if(map.containsKey("maxbr") && StringUtils.isNotEmpty(map.get("maxbr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxbr");
                handoverRecord.setContent(map.get("maxbr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-SpO₂：maxspo2
            if(map.containsKey("maxspo2") && StringUtils.isNotEmpty(map.get("maxspo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxspo2");
                handoverRecord.setContent(map.get("maxspo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-有创血压：maxibp
            if(map.containsKey("maxibp") && StringUtils.isNotEmpty(map.get("maxibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxibp");
                handoverRecord.setContent(map.get("maxibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-无创血压：maxbp
            if(map.containsKey("maxbp") && StringUtils.isNotEmpty(map.get("maxbp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxbp");
                handoverRecord.setContent(map.get("maxbp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-cvp：maxcvp
            if(map.containsKey("maxcvp") && StringUtils.isNotEmpty(map.get("maxcvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxcvp");
                handoverRecord.setContent(map.get("maxcvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-cvp（mmHg₂O）：maxcvpmmHg
            if(map.containsKey("maxcvpmmHg") && StringUtils.isNotEmpty(map.get("maxcvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxcvpmmHg");
                handoverRecord.setContent(map.get("maxcvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-体温：mintemperature
            if(map.containsKey("mintemperature") && StringUtils.isNotEmpty(map.get("mintemperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mintemperature");
                handoverRecord.setContent(map.get("mintemperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-脉搏：minpulse
            if(map.containsKey("minpulse") && StringUtils.isNotEmpty(map.get("minpulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minpulse");
                handoverRecord.setContent(map.get("minpulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-心率：minhr
            if(map.containsKey("minhr") && StringUtils.isNotEmpty(map.get("minhr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minhr");
                handoverRecord.setContent(map.get("minhr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-呼吸：minbr
            if(map.containsKey("minbr") && StringUtils.isNotEmpty(map.get("minbr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minbr");
                handoverRecord.setContent(map.get("minbr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-SpO₂：minspo2
            if(map.containsKey("minspo2") && StringUtils.isNotEmpty(map.get("minspo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minspo2");
                handoverRecord.setContent(map.get("minspo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-有创血压：minibp
            if(map.containsKey("minibp") && StringUtils.isNotEmpty(map.get("minibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minibp");
                handoverRecord.setContent(map.get("minibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-无创血压：minbp
            if(map.containsKey("minbp") && StringUtils.isNotEmpty(map.get("minbp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minbp");
                handoverRecord.setContent(map.get("minbp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-cvp：mincvp
            if(map.containsKey("mincvp") && StringUtils.isNotEmpty(map.get("mincvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mincvp");
                handoverRecord.setContent(map.get("mincvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-cvp（mmHg₂O）：mincvpmmHg
            if(map.containsKey("mincvpmmHg") && StringUtils.isNotEmpty(map.get("mincvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mincvpmmHg");
                handoverRecord.setContent(map.get("mincvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //总入量：intake
            if(map.containsKey("intake") && StringUtils.isNotEmpty(map.get("intake").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("intake");
                handoverRecord.setContent(map.get("intake").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //总出量：output
            if(map.containsKey("output") && StringUtils.isNotEmpty(map.get("output").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("output");
                handoverRecord.setContent(map.get("output").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //人工气道方式-气管插管：tracheaInsert
            if(map.containsKey("tracheaInsert") && StringUtils.isNotEmpty(map.get("tracheaInsert").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tracheaInsert");
                handoverRecord.setContent(map.get("tracheaInsert").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //人工气道方式-气管切开：tracheaCut
            if(map.containsKey("tracheaCut") && StringUtils.isNotEmpty(map.get("tracheaCut").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tracheaCut");
                handoverRecord.setContent(map.get("tracheaCut").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机型号：ventilatorModel
            if(map.containsKey("ventilatorModel") && StringUtils.isNotEmpty(map.get("ventilatorModel").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("ventilatorModel");
                handoverRecord.setContent(map.get("ventilatorModel").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机模式：breathePattern
            if(map.containsKey("breathePattern") && StringUtils.isNotEmpty(map.get("breathePattern").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("breathePattern");
                handoverRecord.setContent(map.get("breathePattern").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机插管深度：tubeDepth
            if(map.containsKey("tubeDepth") && StringUtils.isNotEmpty(map.get("tubeDepth").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tubeDepth");
                handoverRecord.setContent(map.get("tubeDepth").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机吸氧方式：oxygenMethod
            if(map.containsKey("oxygenMethod") && StringUtils.isNotEmpty(map.get("oxygenMethod").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("oxygenMethod");
                handoverRecord.setContent(map.get("oxygenMethod").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机
            // 流量：oxygenFlow
            if(map.containsKey("oxygenFlow") && StringUtils.isNotEmpty(map.get("oxygenFlow").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("oxygenFlow");
                handoverRecord.setContent(map.get("oxygenFlow").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-CVC：cvcDescription
            if(map.containsKey("cvcDescription") && StringUtils.isNotEmpty(map.get("cvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvcDescription");
                handoverRecord.setContent(map.get("cvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PVC：pvcDescription
            if(map.containsKey("pvcDescription") && StringUtils.isNotEmpty(map.get("pvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pvcDescription");
                handoverRecord.setContent(map.get("pvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PVC：pvcDescription
            if(map.containsKey("pvcDescription") && StringUtils.isNotEmpty(map.get("pvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pvcDescription");
                handoverRecord.setContent(map.get("pvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PORT：portDescription
            if(map.containsKey("portDescription") && StringUtils.isNotEmpty(map.get("portDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("portDescription");
                handoverRecord.setContent(map.get("portDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-留置针：leftNeedleDescription
            if(map.containsKey("leftNeedleDescription") && StringUtils.isNotEmpty(map.get("leftNeedleDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("leftNeedleDescription");
                handoverRecord.setContent(map.get("leftNeedleDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //动脉置管：artery
            if(map.containsKey("artery") && StringUtils.isNotEmpty(map.get("artery").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("artery");
                handoverRecord.setContent(map.get("artery").toString());
                handoverRecordService.insert(handoverRecord);
            }

            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }catch (Exception e){
            logger.info("交接班--新增交班报告--确认交班:"+e);
        }
        return json.toString();
    }

    private HandoverRecord newHandoverRecord(Map<String,Object> map,String handoverId,Date date,String operatorCode){
        HandoverRecord handoverRecord=new HandoverRecord();
        handoverRecord.setId(UUIDUtil.getUUID());
        handoverRecord.setHandoverId(handoverId);
        handoverRecord.setStatus(1);
        handoverRecord.setCreateTime(date);
        handoverRecord.setOperatorCode(operatorCode);
        handoverRecord.setVisitId(map.get("visitId").toString());
        handoverRecord.setVisitCode(map.get("visitCode").toString());
        handoverRecord.setPatientId(map.get("patientId").toString());
        return handoverRecord;
    }

    /**
     * 交接班--交接班列表--回退草稿
     * @param map
     * @return
     */
    @RequestMapping(value = "/fallbackDraftBox")
    @ResponseBody
    public String fallbackDraftBox(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        if(map!=null && map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
            Handover handover=handoverService.queryData(map.get("id").toString());
            if(handover!=null){
                handover.setStatus(0);//状态（-1:删除，0:草稿箱，1:已交班，2:已接班）
                handoverService.update(handover);
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 交接班--交接班列表--确认接班
     * @param map
     * @return
     */
    @RequestMapping(value = "/confirmSuccession")
    @ResponseBody
    public String confirmSuccession(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        try {
            if(map!=null && map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
                Handover handover=handoverService.queryData(map.get("id").toString());
                if(handover!=null){
                    handover.setStatus(2);//状态（-1:删除，0:草稿箱，1:已交班，2:已接班）
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(map.containsKey("receiveOperator") && StringUtils.isNotEmpty(map.get("receiveOperator").toString())){
                        handover.setReceiveOperator(map.get("receiveOperator").toString());
                    }
                    if(map.containsKey("receiveDescription") && StringUtils.isNotEmpty(map.get("receiveDescription").toString())){
                        handover.setReceiveDescription(map.get("receiveDescription").toString());
                    }
                    if(map.containsKey("handoverTime") && StringUtils.isNotEmpty(map.get("handoverTime").toString())){
                        handover.setHandoverTime(format.parse(map.get("handoverTime").toString()));
                    }
                    handoverService.update(handover);
                    json.put("msg","成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }
            }
        }catch (Exception e){
            logger.info("交接班--交接班列表--确认接班:"+e);
        }
        return json.toString();
    }

    /**
     * 交接班--交接班列表--打印
     * @param map
     * @return
     */
    @RequestMapping(value = "/printHandover")
    @ResponseBody
    public String printHandover(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        if(map!=null && map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
            Map<String,Object> handover=handoverService.printHandover(map);
            if(handover!=null){
                json.put("data",JSONObject.fromObject(handover));
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 交接班--根据条件分页查询交接班列表
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
            int totalCount =handoverService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =handoverService.getList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 交接班--交班草稿箱--编辑--存为草稿/确认交班
     * @param map
     * @return
     */
    @RequestMapping(value = "/editHandover")
    @ResponseBody
    public String editHandover(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        try {
            String operatorCode="";
            User user=(User)session.getAttribute("user");
            Handover handover=handoverService.queryData(map.get("id").toString());
            handover.setStatus((int)map.get("status"));//状态（-1:删除，0:草稿箱，1:已交班，2:已接班）
            handover.setShift((int)map.get("shift"));
            handover.setVisitId(map.get("visitId").toString());
            handover.setVisitCode(map.get("visitCode").toString());
            handover.setPatientId(map.get("patientId").toString());
            handover.setSendOperator(map.get("sendOperator").toString());
            handover.setSendDescription(map.get("sendDescription").toString());
            if(user!=null){
                operatorCode=user.getLoginName();
                handover.setOperatorCode(user.getLoginName());
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            handover.setHandoverBeginTime(format.parse(map.get("beginTime").toString()));
            handover.setHandoverEndTime(format.parse(map.get("endTime").toString()));
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            handover.setInfoDate(formatDate.parse(map.get("infoDate").toString()));
            handoverService.update(handover);

            //删除交接班记录明细表
            List<HandoverRecord> list=handoverRecordService.queryListByHandoverId(handover.getId());
            if(list!=null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setStatus(-1);
                    handoverRecordService.update(list.get(i));
                }
            }

            //添加交接班记录明细表记录
            Date date=new Date();
            String handoverId=handover.getId();

            //床号：bedName
            if(map.containsKey("bedName") && StringUtils.isNotEmpty(map.get("bedName").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("bedName");
                handoverRecord.setContent(map.get("bedName").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //姓名：name
            if(map.containsKey("name") && StringUtils.isNotEmpty(map.get("name").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("name");
                handoverRecord.setContent(map.get("name").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //性别（男 女）：sex
            if(map.containsKey("sex") && StringUtils.isNotEmpty(map.get("sex").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("sex");
                handoverRecord.setContent(map.get("sex").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //年龄：age
            if(map.containsKey("age") && StringUtils.isNotEmpty(map.get("age").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("age");
                handoverRecord.setContent(map.get("age").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //来源科室：department
            if(map.containsKey("department") && StringUtils.isNotEmpty(map.get("department").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("department");
                handoverRecord.setContent(map.get("department").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //入科时间：visitTime
            if(map.containsKey("visitTime") && StringUtils.isNotEmpty(map.get("visitTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("visitTime");
                handoverRecord.setContent(map.get("visitTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //手术时间：operationTime
            if(map.containsKey("operationTime") && StringUtils.isNotEmpty(map.get("operationTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("operationTime");
                handoverRecord.setContent(map.get("operationTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //过敏史：allergies
            if(map.containsKey("allergies") && StringUtils.isNotEmpty(map.get("allergies").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("allergies");
                handoverRecord.setContent(map.get("allergies").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //诊断：diagnosisName
            if(map.containsKey("diagnosisName") && StringUtils.isNotEmpty(map.get("diagnosisName").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("diagnosisName");
                handoverRecord.setContent(map.get("diagnosisName").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //生命体征时间：vitalSignTime
            if(map.containsKey("vitalSignTime") && StringUtils.isNotEmpty(map.get("vitalSignTime").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("vitalSignTime");
                handoverRecord.setContent(map.get("vitalSignTime").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-体温：temperature
            if(map.containsKey("temperature") && StringUtils.isNotEmpty(map.get("temperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("temperature");
                handoverRecord.setContent(map.get("temperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-脉搏：pulse
            if(map.containsKey("pulse") && StringUtils.isNotEmpty(map.get("pulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pulse");
                handoverRecord.setContent(map.get("pulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-心率：hr
            if(map.containsKey("hr") && StringUtils.isNotEmpty(map.get("hr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("hr");
                handoverRecord.setContent(map.get("hr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-呼吸：br
            if(map.containsKey("br") && StringUtils.isNotEmpty(map.get("br").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("br");
                handoverRecord.setContent(map.get("br").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-SpO₂：spo2
            if(map.containsKey("spo2") && StringUtils.isNotEmpty(map.get("spo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("spo2");
                handoverRecord.setContent(map.get("spo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-有创血压：ibp
            if(map.containsKey("ibp") && StringUtils.isNotEmpty(map.get("ibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("ibp");
                handoverRecord.setContent(map.get("ibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-无创血压：bp
            if(map.containsKey("bp") && StringUtils.isNotEmpty(map.get("bp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("bp");
                handoverRecord.setContent(map.get("bp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-cvp：cvp
            if(map.containsKey("cvp") && StringUtils.isNotEmpty(map.get("cvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvp");
                handoverRecord.setContent(map.get("cvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //当前体征-cvp（mmHg₂O）：cvpmmHg
            if(map.containsKey("cvpmmHg") && StringUtils.isNotEmpty(map.get("cvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvpmmHg");
                handoverRecord.setContent(map.get("cvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-体温：maxtemperature
            if(map.containsKey("maxtemperature") && StringUtils.isNotEmpty(map.get("maxtemperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxtemperature");
                handoverRecord.setContent(map.get("maxtemperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-脉搏：maxpulse
            if(map.containsKey("maxpulse") && StringUtils.isNotEmpty(map.get("maxpulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxpulse");
                handoverRecord.setContent(map.get("maxpulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-心率：maxhr
            if(map.containsKey("maxhr") && StringUtils.isNotEmpty(map.get("maxhr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxhr");
                handoverRecord.setContent(map.get("maxhr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-呼吸：maxbr
            if(map.containsKey("maxbr") && StringUtils.isNotEmpty(map.get("maxbr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxbr");
                handoverRecord.setContent(map.get("maxbr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-SpO₂：maxspo2
            if(map.containsKey("maxspo2") && StringUtils.isNotEmpty(map.get("maxspo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxspo2");
                handoverRecord.setContent(map.get("maxspo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-有创血压：maxibp
            if(map.containsKey("maxibp") && StringUtils.isNotEmpty(map.get("maxibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxibp");
                handoverRecord.setContent(map.get("maxibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-无创血压：maxbp
            if(map.containsKey("maxbp") && StringUtils.isNotEmpty(map.get("maxbp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxbp");
                handoverRecord.setContent(map.get("maxbp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-cvp：maxcvp
            if(map.containsKey("maxcvp") && StringUtils.isNotEmpty(map.get("maxcvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxcvp");
                handoverRecord.setContent(map.get("maxcvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最大体征-cvp（mmHg₂O）：maxcvpmmHg
            if(map.containsKey("maxcvpmmHg") && StringUtils.isNotEmpty(map.get("maxcvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("maxcvpmmHg");
                handoverRecord.setContent(map.get("maxcvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-体温：mintemperature
            if(map.containsKey("mintemperature") && StringUtils.isNotEmpty(map.get("mintemperature").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mintemperature");
                handoverRecord.setContent(map.get("mintemperature").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-脉搏：minpulse
            if(map.containsKey("minpulse") && StringUtils.isNotEmpty(map.get("minpulse").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minpulse");
                handoverRecord.setContent(map.get("minpulse").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-心率：minhr
            if(map.containsKey("minhr") && StringUtils.isNotEmpty(map.get("minhr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minhr");
                handoverRecord.setContent(map.get("minhr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-呼吸：minbr
            if(map.containsKey("minbr") && StringUtils.isNotEmpty(map.get("minbr").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minbr");
                handoverRecord.setContent(map.get("minbr").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-SpO₂：minspo2
            if(map.containsKey("minspo2") && StringUtils.isNotEmpty(map.get("minspo2").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minspo2");
                handoverRecord.setContent(map.get("minspo2").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-有创血压：minibp
            if(map.containsKey("minibp") && StringUtils.isNotEmpty(map.get("minibp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minibp");
                handoverRecord.setContent(map.get("minibp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-无创血压：minbp
            if(map.containsKey("minbp") && StringUtils.isNotEmpty(map.get("minbp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("minbp");
                handoverRecord.setContent(map.get("minbp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-cvp：mincvp
            if(map.containsKey("mincvp") && StringUtils.isNotEmpty(map.get("mincvp").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mincvp");
                handoverRecord.setContent(map.get("mincvp").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //最小体征-cvp（mmHg₂O）：mincvpmmHg
            if(map.containsKey("mincvpmmHg") && StringUtils.isNotEmpty(map.get("mincvpmmHg").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("mincvpmmHg");
                handoverRecord.setContent(map.get("mincvpmmHg").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //总入量：intake
            if(map.containsKey("intake") && StringUtils.isNotEmpty(map.get("intake").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("intake");
                handoverRecord.setContent(map.get("intake").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //总出量：output
            if(map.containsKey("output") && StringUtils.isNotEmpty(map.get("output").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("output");
                handoverRecord.setContent(map.get("output").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //人工气道方式-气管插管：tracheaInsert
            if(map.containsKey("tracheaInsert") && StringUtils.isNotEmpty(map.get("tracheaInsert").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tracheaInsert");
                handoverRecord.setContent(map.get("tracheaInsert").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //人工气道方式-气管切开：tracheaCut
            if(map.containsKey("tracheaCut") && StringUtils.isNotEmpty(map.get("tracheaCut").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tracheaCut");
                handoverRecord.setContent(map.get("tracheaCut").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机型号：ventilatorModel
            if(map.containsKey("ventilatorModel") && StringUtils.isNotEmpty(map.get("ventilatorModel").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("ventilatorModel");
                handoverRecord.setContent(map.get("ventilatorModel").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机模式：breathePattern
            if(map.containsKey("breathePattern") && StringUtils.isNotEmpty(map.get("breathePattern").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("breathePattern");
                handoverRecord.setContent(map.get("breathePattern").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机插管深度：tubeDepth
            if(map.containsKey("tubeDepth") && StringUtils.isNotEmpty(map.get("tubeDepth").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("tubeDepth");
                handoverRecord.setContent(map.get("tubeDepth").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机吸氧方式：oxygenMethod
            if(map.containsKey("oxygenMethod") && StringUtils.isNotEmpty(map.get("oxygenMethod").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("oxygenMethod");
                handoverRecord.setContent(map.get("oxygenMethod").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //呼吸机
            // 流量：oxygenFlow
            if(map.containsKey("oxygenFlow") && StringUtils.isNotEmpty(map.get("oxygenFlow").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("oxygenFlow");
                handoverRecord.setContent(map.get("oxygenFlow").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-CVC：cvcDescription
            if(map.containsKey("cvcDescription") && StringUtils.isNotEmpty(map.get("cvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("cvcDescription");
                handoverRecord.setContent(map.get("cvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PVC：pvcDescription
            if(map.containsKey("pvcDescription") && StringUtils.isNotEmpty(map.get("pvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pvcDescription");
                handoverRecord.setContent(map.get("pvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PVC：pvcDescription
            if(map.containsKey("pvcDescription") && StringUtils.isNotEmpty(map.get("pvcDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("pvcDescription");
                handoverRecord.setContent(map.get("pvcDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-PORT：portDescription
            if(map.containsKey("portDescription") && StringUtils.isNotEmpty(map.get("portDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("portDescription");
                handoverRecord.setContent(map.get("portDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //静脉置管-留置针：leftNeedleDescription
            if(map.containsKey("leftNeedleDescription") && StringUtils.isNotEmpty(map.get("leftNeedleDescription").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("leftNeedleDescription");
                handoverRecord.setContent(map.get("leftNeedleDescription").toString());
                handoverRecordService.insert(handoverRecord);
            }

            //动脉置管：artery
            if(map.containsKey("artery") && StringUtils.isNotEmpty(map.get("artery").toString())){
                HandoverRecord handoverRecord=newHandoverRecord(map,handoverId,date,operatorCode);
                handoverRecord.setCode("artery");
                handoverRecord.setContent(map.get("artery").toString());
                handoverRecordService.insert(handoverRecord);
            }

            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }catch (Exception e){
            logger.info("交接班--交班草稿箱--编辑--存为草稿/确认交班:"+e);
        }
        return json.toString();
    }

    /**
     * 交接班--交班草稿箱--确认交班
     * @param map
     * @return
     */
    @RequestMapping(value = "/editConfirmHandover",method= RequestMethod.POST)
    @ResponseBody
    public String editConfirmHandover(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        if(map!=null && map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
            Handover handover=handoverService.queryData(map.get("id").toString());
            handover.setStatus(1);
            handoverService.update(handover);
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 交接班--交班草稿箱--删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteHandover",method= RequestMethod.POST)
    @ResponseBody
    public String deleteHandover(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        if(map!=null && map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
            Handover handover=handoverService.queryData(map.get("id").toString());
            handover.setStatus(-1);
            handoverService.update(handover);
            json.put("msg","成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

}
