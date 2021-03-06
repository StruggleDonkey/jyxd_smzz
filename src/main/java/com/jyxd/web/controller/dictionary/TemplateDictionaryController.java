package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.TemplateDictionary;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.dictionary.TemplateDictionaryService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/templateDictionary")
public class TemplateDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(TemplateDictionaryController.class);

    @Autowired
    private TemplateDictionaryService templateDictionaryService;

    /**
     * 增加一条护理模板类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody TemplateDictionary templateDictionary, HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        templateDictionary.setId(UUIDUtil.getUUID());
        templateDictionary.setCreateTime(new Date());
        User user=(User)session.getAttribute("user");
        if(user!=null){
            templateDictionary.setOperatorCode(user.getLoginName());
        }
        templateDictionaryService.insert(templateDictionary);
        json.put("msg","添加成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 更新护理模板类型表记录状态
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
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus((int)map.get("status"));
                templateDictionaryService.update(templateDictionary);
                json.put("msg","修改成功");
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑护理模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map,HttpSession session){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("dicTemplateName") && map.containsKey("sortNum")){
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus((int)map.get("status"));
                templateDictionary.setDicTemplateName(map.get("dicTemplateName").toString());
                templateDictionary.setSortNum((int)map.get("sortNum"));
                User user=(User)session.getAttribute("user");
                if(user!=null){
                    templateDictionary.setOperatorCode(user.getLoginName());
                }
                templateDictionaryService.update(templateDictionary);
                json.put("msg","编辑成功");
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 删除护理模板类型表记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id")){
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                templateDictionary.setStatus(-1);
                templateDictionaryService.update(templateDictionary);
            }else{
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询护理模板类型表记录
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
            TemplateDictionary templateDictionary=templateDictionaryService.queryData(map.get("id").toString());
            if(templateDictionary!=null){
                json.put("data",JSONObject.fromObject(templateDictionary));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询护理模板类型表记录列表（也可以不分页）
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
            int totalCount =templateDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<TemplateDictionary> list =templateDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
