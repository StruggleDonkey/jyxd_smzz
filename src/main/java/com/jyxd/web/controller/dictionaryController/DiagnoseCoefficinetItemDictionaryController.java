package com.jyxd.web.controller.dictionaryController;

import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.dictionaryService.DiagnoseCoefficinetItemDictionaryService;
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
@RequestMapping(value = "/diagnoseCoefficinetItemDictionary")
public class DiagnoseCoefficinetItemDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(DiagnoseCoefficinetItemDictionaryController.class);

    @Autowired
    private DiagnoseCoefficinetItemDictionaryService diagnoseCoefficinetItemDictionaryService;

    /**
     * 新增一条ICU主要疾病诊断分类系数表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("msg","新增失败");
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        Map<String,Object> map=new HashMap<>();
        map.put("diagnoseCoefficientName",diagnoseCoefficinetItemDictionary.getDiagnoseCoefficientName());
        DiagnoseCoefficinetItemDictionary data=diagnoseCoefficinetItemDictionaryService.queryDataByName(map);
        if(data!=null){
            json.put("msg","模板名称已存在，请勿重复添加");
        }else{
            User user =(User) session.getAttribute("user");
            diagnoseCoefficinetItemDictionary.setId(UUIDUtil.getUUID());
            diagnoseCoefficinetItemDictionary.setCreateTime(new Date());
            if(user!=null){
                diagnoseCoefficinetItemDictionary.setOperatorCode(user.getLoginName());
            }
            diagnoseCoefficinetItemDictionary.setCreateTime(new Date());
            diagnoseCoefficinetItemDictionaryService.insert(diagnoseCoefficinetItemDictionary);
            json.put("msg","添加成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 更新一条ICU主要疾病诊断分类系数表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","更新失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficinetItemDictionary!=null){
                diagnoseCoefficinetItemDictionary.setStatus((int)map.get("status"));
                diagnoseCoefficinetItemDictionaryService.update(diagnoseCoefficinetItemDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑一条ICU主要疾病诊断分类系数表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","更新失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status") && map.containsKey("diagnoseCoefficientName") && map.containsKey("diagnoseCoefficientId") && map.containsKey("coefficientNum") && map.containsKey("sortNum")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficinetItemDictionary!=null){
                diagnoseCoefficinetItemDictionary.setStatus((int)map.get("status"));
                diagnoseCoefficinetItemDictionary.setCoefficientNum(map.get("coefficientNum").toString());
                diagnoseCoefficinetItemDictionary.setDiagnoseCoefficientId(map.get("diagnoseCoefficientId").toString());
                diagnoseCoefficinetItemDictionary.setDiagnoseCoefficientName(map.get("diagnoseCoefficientName").toString());
                diagnoseCoefficinetItemDictionary.setSortNum((int)map.get("sortNum"));
                diagnoseCoefficinetItemDictionaryService.update(diagnoseCoefficinetItemDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除一条ICU主要疾病诊断分类系数表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","删除失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficinetItemDictionary!=null){
                diagnoseCoefficinetItemDictionary.setStatus(-1);
                diagnoseCoefficinetItemDictionaryService.update(diagnoseCoefficinetItemDictionary);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询一条ICU主要疾病诊断分类系数表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map !=null && map.containsKey("id")){
            DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary=diagnoseCoefficinetItemDictionaryService.queryData(map.get("id").toString());
            if(diagnoseCoefficinetItemDictionary!=null){
                json.put("data",JSONObject.fromObject(diagnoseCoefficinetItemDictionary));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU主要疾病诊断分类系数表列表(可以不分页)
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        List<DiagnoseCoefficinetItemDictionary> list =diagnoseCoefficinetItemDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询ICU主要疾病诊断分类系数表列表(可以不分页)--多表查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        List<Map<String,Object>> list =diagnoseCoefficinetItemDictionaryService.getList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
