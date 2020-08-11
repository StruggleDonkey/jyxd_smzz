package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.IcuLogTemplateItemDictionary;
import com.jyxd.web.service.dictionaryService.IcuLogTemplateItemDictionaryService;
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
@RequestMapping(value = "/icuLogTemplateItemDictionary")
public class IcuLogTemplateItemDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(IcuLogTemplateItemDictionaryController.class);

    @Autowired
    private IcuLogTemplateItemDictionaryService icuLogTemplateItemDictionaryService;

    /**
     * 增加一条ICU日志模板表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody IcuLogTemplateItemDictionary icuLogTemplateItemDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        icuLogTemplateItemDictionary.setId(UUIDUtil.getUUID());
        icuLogTemplateItemDictionaryService.insert(icuLogTemplateItemDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 更新或者删除ICU日志模板表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            IcuLogTemplateItemDictionary icuLogTemplateItemDictionary=icuLogTemplateItemDictionaryService.queryData(map.get("id").toString());
            icuLogTemplateItemDictionary.setStatus((int)map.get("status"));
            icuLogTemplateItemDictionaryService.update(icuLogTemplateItemDictionary);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询ICU日志模板表记录
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
            IcuLogTemplateItemDictionary icuLogTemplateItemDictionary=icuLogTemplateItemDictionaryService.queryData(map.get("id").toString());
            if(icuLogTemplateItemDictionary!=null){
                json.put("data",JSONObject.fromObject(icuLogTemplateItemDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU日志模板表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        List<IcuLogTemplateItemDictionary> list =icuLogTemplateItemDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
