package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.InOutIcuStatistics;
import com.jyxd.web.service.basic.InOutIcuStatisticsService;
import com.jyxd.web.util.*;
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

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/inOutIcuStatistics")
public class InOutIcuStatisticsController {

    private static Logger logger= LoggerFactory.getLogger(InOutIcuStatisticsController.class);

    @Autowired
    private InOutIcuStatisticsService inOutIcuStatisticsService;

    /**
     * 增加一条出入科统计表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InOutIcuStatistics inOutIcuStatistics){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        inOutIcuStatistics.setId(UUIDUtil.getUUID());
        inOutIcuStatistics.setStatisticsDate(new Date());
        inOutIcuStatisticsService.insert(inOutIcuStatistics);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新出入科统计表记录状态
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
            InOutIcuStatistics inOutIcuStatistics=inOutIcuStatisticsService.queryData(map.get("id").toString());
            if(inOutIcuStatistics!=null){

                inOutIcuStatisticsService.update(inOutIcuStatistics);
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
     * 编辑出入科统计表记录单
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","编辑失败");
        try {
            if(map!=null && map.containsKey("statisticsDate")){
                List<InOutIcuStatistics> list=inOutIcuStatisticsService.queryDataByDate(map);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(list!=null && list.size()>0){
                    InOutIcuStatistics inOutIcuStatistics=list.get(0);
                    System.out.println("------------"+sdf.parse(map.get("statisticsDate").toString()));
                    inOutIcuStatistics.setStatisticsDate(sdf.parse(map.get("statisticsDate").toString()));
                    inOutIcuStatistics.setCurrentInNum((int)map.get("currentInNum"));
                    inOutIcuStatistics.setNewInNum((int)map.get("newInNum"));
                    inOutIcuStatistics.setOldInNum((int)map.get("oldInNum"));
                    inOutIcuStatistics.setOutAbandonNum((int)map.get("outAbandonNum"));
                    inOutIcuStatistics.setOutDeathNum((int)map.get("outDeathNum"));
                    inOutIcuStatistics.setOutExitNum((int)map.get("outExitNum"));
                    inOutIcuStatistics.setOutNum((int)map.get("outNum"));
                    inOutIcuStatistics.setOutTransHospNum((int)map.get("outTransHospNum"));
                    inOutIcuStatistics.setOutTransNum((int)map.get("outTransNum"));
                    inOutIcuStatistics.setTwoDayInAgainNum((int)map.get("twoDayInAgainNum"));
                    inOutIcuStatisticsService.update(inOutIcuStatistics);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }else{
                    //新增
                    InOutIcuStatistics inOutIcuStatistics=new InOutIcuStatistics();
                    inOutIcuStatistics.setId(UUIDUtil.getUUID());
                    System.out.println("============"+sdf.parse(map.get("statisticsDate").toString()));
                    inOutIcuStatistics.setStatisticsDate(sdf.parse(map.get("statisticsDate").toString()));//2020-08-13 16:00:00
                    inOutIcuStatistics.setCurrentInNum((int)map.get("currentInNum"));
                    inOutIcuStatistics.setNewInNum((int)map.get("newInNum"));
                    inOutIcuStatistics.setOldInNum((int)map.get("oldInNum"));
                    inOutIcuStatistics.setOutAbandonNum((int)map.get("outAbandonNum"));
                    inOutIcuStatistics.setOutDeathNum((int)map.get("outDeathNum"));
                    inOutIcuStatistics.setOutExitNum((int)map.get("outExitNum"));
                    inOutIcuStatistics.setOutNum((int)map.get("outNum"));
                    inOutIcuStatistics.setOutTransHospNum((int)map.get("outTransHospNum"));
                    inOutIcuStatistics.setOutTransNum((int)map.get("outTransNum"));
                    inOutIcuStatistics.setTwoDayInAgainNum((int)map.get("twoDayInAgainNum"));
                    inOutIcuStatisticsService.insert(inOutIcuStatistics);
                    json.put("msg","编辑成功");
                    json.put("code",HttpCode.OK_CODE.getCode());
                }
            }
        }catch (Exception e){
            logger.info("编辑出入科统计表记录单:"+e);
        }
        return json.toString();
    }

    /**
     * 删除出入科统计表记录
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
            InOutIcuStatistics inOutIcuStatistics=inOutIcuStatisticsService.queryData(map.get("id").toString());
            if(inOutIcuStatistics!=null){

                inOutIcuStatisticsService.update(inOutIcuStatistics);
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
     * 根据主键id查询出入科统计表记录
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
            InOutIcuStatistics inOutIcuStatistics=inOutIcuStatisticsService.queryData(map.get("id").toString());
            if(inOutIcuStatistics!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(inOutIcuStatistics));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询出入科统计表记录列表（也可以不分页）
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
            int totalCount =inOutIcuStatisticsService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<InOutIcuStatistics> list =inOutIcuStatisticsService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入科--列表展示统计（按天或者按月）
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
        if(map.get("type").toString().equals("按天")) {
            String startTime = map.get("startTime").toString();
            String endTime = map.get("endTime").toString();
            List<String> list = SliceUpDateUtil.sliceUpDateRange(startTime, endTime, 3);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("list",list);
            List<Map<String,Object>> dayList=inOutIcuStatisticsService.getListByDay(map);
            if(dayList!=null && dayList.size()>0){
                List<Map<String,Object>> dayTotalList=inOutIcuStatisticsService.getTotalByDay(map);
                dayList.add(0,dayTotalList.get(0));
                JSONArray array=JSONArray.fromObject(dayList);
                json.put("data",array);
                json.put("msg","查询成功");
            }
        }
        if(map.get("type").toString().equals("按月")) {
            List<String> list = new ArrayList<>();
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
            map.put("list", list);
            List<Map<String,Object>> monthList=inOutIcuStatisticsService.getListByMonth(map);
            if(monthList!=null && monthList.size()>0){
                List<Map<String,Object>> monthTotalList=inOutIcuStatisticsService.getTotalByMonth(map);
                monthList.add(0,monthTotalList.get(0));
                JSONArray array=JSONArray.fromObject(monthList);
                json.put("data",array);
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 导出出入科信息统计excel
     * @return
     */
    @RequestMapping(value = "/downloadInOutTotal",method= RequestMethod.POST)
    @ResponseBody
    public void downloadInOutTotal(@RequestBody(required=false) Map<String,Object> map, HttpServletResponse response)throws Exception {
        List<Map<String,Object>> downloadList=new ArrayList<>();
        if(map.get("type").toString().equals("按天")) {
            String startTime = map.get("startTime").toString();
            String endTime = map.get("endTime").toString();
            List<String> list = SliceUpDateUtil.sliceUpDateRange(startTime, endTime, 3);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("list",list);
            downloadList=inOutIcuStatisticsService.getListByDay(map);
            if(downloadList!=null && downloadList.size()>0){
                List<Map<String,Object>> dayTotalList=inOutIcuStatisticsService.getTotalByDay(map);
                downloadList.add(0,dayTotalList.get(0));
            }
        }
        if(map.get("type").toString().equals("按月")) {
            List<String> list = new ArrayList<>();
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
            map.put("list", list);
            downloadList=inOutIcuStatisticsService.getListByMonth(map);
            if(downloadList!=null && downloadList.size()>0){
                List<Map<String,Object>> monthTotalList=inOutIcuStatisticsService.getTotalByDay(map);
                downloadList.add(0,monthTotalList.get(0));
            }
        }
        if(downloadList!=null && downloadList.size()>0){
            ExportExcelUtil.exportExcel(response,"出入科信息统计"+System.currentTimeMillis()+".xlsx",setExcelData(downloadList));
        }
    }

