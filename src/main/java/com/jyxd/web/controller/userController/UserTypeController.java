package com.jyxd.web.controller.userController;

import com.jyxd.web.data.user.UserType;
import com.jyxd.web.service.userService.UserTypeService;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        json.put("code",400);
        json.put("data",new ArrayList<>());
        userType.setId(UUIDUtil.getUUID());
        userTypeService.insert(userType);
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus((int)map.get("status"));
                userTypeService.update(userType);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus((int)map.get("status"));
                userTypeService.update(userType);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id")){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                userType.setStatus(-1);
                userTypeService.update(userType);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            UserType userType=userTypeService.queryData(map.get("id").toString());
            if(userType!=null){
                json.put("data",JSONObject.fromObject(userType));
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<UserType> list =userTypeService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
