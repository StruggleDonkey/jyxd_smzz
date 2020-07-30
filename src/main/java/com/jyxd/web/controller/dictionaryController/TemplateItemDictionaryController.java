package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.TemplateItemDictionary;
import com.jyxd.web.service.dictionaryService.TemplateItemDictionaryService;
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
@RequestMapping(value = "/templateItemDictionary")
public class TemplateItemDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(TemplateItemDictionaryController.class);

    @Autowired
    private TemplateItemDictionaryService templateItemDictionaryService;

    /**
     * 增加一条护理模板表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody TemplateItemDictionary templateItemDictionary){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        templateItemDictionary.setId(UUIDUtil.getUUID());
        templateItemDictionary.setCreateTime(new Date());
        templateItemDictionaryService.insert(templateItemDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新护理模板表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            TemplateItemDictionary templateItemDictionary=templateItemDictionaryService.queryData(map.get("id").toString());
            if(templateItemDictionary!=null){
                templateItemDictionary.setStatus((int)map.get("status"));
                templateItemDictionaryService.update(templateItemDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 编辑护理模板表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("templateItemName")){
            TemplateItemDictionary templateItemDictionary=templateItemDictionaryService.queryData(map.get("id").toString());
            if(templateItemDictionary!=null){
                templateItemDictionary.setStatus((int)map.get("status"));
                templateItemDictionary.setTemplateItemName(map.get("templateItemName").toString());
                templateItemDictionaryService.update(templateItemDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 删除护理模板表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id")){
            TemplateItemDictionary templateItemDictionary=templateItemDictionaryService.queryData(map.get("id").toString());
            if(templateItemDictionary!=null){
                templateItemDictionary.setStatus(-1);
                templateItemDictionaryService.update(templateItemDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询护理模板表记录
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
            TemplateItemDictionary templateItemDictionary=templateItemDictionaryService.queryData(map.get("id").toString());
            if(templateItemDictionary!=null){
                json.put("data",JSONObject.fromObject(templateItemDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询护理模板表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<TemplateItemDictionary> list =templateItemDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
