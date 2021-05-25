package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.*;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.*;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.JsonArrayValueProcessor;
import com.jyxd.web.util.UUIDUtil;
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

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/medOrderExec")
public class MedOrderExecController {

    private static Logger logger = LoggerFactory.getLogger(MedOrderExecController.class);

    @Autowired
    private MedOrderExecService medOrderExecService;

    @Autowired
    private InputAmountService inputAmountService;

    @Autowired
    private InOutAmountService inOutAmountService;

    @Autowired
    private MedOrderExecSyncService medOrderExecSyncService;

    @Autowired
    private NursingRecordService nursingRecordService;

    /**
     * 增加一条医嘱执行表记录
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody MedOrderExec medOrderExec) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "添加失败");
        medOrderExec.setId(UUIDUtil.getUUID());
        medOrderExec.setCreateTime(new Date());
        medOrderExecService.insert(medOrderExec);
        json.put("code", HttpCode.OK_CODE.getCode());
        json.put("msg", "添加成功");
        return json.toString();
    }

    /**
     * 更新医嘱执行表记录状态
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "更新失败");
        if (map != null && map.containsKey("id") && map.containsKey("status")) {
            MedOrderExec medOrderExec = medOrderExecService.queryData(map.get("id").toString());
            if (medOrderExec != null) {

                medOrderExecService.update(medOrderExec);
                json.put("msg", "更新成功");
            } else {
                json.put("msg", "更新失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑医嘱执行表记录单
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "编辑失败");
        if (map != null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")) {
            MedOrderExec medOrderExec = medOrderExecService.queryData(map.get("id").toString());
            if (medOrderExec != null) {

                medOrderExecService.update(medOrderExec);
                json.put("msg", "编辑成功");
            } else {
                json.put("msg", "编辑失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());

        return json.toString();
    }

    /**
     * 删除医嘱执行表记录
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "删除失败");
        if (map.containsKey("id")) {
            MedOrderExec medOrderExec = medOrderExecService.queryData(map.get("id").toString());
            if (medOrderExec != null) {

                medOrderExecService.update(medOrderExec);
                json.put("msg", "删除成功");
            } else {
                json.put("msg", "删除失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询医嘱执行表记录
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData", method = RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("id")) {
            MedOrderExec medOrderExec = medOrderExecService.queryData(map.get("id").toString());
            if (medOrderExec != null) {
                json.put("msg", "查询成功");
                json.put("data", JSONObject.fromObject(medOrderExec));
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询医嘱执行表记录列表（也可以不分页）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("start")) {
            int totalCount = medOrderExecService.queryNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<MedOrderExec> list = medOrderExecService.queryList(map);
        if (list != null && list.size() > 0) {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonArrayValueProcessor());
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(list, jsonConfig));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 医嘱处理--医嘱列表--同步到入量
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/synchronousInPutTest", method = RequestMethod.POST)
    @ResponseBody
    public String synchronousInPutTest(@RequestBody(required = false) Map<String, Object> map, HttpSession session) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "同步失败");
        try {
            User user = (User) session.getAttribute("user");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据医嘱主键查询医嘱列表
            List<MedOrderExec> list = medOrderExecService.queryList(map);
            if (list != null && list.size() > 0) {
                System.out.println(list.toString());
                for (int i = 0; i < list.size(); i++) {
                    MedOrderExec medOrderExec = list.get(i);

                    //新建一个入量
                    InputAmount inputAmount = new InputAmount();
                    inputAmount.setId(UUIDUtil.getUUID());
                    inputAmount.setCreateTime(new Date());
                    inputAmount.setStatus(1);
                    if (user != null) {
                        inputAmount.setOperatorCode(user.getLoginName());
                    }
                    inputAmount.setVisitId(medOrderExec.getVisitId());
                    inputAmount.setVisitCode(medOrderExec.getVisitCode());
                    inputAmount.setPatientId(medOrderExec.getPatientId());
                    inputAmount.setDosage(medOrderExec.getDosage());//剂量
                    inputAmount.setDosageUnits(medOrderExec.getDosageUnits());//剂量单位
                    inputAmount.setOrderNo(medOrderExec.getOrderNo());//医嘱主键
                    inputAmount.setOrderSubNo(medOrderExec.getOrderSubNo());//子医嘱主键
                    inputAmount.setOrderCode(medOrderExec.getOrderCode());//医嘱编码（同步时使用）
                    inputAmount.setOrderName(medOrderExec.getOrderName());//医嘱名称
                    inputAmount.setSpeed(medOrderExec.getPerformSpeed());//流速
                    inputAmount.setUseMode(medOrderExec.getUseMode());//给药途径
                    inputAmount.setOrderType(map.get("orderType").toString());//入量类型
                    inputAmount.setCheckSignature(map.get("checkSignature").toString());//核对人签名(执行人)
                    inputAmount.setDataTime(format.parse(map.get("dataTime").toString()));//记录时间(同步时间)
                    inputAmount.setRemark(medOrderExec.getRemark());//医嘱嘱托
                    inputAmountService.insert(inputAmount);

                    //修改医嘱同步状态和次数
                    medOrderExec.setIsSync(1);//是否已同步到护理单，0：未同步；1：已同步；
                    medOrderExec.setRecentSyncTime(new Date());//最新同步时间
                    medOrderExec.setSyncNum(medOrderExec.getSyncNum() - 1);//剩余同步次数 默认1
                    medOrderExec.setUpdateTime(new Date());
                    medOrderExec.setOrderStatus("2");//执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
                    medOrderExecService.update(medOrderExec);


                }
                //新建一条 医嘱执行同步表 记录
                MedOrderExecSync medOrderExecSync = new MedOrderExecSync();
                medOrderExecSync.setId(UUIDUtil.getUUID());
                medOrderExecSync.setCreateTime(new Date());
                medOrderExecSync.setDataTime(format.parse(map.get("dataTime").toString()));
                medOrderExecSync.setDefaultTimePoint(format.parse(map.get("dataTime").toString()));
                if (user != null) {
                    medOrderExecSync.setOperatorCode(user.getLoginName());
                }
                medOrderExecSync.setOrderNo(map.get("orderType").toString());
                medOrderExecSync.setPatientId(map.get("patientId").toString());
                medOrderExecSync.setOrderType(map.get("orderType").toString());
                medOrderExecSync.setUseMode(map.get("useMode").toString());
                medOrderExecSync.setVisitId(map.get("visitId").toString());
                medOrderExecSync.setVisitCode(map.get("visitCode").toString());
                medOrderExecSync.setNurseCode(map.get("checkSignature").toString());
                medOrderExecSyncService.insert(medOrderExecSync);

                json.put("msg", "同步成功");
                json.put("code", HttpCode.OK_CODE.getCode());
            }
        } catch (Exception e) {
            logger.info("医嘱处理--医嘱列表--同步到入量 synchronousInPut:" + e);
        }
        return json.toString();
    }

    /**
     * 医嘱处理--医嘱列表--同步到入量
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/synchronousInPut", method = RequestMethod.POST)
    @ResponseBody
    public String synchronousInPut(@RequestBody(required = false) Map<String, Object> map, HttpSession session) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "同步失败");
        try {
            User user = (User) session.getAttribute("user");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //根据医嘱主键查询医嘱列表
            List<MedOrderExec> list = medOrderExecService.queryList(map);
            if (list != null && list.size() > 0) {
                System.out.println(list.toString());
                for (int i = 0; i < list.size(); i++) {
                    MedOrderExec medOrderExec = list.get(i);

                    //新建一个入量
                    InOutAmount inOutAmount = new InOutAmount();
                    inOutAmount.setId(UUIDUtil.getUUID());
                    inOutAmount.setCreateTime(new Date());
                    inOutAmount.setStatus(1);
                    if (user != null) {
                        inOutAmount.setOperatorCode(user.getLoginName());
                    }
                    inOutAmount.setVisitId(medOrderExec.getVisitId());
                    inOutAmount.setVisitCode(medOrderExec.getVisitCode());
                    inOutAmount.setPatientId(medOrderExec.getPatientId());
                    inOutAmount.setDosage(medOrderExec.getDosage());//剂量
                    inOutAmount.setOrderNo(medOrderExec.getOrderNo());//医嘱主键
                    inOutAmount.setOrderSubNo(medOrderExec.getOrderSubNo());//子医嘱主键
                    inOutAmount.setOrderCode(medOrderExec.getOrderCode());//医嘱编码（同步时使用）
                    inOutAmount.setOrderName(medOrderExec.getOrderName());//医嘱名称
                    inOutAmount.setCheckSignature(map.get("checkSignature").toString());//核对人签名(执行人)
                    inOutAmount.setDataTime(format.parse(map.get("dataTime").toString()));//记录时间(同步时间)
                    inOutAmount.setRemark(medOrderExec.getRemark());//医嘱嘱托
                    inOutAmountService.insert(inOutAmount);

                    //修改医嘱同步状态和次数

                    medOrderExec.setIsSync(1);//是否已同步到护理单，0：未同步；1：已同步；
                    medOrderExec.setRecentSyncTime(new Date());//最新同步时间
                    medOrderExec.setSyncNum(medOrderExec.getSyncNum() - 1);//剩余同步次数 默认1
                    medOrderExec.setUpdateTime(new Date());
                    medOrderExec.setOrderStatus("2");//执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
                    medOrderExecService.update(medOrderExec);


                }
                //新建一条 医嘱执行同步表 记录
                MedOrderExecSync medOrderExecSync = new MedOrderExecSync();
                medOrderExecSync.setId(UUIDUtil.getUUID());
                medOrderExecSync.setCreateTime(new Date());
                medOrderExecSync.setDataTime(format.parse(map.get("dataTime").toString()));
                medOrderExecSync.setDefaultTimePoint(format.parse(map.get("dataTime").toString()));
                if (user != null) {
                    medOrderExecSync.setOperatorCode(user.getLoginName());
                }
                medOrderExecSync.setOrderNo(map.get("orderType").toString());
                medOrderExecSync.setPatientId(map.get("patientId").toString());
                medOrderExecSync.setOrderType(map.get("orderType").toString());
                medOrderExecSync.setUseMode(map.get("useMode").toString());
                medOrderExecSync.setVisitId(map.get("visitId").toString());
                medOrderExecSync.setVisitCode(map.get("visitCode").toString());
                medOrderExecSync.setNurseCode(map.get("checkSignature").toString());
                medOrderExecSyncService.insert(medOrderExecSync);

                json.put("msg", "同步成功");
                json.put("code", HttpCode.OK_CODE.getCode());
            }
        } catch (Exception e) {
            logger.info("医嘱处理--医嘱列表--同步到入量 synchronousInPut:" + e);
        }
        return json.toString();
    }

    /**
     * 医嘱处理--医嘱列表--同步到护理措施
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/synchronousNursingRecord", method = RequestMethod.POST)
    @ResponseBody
    public String synchronousNursingRecord(@RequestBody(required = false) Map<String, Object> map, HttpSession session) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "同步失败");
        try {
            User user = (User) session.getAttribute("user");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //新增一条护理措施记录 保存内容
            NursingRecord nursingRecord = new NursingRecord();
            nursingRecord.setId(UUIDUtil.getUUID());
            nursingRecord.setCreateTime(new Date());
            nursingRecord.setDataTime(format.parse(map.get("dataTime").toString()));
            nursingRecord.setVisitId(map.get("visitId").toString());
            nursingRecord.setVisitCode(map.get("visitCode").toString());
            nursingRecord.setPatientId(map.get("patientId").toString());
            nursingRecord.setStatus(1);
            if (user != null) {
                nursingRecord.setOperatorCode(user.getLoginName());
            }
            nursingRecord.setCode("nursingRecordContent");//nursingRecordContent 默认为 医嘱列表同步到护理措施  内容
            nursingRecord.setContent(map.get("nursingRecordContent").toString());
            nursingRecordService.insert(nursingRecord);

            //新增一条护理措施记录 保存签名
            NursingRecord nursingRecord1 = new NursingRecord();
            nursingRecord1.setId(UUIDUtil.getUUID());
            nursingRecord1.setCreateTime(new Date());
            nursingRecord1.setDataTime(format.parse(map.get("dataTime").toString()));
            nursingRecord1.setVisitId(map.get("visitId").toString());
            nursingRecord1.setVisitCode(map.get("visitCode").toString());
            nursingRecord1.setPatientId(map.get("patientId").toString());
            nursingRecord1.setStatus(1);
            if (user != null) {
                nursingRecord1.setOperatorCode(user.getLoginName());
            }
            nursingRecord1.setCode("signature");//signature 默认为 医嘱列表同步到护理措施  签名
            nursingRecord1.setContent(map.get("signature").toString());
            nursingRecordService.insert(nursingRecord1);


            //新建一条 医嘱执行同步表 记录
            MedOrderExecSync medOrderExecSync = new MedOrderExecSync();
            medOrderExecSync.setId(UUIDUtil.getUUID());
            medOrderExecSync.setCreateTime(new Date());
            medOrderExecSync.setDataTime(format.parse(map.get("dataTime").toString()));
            medOrderExecSync.setDefaultTimePoint(format.parse(map.get("dataTime").toString()));
            if (user != null) {
                medOrderExecSync.setOperatorCode(user.getLoginName());
            }
            medOrderExecSync.setOrderNo(map.get("orderType").toString());
            medOrderExecSync.setPatientId(map.get("patientId").toString());
            medOrderExecSync.setNurseCode(map.get("signature").toString());
            medOrderExecSync.setContent(map.get("nursingRecordContent").toString());
            medOrderExecSync.setVisitId(map.get("visitId").toString());
            medOrderExecSync.setVisitCode(map.get("visitCode").toString());
            medOrderExecSyncService.insert(medOrderExecSync);

            json.put("msg", "同步成功");
            json.put("code", HttpCode.OK_CODE.getCode());
        } catch (Exception e) {
            logger.info("医嘱处理--医嘱列表--同步到护理措施 :" + e);
        }
        return json.toString();
    }

    /**
     * 根据条件分页查询医嘱执行表记录列表（也可以不分页）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("start")) {
            int totalCount = medOrderExecService.getNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<Map<String, Object>> list = medOrderExecService.getList(map);
        if (list != null && list.size() > 0) {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonArrayValueProcessor());
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(list, jsonConfig));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
