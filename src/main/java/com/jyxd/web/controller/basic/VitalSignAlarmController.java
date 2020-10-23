package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.VitalSignAlarm;
import com.jyxd.web.data.log.Log;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.VitalSignAlarmService;
import com.jyxd.web.service.log.LogService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.LogTypeCode;
import com.jyxd.web.util.MenuCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vitalSignAlarm")
public class VitalSignAlarmController {

    private static Logger logger= LoggerFactory.getLogger(VitalSignAlarmController.class);

    @Autowired
    private VitalSignAlarmService vitalSignAlarmService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条生命体征告警设置记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody VitalSignAlarm vitalSignAlarm,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        vitalSignAlarm.setId(UUIDUtil.getUUID());
        vitalSignAlarm.setCreateTime(new Date());
        switch (vitalSignAlarm.getSettingType()){
            case "血氧饱和度":
                vitalSignAlarm.setUnit("%");
                break;
            case "心率":
                vitalSignAlarm.setUnit("次/分");
                break;
            case "脉搏":
                vitalSignAlarm.setUnit("次/分");
                break;
            case "血压":
                vitalSignAlarm.setUnit("mmHg");
                break;
            case "中心静脉压(mmHg)":
                vitalSignAlarm.setUnit("mmHg");
                break;
            case "中心静脉压(cmH₂O)":
                vitalSignAlarm.setUnit("cmH₂O");
                break;
            case "呼吸":
                vitalSignAlarm.setUnit("次/分");
                break;
            case "体温":
                vitalSignAlarm.setUnit("℃");
                break;
        }
        User user=(User) session.getAttribute("user");
        if(user!=null){
            vitalSignAlarm.setOperatorCode(user.getLoginName());
            //添加操作日志信息
            Log log=new Log();
            log.setId(UUIDUtil.getUUID());
            log.setOperatorCode(user.getLoginName());
            log.setOperateTime(new Date());
            log.setMenuCode(MenuCode.BJSZ_CODE.getCode());
            log.setContent(vitalSignAlarm.toString());
            log.setOperateType(LogTypeCode.ADD_CODE.getCode());
            logService.insert(log);
        }
        vitalSignAlarmService.insert(vitalSignAlarm);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新生命体征告警设置记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            VitalSignAlarm vitalSignAlarm=vitalSignAlarmService.queryData(map.get("id").toString());
            if(vitalSignAlarm!=null){
                vitalSignAlarm.setStatus((int)map.get("status"));
                vitalSignAlarmService.update(vitalSignAlarm);
                User user=(User) session.getAttribute("user");
                if(user!=null){
                    vitalSignAlarm.setOperatorCode(user.getLoginName());
                    //添加操作日志信息
                    Log log=new Log();
                    log.setId(UUIDUtil.getUUID());
                    log.setOperatorCode(user.getLoginName());
                    log.setOperateTime(new Date());
                    log.setMenuCode(MenuCode.BJSZ_CODE.getCode());
                    log.setContent(map.toString());
                    log.setOperateType(LogTypeCode.UPDATE_CODE.getCode());
                    logService.insert(log);
                }
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑生命体征告警设置记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status")){
            VitalSignAlarm vitalSignAlarm=vitalSignAlarmService.queryData(map.get("id").toString());
            if(vitalSignAlarm!=null){
                vitalSignAlarm.setStatus((int)map.get("status"));
                vitalSignAlarm.setIsAlarm((int)map.get("isAlarm"));
                vitalSignAlarm.setIsSync((int)map.get("isSync"));
                User user=(User) session.getAttribute("user");
                if(user!=null){
                    vitalSignAlarm.setOperatorCode(user.getLoginName());
                    //添加操作日志信息
                    Log log=new Log();
                    log.setId(UUIDUtil.getUUID());
                    log.setOperatorCode(user.getLoginName());
                    log.setOperateTime(new Date());
                    log.setMenuCode(MenuCode.BJSZ_CODE.getCode());
                    log.setContent(map.toString());
                    log.setOperateType(LogTypeCode.UPDATE_CODE.getCode());
                    logService.insert(log);
                }
                vitalSignAlarm.setSettingContent(map.get("settingContent").toString());
                vitalSignAlarm.setSettingType(map.get("settingType").toString());
                switch (map.get("settingType").toString()){
                    case "血氧饱和度":
                        vitalSignAlarm.setUnit("%");
                        break;
                    case "心率":
                        vitalSignAlarm.setUnit("次/分");
                        break;
                    case "脉搏":
                        vitalSignAlarm.setUnit("次/分");
                        break;
                    case "血压":
                        vitalSignAlarm.setUnit("mmHg");
                        break;
                    case "中心静脉压(mmHg)":
                        vitalSignAlarm.setUnit("mmHg");
                        break;
                    case "中心静脉压(cmH₂O)":
                        vitalSignAlarm.setUnit("cmH₂O");
                        break;
                    case "呼吸":
                        vitalSignAlarm.setUnit("次/分");
                        break;
                    case "体温":
                        vitalSignAlarm.setUnit("℃");
                        break;
                }
                vitalSignAlarm.setTemplate(map.get("template").toString());
                vitalSignAlarmService.update(vitalSignAlarm);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除生命体征告警设置记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map.containsKey("id")){
            VitalSignAlarm vitalSignAlarm=vitalSignAlarmService.queryData(map.get("id").toString());
            if(vitalSignAlarm!=null){
                vitalSignAlarm.setStatus(-1);
                User user=(User) session.getAttribute("user");
                if(user!=null){
                    vitalSignAlarm.setOperatorCode(user.getLoginName());
                    //添加操作日志信息
                    Log log=new Log();
                    log.setId(UUIDUtil.getUUID());
                    log.setOperatorCode(user.getLoginName());
                    log.setOperateTime(new Date());
                    log.setMenuCode(MenuCode.BJSZ_CODE.getCode());
                    log.setContent(map.toString());
                    log.setOperateType(LogTypeCode.DELETE_CODE.getCode());
                    logService.insert(log);
                }
                vitalSignAlarmService.update(vitalSignAlarm);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询生命体征告警设置记录
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
            VitalSignAlarm vitalSignAlarm=vitalSignAlarmService.queryData(map.get("id").toString());
            if(vitalSignAlarm!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(vitalSignAlarm));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询生命体征告警设置记录列表（也可以不分页）
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
            int totalCount =vitalSignAlarmService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<VitalSignAlarm> list =vitalSignAlarmService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 系统设置--报警设置--查询指标名称
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryDataByType")
    @ResponseBody
    public String queryDataByType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","查询失败");
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        obj1.put("settingType","血氧饱和度");
        obj1.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj2=new JSONObject();
        obj2.put("settingType","心率");
        obj2.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj3=new JSONObject();
        obj3.put("settingType","脉搏");
        obj3.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj4=new JSONObject();
        obj4.put("settingType","血压");
        obj4.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj5=new JSONObject();
        obj5.put("settingType","中心静脉压(mmHg)");
        obj5.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj6=new JSONObject();
        obj6.put("settingType","中心静脉压(cmH₂O)");
        obj6.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj7=new JSONObject();
        obj7.put("settingType","呼吸");
        obj7.put("status",0);//status 0:未添加 1：已添加
        JSONObject obj8=new JSONObject();
        obj8.put("settingType","体温");
        obj8.put("status",0);//status 0:未添加 1：已添加
        array.add(obj1);
        array.add(obj2);
        array.add(obj3);
        array.add(obj4);
        array.add(obj5);
        array.add(obj6);
        array.add(obj7);
        array.add(obj8);
        for (int i = 0; i <array.size(); i++) {
            JSONObject obj=(JSONObject) array.get(i);
            map.put("settingType",obj.get("settingType").toString());
            if(vitalSignAlarmService.queryDataByType(map)!=null){
                obj.put("status",1);
            }
        }
        json.put("data",array);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
