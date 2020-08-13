package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.WardDictionary;
import com.jyxd.web.service.dictionary.WardDictionaryService;
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
@RequestMapping(value = "/wardDictionary")
public class WardDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(WardDictionaryController.class);

    @Autowired
    private WardDictionaryService wardDictionaryService;

    /**
     * 新增一条病区字典记录
     * @param wardDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody WardDictionary wardDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        wardDictionary.setId(UUIDUtil.getUUID());
        wardDictionaryService.insert(wardDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 更新一条病区字典记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map !=null && map.containsKey("id") && map.containsKey("status")){
            WardDictionary wardDictionary=wardDictionaryService.queryData(map.get("id").toString());
           if(wardDictionary!=null){
               wardDictionary.setStatus((int)map.get("status"));
               wardDictionaryService.update(wardDictionary);
           }else{
               return json.toString();
           }

        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑病区字典记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("wardName")){
            WardDictionary wardDictionary=wardDictionaryService.queryData(map.get("id").toString());
            if(wardDictionary!=null){
                wardDictionary.setStatus((int)map.get("status"));
                wardDictionary.setWardName(map.get("wardName").toString());
                wardDictionaryService.update(wardDictionary);
            }else{
                return json.toString();
        }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 删除病区字典记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id")){
            WardDictionary wardDictionary=wardDictionaryService.queryData(map.get("id").toString());
            if(wardDictionary!=null){
                wardDictionary.setStatus(-1);
                wardDictionaryService.update(wardDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询病区字典记录
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
            WardDictionary wardDictionary=wardDictionaryService.queryData(map.get("id").toString());
            if(wardDictionary!=null){
                json.put("data",JSONObject.fromObject(wardDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件查询病区字典列表（可分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map!=null && map.containsKey("start")){
            int totalCount =wardDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<WardDictionary> list =wardDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
