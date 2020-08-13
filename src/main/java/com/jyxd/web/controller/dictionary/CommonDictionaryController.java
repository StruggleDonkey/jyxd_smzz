package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.CommenItemDictionary;
import com.jyxd.web.data.dictionary.CommonDictionary;
import com.jyxd.web.service.dictionary.CommentItemService;
import com.jyxd.web.service.dictionary.CommonDictionaryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/commonDictionary")
public class CommonDictionaryController {

    private static Logger logger= LoggerFactory.getLogger(CommonDictionaryController.class);

    @Autowired
    private CommonDictionaryService commonDictionaryService;

    @Autowired
    private CommentItemService commentItemService;

    /**
     * 增加一条通用字典类型表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) CommonDictionary commonDictionary){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        Map<String,Object> map=new HashMap<>();
        map.put("type",commonDictionary.getType());
        map.put("commonName",commonDictionary.getCommonName());
        CommonDictionary data=commonDictionaryService.queryDataByName(map);
        if(data != null){
            json.put("msg","添加失败,字典项名称或字典项类型已存在，请勿重复添加。");
        }else{
            commonDictionary.setId(UUIDUtil.getUUID());
            commonDictionaryService.insert(commonDictionary);
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","添加成功");
        }
        return json.toString();
    }

    /**
     * 更新通用字典类型表记录状态
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
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            if(commonDictionary!=null){
                commonDictionary.setStatus((int)map.get("status"));
                commonDictionaryService.update(commonDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑通用字典类型表记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map.containsKey("id") && map.containsKey("status") && map.containsKey("type") && map.containsKey("commonName")){
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            if(commonDictionary!=null){
                commonDictionary.setStatus((int)map.get("status"));
                commonDictionary.setType(map.get("type").toString());
                commonDictionary.setCommonName(map.get("commonName").toString());
                commonDictionaryService.update(commonDictionary);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除通用字典类型表记录
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
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            if(commonDictionary!=null){
                commonDictionary.setStatus(-1);
                commonDictionaryService.update(commonDictionary);
                //删除通用字典类型表 需要删除关联表 通用字典表
                map.put("type",commonDictionary.getType());
                List<CommenItemDictionary> list=commentItemService.queryList(map);
                if(list!=null && list.size()>0){
                    for(CommenItemDictionary commenItemDictionary:list){
                        commenItemDictionary.setStatus(-1);
                        commentItemService.update(commenItemDictionary);
                    }
                }
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询通用字典类型表记录
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
            CommonDictionary commonDictionary=commonDictionaryService.queryData(map.get("id").toString());
            if(commonDictionary!=null){
                json.put("data",JSONObject.fromObject(commonDictionary));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典类型记录列表（也可以不分页）
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
            int totalCount =commonDictionaryService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<CommonDictionary> list =commonDictionaryService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
