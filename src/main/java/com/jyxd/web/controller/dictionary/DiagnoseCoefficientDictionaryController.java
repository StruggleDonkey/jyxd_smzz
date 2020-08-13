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

}
