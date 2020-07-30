package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.ScoreDictionary;
import com.jyxd.web.service.dictionaryService.ScoreDictionaryService;
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
@RequestMapping(value = "/scoreDictionary")
public class ScoreDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(ScoreDictionaryController.class);

    @Autowired
    private ScoreDictionaryService scoreDictionaryService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        ScoreDictionary scoreDictionary=new ScoreDictionary();
        scoreDictionary.setId(UUIDUtil.getUUID());
        scoreDictionary.setCreateTime(new Date());
        scoreDictionaryService.insert(scoreDictionary);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id") && map.containsKey("status")){
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            scoreDictionary.setStatus((int)map.get("status"));
            scoreDictionaryService.update(scoreDictionary);
        }
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            if(scoreDictionary!=null){
                json.put("data",JSONObject.fromObject(scoreDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<ScoreDictionary> list =scoreDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
