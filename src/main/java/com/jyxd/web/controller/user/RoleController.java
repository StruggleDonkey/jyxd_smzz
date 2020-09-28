package com.jyxd.web.controller.user;

import com.jyxd.web.data.user.Access;
import com.jyxd.web.data.user.Role;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.user.AccessService;
import com.jyxd.web.service.user.RoleService;
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

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    private static Logger logger= LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccessService accessService;

    /**
     * 增加一条角色表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody Map<String,Object> map, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        Role data=roleService.queryDataByName(map);
        if(data!=null){
            json.put("msg","角色名称已存在，请勿重复添加。");
            json.put("code",3);
        }else{
            if(map!=null && map.containsKey("userTypeCode") && map.containsKey("roleName") && map.containsKey("status") && map.containsKey("menuCodes")){
                User user=(User)session.getAttribute("user");
                Role role=new Role();
                role.setId(UUIDUtil.getUUID());
                role.setStatus((int)map.get("status"));
                role.setCreateTime(new Date());
                role.setRoleName(map.get("roleName").toString());
                role.setUserTypeCode(map.get("userTypeCode").toString());
                roleService.insert(role);
                JSONArray menuCodesArray=JSONArray.fromObject(map.get("menuCodes").toString());
                for(int i=0;i<menuCodesArray.size();i++){
                    JSONObject obj=(JSONObject) menuCodesArray.get(i);
                    Access access=new Access();
                    access.setId(UUIDUtil.getUUID());
                    access.setStatus(1);
                    access.setRoleId(role.getId());
                    access.setMenuCode(obj.getString("menuCode"));
                    access.setType(obj.getInt("type"));
                    if(user!=null){
                        access.setOperateCode(user.getLoginName());
                    }
                    accessService.insert(access);
                }
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","添加成功");
            }
        }
        return json.toString();
    }

    /**
     * 更新角色表状态
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
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus((int)map.get("status"));
                roleService.update(role);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","查无此条数据");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 编辑角色表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("roleName") && map.containsKey("userTypeCode") && map.containsKey("menuCodes")){
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus((int)map.get("status"));
                roleService.update(role);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","查无此条数据，编辑失败");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 删除角色表记录
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
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus(-1);
                roleService.update(role);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }else{
                json.put("msg","查无此条数据，删除失败");
                return json.toString();
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询角色表记录
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
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(role));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询角色表列表（也可以不分页）
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
            int totalCount =roleService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Role> list =roleService.queryList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            JSONArray array=JSONArray.fromObject(list,jsonConfig);
            Map<String,Object> accessMap=new HashMap<>();
            for (int i = 0; i <array.size() ; i++) {
                JSONObject obj=(JSONObject) array.get(i);
                accessMap.put("roleId",obj.getString("id"));
                List<Access> accessList=accessService.queryList(accessMap);
                if(accessList!=null && accessList.size()>0){
                    JSONArray accessArray=JSONArray.fromObject(accessList);
                    obj.put("menuCodes",accessArray);
                }
            }
            json.put("data",array);
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
