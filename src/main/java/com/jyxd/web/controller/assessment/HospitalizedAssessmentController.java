package com.jyxd.web.controller.assessment;

import com.jyxd.web.data.assessment.HospitalizedAssessment;
import com.jyxd.web.service.assessment.HospitalizedAssessmentService;
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
@RequestMapping(value = "/hospitalizedAssessment")
public class HospitalizedAssessmentController {

    private static Logger logger= LoggerFactory.getLogger(HospitalizedAssessmentController.class);

    @Autowired
    private HospitalizedAssessmentService hospitalizedAssessmentService;

    /**
     * 增加一条入院护理评估单记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody HospitalizedAssessment hospitalizedAssessment){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        hospitalizedAssessment.setId(UUIDUtil.getUUID());
        hospitalizedAssessment.setCreateTime(new Date());
        hospitalizedAssessmentService.insert(hospitalizedAssessment);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新入院护理评估单记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") ){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus((int)map.get("status"));
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 编辑入院护理评估单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map!=null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus((int)map.get("status"));
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 删除入院护理评估单记录
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","系统开小差了，请稍后再试。");
        if(map.containsKey("id")){
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                hospitalizedAssessment.setStatus(-1);
                hospitalizedAssessmentService.update(hospitalizedAssessment);
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
     * 根据主键id查询入院护理评估单记录
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
            HospitalizedAssessment hospitalizedAssessment=hospitalizedAssessmentService.queryData(map.get("id").toString());
            if(hospitalizedAssessment!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(hospitalizedAssessment));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询入院护理评估单记录列表（也可以不分页）
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
        List<HospitalizedAssessment> list =hospitalizedAssessmentService.queryList(map);
        if(map!=null && map.containsKey("start")){
            int totalCount =hospitalizedAssessmentService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        if(list!=null && list.size()>0){
            json.put("msg","成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
