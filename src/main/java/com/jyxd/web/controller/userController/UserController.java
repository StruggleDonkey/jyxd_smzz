package com.jyxd.web.controller.userController;

import com.jyxd.web.data.user.User;
import com.jyxd.web.service.userService.UserService;
import com.jyxd.web.util.MD5Util;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 增加一条平台用户表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody User user){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        user.setId(UUIDUtil.getUUID());
        userService.insert(user);
        json.put("code",200);
        return json.toString();
    }

    /**
     * 更新平台用户表状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus((int)map.get("status"));
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 编辑平台用户表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus((int)map.get("status"));
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 删除平台用户表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        if(map!=null && map.containsKey("id")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus(-1);
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据主键id查询平台用户表记录
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
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                json.put("data",JSONObject.fromObject(user));
            }
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 根据条件分页查询平台用户表列表（也可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<User> list =userService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

    /**
     * 用户登录接口
     * @param map
     * @return
     */
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",2);
        json.put("data",new ArrayList<>());
        json.put("msg","账号密码有误，请重新输入！");
        if(map!=null && map.containsKey("password")){
            map.put("password", MD5Util.getMD5String(map.get("password").toString()));
            User user=userService.queryUserByNameAndPassword(map);
            if(user!=null){
                session.setAttribute("user",user);
                json.put("code",200);
                json.put("msg","登录成功");
            }
        }
        return json.toString();
    }

}
