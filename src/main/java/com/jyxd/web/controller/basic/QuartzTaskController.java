package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.QuartzTask;
import com.jyxd.web.service.basic.QuartzTaskService;
import com.jyxd.web.task.QuartzSchedulerUtil;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/quartzTask")
public class QuartzTaskController {

    private static Logger logger= LoggerFactory.getLogger(QuartzTaskController.class);

    @Autowired
    private QuartzTaskService quartzTaskService;

    /**
     * 增加一条定时任务记录
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody QuartzTask quartzTask){
        JSONObject json=new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","添加失败");
        quartzTask.setId(UUIDUtil.getUUID());
        quartzTaskService.insert(quartzTask);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新定时任务记录状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("msg","更新失败");
        try {
            if(map!=null && map.containsKey("id") && map.containsKey("status") ){
                QuartzTask quartzTask=quartzTaskService.queryData(map.get("id").toString());
                if(quartzTask!=null){
                    SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
                    Scheduler scheduler = schedulerFactoryBean.getScheduler();
                    QuartzSchedulerUtil quartzSchedulerUtil=new QuartzSchedulerUtil();
                    switch ((int)map.get("status")){
                        case -1:
                            //删除
                            quartzSchedulerUtil.deleteJob(quartzTask.getJobName(),quartzTask.getJobGroup(),scheduler);
                            quartzTask.setStatus(-1);
                            break;
                        case 0:
                            //暂停
                            if(quartzTask.getStatus()==1){
                                quartzSchedulerUtil.pauseJob(quartzTask.getJobName(),quartzTask.getJobGroup(),scheduler);
                                quartzTask.setStatus(0);
                            }
                            break;
                        case 2:
                            //恢复
                            if(quartzTask.getStatus()==0){
                                quartzSchedulerUtil.resumeJob(quartzTask.getJobName(),quartzTask.getJobGroup(),scheduler);
                                quartzTask.setStatus(1);
                            }
                            break;
                        case 1:
                            //立即执行
                            boolean status=quartzSchedulerUtil.beginJob(quartzTask.getJobName(),quartzTask.getJobGroup(),quartzTask.getCron(),scheduler);
                            if(status){
                                quartzTask.setStatus(1);
                            }
                            break;
                    }
                    quartzTaskService.update(quartzTask);
                    json.put("code",HttpCode.OK_CODE.getCode());
                    json.put("msg","更新成功");
                }
            }
        }catch (Exception e){
            logger.error("更新定时任务记录状态:"+e);
            json.put("msg","异常错误");
        }
        return json.toString();
    }

    /**
     * 编辑定时任务记录
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
            if(map!=null && map.containsKey("id") && map.containsKey("cron") && map.containsKey("description")){
                QuartzTask quartzTask=quartzTaskService.queryData(map.get("id").toString());
                if(quartzTask!=null && quartzTask.getCron().equals(map.get("cron").toString())){
                    //cron相等表明没有修改定时时间
                    quartzTask.setDescription(map.get("description").toString());
                }else{
                    //验证cron合法性
                    if(!CronExpression.isValidExpression(map.get("cron").toString())){
                        json.put("msg","计划输入格式错误，请重新输入");
                        return json.toString();
                    }
                    quartzTask.setCron(map.get("cron").toString());
                    quartzTask.setDescription(map.get("description").toString());
                    if(quartzTask.getStatus()==1){
                        // 状态=1 正常执行
                        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
                        Scheduler scheduler = schedulerFactoryBean.getScheduler();
                        QuartzSchedulerUtil quartzSchedulerUtil=new QuartzSchedulerUtil();
                        boolean status=quartzSchedulerUtil.modifyJob(quartzTask.getJobName(),quartzTask.getJobGroup(),map.get("cron").toString(),scheduler);
                        if(status){
                            quartzTaskService.update(quartzTask);
                            json.put("msg","修改成功");
                            json.put("code",HttpCode.OK_CODE.getCode());
                            return json.toString();
                        }
                    }
                }
                quartzTaskService.update(quartzTask);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }catch (Exception e){
            logger.error("编辑定时任务记录:"+e);
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 删除定时任务记录
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
            QuartzTask quartzTask=quartzTaskService.queryData(map.get("id").toString());
            if(quartzTask!=null){
                quartzTaskService.update(quartzTask);
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
     * 根据主键id查询定时任务记录
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
            QuartzTask quartzTask=quartzTaskService.queryData(map.get("id").toString());
            if(quartzTask!=null){
                json.put("msg","查询成功");
                json.put("data",JSONObject.fromObject(quartzTask));
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询定时任务记录列表（也可以不分页）
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
            int totalCount =quartzTaskService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<QuartzTask> list =quartzTaskService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 系统设置--任务管理--查询任务列表
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
        List<Map<String,Object>> list =quartzTaskService.getList(map);
        if(list!=null && list.size()>0){
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Timestamp.class,new JsonArrayValueProcessor());
            json.put("msg","查询成功");
            json.put("data",JSONArray.fromObject(list,jsonConfig));
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 使用正则表达式来校验cron表达式
     * @param cron
     * @return
     */
    private boolean judgeCron(String cron){
        String regEx = "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$";
        return cron.matches(regEx);
    }

}
