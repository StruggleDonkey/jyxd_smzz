package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.CommonSetting;
import com.jyxd.web.service.basic.CommonSettingService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/commonSetting")
public class CommonSettingController {

    private static Logger logger= LoggerFactory.getLogger(CommonSettingController.class);

    @Autowired
    private CommonSettingService commonSettingService;

    /**
     * 增加一条通用设置表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody CommonSetting commonSetting){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        commonSetting.setId(UUIDUtil.getUUID());
        commonSetting.setCreateTime(new Date());
        commonSettingService.insert(commonSetting);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新通用设置表记录状态
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
            CommonSetting commonSetting=commonSettingService.queryData(map.get("id").toString());
            if(commonSetting!=null){

                commonSettingService.update(commonSetting);
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
     * 编辑通用设置表记录单
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
            CommonSetting commonSetting=commonSettingService.queryData(map.get("id").toString());
            if(commonSetting!=null){

                commonSettingService.update(commonSetting);
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
     * 删除通用设置表记录
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
            CommonSetting commonSetting=commonSettingService.queryData(map.get("id").toString());
            if(commonSetting!=null){

                commonSettingService.update(commonSetting);
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
     * 根据主键id查询通用设置表记录
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
            CommonSetting commonSetting=commonSettingService.queryData(map.get("id").toString());
            if(commonSetting!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(commonSetting));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用设置表记录列表（也可以不分页）
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
            int totalCount =commonSettingService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<CommonSetting> list =commonSettingService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 增加或更新一条通用设置表记录(排班时间)
     * @return
     */
    @RequestMapping(value = "/updateSchedualTime")
    @ResponseBody
    public String updateSchedualTime(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        JSONArray array=JSONArray.fromObject(map.get("list").toString());
        if(array!=null && array.size()>0){
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj=(JSONObject) array.get(i);
                if(StringUtils.isNotEmpty(obj.getString("id"))){
                    //id 不为空则编辑
                    CommonSetting commonSetting=commonSettingService.queryData(obj.getString("id"));
                    commonSetting.setSettingContent(obj.getString("settingContent"));
                    commonSettingService.update(commonSetting);
                }else{
                    //id为空则新增
                    CommonSetting commonSetting=new CommonSetting();
                    commonSetting.setId(UUIDUtil.getUUID());
                    commonSetting.setCreateTime(new Date());
                    commonSetting.setSettingName(obj.getString("settingName"));
                    commonSetting.setSettingContent(obj.getString("settingContent"));
                    commonSetting.setSettingType("排班时间");
                    if("白班".equals(obj.getString("settingName"))){
                        commonSetting.setSortNum(1);
                    }else if("晚班".equals(obj.getString("settingName"))){
                        commonSetting.setSortNum(2);
                    }else{
                        commonSetting.setSortNum(3);
                    }
                    commonSettingService.insert(commonSetting);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }
        return json.toString();
    }

    /**
     * 查询排班时间的通用设置列表
     * @return
     * @param map settingType=排班时间
     */
    @RequestMapping(value = "/getSchedualTimeList")
    @ResponseBody
    public String getSchedualTimeList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        List<CommonSetting> list=commonSettingService.getSchedualTimeList(map);
        if(list!=null && list.size()>0){
            JSONArray array=JSONArray.fromObject(list);
            json.put("data",array);
            json.put("msg","查询成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 新增或更新通用设置表记录(监护仪采集频率)
     * @return
     * @param commonSetting
     */
    @RequestMapping(value = "/updateMonitorFrequency")
    @ResponseBody
    public String updateMonitorFrequency(@RequestBody CommonSetting commonSetting){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","更新失败");
        if(StringUtils.isNotEmpty(commonSetting.getId())){
            //id不为空 则编辑
            CommonSetting data=commonSettingService.queryData(commonSetting.getId());
            data.setSettingContent(commonSetting.getSettingContent());
            commonSettingService.update(data);
            json.put("msg","更新成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }else{
            //id为空则新增
            commonSetting.setSettingName("监护仪采集频率");
            commonSetting.setSettingType("监护仪采集频率");
            commonSetting.setCreateTime(new Date());
            commonSetting.setSortNum(1);
            commonSetting.setId(UUIDUtil.getUUID());
            commonSettingService.insert(commonSetting);
            json.put("msg","更新成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 系统设置--通用设置--监护仪采集频率/默认首页--查询监护仪频率或默认首页
     * @return
     * @param map
     */
    @RequestMapping(value = "/getCommonSettingByType")
    @ResponseBody
    public String getCommonSettingByType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        CommonSetting commonSetting=commonSettingService.getCommonSettingByType(map);
        if(commonSetting!=null){
            JSONObject obj=JSONObject.fromObject(commonSetting);
            json.put("data",obj);
            json.put("msg","查询成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 新增或更新通用设置表记录(默认首页)
     * @return
     * @param commonSetting
     */
    @RequestMapping(value = "/updateDefaultPage")
    @ResponseBody
    public String updateDefaultPage(@RequestBody CommonSetting commonSetting){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","更新失败");
        if(StringUtils.isNotEmpty(commonSetting.getId())){
            //id不为空 则编辑
            CommonSetting data=commonSettingService.queryData(commonSetting.getId());
            data.setSettingContent(commonSetting.getSettingContent());
            commonSettingService.update(data);
            json.put("msg","更新成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }else{
            //id为空则新增
            commonSetting.setSettingName("默认首页");
            commonSetting.setSettingType("默认首页");
            commonSetting.setCreateTime(new Date());
            commonSetting.setSortNum(1);
            commonSetting.setId(UUIDUtil.getUUID());
            commonSettingService.insert(commonSetting);
            json.put("msg","更新成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

}
