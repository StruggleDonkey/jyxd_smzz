package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import com.jyxd.web.service.dictionaryService.DiagnoseCoefficinetItemDictionaryService;
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
@RequestMapping(value = "/diagnoseCoefficinetItemDictionary")
public class DiagnoseCoefficinetItemDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(DiagnoseCoefficinetItemDictionaryController.class);

    @Autowired
    private DiagnoseCoefficinetItemDictionaryService diagnoseCoefficinetItemDictionaryService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        json.put("data",new ArrayList<>());
        DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=new DiagnoseCoefficinetItemDictionary();
        diagnoseCoefficinetItemDictionary.setId(UUIDUtil.getUUID());
        diagnoseCoefficinetItemDictionary.setCreateTime(new Date());
        diagnoseCoefficinetItemDictionaryService.insert(diagnoseCoefficinetItemDictionary);
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        if(map.containsKey("id") && map.containsKey("status")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            diagnoseCoefficinetItemDictionary.setStatus((int)map.get("status"));
            diagnoseCoefficinetItemDictionaryService.update(diagnoseCoefficinetItemDictionary);
        }
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        System.out.println("好借好还或或或或或或或或或或或或或或或");
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficinetItemDictionary!=null){
                json.put("data",JSONObject.fromObject(diagnoseCoefficinetItemDictionary));
            }
        }
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<DiagnoseCoefficinetItemDictionary> list =diagnoseCoefficinetItemDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

}
