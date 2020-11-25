package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.CommenItemDictionary;
import com.jyxd.web.service.dictionary.CommentItemService;
import com.jyxd.web.service.dictionary.CommonDictionaryService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/commentItem")
public class CommentItemController {

    private static Logger logger= LoggerFactory.getLogger(CommentItemController.class);

    @Autowired
    private CommentItemService commentItemService;

    @Autowired
    private CommonDictionaryService commonDictionaryService;

    /**
     * 新增一条通用字典表数据
     * @param commenItemDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) CommenItemDictionary commenItemDictionary){
        JSONObject json=new JSONObject();
        json.put("data",new ArrayList<>());
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg","添加失败");
        commenItemDictionary.setId(UUIDUtil.getUUID());
        commentItemService.insert(commenItemDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新一条通用字典表数据状态
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
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus((int)map.get("status"));
                commentItemService.update(commenItemDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑一条通用字典表数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","编辑失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status") && map.containsKey("commonItemCode") && map.containsKey("commonItemName")
                && map.containsKey("type") && map.containsKey("description")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus((int)map.get("status"));
                commenItemDictionary.setType(map.get("type").toString());
                commenItemDictionary.setCommonItemName(map.get("commonItemName").toString());
                commenItemDictionary.setCommonItemCode(map.get("commonItemCode").toString());
                commenItemDictionary.setDescription(map.get("description").toString());
                commentItemService.update(commenItemDictionary);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除一条通用字典表数据
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
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus(-1);
                commentItemService.update(commenItemDictionary);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询通用字典表对象
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
        if(map.containsKey("id")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                json.put("data",JSONObject.fromObject(commenItemDictionary));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典表列表（可以不分页）
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
        List<CommenItemDictionary> list =commentItemService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典表列表（可以不分页）--多表查询
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
        if(map!=null && map.containsKey("start")){
            int totalCount =commentItemService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =commentItemService.getList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据名称获取code数组（适用于护理文书--护理单--获取出入量类型、护理文书--护理单模块内容）
     * @param map
     * @return
     */
    @RequestMapping(value = "/getCodeListByName",method= RequestMethod.POST)
    @ResponseBody
    public String getCodeListByName(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        String type=commonDictionaryService.queryTypeByName(map);
        if(StringUtils.isNotEmpty(type)){
            map.put("type",type);
            List<Map<String,Object>> list =commentItemService.getCodeListByType(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
