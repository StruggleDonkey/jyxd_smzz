package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.IcuLogTemplateDictionary;
import com.jyxd.web.service.dictionaryService.IcuLogTemplateDictionaryService;
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
@RequestMapping(value = "/icuLogTemplateDictionary")
public class IcuLogTemplateDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(IcuLogTemplateDictionaryController.class);

    @Autowired
    private IcuLogTemplateDictionaryService icuLogTemplateDictionaryService;

    /**
     * 增加一条ICU日志模板类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody IcuLogTemplateDictionary icuLogTemplateDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        icuLogTemplateDictionary.setId(UUIDUtil.getUUID());
        icuLogTemplateDictionaryService.insert(icuLogTemplateDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 更新或者删除ICU日志模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            IcuLogTemplateDictionary icuLogTemplateDictionary=icuLogTemplateDictionaryService.queryData(map.get("id").toString());
            icuLogTemplateDictionary.setStatus((int)map.get("status"));
            icuLogTemplateDictionaryService.update(icuLogTemplateDictionary);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询ICU日志模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            IcuLogTemplateDictionary icuLogTemplateDictionary=icuLogTemplateDictionaryService.queryData(map.get("id").toString());
            if(icuLogTemplateDictionary!=null){
                json.put("data",JSONObject.fromObject(icuLogTemplateDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU日志模板类型表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        List<IcuLogTemplateDictionary> list =icuLogTemplateDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
