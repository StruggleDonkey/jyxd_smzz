package com.jyxd.web.controller.patient;

import com.jyxd.web.data.dictionary.ScoreDictionary;
import com.jyxd.web.data.dictionary.ScoreItemDictionary;
import com.jyxd.web.data.dictionary.ScoreKnowledgeDictionary;
import com.jyxd.web.data.patient.PatientScore;
import com.jyxd.web.data.patient.PatientScoreItem;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.dictionary.ScoreDictionaryService;
import com.jyxd.web.service.dictionary.ScoreItemDictionaryService;
import com.jyxd.web.service.dictionary.ScoreKnowledgeDictionaryService;
import com.jyxd.web.service.patient.PatientScoreItemService;
import com.jyxd.web.service.patient.PatientScoreService;
import com.jyxd.web.service.user.UserService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreKnowledgeDictionaryService scoreKnowledgeDictionaryService;

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
            if(StringUtils.isNotEmpty(map.get("reportTime").toString())){
                patientScore.setReportTime(format.parse(map.get("reportTime").toString()));
            }
            if(StringUtils.isNotEmpty(map.get("assessmentTime").toString())){
                patientScore.setAssessmentTime(format.parse(map.get("assessmentTime").toString()));
            }
            if(StringUtils.isNotEmpty(map.get("nursingStep").toString())){
                patientScore.setNursingStep(map.get("nursingStep").toString());
            }
            if(StringUtils.isNotEmpty(map.get("otherStep").toString())){
                patientScore.setOtherStep(map.get("otherStep").toString());
            }
            if(StringUtils.isNotEmpty(map.get("mortalityRate").toString())){
                patientScore.setMortalityRate(map.get("mortalityRate").toString());
            }
            if(StringUtils.isNotEmpty(map.get("extendColumn").toString())){
                patientScore.setExtendColumn(map.get("extendColumn").toString());
            }
            if(StringUtils.isNotEmpty(map.get("scoreKnowledgeId").toString())){
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
            json.put("msg","保存失败");
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
        List<PatientScore> list =patientScoreService.queryDataByScoreAndType(map);
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
            json.put("number",list.size());
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--新增评分--获取护理措施(跌倒坠床专用接口)
     * @param map id
     * @return
     */
    @RequestMapping(value = "/getNursingStepById",method= RequestMethod.POST)
    @ResponseBody
    public String getNursingStepById(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("id")){
            JSONArray array=new JSONArray();
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            if(scoreDictionary!=null && StringUtils.isNotEmpty(scoreDictionary.getNursingStep())){
                String[] biaozhun=scoreDictionary.getNursingStep().split("标准防范跌倒措施&@&");
                for (int i = 1; i < biaozhun.length-1; i++) {
                    array.add(biaozhun[i]);
                }
                String[] gaofengxian=biaozhun[biaozhun.length-1].split("高风险防范跌倒措施&@&");
                for (int i = 0; i < gaofengxian.length; i++) {
                    array.add(gaofengxian[i]);
                }
                json.put("data",array);
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--新增评分--获取护理措施
     * @param map id
     * @return
     */
    @RequestMapping(value = "/getNursingStepsById",method= RequestMethod.POST)
    @ResponseBody
    public String getNursingStepsById(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("id")){
            JSONArray array=new JSONArray();
            ScoreDictionary scoreDictionary=scoreDictionaryService.queryData(map.get("id").toString());
            if(scoreDictionary!=null && StringUtils.isNotEmpty(scoreDictionary.getNursingStep())){
                String[] biaozhun=scoreDictionary.getNursingStep().split("\\|");
                for (int i = 0; i < biaozhun.length; i++) {
                    if(i>0){
                        array.add(biaozhun[i-1].substring(biaozhun[i-1].length()-1,biaozhun[i-1].length()) +biaozhun[i].substring(0,biaozhun[i].length()-1));
                    }
                }
                json.put("data",array);
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--查询病人评分列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPatientScoreList",method= RequestMethod.POST)
    @ResponseBody
    public String getPatientScoreList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray allArray=new JSONArray();
        JSONObject obj=new JSONObject();
        ScoreDictionary scoreDictionary=scoreDictionaryService.queryDataByType(map);
        if(map!=null && map.containsKey("type") && map.containsKey("patientId")){
            if(scoreDictionary!=null){
                //查询病人是否有评分记录
                List<PatientScore> list=patientScoreService.queryDataListGroupByTime(map);
               if(list!=null && list.size()>0){
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //list不为空代表有数据
                   for (int i = 0; i < list.size(); i++) {
                       JSONObject jsonObject=new JSONObject();
                       JSONArray array=new JSONArray();
                       PatientScore patientScore=list.get(i);
                       // 获取准确时间 带时分秒
                       String times=patientScoreService.getTimeById(patientScore.getId());
                       jsonObject.put("nursingStep","");//护理措施
                       jsonObject.put("otherStep","");//其他措施
                       jsonObject.put("signature","");//签名
                       jsonObject.put("mortalityRate","");//预计病死率
                       jsonObject.put("patientScoreId",patientScore.getId());//病人评分主键id
                       jsonObject.put("date",times.substring(0,10));//日期 2020-11-16
                       jsonObject.put("time",times.substring(11,16));//时间 14:42
                       jsonObject.put("score",patientScore.getScore());//总分
                       if(StringUtils.isNotEmpty(patientScore.getNursingStep())){
                           jsonObject.put("nursingStep",patientScore.getNursingStep());//护理措施
                       }
                       if(StringUtils.isNotEmpty(patientScore.getOtherStep())){
                           jsonObject.put("otherStep",patientScore.getOtherStep());//其他措施
                       }
                       if(StringUtils.isNotEmpty(patientScore.getMortalityRate())){
                           jsonObject.put("mortalityRate",patientScore.getMortalityRate());//预计病死率
                       }
                       map.put("loginName",patientScore.getSignature());//签名（用户登录名）
                       User user=userService.queryDataByLoginName(map);
                       if(user!=null){
                           jsonObject.put("signature",user.getUserName());//签名
                       }
                       //根据type 查询 评分项列表 （parent_id为空的情况）
                       List<ScoreItemDictionary> parentList=scoreItemDictionaryService.queryParentListByType(map);
                       if(parentList!=null && parentList.size()>0){
                           for (int j = 0; j < parentList.size(); j++) {
                               JSONObject jsonObject1=new JSONObject();
                               jsonObject1.put("planLable",parentList.get(j).getScoreItemName());//评分项名称
                               JSONArray jsonArray=new JSONArray();
                               //根据type 和parentId 查询 评分项列表 （parent_id不为空的情况）
                               map.put("parentId",parentList.get(j).getId());//评分项主键id
                               List<ScoreItemDictionary> sonList=scoreItemDictionaryService.querySonListByType(map);
                               if(sonList!=null && sonList.size()>0){
                                   for (int k = 0; k <sonList.size() ; k++) {
                                       JSONObject jsonObject2=new JSONObject();
                                       jsonObject2.put("stageLable",sonList.get(k).getScoreItemName());
                                       jsonObject2.put("value","");
                                       //根据条件查询病人评分明细对象 评分时间 评分项 评分明细 病人主键id 病人评分主键id
                                       map.put("scoreTime",times);//评分时间
                                       map.put("patientScoreId",patientScore.getId());//病人评分主键id
                                       map.put("itemId",sonList.get(k).getId());//评分明细
                                       PatientScoreItem patientScoreItem=patientScoreItemService.queryDataByTypeAndTime(map);
                                       if(patientScoreItem!=null){
                                           jsonObject2.put("value",sonList.get(k).getScore());//评分
                                       }
                                       jsonArray.add(jsonObject2);
                                   }
                               }
                               jsonObject1.put("stageList",jsonArray);
                               array.add(jsonObject1);
                           }
                       }
                       jsonObject.put("planList",array);
                       allArray.add(jsonObject);
                       json.put("data",allArray);
                       json.put("code",HttpCode.OK_CODE.getCode());//成功
                       json.put("msg","查询成功");//成功
                   }
               }else{
                   obj.put("date","");//日期 2020-11-16
                   obj.put("time","");//时间 14:42
                   obj.put("score","");//总分
                   obj.put("nursingStep","");//护理措施
                   obj.put("otherStep","");//其他措施
                   obj.put("signature","");//签名
                   obj.put("mortalityRate","");//预计病死率
                   JSONArray array=new JSONArray();
                   //根据type 查询 评分项列表 （parent_id为空的情况）
                   List<ScoreItemDictionary> parentList=scoreItemDictionaryService.queryParentListByType(map);
                   if(parentList!=null && parentList.size()>0){
                       for (int i = 0; i < parentList.size(); i++) {
                           JSONObject jsonObject=new JSONObject();
                           jsonObject.put("planLable",parentList.get(i).getScoreItemName());//评分项名称
                           JSONArray jsonArray=new JSONArray();
                           //根据type 和parentId 查询 评分项列表 （parent_id不为空的情况）
                           map.put("parentId",parentList.get(i).getId());//评分项主键id
                           List<ScoreItemDictionary> sonList=scoreItemDictionaryService.querySonListByType(map);
                           if(sonList!=null && sonList.size()>0){
                               for (int j = 0; j < sonList.size(); j++) {
                                   JSONObject jsonObject1=new JSONObject();
                                   jsonObject1.put("stageLable",sonList.get(j).getScoreItemName());//评分项明细名称
                                   jsonObject1.put("value","");//评分
                                   jsonArray.add(jsonObject1);//添加到数组中
                               }
                           }
                           jsonObject.put("stageList",jsonArray);
                           array.add(jsonObject);
                       }
                   }
                   obj.put("planList",array);
                   allArray.add(obj);
                   json.put("data",allArray);
                   json.put("code",HttpCode.OK_CODE.getCode());
               }
            }
        }else if(map!=null && !map.containsKey("patientId")){
            //不包含 病人主键id 返回提示消息 选择病人
            json.put("code",HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg","请先选择病人");
            if(scoreDictionary!=null){
                obj.put("date","");//日期 2020-11-16
                obj.put("time","");//时间 14:42
                obj.put("score","");//总分
                obj.put("nursingStep","");//护理措施
                obj.put("otherStep","");//其他措施
                obj.put("signature","");//签名
                obj.put("mortalityRate","");//预计病死率
                JSONArray array=new JSONArray();
                //根据type 查询 评分项列表 （parent_id为空的情况）
                List<ScoreItemDictionary> parentList=scoreItemDictionaryService.queryParentListByType(map);
                if(parentList!=null && parentList.size()>0){
                    for (int i = 0; i < parentList.size(); i++) {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("planLable",parentList.get(i).getScoreItemName());//评分项名称
                        JSONArray jsonArray=new JSONArray();
                        //根据type 和parentId 查询 评分项列表 （parent_id不为空的情况）
                        map.put("parentId",parentList.get(i).getId());//评分项主键id
                        List<ScoreItemDictionary> sonList=scoreItemDictionaryService.querySonListByType(map);
                        if(sonList!=null && sonList.size()>0){
                            for (int j = 0; j < sonList.size(); j++) {
                                JSONObject jsonObject1=new JSONObject();
                                jsonObject1.put("stageLable",sonList.get(j).getScoreItemName());//评分项明细名称
                                jsonObject1.put("value","");//评分
                                jsonArray.add(jsonObject1);//添加到数组中
                            }
                        }
                        jsonObject.put("stageList",jsonArray);
                        array.add(jsonObject);
                    }
                }
                obj.put("planList",array);
                allArray.add(obj);
                json.put("data",allArray);
            }
        }
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--点击编辑按钮查询评分详情
     * @param map type patientScoreId
     * @return
     */
    @RequestMapping(value = "/getEditDetail",method= RequestMethod.POST)
    @ResponseBody
    public String getEditDetail(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
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
                JSONArray array1=JSONArray.fromObject(sonList,jsonConfig);
                for (int j = 0; j < array1.size(); j++) {
                    JSONObject obj=(JSONObject)array1.get(j);
                    // flag 0:未选中   1:已选中
                    obj.put("flag",0);
                    if(StringUtils.isNotEmpty(map.get("patientScoreId").toString())){
                        List<String> itemIdList=patientScoreItemService.getItemIdByPatientScoreId(map.get("patientScoreId").toString());
                        if(itemIdList!=null && itemIdList.size()>0){
                            StringBuffer str=new StringBuffer();
                            for (int k = 0; k <itemIdList.size() ; k++) {
                                str.append(itemIdList.get(k)+",");
                            }
                            if(str.toString().contains(obj.getString("id"))){
                                //判断病人评分明细主键id集合中是否有评分明细主键id
                                obj.put("flag",1);
                            }
                        }
                    }
                }
                jsonObject.put("data",array1);
            }
            json.put("data",array);
            json.put("msg","查询成功");
        }
        PatientScore patientScore=patientScoreService.queryData(map.get("patientScoreId").toString());
        if(patientScore!=null){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
            JSONObject jsonObject=JSONObject.fromObject(patientScore,jsonConfig);
            json.put("patientScore",jsonObject);
            ScoreKnowledgeDictionary scoreKnowledgeDictionary=scoreKnowledgeDictionaryService.queryDataByTypeAndScore(map);
            if(scoreKnowledgeDictionary!=null){
                jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
                json.put("knowledge",JSONArray.fromObject(scoreKnowledgeDictionary,jsonConfig));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--点击删除按钮 删除病人评分
     * @param map patientScoreId
     * @return
     */
    @RequestMapping(value = "/deletePatientScore",method= RequestMethod.POST)
    @ResponseBody
    public String deletePatientScore(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && StringUtils.isNotEmpty(map.get("patientScoreId").toString())){
            PatientScore patientScore=patientScoreService.queryData(map.get("patientScoreId").toString());
            if(patientScore!=null){
                patientScore.setStatus(-1);//删除
                patientScoreService.update(patientScore);
                //删除评分明细
                List<PatientScoreItem> list=patientScoreItemService.queryListByPatientScoreId(map);
                if(list!=null && list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        patientScoreItemService.deleteData(list.get(i).getId());
                    }
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","删除成功");
            }
        }
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--编辑保存病人评分
     * @param map patientScoreId
     * @return
     */
    @RequestMapping(value = "/editPatientScore",method= RequestMethod.POST)
    @ResponseBody
    public String editPatientScore(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        //先删除 再新增
        if(map!=null && StringUtils.isNotEmpty(map.get("patientScoreId").toString())){
            PatientScore patientScore=patientScoreService.queryData(map.get("patientScoreId").toString());
            if(patientScore!=null){
                patientScore.setStatus(-1);//删除
                patientScoreService.update(patientScore);
                //删除评分明细
                List<PatientScoreItem> list=patientScoreItemService.queryListByPatientScoreId(map);
                if(list!=null && list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        patientScoreItemService.deleteData(list.get(i).getId());
                    }
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","删除成功");
            }
        }
        //以下部分为新增
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
            if(StringUtils.isNotEmpty(map.get("reportTime").toString())){
                patientScore.setReportTime(format.parse(map.get("reportTime").toString()));
            }
            if(StringUtils.isNotEmpty(map.get("assessmentTime").toString())){
                patientScore.setAssessmentTime(format.parse(map.get("assessmentTime").toString()));
            }
            if(StringUtils.isNotEmpty(map.get("nursingStep").toString())){
                patientScore.setNursingStep(map.get("nursingStep").toString());
            }
            if(StringUtils.isNotEmpty(map.get("otherStep").toString())){
                patientScore.setOtherStep(map.get("otherStep").toString());
            }
            if(StringUtils.isNotEmpty(map.get("mortalityRate").toString())){
                patientScore.setMortalityRate(map.get("mortalityRate").toString());
            }
            if(StringUtils.isNotEmpty(map.get("extendColumn").toString())){
                patientScore.setExtendColumn(map.get("extendColumn").toString());
            }
            if(StringUtils.isNotEmpty(map.get("scoreKnowledgeId").toString())){
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
            json.put("msg","编辑成功");
        }catch (Exception e){
            logger.info("重症评分--跌倒坠床--编辑保存病人评分:"+e);
            json.put("msg","编辑失败");
        }
        return json.toString();
    }

    /**
     * 重症评分--跌倒坠床--复制评分--查询病人该项评分列表
     * @param map patientId type
     * @return
     */
    @RequestMapping(value = "/getListByPatientIdAndType",method= RequestMethod.POST)
    @ResponseBody
    public String getListByPatientIdAndType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && StringUtils.isNotEmpty(map.get("patientId").toString())){
            List<Map<String,Object>> list=patientScoreService.getListByPatientIdAndType(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","查询成功");
            }
        }
        return json.toString();
    }

    /**
     * 重症评分-评分管理-根据病人id 评分类型 查询病人评分及风险等级列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPatientScoreAndLevel",method= RequestMethod.POST)
    @ResponseBody
    public String getPatientScoreAndLevel(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =patientScoreService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =patientScoreService.getPatientScoreAndLevel(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
