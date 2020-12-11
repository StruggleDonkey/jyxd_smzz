package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.SurgeryNursing;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.SurgeryNursingService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
@RequestMapping(value = "/surgeryNursing")
public class SurgeryNursingController {

    private static Logger logger= LoggerFactory.getLogger(SurgeryNursingController.class);

    @Autowired
    private SurgeryNursingService surgeryNursingService;

    /**
     * 增加一条围手术期护理基本信息表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody SurgeryNursing surgeryNursing){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        surgeryNursing.setId(UUIDUtil.getUUID());
        surgeryNursing.setCreateTime(new Date());
        surgeryNursingService.insert(surgeryNursing);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新围手术期护理基本信息表记录状态
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
            SurgeryNursing surgeryNursing=surgeryNursingService.queryData(map.get("id").toString());
            if(surgeryNursing!=null){
                surgeryNursing.setStatus((int)map.get("status"));
                surgeryNursingService.update(surgeryNursing);
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
     * 编辑围手术期护理基本信息表记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            SurgeryNursing surgeryNursing=surgeryNursingService.queryData(map.get("id").toString());
            if(surgeryNursing!=null){
                surgeryNursing.setStatus((int)map.get("status"));
                surgeryNursingService.update(surgeryNursing);
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
     * 删除围手术期护理基本信息表记录
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
            SurgeryNursing surgeryNursing=surgeryNursingService.queryData(map.get("id").toString());
            if(surgeryNursing!=null){
                surgeryNursing.setStatus(-1);
                surgeryNursingService.update(surgeryNursing);
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
     * 根据主键id查询围手术期护理基本信息表记录
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
            SurgeryNursing surgeryNursing=surgeryNursingService.queryData(map.get("id").toString());
            if(surgeryNursing!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(surgeryNursing));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询围手术期护理基本信息表记录列表（也可以不分页）
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
            int totalCount =surgeryNursingService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<SurgeryNursing> list =surgeryNursingService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--围手术期护理单--首页--保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","保存失败");
        String maintenanceId=UUIDUtil.getUUID();
        if(map!=null && map.containsKey("visitDate") && StringUtils.isNotEmpty(map.get("visitDate").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("visitDate");
            surgeryNursing.setContent(map.get("visitDate").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("diagnosis") && StringUtils.isNotEmpty(map.get("diagnosis").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("diagnosis");
            surgeryNursing.setContent(map.get("diagnosis").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("operationDate") && StringUtils.isNotEmpty(map.get("operationDate").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("operationDate");
            surgeryNursing.setContent(map.get("operationDate").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("operationName") && StringUtils.isNotEmpty(map.get("operationName").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("operationName");
            surgeryNursing.setContent(map.get("operationName").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("temperature") && StringUtils.isNotEmpty(map.get("temperature").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("temperature");
            surgeryNursing.setContent(map.get("temperature").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("pulse") && StringUtils.isNotEmpty(map.get("pulse").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("pulse");
            surgeryNursing.setContent(map.get("pulse").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("hr") && StringUtils.isNotEmpty(map.get("hr").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("hr");
            surgeryNursing.setContent(map.get("hr").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("br") && StringUtils.isNotEmpty(map.get("br").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("br");
            surgeryNursing.setContent(map.get("br").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("bp") && StringUtils.isNotEmpty(map.get("bp").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("bp");
            surgeryNursing.setContent(map.get("bp").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("presurgeryTeach") && StringUtils.isNotEmpty(map.get("presurgeryTeach").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("presurgeryTeach");
            surgeryNursing.setContent(map.get("presurgeryTeach").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("preparedSkin") && StringUtils.isNotEmpty(map.get("preparedSkin").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("preparedSkin");
            surgeryNursing.setContent(map.get("preparedSkin").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("bladderEmpty") && StringUtils.isNotEmpty(map.get("bladderEmpty").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("bladderEmpty");
            surgeryNursing.setContent(map.get("bladderEmpty").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("inspectionAndAssaySheetCompleted") && StringUtils.isNotEmpty(map.get("inspectionAndAssaySheetCompleted").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("inspectionAndAssaySheetCompleted");
            surgeryNursing.setContent(map.get("inspectionAndAssaySheetCompleted").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("mentalStatus") && StringUtils.isNotEmpty(map.get("mentalStatus").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("mentalStatus");
            surgeryNursing.setContent(map.get("mentalStatus").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("venousAccessSite") && StringUtils.isNotEmpty(map.get("venousAccessSite").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("venousAccessSite");
            surgeryNursing.setContent(map.get("venousAccessSite").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("antibioticSkinTest") && StringUtils.isNotEmpty(map.get("antibioticSkinTest").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("antibioticSkinTest");
            surgeryNursing.setContent(map.get("antibioticSkinTest").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("leaveTime") && StringUtils.isNotEmpty(map.get("leaveTime").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("leaveTime");
            surgeryNursing.setContent(map.get("leaveTime").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("leaveNurseSignature") && StringUtils.isNotEmpty(map.get("leaveNurseSignature").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("leaveNurseSignature");
            surgeryNursing.setContent(map.get("leaveNurseSignature").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("backTime") && StringUtils.isNotEmpty(map.get("backTime").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("backTime");
            surgeryNursing.setContent(map.get("backTime").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("backNurseSignature") && StringUtils.isNotEmpty(map.get("backNurseSignature").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("backNurseSignature");
            surgeryNursing.setContent(map.get("backNurseSignature").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("repressionDuration") && StringUtils.isNotEmpty(map.get("repressionDuration").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("repressionDuration");
            surgeryNursing.setContent(map.get("repressionDuration").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("repressBeginTime") && StringUtils.isNotEmpty(map.get("repressBeginTime").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("repressBeginTime");
            surgeryNursing.setContent(map.get("repressBeginTime").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        if(map!=null && map.containsKey("repressEndTime") && StringUtils.isNotEmpty(map.get("repressEndTime").toString())){
            SurgeryNursing surgeryNursing=newSurgeryNursing(map,session);
            surgeryNursing.setMaintenanceId(maintenanceId);
            surgeryNursing.setCode("repressEndTime");
            surgeryNursing.setContent(map.get("repressEndTime").toString());
            surgeryNursingService.insert(surgeryNursing);
            json.put("msg","保存成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 新建围手术期护理单对象
     * @return
     */
    private SurgeryNursing newSurgeryNursing(Map<String,Object> map, HttpSession session){
        SurgeryNursing surgeryNursing=new SurgeryNursing();
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            surgeryNursing.setId(UUIDUtil.getUUID());
            surgeryNursing.setStatus(1);
            surgeryNursing.setCreateTime(new Date());
            if(map!=null && map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString())){
                surgeryNursing.setDataTime(sdf.parse(map.get("dataTime").toString()));
            }
            if(map!=null && map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                surgeryNursing.setPatientId(map.get("patientId").toString());
            }
            if(map!=null && map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString())){
                surgeryNursing.setVisitCode(map.get("visitCode").toString());
            }
            if(map!=null && map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString())){
                surgeryNursing.setVisitId(map.get("visitId").toString());
            }
            User user=(User)session.getAttribute("user");
            if(user!=null){
                surgeryNursing.setOperatorCode(user.getLoginName());
            }
        }catch (Exception e){
            logger.info("新建围手术期护理单对象:"+e);
        }
        return surgeryNursing;
    }

    /**
     * 护理文书--围手术期护理单--首页--历史记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =surgeryNursingService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =surgeryNursingService.getList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--围手术期护理单--首页--历史记录--选择
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryDataByMaintenanceId")
    @ResponseBody
    public String queryDataByMaintenanceId(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","失败");
        if(map!=null && map.containsKey("maintenanceId") ){
            List<Map<String,Object>> list=surgeryNursingService.queryDataByMaintenanceId(map);
            if(list!=null && list.size()>0){
                 json.put("data",JSONArray.fromObject(list));
                 json.put("msg","成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--围手术期护理单--首页--历史记录--删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map!=null && map.containsKey("maintenanceId") && StringUtils.isNotEmpty(map.get("maintenanceId").toString())){
            List<SurgeryNursing> list=surgeryNursingService.queryListByMaintenanceId(map.get("maintenanceId").toString());
            if(list!=null && list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setStatus(-1);
                    surgeryNursingService.update(list.get(i));
                }
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

}
