package com.jyxd.web.controller.user;

import com.jyxd.web.data.user.UserType;
import com.jyxd.web.service.user.UserTypeService;
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

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(value = "/userType")
public class UserTypeController {

    private static Logger logger= LoggerFactory.getLogger(UserTypeController.class);

    @Autowired
    private UserTypeService userTypeService;

    /**
     * 增加一条平台用户类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody UserType userType){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        Map<String,Object> map=new HashMap<>();
        map.put("userTypeCode",userType.getUserTypeCode());
        map.put("userTypeName",userType.getUserTypeName());
        UserType data=userTypeService.queryDataByCode(map);
        if(data!=null){
            json.put("code",3);
            json.put("msg","用户类型名称或用户类型编码已存在，请重新输入。");
        }else{
            userType.setId(UUIDUtil.getUUID());
            userType.setCreateTime(new Date());
            userTypeService.insert(userType);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }
        System.out.println(userType.toString());
        return json.toString();
    }

    /**
     * 更新平台用户类型表状态
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
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus((int)map.get("status"));
                userTypeService.update(userType);
                json.put("msg","更新成功");
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑平台用户类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("userTypeName")){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus((int)map.get("status"));
                userType.setUserTypeName(map.get("userTypeName").toString());
                userTypeService.update(userType);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 删除平台用户类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","删除失败");
        if(map!=null && map.containsKey("id")){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus(-1);
                userTypeService.update(userType);
                json.put("msg","编辑成功");
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询平台用户类型表记录
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
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                json.put("data",JSONObject.fromObject(userType));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询平台用户类型表列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =userTypeService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<UserType> list =userTypeService.queryList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        System.out.println(json);
        return json.toString();
    }

}
