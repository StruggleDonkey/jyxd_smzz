package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.BedUseStatistics;
import com.jyxd.web.data.dictionary.BedDictionary;
import com.jyxd.web.service.basic.BedUseStatisticsService;
import com.jyxd.web.service.dictionary.BedDictionaryService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.SliceUpDateUtil;
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
@RequestMapping(value = "/bedUseStatistics")
public class BedUseStatisticsController {

    private static Logger logger= LoggerFactory.getLogger(BedUseStatisticsController.class);

    @Autowired
    private BedUseStatisticsService bedUseStatisticsService;

    @Autowired
    private BedDictionaryService bedDictionaryService;

    /**
     * 增加一条床位统计表记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody BedUseStatistics bedUseStatistics){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        bedUseStatistics.setId(UUIDUtil.getUUID());
        bedUseStatistics.setStatisticsDate(new Date());
        bedUseStatistics.setCreateTime(new Date());
        bedUseStatisticsService.insert(bedUseStatistics);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新床位统计表记录状态
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
            BedUseStatistics bedUseStatistics=bedUseStatisticsService.queryData(map.get("id").toString());
            if(bedUseStatistics!=null){

                bedUseStatisticsService.update(bedUseStatistics);
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
     * 编辑床位统计表记录单
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
            BedUseStatistics bedUseStatistics=bedUseStatisticsService.queryData(map.get("id").toString());
            if(bedUseStatistics!=null){

                bedUseStatisticsService.update(bedUseStatistics);
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
     * 删除床位统计表记录
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
            BedUseStatistics bedUseStatistics=bedUseStatisticsService.queryData(map.get("id").toString());
            if(bedUseStatistics!=null){

                bedUseStatisticsService.update(bedUseStatistics);
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
     * 根据主键id查询床位统计表记录
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
            BedUseStatistics bedUseStatistics=bedUseStatisticsService.queryData(map.get("id").toString());
            if(bedUseStatistics!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(bedUseStatistics));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询床位统计表记录列表（也可以不分页）
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
            int totalCount =bedUseStatisticsService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<BedUseStatistics> list =bedUseStatisticsService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--床位使用--日期分析（按天 或 按月）
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByTime",method= RequestMethod.POST)
    @ResponseBody
    public String getListByTime(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=new JSONArray();
        JSONObject obj1=new JSONObject();
        obj1.put("name","使用量");
        obj1.put("type","line");
        obj1.put("stack","总量");
        if(map.get("type").toString().equals("按天")) {
            String startTime = map.get("startTime").toString();
            String endTime = map.get("endTime").toString();
            List<String> list = SliceUpDateUtil.sliceUpDateRange(startTime, endTime, 3);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("list", list);
            List<Map<String,Object>> dayList=bedUseStatisticsService.getListByTimeDay(map);
            obj1.put("data",JSONArray.fromObject(dayList));
            json.put("msg","查询成功");
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
            List<Map<String,Object>> monthList=bedUseStatisticsService.getListByTimeMonth(map);
            obj1.put("data",JSONArray.fromObject(monthList));
            json.put("msg","查询成功");
        }
        array.add(obj1);
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--床位使用--日期对比分析（按天 或 按月）
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByTimeAndBedCode",method= RequestMethod.POST)
    @ResponseBody
    public String getListByTimeAndBedCode(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","暂无数据");
        JSONArray array=new JSONArray();
        List<BedDictionary> bedList=bedDictionaryService.queryAllList(map);
        if(bedList!=null && bedList.size()>0){
            for (int i = 0; i < bedList.size(); i++) {
                JSONObject obj1=new JSONObject();
                obj1.put("name",bedList.get(i).getBedName());
                obj1.put("type","line");
                obj1.put("stack","总量");
                if(map.get("type").toString().equals("按天")) {
                    String startTime = map.get("startTime").toString();
                    String endTime = map.get("endTime").toString();
                    List<String> list = SliceUpDateUtil.sliceUpDateRange(startTime, endTime, 3);
                    map.put("startTime", startTime);
                    map.put("endTime", endTime);
                    map.put("list", list);
                    map.put("bedCode",bedList.get(i).getBedCode());
                    List<Map<String,Object>> dayList=bedUseStatisticsService.getListByTimeDay(map);
                    obj1.put("data",JSONArray.fromObject(dayList));
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
                    map.put("bedCode",bedList.get(i).getBedCode());
                    List<Map<String,Object>> monthList=bedUseStatisticsService.getListByTimeMonth(map);
                    obj1.put("data",JSONArray.fromObject(monthList));
                }
                array.add(obj1);
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
