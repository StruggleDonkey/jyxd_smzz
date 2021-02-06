package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BedArrange;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.basic.BedArrangeService;
import com.jyxd.web.service.patient.PatientService;
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

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/bedArrange")
public class BedArrangeController {

    private static Logger logger= LoggerFactory.getLogger(BedArrangeController.class);

    @Autowired
    private BedArrangeService bedArrangeService;

    @Autowired
    private PatientService patientService;

    /**
     * 增加一条床位安排表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody BedArrange bedArrange){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        bedArrange.setId(UUIDUtil.getUUID());
        bedArrangeService.insert(bedArrange);
        if(!StringUtils.isEmpty(bedArrange.getPatientId())){
            Patient patient=patientService.queryData(bedArrange.getPatientId());
            patient.setBedCode(bedArrange.getBedCode());
            patientService.update(patient);
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新床位安排表记录状态
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
            BedArrange bedArrange=bedArrangeService.queryData(map.get("id").toString());
            if(bedArrange!=null){
                bedArrangeService.update(bedArrange);
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
     * 编辑床位安排表记录单
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
            BedArrange bedArrange=bedArrangeService.queryData(map.get("id").toString());
            if(bedArrange!=null){

                bedArrangeService.update(bedArrange);
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
     * 删除床位安排表记录
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
            BedArrange bedArrange=bedArrangeService.queryData(map.get("id").toString());
            if(bedArrange!=null){

                bedArrangeService.update(bedArrange);
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
     * 根据主键id查询床位安排表记录
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
            BedArrange bedArrange=bedArrangeService.queryData(map.get("id").toString());
            if(bedArrange!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(bedArrange));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询床位安排表记录列表（也可以不分页）
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
            int totalCount =bedArrangeService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BedArrange> list =bedArrangeService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 查询床位安排列表信息
     * @return
     */
    @RequestMapping(value = "/getBedArrangeList",method= RequestMethod.POST)
    @ResponseBody
    public String getBedArrangeList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=bedArrangeService.getBedArrangeList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 床位安排--选择病人、选择监护仪
     * @return
     */
    @RequestMapping(value = "/addBedArrange",method= RequestMethod.POST)
    @ResponseBody
    public String addBedArrange(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","选择失败");
        //选择病人
        if(map.containsKey("patientId") && map.containsKey("bedCode") && StringUtils.isNotEmpty(map.get("patientId").toString())
                && StringUtils.isNotEmpty(map.get("bedCode").toString())){
            Map<String,Object> map1=new HashMap<>();
            map1.put("bedCode",map.get("bedCode").toString());
            BedArrange bedArrange=bedArrangeService.queryDataByCode(map1);
            if(bedArrange!=null){
                bedArrange.setPatientId(map.get("patientId").toString());
                bedArrangeService.update(bedArrange);
            }else{
                BedArrange data=new BedArrange();
                data.setPatientId(map.get("patientId").toString());
                data.setBedCode(map.get("bedCode").toString());
                data.setId(UUIDUtil.getUUID());
                bedArrangeService.insert(data);
            }

            Patient patient=patientService.queryData(map.get("patientId").toString());
            if(patient!=null){
                patient.setFlag(1);//入科
                patient.setBedCode(map.get("bedCode").toString());
                patientService.update(patient);
            }
            json.put("msg","选择成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        //选择监护仪
        if(map.containsKey("monitorCode") && map.containsKey("bedCode") && StringUtils.isNotEmpty(map.get("monitorCode").toString())
                && StringUtils.isNotEmpty(map.get("bedCode").toString()) ){
            Map<String,Object> map1=new HashMap<>();
            map1.put("bedCode",map.get("bedCode").toString());
            BedArrange bedArrange=bedArrangeService.queryDataByCode(map1);
            if(bedArrange!=null){
                bedArrange.setMonitorCode(map.get("monitorCode").toString());
                bedArrangeService.update(bedArrange);
            }else{
                BedArrange data=new BedArrange();
                data.setMonitorCode(map.get("monitorCode").toString());
                data.setBedCode(map.get("bedCode").toString());
                data.setId(UUIDUtil.getUUID());
                bedArrangeService.insert(data);
            }
            json.put("msg","选择成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 床位安排--还原病人、还原监护仪
     * @return
     */
    @RequestMapping(value = "/restoreBedArrange",method= RequestMethod.POST)
    @ResponseBody
    public String restoreBedArrange(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","还原失败");
        //选择病人
        if(map.containsKey("patientId") && map.containsKey("bedCode") && StringUtils.isNotEmpty(map.get("bedCode").toString())
                && StringUtils.isNotEmpty(map.get("patientId").toString())){
            BedArrange bedArrange=bedArrangeService.queryDataByCode(map);
            if(bedArrange!=null){
                bedArrange.setPatientId("");
                bedArrangeService.update(bedArrange);
            }
            Patient patient=patientService.queryData(map.get("patientId").toString());
            if(patient!=null){
                patient.setBedCode("");
                patientService.update(patient);
            }
            json.put("msg","还原成功");
            json.put("code",HttpCode.OK_CODE.getCode());json.put("msg","还原成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        //选择监护仪
        if(map.containsKey("monitorCode") && map.containsKey("bedCode") && StringUtils.isNotEmpty(map.get("bedCode").toString())
                && StringUtils.isNotEmpty(map.get("monitorCode").toString())){
            BedArrange bedArrange=bedArrangeService.queryDataByCode(map);
            if(bedArrange!=null){
                bedArrange.setMonitorCode("");
                bedArrangeService.update(bedArrange);
            }
            json.put("msg","还原成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }

        return json.toString();
    }

    /**
     * 床位安排--出科
     * @return
     */
    @RequestMapping(value = "/patientGiven",method= RequestMethod.POST)
    @ResponseBody
    public String patientGiven(@RequestBody(required=false) Map<String,Object> map) throws Exception{
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","出科失败");
        //选择病人
        if(map.containsKey("patientId") && map.containsKey("bedCode")){
            BedArrange bedArrange=bedArrangeService.queryDataByCode(map);
            if(bedArrange!=null){
                bedArrange.setPatientId("");
                bedArrangeService.update(bedArrange);
            }
            Patient patient=patientService.queryData(map.get("patientId").toString());
            if(patient!=null){
                patient.setBedCode("");
                patient.setFlag(0);
                patient.setExitType(map.get("exitType").toString());//出科方式
                if(map.get("exitType").toString().equals("转科")){
                    patient.setToDepartmentCode(map.get("toDepartmentCode").toString());//转出科室编码
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date=formatter.parse(map.get("exitTime").toString());
                    patient.setExitTime(date);
                }
                patientService.update(patient);

            }
        }
        json.put("msg","出科成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
