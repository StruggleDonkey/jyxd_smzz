package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BodyTemperature;
import com.jyxd.web.data.log.Log;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.BodyTemperatureService;
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
@RequestMapping(value = "/bodyTemperature")
public class BodyTemperatureController {

    private static Logger logger= LoggerFactory.getLogger(BodyTemperatureController.class);

    @Autowired
    private BodyTemperatureService bodyTemperatureService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条体温单数据表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody (required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            JSONArray array=JSONArray.fromObject(map.get("list").toString());
            Map<String,Object> parameMap=new HashMap<>();
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            parameMap.put("dataTime",map.get("time").toString());
            if(array!=null && array.size()>0){
                for (int i = 0; i <array.size(); i++) {
                    JSONObject obj=(JSONObject) array.get(i);
                    parameMap.put("patientId",obj.getString("patientId"));
                    //口温
                    if(StringUtils.isNotEmpty(obj.getString("kouwen"))){
                        parameMap.put("code","口温");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("kouwen"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("口温");
                            data.setContent(obj.getString("kouwen"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                    //腋温
                    if(StringUtils.isNotEmpty(obj.getString("yewen"))){
                        parameMap.put("code","腋温");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("yewen"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("腋温");
                            data.setContent(obj.getString("yewen"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                    //肛温
                    if(StringUtils.isNotEmpty(obj.getString("gangwen"))){
                        parameMap.put("code","肛温");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("gangwen"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("肛温");
                            data.setContent(obj.getString("gangwen"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                    //脉搏
                    if(StringUtils.isNotEmpty(obj.getString("maibo"))){
                        parameMap.put("code","脉搏");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("maibo"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("脉搏");
                            data.setContent(obj.getString("maibo"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                    //心率
                    if(StringUtils.isNotEmpty(obj.getString("xinlv"))){
                        parameMap.put("code","心率");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("xinlv"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("心率");
                            data.setContent(obj.getString("xinlv"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                    //呼吸
                    if(StringUtils.isNotEmpty(obj.getString("huxi"))){
                        parameMap.put("code","呼吸");
                        //根据时间和code 查询 体温单对象
                        BodyTemperature bodyTemperature=bodyTemperatureService.queryDataByTimeAndCode(parameMap);
                        if(bodyTemperature!=null){
                            //不等于null 为编辑
                            bodyTemperature.setContent(obj.getString("huxi"));
                            bodyTemperatureService.update(bodyTemperature);
                        }else{
                            //等于null 为新增
                            BodyTemperature data=newBodyTemperature(obj,session);
                            data.setCode("呼吸");
                            data.setContent(obj.getString("huxi"));
                            bodyTemperatureService.insert(data);
                        }
                    }
                }
                User user=(User) session.getAttribute("user");
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
            logger.info("增加一条体温单数据表记录:"+e.getMessage()+"  ------   "+e.toString()+"   "+e.getLocalizedMessage());
        }
        return json.toString();
    }

    public BodyTemperature newBodyTemperature(JSONObject jsonObject,HttpSession session){
        BodyTemperature bodyTemperature=new BodyTemperature();
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
        try {
            bodyTemperature.setId(UUIDUtil.getUUID());
            bodyTemperature.setCreateTime(new Date());
            bodyTemperature.setStatus(1);
            bodyTemperature.setDataTime(sdf.parse(jsonObject.getString("dataTime")));
            bodyTemperature.setPatientId(jsonObject.getString("patientId"));
            bodyTemperature.setVisitCode(jsonObject.getString("visitCode"));
            bodyTemperature.setVisitId(jsonObject.getString("visitId"));
            User user =(User) session.getAttribute("user");
            if(user!=null){
                bodyTemperature.setOperatorCode(user.getLoginName());
            }
        }catch (Exception e){
            logger.info("newBodyTemperature:"+e);
        }
        return bodyTemperature;
    }

    /**
     * 更新体温单数据表记录状态
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
            BodyTemperature bodyTemperature=bodyTemperatureService.queryData(map.get("id").toString());
            if(bodyTemperature!=null){
                bodyTemperature.setStatus((int)map.get("status"));
                bodyTemperatureService.update(bodyTemperature);
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
     * 编辑体温单数据表记录单
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
            BodyTemperature bodyTemperature=bodyTemperatureService.queryData(map.get("id").toString());
            if(bodyTemperature!=null){
                bodyTemperature.setStatus((int)map.get("status"));
                bodyTemperatureService.update(bodyTemperature);
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
     * 删除体温单数据表记录
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
            BodyTemperature bodyTemperature=bodyTemperatureService.queryData(map.get("id").toString());
            if(bodyTemperature!=null){
                bodyTemperature.setStatus(-1);
                bodyTemperatureService.update(bodyTemperature);
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
     * 根据主键id查询体温单数据表记录
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
            BodyTemperature bodyTemperature=bodyTemperatureService.queryData(map.get("id").toString());
            if(bodyTemperature!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(bodyTemperature));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询体温单数据表记录列表（也可以不分页）
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
            int totalCount =bodyTemperatureService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BodyTemperature> list =bodyTemperatureService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据时间查询在科病人的体温图数据
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
        List<Map<String,Object>> list=bodyTemperatureService.getListByTime(map);
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

}
