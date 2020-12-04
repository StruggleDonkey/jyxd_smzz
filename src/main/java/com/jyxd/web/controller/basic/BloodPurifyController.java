package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BloodPurify;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.BloodPurifyService;
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
@RequestMapping(value = "/bloodPurify")
public class BloodPurifyController {

    private static Logger logger= LoggerFactory.getLogger(BloodPurifyController.class);

    @Autowired
    private BloodPurifyService bloodPurifyService;

    /**
     * 增加一条血液净化基本信息表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody BloodPurify bloodPurify){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        bloodPurify.setId(UUIDUtil.getUUID());
        bloodPurify.setCreateTime(new Date());
        bloodPurifyService.insert(bloodPurify);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新血液净化基本信息表记录状态
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
            BloodPurify bloodPurify=bloodPurifyService.queryData(map.get("id").toString());
            if(bloodPurify!=null){
                bloodPurify.setStatus((int)map.get("status"));
                bloodPurifyService.update(bloodPurify);
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
     * 编辑血液净化基本信息表记录单
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
            BloodPurify bloodPurify=bloodPurifyService.queryData(map.get("id").toString());
            if(bloodPurify!=null){
                bloodPurify.setStatus((int)map.get("status"));
                bloodPurifyService.update(bloodPurify);
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
     * 删除血液净化基本信息表记录
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
            BloodPurify bloodPurify=bloodPurifyService.queryData(map.get("id").toString());
            if(bloodPurify!=null){
                bloodPurify.setStatus(-1);
                bloodPurifyService.update(bloodPurify);
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
     * 根据主键id查询血液净化基本信息表记录
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
            BloodPurify bloodPurify=bloodPurifyService.queryData(map.get("id").toString());
            if(bloodPurify!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(bloodPurify));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询血液净化基本信息表记录列表（也可以不分页）
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
            int totalCount =bloodPurifyService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BloodPurify> list =bloodPurifyService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--血液净化记录单--保存血液净化记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        String maintenanceId=UUIDUtil.getUUID();//导管维护主键
        //穿刺部位左：punctureLeft
        if(map!=null && map.containsKey("punctureLeft") && StringUtils.isNotEmpty(map.get("punctureLeft").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("punctureLeft");
            bloodPurify.setContent(map.get("punctureLeft").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //穿刺部位右：punctureRight
        if(map!=null && map.containsKey("punctureRight") && StringUtils.isNotEmpty(map.get("punctureRight").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("punctureRight");
            bloodPurify.setContent(map.get("punctureRight").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //股静脉：thighVein
        if(map!=null && map.containsKey("thighVein") && StringUtils.isNotEmpty(map.get("thighVein").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("thighVein");
            bloodPurify.setContent(map.get("thighVein").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //颈静脉：neckVein
        if(map!=null && map.containsKey("neckVein") && StringUtils.isNotEmpty(map.get("neckVein").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("neckVein");
            bloodPurify.setContent(map.get("neckVein").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //锁骨下静脉：clavicleVein
        if(map!=null && map.containsKey("clavicleVein") && StringUtils.isNotEmpty(map.get("clavicleVein").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("clavicleVein");
            bloodPurify.setContent(map.get("clavicleVein").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //内瘘：innerVein
        if(map!=null && map.containsKey("innerVein") && StringUtils.isNotEmpty(map.get("innerVein").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("innerVein");
            bloodPurify.setContent(map.get("innerVein").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //开始时间：beginTime
        if(map!=null && map.containsKey("beginTime") && StringUtils.isNotEmpty(map.get("beginTime").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("beginTime");
            bloodPurify.setContent(map.get("beginTime").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //结束时间：endTime
        if(map!=null && map.containsKey("endTime") && StringUtils.isNotEmpty(map.get("endTime").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("endTime");
            bloodPurify.setContent(map.get("endTime").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //共计时间：duration
        if(map!=null && map.containsKey("duration") && StringUtils.isNotEmpty(map.get("duration").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("duration");
            bloodPurify.setContent(map.get("duration").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //预冲液：preWashLiquid
        if(map!=null && map.containsKey("preWashLiquid") && StringUtils.isNotEmpty(map.get("preWashLiquid").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("preWashLiquid");
            bloodPurify.setContent(map.get("preWashLiquid").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //抗凝剂-肝素：heparin
        if(map!=null && map.containsKey("heparin") && StringUtils.isNotEmpty(map.get("heparin").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("heparin");
            bloodPurify.setContent(map.get("heparin").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //抗凝剂-低分子肝素：lowMolecularHeparin
        if(map!=null && map.containsKey("lowMolecularHeparin") && StringUtils.isNotEmpty(map.get("lowMolecularHeparin").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("lowMolecularHeparin");
            bloodPurify.setContent(map.get("lowMolecularHeparin").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //抗凝剂-阿加曲班：agatroban
        if(map!=null && map.containsKey("agatroban") && StringUtils.isNotEmpty(map.get("agatroban").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("agatroban");
            bloodPurify.setContent(map.get("agatroban").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //抗凝剂-枸橼酸抗凝剂：citrate
        if(map!=null && map.containsKey("citrate") && StringUtils.isNotEmpty(map.get("citrate").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("citrate");
            bloodPurify.setContent(map.get("citrate").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //封管液：sealLiquid
        if(map!=null && map.containsKey("sealLiquid") && StringUtils.isNotEmpty(map.get("sealLiquid").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("sealLiquid");
            bloodPurify.setContent(map.get("sealLiquid").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //风管时间：sealTime
        if(map!=null && map.containsKey("sealTime") && StringUtils.isNotEmpty(map.get("sealTime").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("sealTime");
            bloodPurify.setContent(map.get("sealTime").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //治疗模式-CVVH:cvvh
        if(map!=null && map.containsKey("cvvh") && StringUtils.isNotEmpty(map.get("cvvh").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("cvvh");
            bloodPurify.setContent(map.get("cvvh").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //治疗模式-CVVHD:cvvhd
        if(map!=null && map.containsKey("cvvhd") && StringUtils.isNotEmpty(map.get("cvvhd").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("cvvhd");
            bloodPurify.setContent(map.get("cvvhd").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //治疗模式-CVVHDF:cvvhdf
        if(map!=null && map.containsKey("cvvhdf") && StringUtils.isNotEmpty(map.get("cvvhdf").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("cvvhdf");
            bloodPurify.setContent(map.get("cvvhdf").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //治疗模式-PE:pe
        if(map!=null && map.containsKey("pe") && StringUtils.isNotEmpty(map.get("pe").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("pe");
            bloodPurify.setContent(map.get("pe").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //治疗模式-HP:hp
        if(map!=null && map.containsKey("hp") && StringUtils.isNotEmpty(map.get("hp").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("hp");
            bloodPurify.setContent(map.get("hp").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //开始治疗者：beginTreater
        if(map!=null && map.containsKey("beginTreater") && StringUtils.isNotEmpty(map.get("beginTreater").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("beginTreater");
            bloodPurify.setContent(map.get("beginTreater").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //查对者：checker
        if(map!=null && map.containsKey("checker") && StringUtils.isNotEmpty(map.get("checker").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("checker");
            bloodPurify.setContent(map.get("checker").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //结束治疗者：endTreater
        if(map!=null && map.containsKey("endTreater") && StringUtils.isNotEmpty(map.get("endTreater").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("endTreater");
            bloodPurify.setContent(map.get("endTreater").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        //医生签字：doctorSignature
        if(map!=null && map.containsKey("doctorSignature") && StringUtils.isNotEmpty(map.get("doctorSignature").toString())){
            BloodPurify bloodPurify=newBloodPurify(map,session);
            bloodPurify.setMaintenanceId(maintenanceId);
            bloodPurify.setCode("doctorSignature");
            bloodPurify.setContent(map.get("doctorSignature").toString());
            bloodPurifyService.insert(bloodPurify);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 新建血液净化对象
     * @param map
     * @param session
     * @return
     */
    public BloodPurify newBloodPurify(Map<String,Object> map, HttpSession session){

        BloodPurify bloodPurify=new BloodPurify();
        try {
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            bloodPurify.setStatus(1);
            bloodPurify.setId(UUIDUtil.getUUID());
            bloodPurify.setCreateTime(new Date());
            if(map!=null && map.containsKey("dataTime")){
                bloodPurify.setDataTime(sdf.parse(map.get("dataTime").toString()));
            }
            if(map!=null && map.containsKey("patientId")){
                bloodPurify.setPatientId(map.get("patientId").toString());
            }
            if(map!=null && map.containsKey("visitCode")){
                bloodPurify.setVisitCode(map.get("visitCode").toString());
            }
            if(map!=null && map.containsKey("visitId")){
                bloodPurify.setVisitId(map.get("visitId").toString());
            }
            User user=(User)session.getAttribute("user");
            if(user!=null){
                bloodPurify.setOperatorCode(user.getLoginName());
            }
        }catch (Exception e){
            logger.info("护理文书--血液净化记录单--保存血液净化记录-newBloodPurify:"+e);
        }
        return bloodPurify;
    }

    /**
     * 护理文书--血液净化记录单--历史记录--查询列表（也可以不分页）
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
            int totalCount =bloodPurifyService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =bloodPurifyService.getList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--血液净化记录单--历史记录--删除
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData",method= RequestMethod.POST)
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","删除失败");
        if(map!=null && map.containsKey("maintenanceId") && StringUtils.isNotEmpty(map.get("maintenanceId").toString())){
            List<BloodPurify> list =bloodPurifyService.queryListByMaintenanceId(map.get("maintenanceId").toString());
            if(list!=null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setStatus(-1);
                    bloodPurifyService.update(list.get(i));
                }
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 护理文书--血液净化记录单--历史记录--选择
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDataByMaintenanceId",method= RequestMethod.POST)
    @ResponseBody
    public String getDataByMaintenanceId(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        if(map!=null && map.containsKey("maintenanceId")){
            Map<String,Object> mm=bloodPurifyService.getDataByMaintenanceId(map);
            if(mm!=null){
                json.put("data",JSONObject.fromObject(mm));
                json.put("msg","成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

}
