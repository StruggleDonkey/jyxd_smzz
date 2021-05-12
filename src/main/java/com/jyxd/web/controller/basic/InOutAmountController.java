package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.CatheterMaintain;
import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.data.basic.InOutAmount;
import com.jyxd.web.service.basic.CatheterMaintainService;
import com.jyxd.web.service.basic.CustomFieldService;
import com.jyxd.web.service.basic.InOutAmountService;
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

import java.util.*;

@Controller
@RequestMapping(value = "/inOutAmount")
public class InOutAmountController {

    private static Logger logger= LoggerFactory.getLogger(InOutAmountController.class);

    @Autowired
    private InOutAmountService inOutAmountService;

    @Autowired
    private CustomFieldService customFieldService;

    /**
     * 增加一条出入量表（二表合一）记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InOutAmount inOutAmount){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        inOutAmount.setId(UUIDUtil.getUUID());
        inOutAmount.setCreateTime(new Date());
        inOutAmountService.insert(inOutAmount);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新出入量表（二表合一）状态
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
            InOutAmount inOutAmount=inOutAmountService.queryData(map.get("id").toString());
            if(inOutAmount!=null){
                inOutAmount.setStatus((int)map.get("status"));
                inOutAmountService.update(inOutAmount);
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
     * 编辑出入量表（二表合一）记录单
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
            InOutAmount inOutAmount=inOutAmountService.queryData(map.get("id").toString());
            if(inOutAmount!=null){
                inOutAmount.setStatus((int)map.get("status"));
                inOutAmountService.update(inOutAmount);
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
     * 删除出入量表（二表合一）记录
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
            InOutAmount inOutAmount=inOutAmountService.queryData(map.get("id").toString());
            if(inOutAmount!=null){
                inOutAmount.setStatus(-1);
                inOutAmountService.update(inOutAmount);
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
     * 根据主键id查询出入量表（二表合一）记录
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
            InOutAmount inOutAmount=inOutAmountService.queryData(map.get("id").toString());
            if(inOutAmount!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(inOutAmount));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInOutAmountList",method= RequestMethod.POST)
    @ResponseBody
    public String getInOutAmountList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            //先查询有哪些自定义字段，放入查询条件中
            Map<String,Object> m=new HashMap<>();
            m.put("status",1);
            m.put("associatedTable","table_in_out_amount");//关联表名
            List<CustomField> list=customFieldService.queryList(m);
            map.put("list",list);
            int totalCount =inOutAmountService.getInOutAmountNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> amountList =inOutAmountService.getInOutAmountList(map);
        if(amountList!=null && amountList.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(amountList));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
