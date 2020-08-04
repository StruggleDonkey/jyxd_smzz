package com.jyxd.web.controller.userController;

import com.jyxd.web.data.user.Role;
import com.jyxd.web.service.userService.RoleService;
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
@RequestMapping(value = "/role")
public class RoleController {

    private static Logger logger= LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 增加一条角色表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody Role role){
        JSONObject json=new JSONObject();
        json.put("code",400);
        json.put("data",new ArrayList<>());
        role.setId(UUIDUtil.getUUID());
        roleService.insert(role);
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus((int)map.get("status"));
                roleService.update(role);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus((int)map.get("status"));
                roleService.update(role);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        if(map!=null && map.containsKey("id")){
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                role.setStatus(-1);
                roleService.update(role);
            }else{
                return json.toString();
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            Role role=roleService.queryData(map.get("id").toString());
            if(role!=null){
                json.put("data",JSONObject.fromObject(role));
            }
        }
        json.put("code",200);
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
        json.put("code",400);
        json.put("data",new ArrayList<>());
        List<Role> list =roleService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",200);
        return json.toString();
    }

}
