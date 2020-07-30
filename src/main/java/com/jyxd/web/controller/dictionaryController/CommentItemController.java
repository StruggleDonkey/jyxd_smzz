package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.CommenItemDictionary;
import com.jyxd.web.service.dictionaryService.CommentItemService;
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
@RequestMapping(value = "/commentItem")
public class CommentItemController {

    private static Logger logger= LoggerFactory.getLogger(CommentItemController.class);

    @Autowired
    private CommentItemService commentItemService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("data",new ArrayList<>());
        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
        commenItemDictionary.setCommonItemCode("AAAAAAAA");
        commenItemDictionary.setCommonItemName("CCCCCCCCCC");
        commenItemDictionary.setDescription("无");
        commenItemDictionary.setId(UUIDUtil.getUUID());
        commenItemDictionary.setStatus(0);
        commenItemDictionary.setType("1");
        commentItemService.insert(commenItemDictionary);
        json.put("status",true);
        return json.toString();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        if(map.containsKey("id") && map.containsKey("status")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            commenItemDictionary.setStatus((int)map.get("status"));
            commentItemService.update(commenItemDictionary);
        }
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map.containsKey("id")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                json.put("data",JSONObject.fromObject(commenItemDictionary));
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
        List<CommenItemDictionary> list =commentItemService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("status",true);
        json.put("code",200);
        return json.toString();
    }

}
