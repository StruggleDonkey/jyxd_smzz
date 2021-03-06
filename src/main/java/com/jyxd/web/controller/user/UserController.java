package com.jyxd.web.controller.user;

import com.jyxd.web.data.basic.CommonSetting;
import com.jyxd.web.data.user.Access;
import com.jyxd.web.data.user.Role;
import com.jyxd.web.data.user.User;
import com.jyxd.web.data.user.UserType;
import com.jyxd.web.service.basic.CommonSettingService;
import com.jyxd.web.service.user.AccessService;
import com.jyxd.web.service.user.RoleService;
import com.jyxd.web.service.user.UserService;
import com.jyxd.web.service.user.UserTypeService;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccessService accessService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommonSettingService commonSettingService;

    @Autowired
    private UserTypeService userTypeService;

    /**
     * 增加一条平台用户表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","新增失败");
        try {
            if(map!=null && map.containsKey("status") && userService.queryDataByWorkNumber(map)==null){
                User user=new User();
                user.setId(UUIDUtil.getUUID());
                user.setPassword("e10adc3949ba59abbe56e057f20f883e");//密码默认123456
                user.setCreateTime(new Date());
                user.setStatus((int)map.get("status"));
                user.setWorkNumber(map.get("workNumber").toString());
                user.setLoginName(map.get("loginName").toString());
                user.setRoleId(map.get("roleId").toString());
                user.setSex((int)map.get("sex"));
                user.setUserName(map.get("userName").toString());
                user.setIsShedual((int)map.get("isShedual"));
                if(StringUtils.isNotEmpty(map.get("simplicity").toString())){
                    user.setSimplicity(map.get("simplicity").toString());
                }
                if(StringUtils.isNotEmpty(map.get("userTypeCode").toString())){
                    user.setUserTypeCode(map.get("userTypeCode").toString());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(StringUtils.isNotEmpty(map.get("enterTime").toString())){
                    user.setEnterTime(sdf.parse(map.get("enterTime").toString()));
                }
                if(StringUtils.isNotEmpty(map.get("exitTime").toString())){
                    user.setExitTime(sdf.parse(map.get("exitTime").toString()));
                }
                userService.insert(user);
                json.put("msg","新增成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            logger.info("增加一条平台用户表记录:"+e);
        }
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
        json.put("msg","修改失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus((int)map.get("status"));
                userService.update(user);
                json.put("msg","修改成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                return json.toString();
            }
        }
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
        json.put("msg","编辑失败");
        try {
            if(map!=null && map.containsKey("id") && map.containsKey("status") ){
                User user=userService.queryData(map.get("id").toString());
                if(user!=null){
                    user.setStatus((int)map.get("status"));
                    user.setWorkNumber(map.get("workNumber").toString());
                    user.setLoginName(map.get("loginName").toString());
                    user.setRoleId(map.get("roleId").toString());
                    user.setSex((int)map.get("sex"));
                    user.setUserName(map.get("userName").toString());
                    user.setIsShedual((int)map.get("isShedual"));
                    if(StringUtils.isNotEmpty(map.get("simplicity").toString())){
                        user.setSimplicity(map.get("simplicity").toString());
                    }
                    if(StringUtils.isNotEmpty(map.get("userTypeCode").toString())){
                        user.setUserTypeCode(map.get("userTypeCode").toString());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(StringUtils.isNotEmpty(map.get("enterTime").toString())){
                        user.setEnterTime(sdf.parse(map.get("enterTime").toString()));
                    }
                    if(StringUtils.isNotEmpty(map.get("exitTime").toString())){
                        user.setExitTime(sdf.parse(map.get("exitTime").toString()));
                    }
                    userService.update(user);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }else{
                    return json.toString();
                }
            }
        }catch (Exception e){
            logger.info("编辑平台用户表记录:"+e);
        }
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
        json.put("msg","删除失败");
        if(map!=null && map.containsKey("id")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                user.setStatus(-1);
                userService.update(user);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","删除成功");
            }else{
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 重置用户密码为123456
     * @param map
     * @return
     */
    @RequestMapping(value = "/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","重置失败");
        if(map!=null && map.containsKey("id")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
               user.setPassword("e10adc3949ba59abbe56e057f20f883e");//密码123456
                userService.update(user);
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","重置成功");
            }else{
                return json.toString();
            }
        }
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
     * 根据条件分页查询平台用户表列表（也可以不分页）--多表查询
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
            int totalCount =userService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =userService.getList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
            json.put("msg","查询成功");
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
            map.put("password", MD5Util.string2MD5(map.get("password").toString()));
            User user=userService.queryUserByNameAndPassword(map);
            if(user!=null){
                JsonConfig jsonConfig=new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class,new JsonArrayValueProcessor());
                session.setAttribute("user",user);
                Map<String,Object> hashMap=new HashMap<>();
                hashMap.put("roleId",user.getRoleId());
                List<Access> list=accessService.getList(hashMap);
                JSONArray jsonArray=new JSONArray();
                if(list!=null && list.size()>0){
                    for (int i = 0; i < list.size(); i++) {
                        jsonArray.add(list.get(i).getMenuCode());
                    }
                }
                json.put("menu",jsonArray);
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
            JSONObject jsonObject=JSONObject.fromObject(user,jsonConfig);
            json.put("password",MD5Util.convertMD5(user.getPassword()));
            Map<String,Object> hashMap=new HashMap<>();
            hashMap.put("roleId",user.getRoleId());
            List<Access> list=accessService.getList(hashMap);
            JSONArray jsonArray=new JSONArray();
            if(list!=null && list.size()>0){
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.add(list.get(i).getMenuCode());
                }
            }
            jsonObject.put("menu",jsonArray);
            Role role=roleService.queryData(user.getRoleId());
            if(role!=null){
                JSONArray jsonArray1=new JSONArray();
                jsonArray1.add(role.getRoleName());
                jsonObject.put("roles",jsonArray1);
            }
            //查询默认首页
            Map<String,Object> map1=new HashMap<>();
            map1.put("settingType","默认首页");
            CommonSetting commonSetting=commonSettingService.getCommonSettingByType(map1);
            if(commonSetting!=null){
                json.put("homePage",commonSetting.getSettingContent());
            }else{
                json.put("homePage","工作站");//默认为工作站
            }
            json.put("data",jsonObject);
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

    /**
     * 修改密码
     * @param map
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","修改失败");
        if(map!=null && map.containsKey("id") && map.containsKey("password") && map.containsKey("newPassword")){
            User user=userService.queryData(map.get("id").toString());
            if(user!=null){
                if(user.getPassword().equals(MD5Util.string2MD5(map.get("password").toString()))){
                    user.setPassword(MD5Util.string2MD5(map.get("newPassword").toString()));
                    userService.update(user);
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","修改成功");
                }else{
                    json.put("code",HttpCode.PASSWORD_ERROR_CODE.getCode());
                    json.put("msg","原密码错误，请重新输入原密码！");
                }
            }
        }
        return json.toString();
    }

    /**
     * 根据用户类型查询用户列表
     * @param map userTypeName
     * @return
     */
    @RequestMapping(value = "/getUserListByUserType")
    @ResponseBody
    public String getUserListByUserType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","修改失败");
        if(map!=null){
          UserType userType =userTypeService.queryDataByName(map);
          if(userType!=null){
              map.put("userTypeCode",userType.getUserTypeCode());
          }
        }
        List<Map<String,Object>> list=userService.getList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }
        return json.toString();
    }

    /**
     * 从his系统获取并更新用户数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateUserByHis",method= RequestMethod.POST)
    @ResponseBody
    public String updateUserByHis(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");
        try {
            //查询本地数据库字典数据
            List<User> list=userService.queryUserList(map);
            //从his数据库视图中查询科室字典数据
            List<Map<String,Object>> hisList=userService.getUserByHis(map);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(hisList!=null && hisList.size()>0){
                if(list!=null && list.size()>0){
                    //如果数据库有数据则需要和his中获取的数据做比较再更新

                    ArrayList arrayList= new ArrayList();
                    for (int i = 0; i <list.size(); i++) {
                        arrayList.add(list.get(i).getWorkNumber());//工号唯一性
                    }

                    ArrayList arrayHisList= new ArrayList();
                    for (int i = 0; i < hisList.size(); i++) {
                        arrayHisList.add(hisList.get(i).get("user_id").toString());//工号唯一性
                    }

                    for (int i = 0; i <list.size(); i++) {
                        if(!arrayHisList.contains(list.get(i).getWorkNumber())){
                            //如果his系统中数据 里 没有本地数据 则删除本地数据
                            list.get(i).setStatus(-1);
                            userService.update(list.get(i));
                        }
                    }

                    for (int i = 0; i <hisList.size(); i++) {
                        if(!arrayList.contains(hisList.get(i).get("user_id").toString())){
                            //如果本地数据不包含 his系统数据 则新增数据
                            User user=new User();
                            user.setStatus(1);
                            user.setId(UUIDUtil.getUUID());
                            user.setUserName(hisList.get(i).get("user_name").toString());
                            user.setSimplicity(hisList.get(i).get("user_name").toString());
                            user.setLoginName(hisList.get(i).get("user_name").toString());
                            user.setPassword(hisList.get(i).get("password").toString());
                            user.setCreateTime(new Date());
                            user.setIsShedual(1);//是否参与排班（0：不参与 1：参与）
                            user.setUserTypeCode(hisList.get(i).get("user_type_code").toString());
                            user.setWorkNumber(hisList.get(i).get("user_id").toString());
                            user.setSex((int)hisList.get(i).get("sex"));
                            if(StringUtils.isNotEmpty(hisList.get(i).get("enter_time").toString())){
                                user.setEnterTime(format.parse(hisList.get(i).get("enter_time").toString()));
                            }
                            if(StringUtils.isNotEmpty(hisList.get(i).get("exit_time").toString())){
                                user.setExitTime(format.parse(hisList.get(i).get("exit_time").toString()));
                            }
                            Map<String,Object> map1=new HashMap<>();
                            map1.put("userTypeCode",hisList.get(i).get("user_type_code").toString());
                            Role role=roleService.queryDataByTypeCode(map1);
                            if(role!=null){
                                user.setRoleId(role.getId());
                            }
                            userService.insert(user);
                        }
                    }
                }else{
                    //直接将his获取的数据添加到本地数据库
                    for (int i = 0; i < hisList.size(); i++) {
                        User user=new User();
                        user.setStatus(1);
                        user.setId(UUIDUtil.getUUID());
                        user.setUserName(hisList.get(i).get("user_name").toString());
                        user.setSimplicity(hisList.get(i).get("user_name").toString());
                        user.setLoginName(hisList.get(i).get("user_name").toString());
                        user.setPassword(hisList.get(i).get("password").toString());
                        user.setCreateTime(new Date());
                        user.setIsShedual(1);//是否参与排班（0：不参与 1：参与）
                        user.setUserTypeCode(hisList.get(i).get("user_type_code").toString());
                        user.setWorkNumber(hisList.get(i).get("user_id").toString());
                        user.setSex((int)hisList.get(i).get("sex"));
                        if(StringUtils.isNotEmpty(hisList.get(i).get("enter_time").toString())){
                            user.setEnterTime(format.parse(hisList.get(i).get("enter_time").toString()));
                        }
                        if(StringUtils.isNotEmpty(hisList.get(i).get("exit_time").toString())){
                            user.setExitTime(format.parse(hisList.get(i).get("exit_time").toString()));
                        }
                        Map<String,Object> map1=new HashMap<>();
                        map1.put("userTypeCode",hisList.get(i).get("user_type_code").toString());
                        Role role=roleService.queryDataByTypeCode(map1);
                        if(role!=null){
                            user.setRoleId(role.getId());
                        }
                        userService.insert(user);
                    }
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","成功");
            }
        }catch (Exception e){
            logger.info("updateUserByHis:"+e);
        }
        return json.toString();
    }

}
