package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.MonitorDictionary;
import com.jyxd.web.service.dictionaryService.MonitorDictionaryService;
import com.jyxd.web.util.HttpCode;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/monitorDictionary")
public class MonitorDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(MonitorDictionaryController.class);

    @Autowired
    private MonitorDictionaryService monitorDictionaryService;

    /**
     * 新增一条监护仪字典表数据
     * @param monitorDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody MonitorDictionary monitorDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        monitorDictionary.setId(UUIDUtil.getUUID());
        monitorDictionaryService.insert(monitorDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新一条监护仪字典表数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        if(map !=null && map.containsKey("id") && map.containsKey("status")){
            MonitorDictionary monitorDictionary=monitorDictionaryService.queryData(map.get("id").toString());
            if(monitorDictionary!=null){
                monitorDictionary.setStatus((int)map.get("status"));
                monitorDictionaryService.update(monitorDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","更新失败，查无此条数据。");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 编辑监护仪字典表数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("monitorName") && map.containsKey("monitorIp") && map.containsKey("monitorPort")){
            MonitorDictionary monitorDictionary=monitorDictionaryService.queryData(map.get("id").toString());
            if(monitorDictionary!=null){
                monitorDictionary.setStatus((int)map.get("status"));
                monitorDictionary.setMonitorName(map.get("monitorName").toString());
                monitorDictionary.setMonitorIp(map.get("monitorIp").toString());
                monitorDictionary.setMonitorPort(map.get("monitorPort").toString());
                monitorDictionaryService.update(monitorDictionary);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","编辑失败，查无此条数据");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 删除一条监护仪字典表数据
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
            MonitorDictionary monitorDictionary=monitorDictionaryService.queryData(map.get("id").toString());
            if(monitorDictionary!=null){
                monitorDictionary.setStatus(-1);
                monitorDictionaryService.update(monitorDictionary);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","删除失败,查无此条数据");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询一条监护仪数据
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
            MonitorDictionary monitorDictionary=monitorDictionaryService.queryData(map.get("id").toString());
            if(monitorDictionary!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(monitorDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件查询监护仪数据列表（可分页）
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
        List<MonitorDictionary> list =monitorDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
