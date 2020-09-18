package com.jyxd.web.controller.user;

import com.jyxd.web.data.user.User;
import com.jyxd.web.service.user.UserService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.MD5Util;
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
import java.util.ArrayList;
import java.util.Date;
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
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        user.setId(UUIDUtil.getUUID());
        userService.insert(user);
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus((int)map.get("status"));
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus((int)map.get("status"));
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map!=null && map.containsKey("id")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus(-1);
                userService.update(user);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                json.put("data",JSONObject.fromObject(user));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map!=null && map.containsKey("start")){
            int totalCount =userService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<User> list =userService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
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
        json.put("code",HttpCode.LOGIN_FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","账号密码有误，请重新输入！");
        if(map!=null && map.containsKey("password")){
            map.put("password", MD5Util.getMD5String(map.get("password").toString()));
            User user=userService.queryUserByNameAndPassword(map);
            if(user!=null){
                JsonConfig jsonConfig=new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
                session.setAttribute("user",user);
                json.put("user",JSONObject.fromObject(user,jsonConfig));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","登录成功");
            }
        }
        return json.toString();
    }

    /**
     * 用户退出登录接口
     * @param
     * @return
     */
    @RequestMapping(value = "/logout",method= RequestMethod.POST)
    @ResponseBody
    public String logout(HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","退出登录失败！");
        session.invalidate();
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","退出登录成功");
        return json.toString();
    }

    /**
     * 床位信息--新增病人--根据条件查询医生或护士员工列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getUserListByType",method= RequestMethod.POST)
    @ResponseBody
    public String getUserListByType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=userService.getUserListByType(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","查询成功");
        return json.toString();
    }

    /**
     * 用户登录成功后获取登录信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/getUser",method= RequestMethod.POST)
    @ResponseBody
    public String getUser(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.LOGIN_FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","获取失败！");
        User user=(User) session.getAttribute("user");
        if(user!=null){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
            json.put("user",JSONObject.fromObject(user,jsonConfig));
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","获取成功");
        }
        return json.toString();
    }

    /**
     * 解锁 锁屏
     * @param map
     * @return
     */
    @RequestMapping(value = "/unlock",method= RequestMethod.POST)
    @ResponseBody
    public String unlock(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.LOGIN_FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","解锁失败！");
        User user=(User) session.getAttribute("user");
        if(user!=null && user.getPassword().equals(MD5Util.getMD5String(map.get("password").toString()))){
            String url=(String)session.getAttribute("url");
            json.put("url",url);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","解锁成功");
        }
        return json.toString();
    }

    /**
     * 锁屏
     * @param map
     * @return
     */
    @RequestMapping(value = "/lock",method= RequestMethod.POST)
    @ResponseBody
    public String lock(@RequestBody(required=false) Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.LOGIN_FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","锁屏失败！");
        if(StringUtils.isNotEmpty(map.get("url").toString())){
            session.setAttribute("url",map.get("url").toString());
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","锁屏成功");
        }
        return json.toString();
    }
}
