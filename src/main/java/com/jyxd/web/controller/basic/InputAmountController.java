package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.InputAmount;
import com.jyxd.web.service.basic.InputAmountService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/inputAmount")
public class InputAmountController {

    private static Logger logger= LoggerFactory.getLogger(InputAmountController.class);

    @Autowired
    private InputAmountService inputAmountService;

    /**
     * 增加一条入量表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InputAmount inputAmount){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        inputAmount.setId(UUIDUtil.getUUID());
        inputAmount.setCreateTime(new Date());
        inputAmountService.insert(inputAmount);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新入量表记录状态
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
            InputAmount inputAmount=inputAmountService.queryData(map.get("id").toString());
            if(inputAmount!=null){
                inputAmount.setStatus((int)map.get("status"));
                inputAmountService.update(inputAmount);
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
     * 编辑入量表记录单
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
            InputAmount inputAmount=inputAmountService.queryData(map.get("id").toString());
            if(inputAmount!=null){
                inputAmount.setStatus((int)map.get("status"));
                inputAmountService.update(inputAmount);
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
     * 删除入量表记录
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
            InputAmount inputAmount=inputAmountService.queryData(map.get("id").toString());
            if(inputAmount!=null){
                inputAmount.setStatus(-1);
                inputAmountService.update(inputAmount);
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
     * 根据主键id查询入量表记录
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
            InputAmount inputAmount=inputAmountService.queryData(map.get("id").toString());
            if(inputAmount!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(inputAmount));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询入量表记录列表（也可以不分页）
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
            int totalCount =inputAmountService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<InputAmount> list =inputAmountService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--保存一条入量记录
     * @param inputAmount
     * @return
     */
    @RequestMapping(value = "/saveData",method= RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody InputAmount inputAmount){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        //有id 则编辑  无id 则新增
        if(inputAmount!=null && StringUtils.isNotEmpty(inputAmount.getId())){
            inputAmount.setCreateTime(new Date());
            inputAmountService.update(inputAmount);
            json.put("msg","编辑成功");
        }else{
            inputAmount.setId(UUIDUtil.getUUID());
            inputAmount.setCreateTime(new Date());
            inputAmountService.insert(inputAmount);
            json.put("msg","新增成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--根据病人id查询入量列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByPatientId",method= RequestMethod.POST)
    @ResponseBody
    public String getListByPatientId(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("patientId")){
            List<Map<String,Object>> list=inputAmountService.getListByPatientId(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("code",HttpCode.OK_CODE.getCode());
                json.put("msg","查询成功");
            }
        }else{
            json.put("code",HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg","请先选择病人");
        }
        return json.toString();
    }

}
