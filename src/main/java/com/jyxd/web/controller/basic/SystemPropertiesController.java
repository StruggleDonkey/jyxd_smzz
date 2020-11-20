package com.jyxd.web.controller.basic;

import com.jyxd.web.service.basic.SystemPropertiesService;
import com.jyxd.web.util.HttpCode;
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
@RequestMapping(value = "/sys")
public class SystemPropertiesController {

    private static Logger logger= LoggerFactory.getLogger(SystemPropertiesController.class);

    @Autowired
    private SystemPropertiesService systemPropertiesService;

    /**
     * 根据条件查询系统配置数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/querySystemProperties",method= RequestMethod.POST)
    @ResponseBody
    public String querySystemProperties(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<String> list=systemPropertiesService.querySystemProperties(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--出量/入量--查询所有出量/入量类型
     * @param map remark=出量类型  或者 remark=入量类型
     * @return
     */
    @RequestMapping(value = "/getOutTypeList",method= RequestMethod.POST)
    @ResponseBody
    public String getOutTypeList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=systemPropertiesService.getOutTypeList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
