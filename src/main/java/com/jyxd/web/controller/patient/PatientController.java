package com.jyxd.web.controller.patient;

import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping(value = "/patient")
public class PatientController {

    private static Logger logger= LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /**
     * 增加一条病人表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody Patient patient){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","系统开小差了，请稍后再试。");
        patient.setId(UUIDUtil.getUUID());
        patient.setCreateTime(new Date());
        patientService.insert(patient);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新病人表记录状态
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
            Patient patient=patientService.queryData(map.get("id").toString());
            if(patient!=null){
                patient.setStatus((int)map.get("status"));
                patientService.update(patient);
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
     * 编辑病人表
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
            Patient patient=patientService.queryData(map.get("id").toString());
            if(patient!=null){
                patient.setStatus((int)map.get("status"));
                patientService.update(patient);
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
     * 删除病人表记录
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
            Patient patient=patientService.queryData(map.get("id").toString());
            if(patient!=null){
                patient.setStatus(-1);
                patientService.update(patient);
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
     * 根据主键id查询病人表记录
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
            Patient patient=patientService.queryData(map.get("id").toString());
            if(patient!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(patient));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询病人表记录列表（也可以不分页）
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
            int totalCount =patientService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Patient> list =patientService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询病人表记录列表（也可以不分页）--多表查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map!=null && map.containsKey("start")){
            int totalCount =patientService.getNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =patientService.getList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 查询所有病人数量
     * @return
     */
    @RequestMapping(value = "/getAllNum",method= RequestMethod.POST)
    @ResponseBody
    public String getAllNum(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        int num=patientService.getAllNum();
        json.put("num",num);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 导出病人信息excel
     * @return
     */
    @RequestMapping(value = "/downloadPatient",method= RequestMethod.POST)
    @ResponseBody
    public void downloadPatient(@RequestBody(required=false) Map<String,Object> map, HttpServletResponse response)throws Exception {
        List<LinkedHashMap<String,Object>> list =patientService.getDownloadList(map);
        if(list!=null && list.size()>0){
            ExportExcelUtil.exportExcel(response,"病人信息统计"+System.currentTimeMillis()+".xlsx",setExcelData(list));
        }
    }

    /**
     *
     *〈设置订单汇总表格数据〉<br>
     *
     * @author czy <br>
     *         2019年12月31日
     * @update
     * @param
     * @return  ExcelData
     * @exception/throws [异常类型] [异常说明]
     * @see   [类、类#方法、类#成员]
     * @since [起始版本]
     */
    public static ExcelData setExcelData(List<LinkedHashMap<String, Object>> list) {
        ExcelData data = new ExcelData();
        data.setName("病人信息统计表");
        List<String> titles = new ArrayList();
        titles.add("住院号");
        titles.add("姓名");
        titles.add("性别");
        titles.add("入科时间");
        titles.add("出科时间");
        titles.add("当前状态");
        titles.add("出科方式");
        titles.add("来源科室");
        titles.add("去向科室");
        titles.add("入科床号");
        titles.add("住院时间");
        titles.add("是否非计划");
        titles.add("病情");
        titles.add("责任医生");
        titles.add("责任护士");
        titles.add("最新手术时间");
        titles.add("最新手术名称");
        titles.add("诊断");
        titles.add("过敏史");
        titles.add("阳性");
        data.setTitles(titles);
        if(list != null && list.size() > 0) {
            List<List<Object>> rows = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                List<Object> row1 = new ArrayList();
                row1.add(list.get(i).get("住院号"));
                row1.add(list.get(i).get("姓名"));
                row1.add(list.get(i).get("性别"));
                row1.add(list.get(i).get("入科时间"));
                row1.add(list.get(i).get("出科时间"));
                row1.add(list.get(i).get("当前状态"));
                row1.add(list.get(i).get("出科方式"));
                row1.add(list.get(i).get("来源科室"));
                row1.add(list.get(i).get("去向科室"));
                row1.add(list.get(i).get("入科床号"));
                row1.add(list.get(i).get("住院时间"));
                row1.add(list.get(i).get("是否非计划"));
                row1.add(list.get(i).get("病情"));
                row1.add(list.get(i).get("责任医生"));
                row1.add(list.get(i).get("责任护士"));
                row1.add(list.get(i).get("最新手术时间"));
                row1.add(list.get(i).get("最新手术名称"));
                row1.add(list.get(i).get("诊断"));
                row1.add(list.get(i).get("过敏史"));
                row1.add(list.get(i).get("阳性"));

                rows.add(row1);
            }
            data.setRows(rows);
        }
        return data;
    }

