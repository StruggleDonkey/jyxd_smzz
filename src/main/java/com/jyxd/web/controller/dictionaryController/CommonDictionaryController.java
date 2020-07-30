package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.CommonDictionary;
import com.jyxd.web.service.dictionaryService.CommonDictionaryService;
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
@RequestMapping(value = "/commonDictionary")
public class CommonDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(CommonDictionaryController.class);

    @Autowired
    private CommonDictionaryService commonDictionaryService;

    /**
     * 增加一条通用字典类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        CommonDictionary commonDictionary=new CommonDictionary();
        commonDictionary.setId(UUIDUtil.getUUID());
        commonDictionary.setStatus(0);
        commonDictionaryService.insert(commonDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新或者删除通用字典类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id") && map.containsKey("status")){
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            commonDictionary.setStatus((int)map.get("status"));
            commonDictionaryService.update(commonDictionary);
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询通用字典类型表记录
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
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            if(commonDictionary!=null){
                json.put("data",JSONObject.fromObject(commonDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典类型记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<CommonDictionary> list =commonDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
