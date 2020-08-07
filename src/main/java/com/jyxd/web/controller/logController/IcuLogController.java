package com.jyxd.web.controller.logController;

import com.jyxd.web.data.log.IcuLog;
import com.jyxd.web.service.logService.IcuLogService;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/icuLog")
public class IcuLogController {

    private static Logger logger= LoggerFactory.getLogger(IcuLogController.class);

    @Autowired
    private IcuLogService icuLogService;


    /**
     * 增加一条ICU日志表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody IcuLog icuLog, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        icuLog.setId(UUIDUtil.getUUID());
        icuLog.setCreateTime(new Date());
        json.put("code",200);
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 根据主键id查询ICU日志表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            IcuLog icuLog=icuLogService.queryData(map.get("id").toString());
            if(icuLog!=null){
                json.put("data",JSONObject.fromObject(icuLog));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU日志表列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<IcuLog> list =icuLogService.queryList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
        }
        json.put("code",200);
        System.out.println(json);
        return json.toString();
    }

}