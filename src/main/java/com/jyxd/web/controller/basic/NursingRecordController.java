package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.NursingRecord;
import com.jyxd.web.data.log.Log;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.NursingRecordService;
import com.jyxd.web.service.log.LogService;
import com.jyxd.web.util.*;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/nursingRecord")
public class NursingRecordController {

    private static Logger logger= LoggerFactory.getLogger(NursingRecordController.class);

    @Autowired
    private NursingRecordService nursingRecordService;

    @Autowired
    private LogService logService;

    /**
     * 增加一条护理记录表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody NursingRecord nursingRecord,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        nursingRecord.setId(UUIDUtil.getUUID());
        nursingRecord.setCreateTime(new Date());
        User user=(User)session.getAttribute("user");
        if(user!=null){
            nursingRecord.setOperatorCode(user.getLoginName());
        }
        nursingRecordService.insert(nursingRecord);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 快捷录入--护理单--批量新增在科病人的护理信息
     * @return
     */
    @RequestMapping(value = "/batchAdd")
    @ResponseBody
    public String batchAdd(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        JSONArray array=JSONArray.fromObject(map.get("list").toString());
        Map<String,Object> pararemMap=new HashMap<>();
        pararemMap.put("dataTime",map.get("time").toString());
        if(array!=null && array.size()>0){
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj=(JSONObject) array.get(i);
                pararemMap.put("patientId",obj.getString("patientId"));
                //体温
                 if(StringUtils.isNotEmpty(obj.getString("tiwen"))){
                     pararemMap.put("code","体温");
                     //根据时间和code 查询 护理单对象
                     NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                     if(nursingRecord==null){
                         //为空则新增
                         NursingRecord data=newNursingRecord(obj,session);
                         data.setCode("体温");
                         data.setContent(obj.getString("tiwen"));
                         nursingRecordService.insert(data);
                     }else{
                         //不为空则编辑
                         nursingRecord.setContent(obj.getString("tiwen"));
                         nursingRecordService.update(nursingRecord);
                     }
                 }
                 //心率
                if(StringUtils.isNotEmpty(obj.getString("xinlv"))){
                    pararemMap.put("code","心率");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("心率");
                        data.setContent(obj.getString("xinlv"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("xinlv"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //脉搏
                if(StringUtils.isNotEmpty(obj.getString("maibo"))){
                    pararemMap.put("code","脉搏");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("脉搏");
                        data.setContent(obj.getString("maibo"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("maibo"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //呼吸
                if(StringUtils.isNotEmpty(obj.getString("huxi"))){
                    pararemMap.put("code","呼吸");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("呼吸");
                        data.setContent(obj.getString("huxi"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("huxi"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //有创血压
                if(StringUtils.isNotEmpty(obj.getString("youchuangxueya"))){
                    pararemMap.put("code","有创血压");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("有创血压");
                        data.setContent(obj.getString("youchuangxueya"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("youchuangxueya"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //无创血压
                if(StringUtils.isNotEmpty(obj.getString("wuchuangxueya"))){
                    pararemMap.put("code","无创血压");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("无创血压");
                        data.setContent(obj.getString("wuchuangxueya"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("wuchuangxueya"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //血氧饱和度
                if(StringUtils.isNotEmpty(obj.getString("xueyangbaohedu"))){
                    pararemMap.put("code","血氧饱和度");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("血氧饱和度");
                        data.setContent(obj.getString("xueyangbaohedu"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("xueyangbaohedu"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //CVPcm
                if(StringUtils.isNotEmpty(obj.getString("CVPcm"))){
                    pararemMap.put("code","CVPcm");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("CVPcm");
                        data.setContent(obj.getString("CVPcm"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("CVPcm"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
                //CVPmm
                if(StringUtils.isNotEmpty(obj.getString("CVPmm"))){
                    pararemMap.put("code","CVPmm");
                    //根据时间和code 查询 护理单对象
                    NursingRecord nursingRecord=nursingRecordService.queryDataByTimeAndCode(pararemMap);
                    if(nursingRecord==null){
                        //为空则新增
                        NursingRecord data=newNursingRecord(obj,session);
                        data.setCode("CVPmm");
                        data.setContent(obj.getString("CVPmm"));
                        nursingRecordService.insert(data);
                    }else{
                        //不为空则编辑
                        nursingRecord.setContent(obj.getString("CVPmm"));
                        nursingRecordService.update(nursingRecord);
                    }
                }
            }
            User user=(User) session.getAttribute("user");
            if(user!=null){
                //添加操作日志信息
                Log log=new Log();
                log.setId(UUIDUtil.getUUID());
                log.setOperatorCode(user.getLoginName());
                log.setOperateTime(new Date());
                log.setMenuCode(MenuCode.HLDKJLR_CODE.getCode());
                log.setContent(map.toString());
                log.setOperateType(LogTypeCode.ADD_CODE.getCode());
                logService.insert(log);
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }
        return json.toString();
    }

    static NursingRecord newNursingRecord(JSONObject jsonObject,HttpSession session){
        NursingRecord nursingRecord=new NursingRecord();
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            nursingRecord.setStatus(1);
            nursingRecord.setId(UUIDUtil.getUUID());
            nursingRecord.setCreateTime(new Date());
            nursingRecord.setDataTime(sdf.parse(jsonObject.getString("dataTime")));
            nursingRecord.setPatientId(jsonObject.getString("patientId"));
            nursingRecord.setVisitCode(jsonObject.getString("visitCode"));
            nursingRecord.setVisitId(jsonObject.getString("visitId"));
            User user =(User) session.getAttribute("user");
            if(user!=null){
                nursingRecord.setOperatorCode(user.getLoginName());
            }
        }catch (Exception e){
                logger.info("newNursingRecord:"+e);
        }
        return nursingRecord;
    }

    /**
     * 更新护理记录表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            NursingRecord nursingRecord=nursingRecordService.queryData(map.get("id").toString());
            if(nursingRecord!=null){
                nursingRecord.setStatus((int)map.get("status"));
                nursingRecordService.update(nursingRecord);
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
     * 编辑护理记录表记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        try {
            if(map!=null && map.containsKey("id") ){
                NursingRecord nursingRecord=nursingRecordService.queryData(map.get("id").toString());
                if(nursingRecord!=null){
                    if(StringUtils.isNotEmpty(map.get("operatorCode").toString())){
                        nursingRecord.setOperatorCode(map.get("operatorCode").toString());
                    }
                    if(StringUtils.isNotEmpty(map.get("content").toString())){
                        nursingRecord.setContent(map.get("content").toString());
                    }
                    if(StringUtils.isNotEmpty(map.get("dateTime").toString())){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        nursingRecord.setDataTime(sdf.parse(map.get("dataTime").toString()));
                    }
                    nursingRecordService.update(nursingRecord);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }else{
                    json.put("msg","编辑失败，没有这个对象。");
                    return json.toString();
                }
            }
        }catch (Exception e){
            logger.info("编辑护理记录表记录单:"+e);
        }
        return json.toString();
    }

    /**
     * 删除护理记录表记录
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
            NursingRecord nursingRecord=nursingRecordService.queryData(map.get("id").toString());
            if(nursingRecord!=null){
                nursingRecord.setStatus(-1);
                nursingRecordService.update(nursingRecord);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","删除失败，没有这个对象。");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询护理记录表记录
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
            NursingRecord nursingRecord=nursingRecordService.queryData(map.get("id").toString());
            if(nursingRecord!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(nursingRecord));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询护理记录表记录列表（也可以不分页）
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
            int totalCount =nursingRecordService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<NursingRecord> list =nursingRecordService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 快捷录入--护理单--查询在科病人的护理信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByTime",method= RequestMethod.POST)
    @ResponseBody
    public String getListByTime(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=nursingRecordService.getListByTime(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            JSONArray array=JSONArray.fromObject(list,jsonConfig);
            json.put("data",array);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","查询成功");
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--护理记录--新增一条护理记录
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            NursingRecord nursingRecord=new NursingRecord();
            nursingRecord.setId(UUIDUtil.getUUID());
            nursingRecord.setCreateTime(new Date());
            nursingRecord.setStatus(1);
            if(StringUtils.isNotEmpty(map.get("operatorCode").toString())){
                nursingRecord.setOperatorCode(map.get("operatorCode").toString());
            }
            if(StringUtils.isNotEmpty(map.get("content").toString())){
                nursingRecord.setContent(map.get("content").toString());
            }
            nursingRecord.setCode(map.get("code").toString());//固定为 score
            nursingRecord.setVisitId(map.get("visitId").toString());
            nursingRecord.setVisitCode(map.get("visitCode").toString());
            nursingRecord.setPatientId(map.get("patientId").toString());
            if(StringUtils.isNotEmpty(map.get("dateTime").toString())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                nursingRecord.setDataTime(sdf.parse(map.get("dataTime").toString()));
            }
            nursingRecordService.insert(nursingRecord);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }catch (Exception e){
            logger.info("护理文书--护理单--护理记录--新增一条护理记录:"+e);
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--护理记录--查询护理记录列表
     * @return code=score status=1
     */
    @RequestMapping(value = "/getListByCode")
    @ResponseBody
    public String getListByCode(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        try {
            List<Map<String,Object>> list=nursingRecordService.getListByCode(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","查询成功");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--护理记录--查询护理记录列表:"+e);
        }
        return json.toString();
    }

}
