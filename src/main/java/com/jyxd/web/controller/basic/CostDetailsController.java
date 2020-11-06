package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.CostDetails;
import com.jyxd.web.service.basic.CostDetailsService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/costDetails")
public class CostDetailsController {

    private static Logger logger= LoggerFactory.getLogger(CostDetailsController.class);

    @Autowired
    private CostDetailsService costDetailsService;

    /**
     * 增加一条费用明细表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody CostDetails costDetails){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        costDetails.setId(UUIDUtil.getUUID());
        costDetails.setCreateTime(new Date());
        costDetailsService.insert(costDetails);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新费用明细表记录状态
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
            CostDetails costDetails=costDetailsService.queryData(map.get("id").toString());
            if(costDetails!=null){
                costDetailsService.update(costDetails);
                json.put("msg","更新成功");
            }else{
                json.put("msg","更新失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑费用明细表记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            CostDetails costDetails=costDetailsService.queryData(map.get("id").toString());
            if(costDetails!=null){
                costDetailsService.update(costDetails);
                json.put("msg","编辑成功");
            }else{
                json.put("msg","编辑失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());

        return json.toString();
    }

    /**
     * 删除费用明细表记录
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
            CostDetails costDetails=costDetailsService.queryData(map.get("id").toString());
            if(costDetails!=null){
                costDetailsService.update(costDetails);
                json.put("msg","删除成功");
            }else{
                json.put("msg","删除失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询费用明细表记录
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
            CostDetails costDetails=costDetailsService.queryData(map.get("id").toString());
            if(costDetails!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(costDetails));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询费用明细表记录列表（也可以不分页）
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
            int totalCount =costDetailsService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<CostDetails> list =costDetailsService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
