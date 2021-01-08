package com.jyxd.web.controller.log;

import com.jyxd.web.data.basic.BasicNursing;
import com.jyxd.web.data.basic.VitalSign;
import com.jyxd.web.data.log.IcuLog;
import com.jyxd.web.service.basic.BasicNursingService;
import com.jyxd.web.service.basic.VitalSignService;
import com.jyxd.web.service.log.IcuLogService;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/icuLog")
public class IcuLogController {

    private static Logger logger= LoggerFactory.getLogger(IcuLogController.class);

    @Autowired
    private IcuLogService icuLogService;

    @Autowired
    private BasicNursingService basicNursingService;

    @Autowired
    private VitalSignService vitalSignService;


    /**
     * 增加一条ICU日志表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody IcuLog icuLog, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        icuLog.setId(UUIDUtil.getUUID());
        icuLog.setCreateTime(new Date());
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 根据主键id查询ICU日志表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            IcuLog icuLog=icuLogService.queryData(map.get("id").toString());
            if(icuLog!=null){
                json.put("data",JSONObject.fromObject(icuLog));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU日志表列表（也可以不分页）
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
            int totalCount =icuLogService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<IcuLog> list =icuLogService.queryList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-患者日志-新增日志-保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/save",method= RequestMethod.POST)
    @ResponseBody
    public String save(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            IcuLog icuLog=new IcuLog();
            icuLog.setCreateTime(new Date());
            icuLog.setId(UUIDUtil.getUUID());
            icuLog.setVisitId(map.get("visitId").toString());
            icuLog.setVisitCode(map.get("visitCode").toString());
            icuLog.setPatientId(map.get("patientId").toString());
            icuLog.setItemId(map.get("itemId").toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            icuLog.setHappenTime(format.parse(map.get("happenTime").toString()));
            icuLog.setHappenOperatorCode(map.get("happenOperatorCode").toString());
            icuLog.setFinishType((int)map.get("finisType"));
            icuLog.setLogType(map.get("logType").toString());
            if(map.containsKey("happenContent") && StringUtils.isNotEmpty(map.get("happenContent").toString())){
                icuLog.setHappenContent(map.get("happenContent").toString());
            }
            if(map.containsKey("synValue") && StringUtils.isNotEmpty(map.get("synValue").toString())){
                icuLog.setSynValue(map.get("synValue").toString());
            }
            //logForm 日志形式(0：发生记录 1：过程记录)
            if(map.containsKey("logForm") && StringUtils.isNotEmpty(map.get("logForm").toString())  && "0".equals(map.get("logForm").toString())){
                //发生记录 status 设置为已完成 否则设置为进行中
                icuLog.setStatus(1);
            }else{
                icuLog.setStatus(0);
            }

            //synNursing 是否同步至护理记录(0：否  1：是)
            if(map.containsKey("synNursing") && StringUtils.isNotEmpty(map.get("synNursing").toString())  && "1".equals(map.get("synNursing").toString())){
                switch(map.get("synTable ").toString()){
                    case "table_basic_nursing":
                        //基础护理
                        BasicNursing basicNursing=new BasicNursing();
                        basicNursing.setStatus(1);
                        basicNursing.setId(UUIDUtil.getUUID());
                        basicNursing.setOperatorCode(map.get("happenOperatorCode").toString());
                        basicNursing.setVisitId(map.get("visitId").toString());
                        basicNursing.setVisitCode(map.get("visitCode").toString());
                        basicNursing.setPatientId(map.get("patientId").toString());
                        basicNursing.setCreateTime(new Date());
                        basicNursing.setDataTime(format.parse(map.get("happenTime").toString()));
                        basicNursing.setCode(map.get("synCode").toString());
                        basicNursing.setContent(map.get("synValue").toString());
                        basicNursingService.insert(basicNursing);
                        break;
                    case "table_vital_sign":
                        //生命体征
                        VitalSign vitalSign=new VitalSign();
                        vitalSign.setStatus(1);
                        vitalSign.setId(UUIDUtil.getUUID());
                        vitalSign.setOperatorCode(map.get("happenOperatorCode").toString());
                        vitalSign.setVisitId(map.get("visitId").toString());
                        vitalSign.setVisitCode(map.get("visitCode").toString());
                        vitalSign.setPatientId(map.get("patientId").toString());
                        vitalSign.setCreateTime(new Date());
                        vitalSign.setDataTime(format.parse(map.get("happenTime").toString()));
                        vitalSign.setCode(map.get("synCode").toString());
                        vitalSign.setContent(map.get("synValue").toString());
                        vitalSignService.insert(vitalSign);
                        break;
                }
            }
            icuLogService.insert(icuLog);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }catch (Exception e){
            logger.info("病人管理-患者日志-新增日志-保存:"+e);
        }
        return json.toString();
    }

    /**
     * 病人管理-患者日志-新增日志-分页查询日志列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map!=null && map.containsKey("start")){
            int totalCount =icuLogService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =icuLogService.getList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-患者日志-日志列表-编辑回显
     * @param map
     * @return
     */
    @RequestMapping(value = "/getEditData",method= RequestMethod.POST)
    @ResponseBody
    public String getEditData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        Map<String,Object> data=icuLogService.getEditData(map);
        if(data!=null){
            if(StringUtils.isNotEmpty(map.get("icu_log_template_id").toString())){
                JSONArray jsonArray=new JSONArray();
                jsonArray.add(map.get("icu_log_template_id").toString());//日志模板类型id
                jsonArray.add(map.get("icu_log_template_item_id").toString());
                data.put("selectOne",jsonArray);
            }
            if(StringUtils.isNotEmpty(map.get("syn_value_select").toString())){
                JSONArray jsonArray=new JSONArray();
                jsonArray.add(map.get("syn_value").toString());
                data.put("selectTwo",jsonArray);
            }
        }
        json.put("data",JSONObject.fromObject(data));
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-患者日志-日志列表-编辑-保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveEditData",method= RequestMethod.POST)
    @ResponseBody
    public String saveEditData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            IcuLog icuLog=icuLogService.queryData(map.get("id").toString());
            if(icuLog!=null){
                if(StringUtils.isNotEmpty(map.get("happenTime").toString())){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    icuLog.setHappenTime(format.parse(map.get("happenTime").toString()));
                }
                if(StringUtils.isNotEmpty(map.get("logType").toString())){
                    icuLog.setItemId(map.get("itemId").toString());
                    icuLog.setLogType(map.get("logType").toString());
                }
                if(StringUtils.isNotEmpty(map.get("synValue").toString())){
                    icuLog.setSynValue(map.get("synValue").toString());
                }
                if(StringUtils.isNotEmpty(map.get("happenOperatorCode").toString())){
                    icuLog.setHappenOperatorCode(map.get("happenOperatorCode").toString());
                }
                if(StringUtils.isNotEmpty(map.get("happenContent").toString())){
                    icuLog.setHappenContent(map.get("happenContent").toString());
                }
                icuLogService.update(icuLog);
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            logger.info("病人管理-患者日志-日志列表-编辑-保存:"+e);
        }
        return json.toString();
    }

    /**
     * 病人管理-患者日志-日志列表-结束-保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveEndData",method= RequestMethod.POST)
    @ResponseBody
    public String saveEndData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            IcuLog icuLog=icuLogService.queryData(map.get("id").toString());
            if(icuLog!=null){
                if(StringUtils.isNotEmpty(map.get("finishTime").toString())){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    icuLog.setFinishTime(format.parse(map.get("finishTime").toString()));
                }
                if(StringUtils.isNotEmpty(map.get("finishOperatorCode").toString())){
                    icuLog.setFinishOperatorCode(map.get("finishOperatorCode").toString());
                }
                if(StringUtils.isNotEmpty(map.get("finishContent").toString())){
                    icuLog.setFinishContent(map.get("finishContent").toString());
                }
                icuLog.setStatus((int)map.get("status"));
                icuLogService.update(icuLog);
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            logger.info("病人管理-患者日志-日志列表-结束-保存:"+e);
        }
        return json.toString();
    }

    /**
     * 病人管理-患者日志-日志列表-删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData",method= RequestMethod.POST)
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            IcuLog icuLog=icuLogService.queryData(map.get("id").toString());
            if(icuLog!=null){
                icuLog.setStatus(-1);
                icuLogService.update(icuLog);
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            logger.info("病人管理-患者日志-日志列表-删除:"+e);
        }
        return json.toString();
    }

}
