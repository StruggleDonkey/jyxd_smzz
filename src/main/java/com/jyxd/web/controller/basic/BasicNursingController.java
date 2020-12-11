package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BasicNursing;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.BasicNursingService;
import com.jyxd.web.service.dictionary.CommentItemService;
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
import java.util.*;

@Controller
@RequestMapping(value = "/basicNursing")
public class BasicNursingController {

    private static Logger logger= LoggerFactory.getLogger(BasicNursingController.class);

    @Autowired
    private BasicNursingService basicNursingService;

    @Autowired
    private CommentItemService commentItemService;

    /**
     * 增加一条基础护理表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody BasicNursing basicNursing, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        basicNursing.setId(UUIDUtil.getUUID());
        basicNursing.setCreateTime(new Date());
        User user =(User)session.getAttribute("user");
        if(user!=null){
            basicNursing.setOperatorCode(user.getLoginName());
        }
        basicNursingService.insert(basicNursing);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新基础护理表记录状态
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
            BasicNursing basicNursing=basicNursingService.queryData(map.get("id").toString());
            if(basicNursing!=null){
                basicNursing.setStatus((int)map.get("status"));
                basicNursingService.update(basicNursing);
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
     * 编辑基础护理表记录单
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
            BasicNursing basicNursing=basicNursingService.queryData(map.get("id").toString());
            if(basicNursing!=null){
                basicNursing.setStatus((int)map.get("status"));
                basicNursingService.update(basicNursing);
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
     * 删除基础护理表记录
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
            BasicNursing basicNursing=basicNursingService.queryData(map.get("id").toString());
            if(basicNursing!=null){
                basicNursing.setStatus(-1);
                basicNursingService.update(basicNursing);
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
     * 根据主键id查询基础护理表记录
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
            BasicNursing basicNursing=basicNursingService.queryData(map.get("id").toString());
            if(basicNursing!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(basicNursing));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询基础护理表记录列表（也可以不分页）
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
            int totalCount =basicNursingService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BasicNursing> list =basicNursingService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 快捷录入--护理单--查询所有在科病人护理信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/getNursingList",method= RequestMethod.POST)
    @ResponseBody
    public String getNursingList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("time")){
            List<Map<String,Object>> list=basicNursingService.getNursingList(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","查询成功");
            }
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--基础护理--保存基础护理数据
     * @return
     */
    @RequestMapping(value = "/saveData")
    @ResponseBody
    public String saveData(@RequestBody BasicNursing basicNursing, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String,Object> map=new HashMap<>();
            map.put("status",1);
            map.put("patientId",basicNursing.getPatientId());
            map.put("dataTime",format.format(basicNursing.getDataTime()));
            map.put("code",basicNursing.getCode());
            //先根据病人id 时间 code  查询基础护理对象
            BasicNursing data=basicNursingService.getDataByPatientIdAndCodeAndTime(map);
            User user =(User)session.getAttribute("user");
            if(data!=null){
                //对象存在则编辑 不存在则新增
                data.setContent(basicNursing.getContent());
                if(user!=null){
                    data.setOperatorCode(user.getLoginName());
                }
                basicNursingService.update(data);
            }else{
                basicNursing.setId(UUIDUtil.getUUID());
                basicNursing.setCreateTime(new Date());
                if(user!=null){
                    basicNursing.setOperatorCode(user.getLoginName());
                }
                basicNursingService.insert(basicNursing);
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }catch (Exception e){
            logger.info("护理文书--护理单--基础护理--保存基础护理数据:"+e);
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--基础护理--删除整行
     * @return
     */
    @RequestMapping(value = "/deleteData")
    @ResponseBody
    public String deleteData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","删除失败");
        List<BasicNursing> list=basicNursingService.queryListByPatientIdAndTime(map);
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                BasicNursing basicNursing=list.get(i);
                basicNursing.setStatus(-1);
                basicNursingService.update(basicNursing);
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","删除成功");
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--基础护理--根据病人id查询基础护理列表
     * @return
     */
    @RequestMapping(value = "/getListByPatientId")
    @ResponseBody
    public String getListByPatientId(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        List<Map<String,Object>> list=basicNursingService.getListByPatientId(map);
        if(list!=null && list.size()>0){
            String msg="";
            for (int i = 0; i < list.size(); i++) {
                Map<String,Object> map1=list.get(i);
                //护理-气压治疗：airPressureTreatment
                if(StringUtils.isNotEmpty(map1.get("airPressureTreatment").toString())){
                    map.put("codes",map1.get("airPressureTreatment").toString());
                    map.put("type","air_pressure_treatment");
                    msg=commentItemService.getNamesByCodes(map);
                    map1.put("airPressureTreatment",msg);
                }
                //护理-皮肤护理：skinNursing
                if(StringUtils.isNotEmpty(map1.get("skinNursing").toString())){
                    map.put("codes",map1.get("skinNursing").toString());
                    map.put("type","skin_nursing");
                    msg=commentItemService.getNamesByCodes(map);
                    map1.put("skinNursing",msg);
                }
                //护理-基础护理：basicNursing
                if(StringUtils.isNotEmpty(map1.get("basicNursing").toString())){
                    map.put("codes",map1.get("basicNursing").toString());
                    map.put("type","basic_nursing");
                    msg=commentItemService.getNamesByCodes(map);
                    map1.put("basicNursing",msg);
                }
            }
            json.put("data",JSONArray.fromObject(list));
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }
        return json.toString();
    }

}
