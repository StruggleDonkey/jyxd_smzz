package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.InputAmount;
import com.jyxd.web.data.basic.OutputAmount;
import com.jyxd.web.data.log.Log;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.InputAmountService;
import com.jyxd.web.service.basic.OutputAmountService;
import com.jyxd.web.service.log.LogService;
import com.jyxd.web.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/outputAmount")
public class OutputAmountController {

    private static Logger logger= LoggerFactory.getLogger(OutputAmountController.class);

    @Autowired
    private OutputAmountService outputAmountService;

    @Autowired
    private InputAmountService inputAmountService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条出量表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            JSONArray array=JSONArray.fromObject(map.get("list").toString());
            Map<String,Object> parameMap=new HashMap<>();
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            User user=(User) session.getAttribute("user");
            parameMap.put("dataTime",map.get("time").toString());
            if(array!=null && array.size()>0){
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj=(JSONObject) array.get(i);
                    parameMap.put("patientId",obj.getString("patientId"));
                    if(StringUtils.isNotEmpty(obj.getString("dabian"))){
                        //大便次数
                        parameMap.put("outputName",obj.getString("dabian"));
                        OutputAmount outputAmount=outputAmountService.queryDataByTime(parameMap);
                        if(outputAmount!=null){
                            //不等于空 为编辑
                            outputAmount.setDosage(obj.getString("dosage"));
                            outputAmountService.update(outputAmount);
                        }else {
                            //等于空 为新增
                            OutputAmount data=new OutputAmount();
                            data.setId(UUIDUtil.getUUID());
                            data.setStatus(1);
                            data.setCreateTime(new Date());
                            if(user!=null){
                                data.setOperatorCode(user.getLoginName());
                            }
                            data.setDosage(obj.getString("dosage"));
                            data.setOutputName(obj.getString("dabian"));
                            data.setDataTime(sdf.parse(obj.getString("dataTime")));
                            data.setPatientId(obj.getString("patientId"));
                            data.setVisitCode(obj.getString("visitCode"));
                            data.setVisitId(obj.getString("visitId"));
                            outputAmountService.insert(data);
                        }
                    }
                    if(StringUtils.isNotEmpty(obj.getString("niaoliang"))){
                        //尿量
                        parameMap.put("outputName",obj.getString("niaoliang"));
                        OutputAmount outputAmount=outputAmountService.queryDataByTime(parameMap);
                        if(outputAmount!=null){
                            //不等于空 为编辑
                            outputAmount.setDosage(obj.getString("dosage"));
                            outputAmountService.update(outputAmount);
                        }else {
                            //等于空 为新增
                            OutputAmount data=new OutputAmount();
                            data.setId(UUIDUtil.getUUID());
                            data.setStatus(1);
                            data.setCreateTime(new Date());
                            if(user!=null){
                                data.setOperatorCode(user.getLoginName());
                            }
                            data.setDosage(obj.getString("dosage"));
                            data.setOutputName(obj.getString("niaoliang"));
                            data.setDataTime(sdf.parse(obj.getString("dataTime")));
                            data.setPatientId(obj.getString("patientId"));
                            data.setVisitCode(obj.getString("visitCode"));
                            data.setVisitId(obj.getString("visitId"));
                            outputAmountService.insert(data);
                        }
                    }
                    if(StringUtils.isNotEmpty(obj.getString("yeti"))){
                        //液体入量
                        parameMap.put("orderType",obj.getString("yeti"));
                        InputAmount inputAmount =inputAmountService.queryDataByTime(parameMap);
                        if(inputAmount!=null){
                            //不等于空 为编辑
                            inputAmount.setDosage(obj.getString("dosage"));
                            inputAmountService.update(inputAmount);
                        }else{
                            //等于空 为新增
                            InputAmount data=new InputAmount();
                            data.setId(UUIDUtil.getUUID());
                            data.setStatus(1);
                            data.setCreateTime(new Date());
                            if(user!=null){
                                data.setOperatorCode(user.getLoginName());
                            }
                            data.setDosage(obj.getString("dosage"));
                            data.setOrderType(obj.getString("yeti"));
                            data.setDataTime(sdf.parse(obj.getString("dataTime")));
                            data.setPatientId(obj.getString("patientId"));
                            data.setVisitCode(obj.getString("visitCode"));
                            data.setVisitId(obj.getString("visitId"));
                            inputAmountService.insert(data);
                        }
                    }
                }
                if(user!=null){
                    //添加操作日志信息
                    Log log=new Log();
                    log.setId(UUIDUtil.getUUID());
                    log.setOperatorCode(user.getLoginName());
                    log.setOperateTime(new Date());
                    log.setMenuCode(MenuCode.TWDKJLR_CODE.getCode());
                    log.setContent(map.toString());
                    log.setOperateType(LogTypeCode.ADD_CODE.getCode());
                    logService.insert(log);
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","添加成功");
            }
        }catch (Exception e){
            logger.info("增加一条出量表记录:"+e);
        }
        return json.toString();
    }

    /**
     * 更新出量表记录状态
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
            OutputAmount outputAmount=outputAmountService.queryData(map.get("id").toString());
            if(outputAmount!=null){
                outputAmount.setStatus((int)map.get("status"));
                outputAmountService.update(outputAmount);
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
     * 编辑出量表记录单
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
            if(map!=null && map.containsKey("id")){
                OutputAmount outputAmount=outputAmountService.queryData(map.get("id").toString());
                if(outputAmount!=null){
                    if(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        outputAmount.setDataTime(sdf.parse(map.get("dataTime").toString()));
                    }
                    if(map.containsKey("outputName") && StringUtils.isNotEmpty(map.get("outputName").toString())){
                        outputAmount.setOutputName(map.get("outputName").toString());
                    }
                    if(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString())){
                        outputAmount.setDosage(map.get("dosage").toString());
                    }
                    if(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString())){
                        outputAmount.setCheckSignature(map.get("checkSignature").toString());
                        outputAmount.setCheckSignature(map.get("checkSignature").toString());
                    }
                    if(map.containsKey("dosageUnits") && StringUtils.isNotEmpty(map.get("dosageUnits").toString())){
                        outputAmount.setDosageUnits(map.get("dosageUnits").toString());
                    }
                    if(map.containsKey("outputType") && StringUtils.isNotEmpty(map.get("outputType").toString())){
                        outputAmount.setOutputType(map.get("outputType").toString());
                    }
                    if(map.containsKey("speed") && StringUtils.isNotEmpty(map.get("speed").toString())){
                        outputAmount.setSpeed(map.get("speed").toString());
                    }
                    outputAmountService.update(outputAmount);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }else{
                    json.put("msg","编辑失败，没有这个对象。");
                    return json.toString();
                }
            }
        }catch (Exception e){
            logger.info("编辑出量表记录单:"+e);
        }
        return json.toString();
    }

    /**
     * 删除出量表记录
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
            OutputAmount outputAmount=outputAmountService.queryData(map.get("id").toString());
            if(outputAmount!=null){
                outputAmount.setStatus(-1);
                outputAmountService.update(outputAmount);
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
     * 根据主键id查询出量表记录
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
            OutputAmount outputAmount=outputAmountService.queryData(map.get("id").toString());
            if(outputAmount!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(outputAmount));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询出量表记录列表（也可以不分页）
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
            int totalCount =outputAmountService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<OutputAmount> list =outputAmountService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 快捷录入--体温单--体温表单--根据时间查询在科病人体温表单数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByTime",method= RequestMethod.POST)
    @ResponseBody
    public String getListByTime(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=outputAmountService.getListByTime(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            JSONArray array=JSONArray.fromObject(list,jsonConfig);
            json.put("data",array);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","查询成功");
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--出量--新增一条出量记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/add",method= RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","新增失败");
        try {
            OutputAmount outputAmount=new OutputAmount();
            outputAmount.setId(UUIDUtil.getUUID());
            outputAmount.setCreateTime(new Date());
            outputAmount.setVisitId(map.get("visitId").toString());
            outputAmount.setVisitCode(map.get("visitCode").toString());
            outputAmount.setPatientId(map.get("patientId").toString());
            outputAmount.setStatus(1);
            if(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                outputAmount.setDataTime(sdf.parse(map.get("dataTime").toString()));
            }
            if(map.containsKey("outputName") && StringUtils.isNotEmpty(map.get("outputName").toString())){
                outputAmount.setOutputName(map.get("outputName").toString());
            }
            if(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString())){
                outputAmount.setDosage(map.get("dosage").toString());
            }
            if(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString())){
                outputAmount.setCheckSignature(map.get("checkSignature").toString());
            }
            if(map.containsKey("dosageUnits") && StringUtils.isNotEmpty(map.get("dosageUnits").toString())){
                outputAmount.setDosageUnits(map.get("dosageUnits").toString());
            }
            if(map.containsKey("outputType") && StringUtils.isNotEmpty(map.get("outputType").toString())){
                outputAmount.setOutputType(map.get("outputType").toString());
            }
            if(map.containsKey("speed") && StringUtils.isNotEmpty(map.get("speed").toString())){
                outputAmount.setSpeed(map.get("speed").toString());
            }
            User user=(User)session.getAttribute("user");
            if(user!=null){
                outputAmount.setOperatorCode(user.getLoginName());
            }
            outputAmountService.insert(outputAmount);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","新增成功");
        }catch (Exception e){
            logger.info("护理文书--护理单--出量--新增一条出量记录:"+e);
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--出量--保存一条出量记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveData",method= RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            if(map.containsKey("id") && StringUtils.isNotEmpty(map.get("id").toString())){
                //编辑
                OutputAmount data=outputAmountService.queryData(map.get("id").toString());
                if(data!=null){
                    data.setVisitId(map.get("visitId").toString());
                    data.setVisitCode(map.get("visitCode").toString());
                    data.setPatientId(map.get("patientId").toString());
                    data.setStatus(1);
                    if(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        data.setDataTime(sdf.parse(map.get("dataTime").toString()));
                    }
                    if(map.containsKey("outputName") && StringUtils.isNotEmpty(map.get("outputName").toString())){
                        data.setOutputName(map.get("outputName").toString());
                    }
                    if(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString())){
                        data.setDosage(map.get("dosage").toString());
                    }
                    if(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString())){
                        data.setCheckSignature(map.get("checkSignature").toString());
                    }
                    if(map.containsKey("dosageUnits") && StringUtils.isNotEmpty(map.get("dosageUnits").toString())){
                        data.setDosageUnits(map.get("dosageUnits").toString());
                    }
                    if(map.containsKey("outputType") && StringUtils.isNotEmpty(map.get("outputType").toString())){
                        data.setOutputType(map.get("outputType").toString());
                    }
                    if(map.containsKey("speed") && StringUtils.isNotEmpty(map.get("speed").toString())){
                        data.setSpeed(map.get("speed").toString());
                    }
                    User user=(User)session.getAttribute("user");
                    if(user!=null){
                        data.setOperatorCode(user.getLoginName());
                    }
                    outputAmountService.update(data);
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","编辑成功");
                }
            }else {
                //新增
                OutputAmount outputAmount=new OutputAmount();
                outputAmount.setId(UUIDUtil.getUUID());
                outputAmount.setCreateTime(new Date());
                outputAmount.setVisitId(map.get("visitId").toString());
                outputAmount.setVisitCode(map.get("visitCode").toString());
                outputAmount.setPatientId(map.get("patientId").toString());
                outputAmount.setStatus(1);
                if(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    outputAmount.setDataTime(sdf.parse(map.get("dataTime").toString()));
                }
                if(map.containsKey("outputName") && StringUtils.isNotEmpty(map.get("outputName").toString())){
                    outputAmount.setOutputName(map.get("outputName").toString());
                }
                if(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString())){
                    outputAmount.setDosage(map.get("dosage").toString());
                }
                if(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString())){
                    outputAmount.setCheckSignature(map.get("checkSignature").toString());
                }
                if(map.containsKey("dosageUnits") && StringUtils.isNotEmpty(map.get("dosageUnits").toString())){
                    outputAmount.setDosageUnits(map.get("dosageUnits").toString());
                }
                if(map.containsKey("outputType") && StringUtils.isNotEmpty(map.get("outputType").toString())){
                    outputAmount.setOutputType(map.get("outputType").toString());
                }
                if(map.containsKey("speed") && StringUtils.isNotEmpty(map.get("speed").toString())){
                    outputAmount.setSpeed(map.get("speed").toString());
                }
                User user=(User)session.getAttribute("user");
                if(user!=null){
                    outputAmount.setOperatorCode(user.getLoginName());
                }
                outputAmountService.insert(outputAmount);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","新增成功");
            }

        }catch (Exception e){
            logger.info("护理文书--护理单--出量--保存一条出量记录:"+e);
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--出量--查询病人出量列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPatientOutputList",method= RequestMethod.POST)
    @ResponseBody
    public String getPatientOutputList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        try {
            if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                List<Map<String,Object>> list=outputAmountService.getPatientOutputList(map);
                if(list!=null && list.size()>0){
                    json.put("data",JSONArray.fromObject(list));
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","查询成功");
                }
            }else{
                json.put("code",HttpCode.NO_PATIENT_CODE.getCode());
                json.put("msg","请先选择病人");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--出量--查询病人出量列表:"+e);
        }
        return json.toString();
    }

    /**
     * 统计分析--出入量--出量分析
     * @param map
     * @return
     */
    @RequestMapping(value = "/getOutAnalyze",method= RequestMethod.POST)
    @ResponseBody
    public String getOutAnalyze(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=outputAmountService.getOutAnalyze(map);
        if(list!=null && list.size()>0){
            JSONArray jsonArray=new JSONArray();
            JSONArray jsonArray1=new JSONArray();
            for (int i = 0; i < list.size() ; i++) {
                Map<String,Object> jsonObject= list.get(i);
                jsonArray.add(jsonObject.get("name").toString());
                JSONObject obj=new JSONObject();
                obj.put("value",jsonObject.get("dosage").toString());
                obj.put("name",jsonObject.get("name").toString());
                jsonArray1.add(obj);
            }
            json.put("msg","成功");
            json.put("legend_data",jsonArray);
            json.put("series_data",jsonArray1);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
