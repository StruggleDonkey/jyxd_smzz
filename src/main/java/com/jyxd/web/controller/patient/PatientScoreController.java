package com.jyxd.web.controller.patient;

import com.jyxd.web.data.dictionary.ScoreDictionary;
import com.jyxd.web.data.dictionary.ScoreItemDictionary;
import com.jyxd.web.data.patient.PatientScore;
import com.jyxd.web.data.patient.PatientScoreItem;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.dictionary.ScoreDictionaryService;
import com.jyxd.web.service.dictionary.ScoreItemDictionaryService;
import com.jyxd.web.service.patient.PatientScoreItemService;
import com.jyxd.web.service.patient.PatientScoreService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/patientScore")
public class PatientScoreController {

    private static Logger logger= LoggerFactory.getLogger(PatientScoreController.class);

    @Autowired
    private PatientScoreService patientScoreService;

    @Autowired
    private ScoreDictionaryService scoreDictionaryService;

    @Autowired
    private ScoreItemDictionaryService scoreItemDictionaryService;

    @Autowired
    private PatientScoreItemService patientScoreItemService;

    /**
     * 增加一条病人评分表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            PatientScore patientScore=new PatientScore();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            patientScore.setId(UUIDUtil.getUUID());
            patientScore.setCreateTime(new Date());
            patientScore.setScoreTime(format.parse(map.get("scoreTime").toString()));
            patientScore.setScore((int)map.get("score"));
            patientScore.setStatus(1);
            User user=(User) session.getAttribute("user");
            if(user!=null){
                patientScore.setOperatorCode(user.getLoginName());
            }
            patientScore.setSignature(map.get("signature").toString());
            patientScore.setVisitId(map.get("visitId").toString());
            patientScore.setVisitCode(map.get("visitCode").toString());
            patientScore.setType(map.get("type").toString());
            patientScore.setPatientId(map.get("patientId").toString());
            if(map.get("reportTime")!=null){
                patientScore.setReportTime(format.parse(map.get("reportTime").toString()));
            }
            if(map.get("assessmentTime")!=null){
                patientScore.setAssessmentTime(format.parse(map.get("assessmentTime").toString()));
            }
            if(map.get("nursingStep")!=null){
                patientScore.setNursingStep(map.get("nursingStep").toString());
            }
            if(map.get("otherStep")!=null){
                patientScore.setOtherStep(map.get("otherStep").toString());
            }
            if(map.get("mortalityRate")!=null){
                patientScore.setMortalityRate(map.get("mortalityRate").toString());
            }
            if(map.get("extendColumn")!=null){
                patientScore.setExtendColumn(map.get("extendColumn").toString());
            }
            if(map.get("scoreKnowledgeId")!=null){
                patientScore.setScoreKnowledgeId(map.get("scoreKnowledgeId").toString());
            }
            patientScoreService.insert(patientScore);

            //新增病人评分明细记录
            if(map.get("list")!=null){
                JSONArray array=JSONArray.fromObject(map.get("list").toString());
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject=(JSONObject) array.get(i);
                    PatientScoreItem patientScoreItem=new PatientScoreItem();
                    patientScoreItem.setId(UUIDUtil.getUUID());
                    patientScoreItem.setCreateTime(new Date());
                    patientScoreItem.setVisitId(map.get("visitId").toString());
                    patientScoreItem.setVisitCode(map.get("visitCode").toString());
                    patientScoreItem.setType(map.get("type").toString());
                    patientScoreItem.setPatientId(map.get("patientId").toString());
                    patientScoreItem.setScoreTime(format.parse(map.get("scoreTime").toString()));
                    patientScoreItem.setPatientScoreId(patientScore.getId());
                    patientScoreItem.setParentId(jsonObject.getString("parentId"));
                    patientScoreItem.setItemId(jsonObject.getString("itemId"));
                    patientScoreItem.setContent(jsonObject.getString("content"));
                    patientScoreItem.setExtraContent(jsonObject.getString("extraContent"));
                    patientScoreItemService.insert(patientScoreItem);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }catch (Exception e){
            logger.info("增加一条病人评分表记录:"+e);
            json.put("msg","异常");
        }
        return json.toString();
    }

    /**
     * 更新病人评分表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            PatientScore patientScore=patientScoreService.queryData(map.get("id").toString());
            if(patientScore!=null){
                patientScore.setStatus((int)map.get("status"));
                patientScoreService.update(patientScore);
                json.put("msg","更新成功");
            }else{
                json.put("msg","更新失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑病人评分表
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            PatientScore patientScore=patientScoreService.queryData(map.get("id").toString());
            if(patientScore!=null){
                patientScore.setStatus((int)map.get("status"));
                patientScoreService.update(patientScore);
                json.put("msg","编辑成功");
            }else{
                json.put("msg","编辑失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());

        return json.toString();
    }

    /**
     * 删除病人评分表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map.containsKey("id")){
            PatientScore patientScore=patientScoreService.queryData(map.get("id").toString());
            if(patientScore!=null){
                patientScore.setStatus(-1);
                patientScoreService.update(patientScore);
                json.put("msg","删除成功");
            }else{
                json.put("msg","删除失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询病人评分表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map !=null && map.containsKey("id")){
            PatientScore patientScore=patientScoreService.queryData(map.get("id").toString());
            if(patientScore!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(patientScore));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询病人评分表记录列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =patientScoreService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<PatientScore> list =patientScoreService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据评分名字查询该评分详情
     * @param map scoreName
     * @return
     */
    @RequestMapping(value = "/getListByScoreName",method= RequestMethod.POST)
    @ResponseBody
    public String getListByScoreName(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        //先根据名称查询评分字典
        ScoreDictionary scoreDictionary =scoreDictionaryService.queryDataByName(map);
        if(scoreDictionary!=null){
            map.put("type",scoreDictionary.getType());//评分类型
            //根据唯一评分类型查询评分项列表
            List<ScoreItemDictionary> list=scoreItemDictionaryService.queryParentListByType(map);
            if(list!=null && list.size()>0){
                JsonConfig jsonConfig=new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
                JSONArray array=JSONArray.fromObject(list,jsonConfig);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject=(JSONObject) array.get(i);
                    map.put("parentId",jsonObject.getString("id"));//父键id
                    //根据type和parentId 查询评分项明细列表
                    List<ScoreItemDictionary> sonList=scoreItemDictionaryService.querySonListByType(map);
                    jsonObject.put("data",JSONArray.fromObject(sonList,jsonConfig));
                }
                json.put("data",array);
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 重症评分--统计分析
     * 根据评分类型 分数 病人主键id 查询评分记录列表
     * @param map patientId type score
     * @return
     */
    @RequestMapping(value = "/queryDataByScoreAndType",method= RequestMethod.POST)
    @ResponseBody
    public String queryDataByScoreAndType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<PatientScore> list =patientScoreService.queryList(map);
        if(list!=null && list.size()>0){
            JSONArray array=new JSONArray();//时间
            JSONArray jsonArray=new JSONArray();//分数
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i <list.size() ; i++) {
                array.add(format.format(list.get(i).getScoreTime()));
                jsonArray.add(list.get(i).getScore());
            }
            json.put("data",array);
            JSONArray dataArray=new JSONArray();
            JSONObject obj=new JSONObject();
            obj.put("name","分数");
            obj.put("type","line");
            obj.put("stack","总量");
            obj.put("areaStyle",new JSONArray());
            obj.put("data",jsonArray);
            dataArray.add(obj);
            json.put("array",dataArray);
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
