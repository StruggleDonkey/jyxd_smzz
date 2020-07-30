package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.DiagnoseCoefficientDictionary;
import com.jyxd.web.service.dictionaryService.DiagnoseCoefficientDictionaryService;
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
@RequestMapping(value = "/diagnoseCoefficientDictionary")
public class DiagnoseCoefficientDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(DiagnoseCoefficientDictionaryController.class);

    @Autowired
    private DiagnoseCoefficientDictionaryService diagnoseCoefficientDictionaryService;

    /**
     * 增加一条ICU主要疾病诊断类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody DiagnoseCoefficientDictionary diagnoseCoefficientDictionary){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        System.out.println(diagnoseCoefficientDictionary.toString());
        diagnoseCoefficientDictionary.setId(UUIDUtil.getUUID());
        /*BedDictionary bedDictionary=new BedDictionary();
        bedDictionary.setId(UUIDUtil.getUUID());
        bedDictionary.setBedCode("002");
        bedDictionary.setBedKey("AAAAAA");
        bedDictionary.setBedName("二床");
        bedDictionary.setStatus(0);*/
        diagnoseCoefficientDictionaryService.insert(diagnoseCoefficientDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新或者删除ICU主要疾病诊断类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id") && map.containsKey("status")){
            DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            diagnoseCoefficientDictionary.setStatus((int)map.get("status"));
            diagnoseCoefficientDictionaryService.update(diagnoseCoefficientDictionary);
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询ICU主要疾病诊断类型表记录
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
            DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficientDictionary!=null){
                json.put("data",JSONObject.fromObject(diagnoseCoefficientDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU主要疾病诊断类型表列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<DiagnoseCoefficientDictionary> list =diagnoseCoefficientDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
