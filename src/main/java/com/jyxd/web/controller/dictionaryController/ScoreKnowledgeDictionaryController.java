package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.ScoreKnowledgeDictionary;
import com.jyxd.web.service.dictionaryService.ScoreKnowledgeDictionaryService;
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
@RequestMapping(value = "/scoreKnowledgeDictionary")
public class ScoreKnowledgeDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(ScoreKnowledgeDictionaryController.class);

    @Autowired
    private ScoreKnowledgeDictionaryService scoreKnowledgeDictionaryService;

    /**
     * 增加一条评分知识库表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody ScoreKnowledgeDictionary scoreKnowledgeDictionary){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        scoreKnowledgeDictionary.setId(UUIDUtil.getUUID());
        scoreKnowledgeDictionaryService.insert(scoreKnowledgeDictionary);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新评分知识库表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            ScoreKnowledgeDictionary scoreKnowledgeDictionary=scoreKnowledgeDictionaryService.queryData(map.get("id").toString());
            if(scoreKnowledgeDictionary!=null){
                scoreKnowledgeDictionary.setStatus((int)map.get("status"));
                scoreKnowledgeDictionaryService.update(scoreKnowledgeDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 编辑评分知识库表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            ScoreKnowledgeDictionary scoreKnowledgeDictionary=scoreKnowledgeDictionaryService.queryData(map.get("id").toString());
            if(scoreKnowledgeDictionary!=null){
                scoreKnowledgeDictionary.setStatus((int)map.get("status"));

                scoreKnowledgeDictionaryService.update(scoreKnowledgeDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 删除评分知识库表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map.containsKey("id")){
            ScoreKnowledgeDictionary scoreKnowledgeDictionary=scoreKnowledgeDictionaryService.queryData(map.get("id").toString());
            if(scoreKnowledgeDictionary!=null){
                scoreKnowledgeDictionary.setStatus(-1);
                scoreKnowledgeDictionaryService.update(scoreKnowledgeDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询评分知识库表记录
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
            ScoreKnowledgeDictionary scoreKnowledgeDictionary=scoreKnowledgeDictionaryService.queryData(map.get("id").toString());
            if(scoreKnowledgeDictionary!=null){
                json.put("data",JSONObject.fromObject(scoreKnowledgeDictionary));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询评分知识库表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<ScoreKnowledgeDictionary> list =scoreKnowledgeDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
