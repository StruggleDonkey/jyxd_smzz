package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BloodSugar;
import com.jyxd.web.data.log.Log;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.BloodSugarService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/bloodSugar")
public class BloodSugarController {

    private static Logger logger= LoggerFactory.getLogger(BloodSugarController.class);

    @Autowired
    private BloodSugarService bloodSugarService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条血糖表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody  Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            JSONArray array=JSONArray.fromObject(map.get("list").toString());
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            if(array!=null && array.size()>0){
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj=(JSONObject) array.get(i);
                    //有id 说明是编辑
                    if(StringUtils.isNotEmpty(obj.getString("id"))){
                        BloodSugar bloodSugar=bloodSugarService.queryData(obj.getString("id"));
                        bloodSugar.setContent(obj.getString("content"));
                        bloodSugar.setCode(obj.getString("code"));
                        User user=(User) session.getAttribute("user");
                        if(user!=null){
                            //添加操作日志信息
                            Log log=new Log();
                            log.setId(UUIDUtil.getUUID());
                            log.setOperatorCode(user.getLoginName());
                            log.setOperateTime(new Date());
                            log.setMenuCode(MenuCode.XTDKJLR_CODE.getCode());
                            log.setContent(map.toString());
                            log.setOperateType(LogTypeCode.UPDATE_CODE.getCode());
                            logService.insert(log);
                        }
                        bloodSugarService.update(bloodSugar);
                    }else {
                        //id 为null 说明是新增
                        BloodSugar bloodSugar=new BloodSugar();
                        bloodSugar.setId(UUIDUtil.getUUID());
                        bloodSugar.setStatus(1);
                        bloodSugar.setCreateTime(new Date());
                        User user=(User) session.getAttribute("user");
                        if(user!=null){
                            bloodSugar.setOperatorCode(user.getLoginName());
                            //添加操作日志信息
                            Log log=new Log();
                            log.setId(UUIDUtil.getUUID());
                            log.setOperatorCode(user.getLoginName());
                            log.setOperateTime(new Date());
                            log.setMenuCode(MenuCode.XTDKJLR_CODE.getCode());
                            log.setContent(map.toString());
                            log.setOperateType(LogTypeCode.ADD_CODE.getCode());
                            logService.insert(log);
                        }
                        bloodSugar.setCode(obj.getString("code"));
                        bloodSugar.setContent(obj.getString("content"));
                        bloodSugar.setPatientId(obj.getString("patientId"));
                        bloodSugar.setVisitCode(obj.getString("visitCode"));
                        bloodSugar.setVisitId(obj.getString("visitId"));
                        bloodSugar.setDataTime(sdf.parse(obj.getString("dataTime")));
                        bloodSugarService.insert(bloodSugar);
                    }
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","添加成功");
            }
        }catch (Exception e){
            logger.info("增加一条血糖表记录:"+e);
        }
        return json.toString();
    }

    /**
     * 更新血糖表记录状态
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
            BloodSugar bloodSugar=bloodSugarService.queryData(map.get("id").toString());
            if(bloodSugar!=null){
                bloodSugar.setStatus((int)map.get("status"));
                bloodSugarService.update(bloodSugar);
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
     * 编辑血糖表记录单
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
            BloodSugar bloodSugar=bloodSugarService.queryData(map.get("id").toString());
            if(bloodSugar!=null){
                bloodSugar.setStatus((int)map.get("status"));
                bloodSugarService.update(bloodSugar);
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
     * 删除血糖表记录
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
            BloodSugar bloodSugar=bloodSugarService.queryData(map.get("id").toString());
            if(bloodSugar!=null){
                bloodSugar.setStatus(-1);
                bloodSugarService.update(bloodSugar);
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
     * 根据主键id查询血糖表记录
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
            BloodSugar bloodSugar=bloodSugarService.queryData(map.get("id").toString());
            if(bloodSugar!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(bloodSugar));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询血糖表记录列表（也可以不分页）
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
            int totalCount =bloodSugarService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BloodSugar> list =bloodSugarService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据时间查询在科病人的血糖信息
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
        List<Map<String,Object>> list=bloodSugarService.getListByTime(map);
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