    /**
     *
     *〈设置表格数据〉<br>
     *
     * @author zhangjie <br>
     *         2020年9月27日
     * @update
     * @param
     * @return  ExcelData
     * @exception/throws [异常类型] [异常说明]
     * @see   [类、类#方法、类#成员]
     * @since [起始版本]
     */
    public static ExcelData setExcelData(List<Map<String,Object>> list) {
        ExcelData data = new ExcelData();
        data.setName("出入科信息统计表");
        List<String> titles = new ArrayList();
        titles.add("日期");
        titles.add("原有患者");
        titles.add("现有患者");
        titles.add("转入患者");
        titles.add("转出患者");
        titles.add("出院患者");
        titles.add("转科患者");
        titles.add("死亡患者");
        titles.add("放弃患者");
        titles.add("转院患者");
        titles.add("48小时返回患者");
        data.setTitles(titles);
        if(list != null && list.size() > 0) {
            List<List<Object>> rows = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                List<Object> row1 = new ArrayList();
                row1.add(list.get(i).get("statisticsDate"));
                row1.add(list.get(i).get("old_in_num"));
                row1.add(list.get(i).get("current_in_num"));
                row1.add(list.get(i).get("new_in_num"));
                row1.add(list.get(i).get("out_num"));
                row1.add(list.get(i).get("out_exit_num"));
                row1.add(list.get(i).get("out_trans_num"));
                row1.add(list.get(i).get("out_death_num"));
                row1.add(list.get(i).get("out_abandon_num"));
                row1.add(list.get(i).get("out_trans_hosp_num"));
                row1.add(list.get(i).get("two_day_in_again_num"));
                rows.add(row1);
            }
            data.setRows(rows);
        }
        return data;
    }

}
