package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.InputAllowance;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.InputAllowanceService;
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
@RequestMapping(value = "/inputAllowance")
public class InputAllowanceController {

    private static Logger logger= LoggerFactory.getLogger(InputAllowanceController.class);

    @Autowired
    private InputAllowanceService inputAllowanceService;

    /**
     * 增加一条基础护理表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InputAllowance inputAllowance){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        inputAllowance.setId(UUIDUtil.getUUID());
        inputAllowance.setCreateTime(new Date());
        inputAllowanceService.insert(inputAllowance);
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
            InputAllowance inputAllowance=inputAllowanceService.queryData(map.get("id").toString());
            if(inputAllowance!=null){
                inputAllowance.setStatus((int)map.get("status"));
                inputAllowanceService.update(inputAllowance);
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
            InputAllowance inputAllowance=inputAllowanceService.queryData(map.get("id").toString());
            if(inputAllowance!=null){
                inputAllowance.setStatus((int)map.get("status"));
                inputAllowanceService.update(inputAllowance);
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
            InputAllowance inputAllowance=inputAllowanceService.queryData(map.get("id").toString());
            if(inputAllowance!=null){
                inputAllowance.setStatus(-1);
                inputAllowanceService.update(inputAllowance);
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
            InputAllowance inputAllowance=inputAllowanceService.queryData(map.get("id").toString());
            if(inputAllowance!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(inputAllowance));
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
            int totalCount =inputAllowanceService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<InputAllowance> list =inputAllowanceService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--录入余量--保存
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveData",method= RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        try {
            if(map!=null && map.containsKey("list") && StringUtils.isNotEmpty(map.get("list").toString())){
                List<InputAllowance> list=inputAllowanceService.queryListByOrderNo(map);
                if(list!=null && list.size()>0){
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setStatus(-1);
                        inputAllowanceService.update(list.get(i));
                    }
                }
                JSONArray array=JSONArray.fromObject(map.get("list").toString());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i <array.size() ; i++) {
                    JSONObject obj=(JSONObject) array.get(i);
                    InputAllowance inputAllowance=newInputAllowance(map,session);
                    inputAllowance.setShift(obj.getInt("shift"));
                    inputAllowance.setDosage(obj.getString("dosage"));
                    inputAllowance.setAllowanceDosage(obj.getString("allowanceDosage"));
                    inputAllowance.setDataTime(format.parse(obj.getString("dataTime")));
                    inputAllowance.setStatus(obj.getInt("status"));
                    inputAllowanceService.insert(inputAllowance);
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","成功");
            }
        }catch (Exception e){
            logger.info("护理文书--护理单--入量--录入余量--保存:"+e);
        }
        return json.toString();
    }

    private InputAllowance newInputAllowance(Map<String,Object> map, HttpSession session){
        InputAllowance inputAllowance=new InputAllowance();
        try {
            inputAllowance.setCreateTime(new Date());
            inputAllowance.setId(UUIDUtil.getUUID());
            inputAllowance.setVisitId(map.get("visitId").toString());
            inputAllowance.setVisitCode(map.get("visitCode").toString());
            inputAllowance.setPatientId(map.get("patientId").toString());
            User user=(User)session.getAttribute("user");
            if(user!=null){
                inputAllowance.setOperatorCode(user.getLoginName());
            }
            inputAllowance.setOrderNo(map.get("orderNo").toString());
            inputAllowance.setOrderType(map.get("orderType").toString());
        }catch (Exception e){
            logger.info("newInputAllowance:"+e);
        }
        return inputAllowance;
    }

}