    /**
     * 导出病人信息excel（暂时不用这个接口）
     * @return
     */
    @RequestMapping(value = "/exportPatient",method= RequestMethod.GET)
    @ResponseBody
    public void exportPatient(HttpServletRequest request, HttpServletResponse response)throws Exception {
        //String keyword = request.getParameter("keyword");
        patientService.getExcel(request,response);
        System.out.println("导出成功");
    }

    /**
     * 首页查询患者现有数量 今日转入数量 今日转出数量
     * @return
     */
    @RequestMapping(value = "/getNowPatientNum",method= RequestMethod.POST)
    @ResponseBody
    public String getNowPatientNum(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        Map<String,Object> map=new HashMap();
        map.put("existing","existing");//患者现有数量sql查询条件
        int existingNum=patientService.getNowPatientNum(map);
        map.clear();
        map.put("todayEnter","todayEnter");//患者今日转入数量sql查询条件
        int todayEnterNum=patientService.getNowPatientNum(map);
        map.clear();
        map.put("todayExit","todayExit");//患者今日转出数量sql查询条件
        int todayExitNum=patientService.getNowPatientNum(map);
        json.put("existingNum",existingNum);
        json.put("todayEnterNum",todayEnterNum);
        json.put("todayExitNum",todayExitNum);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 首页查询在科病人来源
     * @return
     */
    @RequestMapping(value = "/getPatientSource",method= RequestMethod.POST)
    @ResponseBody
    public String getPatientSource(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=patientService.getPatientSource();
        json.put("data",array);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 首页查询本月转入和转出
     * @return
     */
    @RequestMapping(value = "/getMonthPatient",method= RequestMethod.POST)
    @ResponseBody
    public String getMonthPatient(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        Map<String,Object> map=new HashMap();
        map.put("monthEnter","monthEnter");//患者本月转入数量sql查询条件
        int monthEnterNum=patientService.getNowPatientNum(map);
        map.clear();
        map.put("monthExit","monthExit");//患者本月转出数量sql查询条件
        int monthExitNum=patientService.getNowPatientNum(map);
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        obj1.put("value",monthEnterNum);
        obj1.put("name","转入");
        JSONObject obj2=new JSONObject();
        obj2.put("value",monthExitNum);
        obj2.put("name","转出");
        array.add(obj1);
        array.add(obj2);
        json.put("data",array);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 首页查询床位列表
     * @return
     */
    @RequestMapping(value = "/getBedPatientList",method= RequestMethod.POST)
    @ResponseBody
    public String getBedPatientList(){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Map<String,Object>> list=patientService.getBedPatientList();
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("data",JSONArray.fromObject(list,jsonConfig));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 查询待分配或已出科的病人列表（是否分配床位）
     * @return
     */
    @RequestMapping(value = "/getNoBedPatientList",method= RequestMethod.POST)
    @ResponseBody
    public String getNoBedPatientList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        List<Patient> list=patientService.getNoBedPatientList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入科--转入转出分析
     * @return
     */
    @RequestMapping(value = "/getOutAndIn",method= RequestMethod.POST)
    @ResponseBody
    public String getOutAndIn(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=new JSONArray();
        JSONObject enterObj=new JSONObject();
        JSONObject exitObj=new JSONObject();
        enterObj.put("name","转入患者");
        enterObj.put("value",0);
        exitObj.put("name","转出患者");
        exitObj.put("value",0);
        if(map.containsKey("type")){
            //flag 0：出科  1：在科
            if(map.get("type").toString().equals("按天")){
                String startTime=map.get("startTime").toString();
                String endTime=map.get("endTime").toString();
                map.put("flag",0);//出科
                map.put("exitStartTime",startTime);
                map.put("exitEndTime",endTime);
                int exitNum=patientService.getOutAndIn(map);
                exitObj.put("value",exitNum);
                map.put("flag",1);//在科
                map.put("enterStartTime",startTime);
                map.put("enterEndTime",endTime);
                int enterNum=patientService.getOutAndIn(map);
                enterObj.put("value",enterNum);
            }
            if(map.get("type").toString().equals("按月")){
                String startTime=map.get("year").toString()+"01-01";
                String endTime=map.get("year").toString()+"12-31";
                map.put("flag",0);//出科
                map.put("exitStartTime",startTime);
                map.put("exitEndTime",endTime);
                int exitNum=patientService.getOutAndIn(map);
                exitObj.put("value",exitNum);
                map.put("flag",1);//在科
                map.put("enterStartTime",startTime);
                map.put("enterEndTime",endTime);
                int enterNum=patientService.getOutAndIn(map);
                enterObj.put("value",enterNum);
            }
        }
        array.add(exitObj);
        array.add(enterObj);
        json.put("data",array);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入科--转出方式分析
     * @return
     */
    @RequestMapping(value = "/getExitType",method= RequestMethod.POST)
    @ResponseBody
    public String getExitType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        JSONObject obj2=new JSONObject();
        JSONObject obj3=new JSONObject();
        JSONObject obj4=new JSONObject();
        JSONObject obj5=new JSONObject();
        obj1.put("name","出院患者");
        obj1.put("value",0);
        obj2.put("name","转科患者");
        obj2.put("value",0);
        obj3.put("name","死亡患者");
        obj3.put("value",0);
        obj4.put("name","放弃患者");
        obj4.put("value",0);
        obj5.put("name","转院患者");
        obj5.put("value",0);
        if(map.containsKey("type")){
            //flag 0：出科  1：在科
            if(map.get("type").toString().equals("按天")){
                String startTime=map.get("startTime").toString();
                String endTime=map.get("endTime").toString();
                map.put("flag",0);//出科
                map.put("exitStartTime",startTime);
                map.put("exitEndTime",endTime);
                map.put("exitType","出院");
                int num1=patientService.getOutAndIn(map);
                obj1.put("value",num1);
                map.put("exitType","转科");
                int num2=patientService.getOutAndIn(map);
                obj2.put("value",num2);
                map.put("exitType","死亡");
                int num3=patientService.getOutAndIn(map);
                obj3.put("value",num3);
                map.put("exitType","放弃");
                int num4=patientService.getOutAndIn(map);
                obj4.put("value",num4);
                map.put("exitType","转院");
                int num5=patientService.getOutAndIn(map);
                obj5.put("value",num5);
            }
            if(map.get("type").toString().equals("按月")){
                String startTime=map.get("year").toString()+"01-01";
                String endTime=map.get("year").toString()+"12-31";
                map.put("flag",0);//出科
                map.put("exitStartTime",startTime);
                map.put("exitEndTime",endTime);
                map.put("exitType","出院");
                int num1=patientService.getOutAndIn(map);
                obj1.put("value",num1);
                map.put("exitType","转科");
                int num2=patientService.getOutAndIn(map);
                obj2.put("value",num2);
                map.put("exitType","死亡");
                int num3=patientService.getOutAndIn(map);
                obj3.put("value",num3);
                map.put("exitType","放弃");
                int num4=patientService.getOutAndIn(map);
                obj4.put("value",num4);
                map.put("exitType","转院");
                int num5=patientService.getOutAndIn(map);
                obj5.put("value",num5);
            }
        }
        array.add(obj1);
        array.add(obj2);
        array.add(obj3);
        array.add(obj4);
        array.add(obj5);
        json.put("data",array);
        json.put("msg","查询成功");
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入科--转入科室分析
     * @return
     */
    @RequestMapping(value = "/getEnterDepartment",method= RequestMethod.POST)
    @ResponseBody
    public String getEnterDepartment(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        try {
            if(map.get("type").toString().equals("按天")){
                String startTime=map.get("startTime").toString();
                String endTime=map.get("endTime").toString();
                map.put("enterStartTime",startTime);
                map.put("enterEndTime",endTime);
                map.put("flag",1);// 在科
            }
            if(map.get("type").toString().equals("按月")) {
                String startTime = map.get("year").toString() + "01-01";
                String endTime = map.get("year").toString() + "12-31";
                map.put("enterStartTime",startTime);
                map.put("enterEndTime",endTime);
                map.put("flag",1);// 在科
            }
            List<Map<String,Object>> list=patientService.getAllEnterDepartment(map);
            if(list!=null && list.size()>0){
                JSONArray array=new JSONArray();
                for (int i = 0; i <list.size() ; i++) {
                    JSONObject obj=new JSONObject();
                    obj.put("name",list.get(i).get("department_name").toString());
                    map.put("departmentCode",list.get(i).get("department_code").toString());//转入科室编码
                    int num=patientService.getEnterAndExitDepartment(map);
                    obj.put("value",num);
                    array.add(obj);
                }
                json.put("data",array);
                json.put("msg","查询成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            System.out.println("getEnterDepartment:"+e);
        }
        return json.toString();
    }

    /**
     * 统计分析--出入科--转出科室分析
     * @return
     */
    @RequestMapping(value = "/getExitDepartment",method= RequestMethod.POST)
    @ResponseBody
    public String getExitDepartment(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        if(map.get("type").toString().equals("按天")){
            String startTime=map.get("startTime").toString();
            String endTime=map.get("endTime").toString();
            map.put("exitStartTime",startTime);
            map.put("exitEndTime",endTime);
            map.put("flag",0);// 出科
        }
        if(map.get("type").toString().equals("按月")) {
            String startTime = map.get("year").toString() + "01-01";
            String endTime = map.get("year").toString() + "12-31";
            map.put("exitStartTime",startTime);
            map.put("exitEndTime",endTime);
            map.put("flag",0);// 出科
        }
        List<Map<String,Object>> list=patientService.getAllExitDepartment(map);
        if(list!=null && list.size()>0){
            JSONArray array=new JSONArray();
            for (int i = 0; i <list.size() ; i++) {
                JSONObject obj=new JSONObject();
                obj.put("name",list.get(i).get("department_name").toString());
                map.put("toDepartmentCode",list.get(i).get("to_department_code").toString());//转出科室编码
                int num=patientService.getEnterAndExitDepartment(map);
                obj.put("value",num);
                array.add(obj);
            }
            json.put("data",array);
            json.put("msg","查询成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }
        return json.toString();
    }

    /**
     * 统计分析--出入科--趋势分析--转出方式
     * @return
     */
    @RequestMapping(value = "/getNumByExitType",method= RequestMethod.POST)
    @ResponseBody
    public String getNumByExitType(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        obj1.put("name","出院患者");
        obj1.put("type","line");
        obj1.put("stack","总量");
        JSONObject obj2=new JSONObject();
        obj2.put("name","转科患者");
        obj2.put("type","line");
        obj2.put("stack","总量");
        JSONObject obj3=new JSONObject();
        obj3.put("name","死亡患者");
        obj3.put("type","line");
        obj3.put("stack","总量");
        JSONObject obj4=new JSONObject();
        obj4.put("name","放弃患者");
        obj4.put("type","line");
        obj4.put("stack","总量");
        JSONObject obj5=new JSONObject();
        obj5.put("name","转院患者");
        obj5.put("type","line");
        obj5.put("stack","总量");
        try {
            if(map.get("type").toString().equals("按天")){
                String startTime=map.get("startTime").toString();
                String endTime=map.get("endTime").toString();
                List<String> list=SliceUpDateUtil.sliceUpDateRange(startTime,endTime,3);
                map.put("exitStartTime",startTime);
                map.put("exitEndTime",endTime);
                map.put("list",list);
                map.put("exitType","出院");
                List<Map<String,Object>> list1=patientService.getNumByExitType(map);
                obj1.put("data",JSONArray.fromObject(list1));
                map.put("exitType","转科");
                List<Map<String,Object>> list2=patientService.getNumByExitType(map);
                obj2.put("data",JSONArray.fromObject(list2));
                map.put("exitType","死亡");
                List<Map<String,Object>> list3=patientService.getNumByExitType(map);
                obj3.put("data",JSONArray.fromObject(list3));
                map.put("exitType","放弃");
                List<Map<String,Object>> list4=patientService.getNumByExitType(map);
                obj4.put("data",JSONArray.fromObject(list4));
                map.put("exitType","转院");
                List<Map<String,Object>> list5=patientService.getNumByExitType(map);
                obj5.put("data",JSONArray.fromObject(list5));
            }
            if(map.get("type").toString().equals("按月")) {
                List<String> list=new ArrayList<>();
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                list.add("6");
                list.add("7");
                list.add("8");
                list.add("9");
                list.add("10");
                list.add("11");
                list.add("12");
                map.put("list",list);
                map.put("exitType","出院");
                List<Map<String,Object>> list1=patientService.getNumByExitTypeMonth(map);
                obj1.put("data",JSONArray.fromObject(list1));
                map.put("exitType","转科");
                List<Map<String,Object>> list2=patientService.getNumByExitTypeMonth(map);
                obj2.put("data",JSONArray.fromObject(list2));
                map.put("exitType","死亡");
                List<Map<String,Object>> list3=patientService.getNumByExitTypeMonth(map);
                obj3.put("data",JSONArray.fromObject(list3));
                map.put("exitType","放弃");
                List<Map<String,Object>> list4=patientService.getNumByExitTypeMonth(map);
                obj4.put("data",JSONArray.fromObject(list4));
                map.put("exitType","转院");
                List<Map<String,Object>> list5=patientService.getNumByExitTypeMonth(map);
                obj5.put("data",JSONArray.fromObject(list5));
            }
            array.add(obj1);
            array.add(obj2);
            array.add(obj3);
            array.add(obj4);
            array.add(obj5);
            json.put("data",array);
            json.put("msg","查询成功");
            json.put("code",HttpCode.OK_CODE.getCode());
        }catch (Exception e){
            System.out.println("getNumByExitType:"+e);
        }
        return json.toString();
    }
}
