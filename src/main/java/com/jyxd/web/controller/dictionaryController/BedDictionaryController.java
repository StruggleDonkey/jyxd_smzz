package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.BedDictionary;
import com.jyxd.web.service.dictionaryService.BedDictionaryService;
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
@RequestMapping(value = "/bedDictionary")
public class BedDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(BedDictionaryController.class);

    @Autowired
    private BedDictionaryService bedDictionaryService;

    /**
     * 增加一条床位字典记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody BedDictionary bedDictionary){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        System.out.println(bedDictionary.toString());
        bedDictionary.setId(UUIDUtil.getUUID());
        bedDictionaryService.insert(bedDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新床位地点记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            BedDictionary bedDictionary=bedDictionaryService.queryData(map.get("id").toString());
            if(bedDictionary!=null){
                bedDictionary.setStatus((int)map.get("status"));
                bedDictionaryService.update(bedDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 编辑床位地点记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            BedDictionary bedDictionary=bedDictionaryService.queryData(map.get("id").toString());
            if(bedDictionary!=null){
                bedDictionary.setStatus((int)map.get("status"));
                bedDictionary.setBedName(map.get("bedName").toString());
                bedDictionaryService.update(bedDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 删除床位地点记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id")){
            BedDictionary bedDictionary=bedDictionaryService.queryData(map.get("id").toString());
            if(bedDictionary!=null){
                bedDictionary.setStatus(-1);
                bedDictionaryService.update(bedDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询床位字典记录
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
            BedDictionary bedDictionary=bedDictionaryService.queryData(map.get("id").toString());
            if(bedDictionary!=null){
                json.put("data",JSONObject.fromObject(bedDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询床位字典记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<BedDictionary> list =bedDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
