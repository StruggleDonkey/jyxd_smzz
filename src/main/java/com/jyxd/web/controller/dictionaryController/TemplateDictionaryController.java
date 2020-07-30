package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.TemplateDictionary;
import com.jyxd.web.service.dictionaryService.TemplateDictionaryService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/templateDictionary")
public class TemplateDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(TemplateDictionaryController.class);

    @Autowired
    private TemplateDictionaryService templateDictionaryService;

    /**
     * 增加一条护理模板类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody TemplateDictionary templateDictionary){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        templateDictionary.setId(UUIDUtil.getUUID());
        templateDictionary.setCreateTime(new Date());
        templateDictionaryService.insert(templateDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新护理模板类型表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus((int)map.get("status"));
                templateDictionaryService.update(templateDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 编辑护理模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("dicTemplateName")){
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus((int)map.get("status"));
                templateDictionary.setDicTemplateName(map.get("dicTemplateName").toString());
                templateDictionaryService.update(templateDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 删除护理模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id")){
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus(-1);
                templateDictionaryService.update(templateDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询护理模板类型表记录
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
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                json.put("data",JSONObject.fromObject(templateDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询护理模板类型表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<TemplateDictionary> list =templateDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
