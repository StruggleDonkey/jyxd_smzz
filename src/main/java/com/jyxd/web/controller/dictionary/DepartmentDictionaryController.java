package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.service.dictionary.DepartmentDictionaryService;
import com.jyxd.web.util.HttpCode;
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
@RequestMapping(value = "/departmentDictionary")
public class DepartmentDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(DepartmentDictionaryController.class);

    @Autowired
    private DepartmentDictionaryService departmentDictionaryService;

    /**
     * 新增一条科室字典记录
     * @param departmentDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody DepartmentDictionary departmentDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        departmentDictionary.setId(UUIDUtil.getUUID());
        departmentDictionaryService.insert(departmentDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 更新一条科室字典记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("status",false);
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map != null && map.containsKey("id") && map.containsKey("status")){
            DepartmentDictionary departmentDictionary=departmentDictionaryService.queryData(map.get("id").toString());
            if(departmentDictionary!=null){
                departmentDictionary.setStatus((int)map.get("status"));
                departmentDictionaryService.update(departmentDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑科室字典记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("departmentName")){
            DepartmentDictionary departmentDictionary=departmentDictionaryService.queryData(map.get("id").toString());
            if(departmentDictionary!=null){
                departmentDictionary.setStatus((int)map.get("status"));
                departmentDictionary.setDepartmentName(map.get("departmentName").toString());
                departmentDictionaryService.update(departmentDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 删除科室字典记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id")){
            DepartmentDictionary departmentDictionary=departmentDictionaryService.queryData(map.get("id").toString());
            if(departmentDictionary!=null){
                departmentDictionary.setStatus(-1);
                departmentDictionaryService.update(departmentDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询一条科室字典记录
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
            DepartmentDictionary departmentDictionary=departmentDictionaryService.queryData(map.get("id").toString());
            if(departmentDictionary!=null){
                json.put("data",JSONObject.fromObject(departmentDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件查询科室字典记录列表（可分页）
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
            int totalCount =departmentDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<DepartmentDictionary> list =departmentDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
