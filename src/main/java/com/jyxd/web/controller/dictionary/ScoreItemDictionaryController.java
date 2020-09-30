package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.ScoreItemDictionary;
import com.jyxd.web.service.dictionary.ScoreItemDictionaryService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/scoreItemDictionary")
public class ScoreItemDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(ScoreItemDictionaryController.class);

    @Autowired
    private ScoreItemDictionaryService scoreItemDictionaryService;

    /**
     * 增加一条评分项字典表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody ScoreItemDictionary scoreItemDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","新增失败");
        scoreItemDictionary.setId(UUIDUtil.getUUID());
        scoreItemDictionary.setCreateTime(new Date());
        scoreItemDictionaryService.insert(scoreItemDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","新增成功");
        return json.toString();
    }

    /**
     * 更新或者删除评分项字典表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            scoreItemDictionary.setStatus((int)map.get("status"));
            scoreItemDictionaryService.update(scoreItemDictionary);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑评分项字典表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map.containsKey("id") && map.containsKey("status")){
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            if(scoreItemDictionary!=null){
                scoreItemDictionary.setMode((int)map.get("mode"));
                scoreItemDictionary.setByCount((int)map.get("byCount"));
                scoreItemDictionary.setHideTitle((int)map.get("hideTitle"));
                scoreItemDictionary.setShowMode((int)map.get("showMode"));
                scoreItemDictionary.setForceHorizon((int)map.get("forceHorizon"));
                scoreItemDictionary.setGroupExclusionNum(map.get("groupExclusionNum").toString());
                scoreItemDictionary.setFastColumnName(map.get("fastColumnName").toString());
                scoreItemDictionary.setGroupNum(map.get("groupNum").toString());
                scoreItemDictionary.setCalculateNum((Double) map.get("calculateNum"));
                scoreItemDictionary.setCalculateRule(map.get("calculateRule").toString());
                scoreItemDictionary.setSortNum((int)map.get("sortNum"));
                scoreItemDictionary.setDescription(map.get("description").toString());
                scoreItemDictionary.setStatus((int)map.get("status"));
                scoreItemDictionaryService.update(scoreItemDictionary);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","编辑成功");
            }
        }
        return json.toString();
    }

    /**
     * 删除评分项字典表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map.containsKey("id")){
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            scoreItemDictionary.setStatus(-1);
            scoreItemDictionaryService.update(scoreItemDictionary);
            json.put("msg","删除成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 根据主键id查询评分项字典表记录
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
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            if(scoreItemDictionary!=null){
                json.put("data",JSONObject.fromObject(scoreItemDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询评分项字典表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        if(map!=null && map.containsKey("start")){
            int totalCount =scoreItemDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<ScoreItemDictionary> list =scoreItemDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询评分项字典表子评分明细记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/querySonList",method= RequestMethod.POST)
    @ResponseBody
    public String querySonList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        if(map!=null && map.containsKey("start")){
            int totalCount =scoreItemDictionaryService.querySonNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =scoreItemDictionaryService.querySonList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 字典管理--评分字典--新增明细（评分项明细）
     * @return
     */
    @RequestMapping(value = "/insertDetail")
    @ResponseBody
    public String insertDetail(@RequestBody ScoreItemDictionary scoreItemDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","新增失败");
        scoreItemDictionary.setId(UUIDUtil.getUUID());
        scoreItemDictionary.setCreateTime(new Date());
        scoreItemDictionaryService.insert(scoreItemDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","新增成功");
        return json.toString();
    }

    /**
     * 字典管理--评分字典--编辑明细（评分项明细）
     * @param map
     * @return
     */
    @RequestMapping(value = "/editDetail")
    @ResponseBody
    public String editDetail(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map.containsKey("id") && map.containsKey("status")){
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            if(scoreItemDictionary!=null){
                scoreItemDictionary.setParentId(map.get("parentId").toString());
                scoreItemDictionary.setScoreItemName(map.get("scoreItemName").toString());
                scoreItemDictionary.setScore((int)map.get("score"));
                scoreItemDictionary.setScoreItemMin(map.get("scoreItemMin").toString());
                scoreItemDictionary.setScoreItemMax(map.get("scoreItemMax").toString());
                scoreItemDictionary.setItemGroupExclusionNum(map.get("itemGroupExclusionNum").toString());
                scoreItemDictionary.setDoubleScore((int)map.get("doubleScore"));
                scoreItemDictionary.setSortNum((int)map.get("sortNum"));
                scoreItemDictionary.setDescription(map.get("description").toString());
                scoreItemDictionary.setStatus((int)map.get("status"));
                scoreItemDictionaryService.update(scoreItemDictionary);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","编辑成功");
            }
        }
        return json.toString();
    }

    /**
     * 字典管理--评分字典--删除明细（评分项明细）
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteDetail")
    @ResponseBody
    public String deleteDetail(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map.containsKey("id")){
            ScoreItemDictionary scoreItemDictionary=scoreItemDictionaryService.queryData(map.get("id").toString());
            scoreItemDictionary.setStatus(-1);
            scoreItemDictionaryService.update(scoreItemDictionary);
            json.put("msg","删除成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }
}
