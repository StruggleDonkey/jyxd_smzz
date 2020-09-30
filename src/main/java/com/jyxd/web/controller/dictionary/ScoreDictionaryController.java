package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.ScoreDictionary;
import com.jyxd.web.service.dictionary.ScoreDictionaryService;
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

import java.util.*;

@Controller
@RequestMapping(value = "/scoreDictionary")
public class ScoreDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(ScoreDictionaryController.class);

    @Autowired
    private ScoreDictionaryService scoreDictionaryService;

    /**
     * 字典管理--评分字典--新增评分
     * @param scoreDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) ScoreDictionary scoreDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","新增失败");
        Map<String,Object> map=new HashMap<>();
        map.put("type",scoreDictionary.getType());
        ScoreDictionary data=scoreDictionaryService.queryDataByType(map);
        if(data == null){
            scoreDictionary.setId(UUIDUtil.getUUID());
            scoreDictionary.setCreateTime(new Date());
            scoreDictionaryService.insert(scoreDictionary);
            json.put("msg","新增成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            scoreDictionary.setStatus((int)map.get("status"));
            scoreDictionaryService.update(scoreDictionary);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 字典管理--评分字典--编辑评分
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
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            if(scoreDictionary!=null){
                scoreDictionary.setScoreName(map.get("scoreName").toString());//评分名称
                scoreDictionary.setType(map.get("type").toString());//评分类型
                scoreDictionary.setScoreType(map.get("scoreType").toString());//评分分类
                scoreDictionary.setSortNum((int)map.get("sortNum"));//排序
                scoreDictionary.setDescription(map.get("description").toString());//评分描述
                scoreDictionary.setLegend(map.get("legend").toString());//图例
                scoreDictionary.setScoreRule(map.get("scoreRule").toString());//评分规则
                scoreDictionary.setScoreOpportunity(map.get("scoreOpportunity").toString());//评估时机
                scoreDictionary.setShowNursing((int)map.get("showNursing"));//是否录入护理措施（0：否 1：是）
                scoreDictionary.setShowOtherStep((int)map.get("showOtherStep"));//是否录入其他措施（0：否 1：是）
                scoreDictionary.setShowSignature((int)map.get("showSignature"));//是否录入签名（0：否 1：是）
                scoreDictionary.setShowSmartScore((int)map.get("showSmartScore"));//是否允许智能评分(0:否，1:是)
                scoreDictionary.setSynReport((int)map.get("synReport"));//是否同步至护理单(0：否  1：是)
                scoreDictionary.setReportType(map.get("reportType").toString());//关联护理单类型
                scoreDictionary.setReportCode(map.get("reportCode").toString());//护理单关联类型code
                scoreDictionary.setSynAssessment((int)map.get("synAssessment"));//是否同步至评估单(0：否  1：是)
                scoreDictionary.setAssessmentType(map.get("assessmentType").toString());//关联评估单类型
                scoreDictionary.setAssessmentCode(map.get("assessmentCode").toString());//评估单关联类型code
                scoreDictionary.setSynNursing((int)map.get("synNursing"));//是否同步至护理记录(0：否  1：是)
                scoreDictionary.setHasStatistics((int)map.get("hasStatistics"));//是否带统计(0：不带 1：带)
                scoreDictionary.setDateType(map.get("dateType").toString());//日期格式  默认'YYYY-MM-DD HH:mm:ss'
                scoreDictionary.setStatus((int)map.get("status"));//状态（0：禁用 1：正常 -1：删除）
                scoreDictionaryService.update(scoreDictionary);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","编辑成功");
            }
        }
        return json.toString();
    }

    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            if(scoreDictionary!=null){
                json.put("data",JSONObject.fromObject(scoreDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        if(map!=null && map.containsKey("start")){
            int totalCount =scoreDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<ScoreDictionary> list =scoreDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 字典管理--评分字典--评分列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","查询失败");
        JSONObject obj=new JSONObject();
        JSONArray array=new JSONArray();
        obj.put("scoreType",map.get("scoreType").toString());
        List<ScoreDictionary> list =scoreDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            obj.put("size",list.size());
            obj.put("list",JSONArray.fromObject(list));
            array.add(obj);
            json.put("data",array);
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
