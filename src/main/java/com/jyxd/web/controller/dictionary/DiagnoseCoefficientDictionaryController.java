package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.DiagnoseCoefficientDictionary;
import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.dictionary.DiagnoseCoefficientDictionaryService;
import com.jyxd.web.service.dictionary.DiagnoseCoefficinetItemDictionaryService;
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

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/diagnoseCoefficientDictionary")
public class DiagnoseCoefficientDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(DiagnoseCoefficientDictionaryController.class);

    @Autowired
    private DiagnoseCoefficientDictionaryService diagnoseCoefficientDictionaryService;

    @Autowired
    private DiagnoseCoefficinetItemDictionaryService diagnoseCoefficinetItemDictionaryService;

    /**
     * 增加一条ICU主要疾病诊断类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody DiagnoseCoefficientDictionary diagnoseCoefficientDictionary, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","新增失败");
        Map<String,Object> map=new HashMap<>();
        map.put("diagnoseTypeName",diagnoseCoefficientDictionary.getDiagnoseTypeName());
        map.put("operation",diagnoseCoefficientDictionary.getOperation());
        DiagnoseCoefficientDictionary data=diagnoseCoefficientDictionaryService.queryDataByName(map);
        if(data!=null){
            json.put("msg","类型名称已存在，请勿重复添加");
        }else{
            User user=(User)session.getAttribute("user");
            if(user!=null){
                diagnoseCoefficientDictionary.setOperatorCode(user.getLoginName());
            }
            diagnoseCoefficientDictionary.setId(UUIDUtil.getUUID());
            diagnoseCoefficientDictionary.setCreateTime(new Date());
            diagnoseCoefficientDictionaryService.insert(diagnoseCoefficientDictionary);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","新增成功");
        }
        return json.toString();
    }

    /**
     * 更新ICU主要疾病诊断类型表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        if(map.containsKey("id") && map.containsKey("status")){
            DiagnoseCoefficientDictionary data=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            if(data!=null){
                DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
                diagnoseCoefficientDictionary.setStatus((int)map.get("status"));
                diagnoseCoefficientDictionaryService.update(diagnoseCoefficientDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑ICU主要疾病诊断类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map.containsKey("id") && map.containsKey("status") && map.containsKey("operation") && map.containsKey("diagnoseTypeName") && map.containsKey("sortNum")){
            DiagnoseCoefficientDictionary data=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            if(data!=null){
                DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
                diagnoseCoefficientDictionary.setStatus((int)map.get("status"));
                diagnoseCoefficientDictionary.setDiagnoseTypeName(map.get("diagnoseTypeName").toString());
                diagnoseCoefficientDictionary.setOperation((int)map.get("operation"));
                diagnoseCoefficientDictionary.setSortNum((int)map.get("sortNum"));
                diagnoseCoefficientDictionaryService.update(diagnoseCoefficientDictionary);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除ICU主要疾病诊断类型表记录
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
            DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficientDictionary!=null){
                diagnoseCoefficientDictionary.setStatus(-1);
                diagnoseCoefficientDictionaryService.update(diagnoseCoefficientDictionary);
                //删除ICU主要疾病诊断类型表记录 需要删除关联表  ICU主要疾病诊断分类系数表
                map.put("diagnoseCoefficientId",diagnoseCoefficientDictionary.getId());
                List<DiagnoseCoefficinetItemDictionary> list=diagnoseCoefficinetItemDictionaryService.queryList(map);
                if(list != null && list.size()>0){
                    for(DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary:list){
                        diagnoseCoefficientDictionary.setStatus(-1);
                        diagnoseCoefficinetItemDictionaryService.update(diagnoseCoefficinetItemDictionary);
                    }
                }
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询ICU主要疾病诊断类型表记录
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
            DiagnoseCoefficientDictionary diagnoseCoefficientDictionary=diagnoseCoefficientDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficientDictionary!=null){
                json.put("data",JSONObject.fromObject(diagnoseCoefficientDictionary));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU主要疾病诊断类型表列表（也可以不分页）
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
            int totalCount =diagnoseCoefficientDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<DiagnoseCoefficientDictionary> list =diagnoseCoefficientDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 返回新增病人中选择诊断分类结果
     * @return
     */
    @RequestMapping(value = "/getDRG",method= RequestMethod.POST)
    @ResponseBody
    public String getDRG(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        Map<String,Object> map=new HashMap<>();
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        JSONObject obj2=new JSONObject();
        obj1.put("value","手术");
        obj1.put("label","手术");
        obj2.put("value","非手术");
        obj2.put("label","非手术");
        JSONArray array2=new JSONArray();
        JSONArray array4=new JSONArray();
        //手术
        map.put("operation",1);//是否手术（0：否 1：是）
        List<DiagnoseCoefficientDictionary> list=diagnoseCoefficientDictionaryService.getList(map);
        if(list!=null && list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                JSONObject obj4 =new JSONObject();
                obj4.put("value",list.get(i).getId());
                obj4.put("label",list.get(i).getDiagnoseTypeName());
                JSONArray array1=new JSONArray();
                map.put("diagnoseCoefficientId",list.get(i).getId());
                List<Map<String,Object>> list1=diagnoseCoefficinetItemDictionaryService.getList(map);
                if(list1!=null && list1.size()>0){
                    for (int j = 0; j < list1.size(); j++) {
                        JSONObject obj3=new JSONObject();
                        obj3.put("value",list1.get(j).get("id").toString());
                        obj3.put("label",list1.get(j).get("diagnose_coefficient_name").toString());
                        array1.add(obj3);
                    }
                }
                obj4.put("children",array1);
                array2.add(obj4);
            }
        }
        obj1.put("children",array2);
        //非手术
        map.put("operation",0);//是否手术（0：否 1：是）
        List<DiagnoseCoefficientDictionary> list2=diagnoseCoefficientDictionaryService.getList(map);
        if(list2!=null && list2.size()>0){
            for (int i = 0; i <list2.size() ; i++) {
                JSONObject obj5 =new JSONObject();
                obj5.put("value",list2.get(i).getId());
                obj5.put("label",list2.get(i).getDiagnoseTypeName());
                JSONArray array3=new JSONArray();
                map.put("diagnoseCoefficientId",list2.get(i).getId());
                List<Map<String,Object>> list3=diagnoseCoefficinetItemDictionaryService.getList(map);
                if(list3!=null && list3.size()>0){
                    for (int j = 0; j < list3.size(); j++) {
                        JSONObject obj6=new JSONObject();
                        obj6.put("value",list3.get(j).get("id").toString());
                        obj6.put("label",list3.get(j).get("diagnose_coefficient_name").toString());
                        array3.add(obj6);
                    }
                }
                obj5.put("children",array3);
                array4.add(obj5);
            }
        }
        obj2.put("children",array4);
        array.add(obj1);
        array.add(obj2);
        json.put("data",array);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }
}
