package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.service.dictionary.DepartmentDictionaryService;
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

import java.util.ArrayList;
import java.util.HashMap;
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
        json.put("msg","新增失败");
        Map<String,Object> map=new HashMap<>();
        map.put("departmentCode",departmentDictionary.getDepartmentCode());
        if(departmentDictionaryService.queryDataByCode(map)==null){
            departmentDictionary.setId(UUIDUtil.getUUID());
            departmentDictionaryService.insert(departmentDictionary);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","新增成功");
        }else{
            json.put("code",HttpCode.EXISTING_CODE.getCode());
            json.put("msg","新增失败，科室编码已存在请重新输入");
        }
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

    /**
     * 从his系统获取并更新科室字典数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateDepartmentByHis",method= RequestMethod.POST)
    @ResponseBody
    public String updateDepartmentByHis(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");

        //查询本地数据库字典数据
        List<DepartmentDictionary> list=departmentDictionaryService.queryDepartmentList(map);
        //从his数据库视图中查询科室字典数据
        List<Map<String,Object>> hisList=departmentDictionaryService.getDepartmentByHis(map);
        if(hisList!=null && hisList.size()>0){
            if(list!=null && list.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新

                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list.size(); i++) {
                    arrayList.add(list.get(i).getDepartmentCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList.size(); i++) {
                    arrayHisList.add(hisList.get(i).get("department_code").toString());
                }

                for (int i = 0; i <list.size(); i++) {
                   if(!arrayHisList.contains(list.get(i).getDepartmentCode())){
                    //如果his系统中数据 里 没有本地数据 则删除本地数据
                       list.get(i).setStatus(-1);
                       departmentDictionaryService.update(list.get(i));
                   }
                }

                for (int i = 0; i <hisList.size(); i++) {
                    if(!arrayList.contains(hisList.get(i).get("department_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        DepartmentDictionary departmentDictionary=new DepartmentDictionary();
                        departmentDictionary.setStatus(1);
                        departmentDictionary.setId(UUIDUtil.getUUID());
                        departmentDictionary.setDepartmentName(hisList.get(i).get("department_name").toString());
                        departmentDictionary.setDepartmentCode(hisList.get(i).get("department_code").toString());
                        departmentDictionaryService.insert(departmentDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList.size(); i++) {
                    DepartmentDictionary departmentDictionary=new DepartmentDictionary();
                    departmentDictionary.setStatus(1);
                    departmentDictionary.setId(UUIDUtil.getUUID());
                    if(StringUtils.isNotEmpty(hisList.get(i).get("department_code").toString())){
                        departmentDictionary.setDepartmentCode(hisList.get(i).get("department_code").toString());
                    }
                    if(StringUtils.isNotEmpty(hisList.get(i).get("department_name").toString())){
                        departmentDictionary.setDepartmentName(hisList.get(i).get("department_name").toString());
                    }
                    departmentDictionaryService.insert(departmentDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }
        return json.toString();
    }

}
