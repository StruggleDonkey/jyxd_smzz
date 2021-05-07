package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.VitalSign;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.VitalSignService;
import com.jyxd.web.service.patient.PatientScoreItemService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/vitalSign")
public class VitalSignController {

    private static Logger logger= LoggerFactory.getLogger(VitalSignController.class);

    @Autowired
    private VitalSignService vitalSignService;
    @Autowired
    private PatientScoreItemService patientScoreItemService;

    /**
     * 增加一条生命体征表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody VitalSign vitalSign, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        Map<String,Object> map=new HashMap<>();
        map.put("status",1);
        map.put("dataTime",vitalSign.getDataTime());
        map.put("code",vitalSign.getCode());
        map.put("patientId",vitalSign.getPatientId());
        //首先根据时间和code 和 病人主键id查询对象 是否已经有数据 如果有则新增失败
        VitalSign data=vitalSignService.queryDataByTimeAndCode(map);
        if(data==null){
            vitalSign.setId(UUIDUtil.getUUID());
            vitalSign.setCreateTime(new Date());
            vitalSign.setCreateTime(new Date());
            vitalSign.setUpdateTime(new Date());
            vitalSign.setStatus(1);
            vitalSignService.insert(vitalSign);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }
        return json.toString();
    }

    /**
     * 更新生命体征表记录状态
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
            VitalSign vitalSign=vitalSignService.queryData(map.get("id").toString());
            if(vitalSign!=null){
                vitalSign.setStatus((int)map.get("status"));
                vitalSignService.update(vitalSign);
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
     * 编辑生命体征表记录单
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
            VitalSign vitalSign=vitalSignService.queryData(map.get("id").toString());
            if(vitalSign!=null){
                vitalSign.setStatus((int)map.get("status"));
                vitalSignService.update(vitalSign);
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
     * 删除生命体征表记录
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
            VitalSign vitalSign=vitalSignService.queryData(map.get("id").toString());
            if(vitalSign!=null){
                vitalSign.setStatus(-1);
                vitalSignService.update(vitalSign);
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
     * 根据主键id查询生命体征表记录
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
            VitalSign vitalSign=vitalSignService.queryData(map.get("id").toString());
            if(vitalSign!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(vitalSign));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询生命体征表记录列表（也可以不分页）
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
            int totalCount =vitalSignService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<VitalSign> list =vitalSignService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--生命体征--编辑一条记录（编辑时间除外）
     * @param map
     * @return
     */
    @RequestMapping(value = "/editData")
    @ResponseBody
    public String editData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        VitalSign data=vitalSignService.queryDataByTimeAndCode(map);
        if(data!=null){
            data.setContent(map.get("content").toString());
            data.setUpdateTime(new Date());
            vitalSignService.update(data);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","编辑成功");
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--生命体征--编辑一条记录（编辑时间）
     * @param map
     * @return
     */
    @RequestMapping(value = "/editDataTime")
    @ResponseBody
    public String editDataTime(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        try {
            //先根据原来时间查询列表对象
            List<VitalSign> list=vitalSignService.queryListByTime(map);
            if(list!=null && list.size()>0){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < list.size(); i++) {
                    VitalSign vitalSign=list.get(i);
                    vitalSign.setDataTime(format.parse(map.get("time").toString()));
                    vitalSign.setUpdateTime(new Date());
                    vitalSignService.update(vitalSign);
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","编辑成功");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--生命体征--编辑一条记录（编辑时间）:"+e);
            return json.toString();
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--生命体征--根据病人主键id 查询生命体征列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        try {
            if(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString())){
                List<Map<String,Object>> list=vitalSignService.getList(map);
                if(list!=null && list.size()>0){
                    json.put("data",JSONArray.fromObject(list));
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","查询成功");
                }
            }else{
                json.put("code",HttpCode.NO_PATIENT_CODE.getCode());
                json.put("msg","请先选择病人");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--生命体征--根据病人主键id 查询生命体征列表:"+e);
            return json.toString();
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--生命体征--删除整行
     * @param map
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        json.put("data",new ArrayList<>());
        try {
            List<VitalSign> list=vitalSignService.queryListByTime(map);
            if(list!=null && list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    VitalSign vitalSign=list.get(i);
                    vitalSign.setStatus(-1);
                    vitalSign.setUpdateTime(new Date());
                    vitalSignService.update(vitalSign);
                }
                json.put("data",JSONArray.fromObject(list));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","删除成功");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--生命体征--删除整行:"+e);
            return json.toString();
        }
        return json.toString();
    }

    /**
     * 保存一条生命体征表记录
     * @return
     */
    @RequestMapping(value = "/saveData")
    @ResponseBody
    public String saveData(@RequestBody VitalSign vitalSign,@RequestBody(required = false) Map<String,Object> glasgowMap, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            if(StringUtils.isNotEmpty(vitalSign.getPatientId())){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map<String,Object> map=new HashMap<>();
                map.put("status",1);
                map.put("dataTime",format.format(vitalSign.getDataTime()));
                map.put("code",vitalSign.getCode());
                map.put("patientId",vitalSign.getPatientId());
                if (StringUtils.equals(vitalSign.getCode(),"glasgow")){
                    if (CollectionUtils.isEmpty(map)){
                        json.put("msg","添加glasgow评分失败，评分数据不能为空");
                        return json.toString();
                    }
                    saveGlasgow(glasgowMap,(User)session.getAttribute("user"));
                }
                //首先根据时间和code 和 病人主键id查询对象 是否已经有数据 如果有则编辑
                VitalSign data=vitalSignService.queryDataByTimeAndCode(map);
                if(data!=null){
                    data.setContent(vitalSign.getContent());
                    data.setUpdateTime(new Date());
                    vitalSignService.update(data);
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","添加成功");
                }else{
                    vitalSign.setId(UUIDUtil.getUUID());
                    vitalSign.setCreateTime(new Date());
                    vitalSign.setUpdateTime(new Date());
                    vitalSign.setStatus(1);
                    vitalSignService.insert(vitalSign);
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","添加成功");
                }
            }else{
                json.put("code",HttpCode.NO_PATIENT_CODE.getCode());
                json.put("msg","请先选择病人");
            }
        }catch (Exception e){
            logger.info(":"+e);
        }
        return json.toString();
    }

    /**
     * 新增glasgow评分
     * @param map
     * @param user
     * @throws ParseException
     */
    private void saveGlasgow(Map<String,Object> map,User user) throws ParseException {
        patientScoreItemService.insertPatientScore(map,user);
    }
}
