package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.data.basic.InOutAmount;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.CustomContentService;
import com.jyxd.web.service.basic.CustomFieldService;
import com.jyxd.web.service.basic.InOUtAmountStatisticsService;
import com.jyxd.web.service.basic.InOutAmountService;
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

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/primaryCare")
public class PrimaryCareController {

    private static Logger logger = LoggerFactory.getLogger(PrimaryCareController.class);

    @Autowired
    private InOutAmountService inOutAmountService;

    @Autowired
    private CustomFieldService customFieldService;

    @Autowired
    private CustomContentService customContentService;
    @Autowired
    private InOUtAmountStatisticsService inOUtAmountStatisticsService;

    /**
     * 增加一条出入量表（二表合一）记录
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InOutAmount inOutAmount) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "添加失败");
        inOutAmount.setId(UUIDUtil.getUUID());
        inOutAmount.setCreateTime(new Date());
        inOutAmountService.insert(inOutAmount);
        json.put("code", HttpCode.OK_CODE.getCode());
        json.put("msg", "添加成功");
        return json.toString();
    }

    /**
     * 更新出入量表（二表合一）状态
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
            InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
            if (inOutAmount != null) {
                inOutAmount.setStatus((int) map.get("status"));
                inOutAmountService.update(inOutAmount);
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
     * 编辑出入量表（二表合一）记录单
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
            InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
            if (inOutAmount != null) {
                inOutAmount.setStatus((int) map.get("status"));
                inOutAmountService.update(inOutAmount);
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
     * 删除出入量表（二表合一）记录
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
            InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
            if (inOutAmount != null) {
                inOutAmount.setStatus(-1);
                inOutAmountService.update(inOutAmount);
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
     * 根据主键id查询出入量表（二表合一）记录
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
            InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
            if (inOutAmount != null) {
                json.put("msg", "查询成功");
                json.put("data", JSONObject.fromObject(inOutAmount));
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInOutAmountList", method = RequestMethod.POST)
    @ResponseBody
    public String getInOutAmountList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map == null || !map.containsKey("patientId") || StringUtils.isEmpty(map.get("patientId").toString())) {
            json.put("code", HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg", "请选择病人");
            return json.toString();
        }
        if (map != null && map.containsKey("start")) {
            int totalCount = inOutAmountService.getInOutAmountNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        //先查询有哪些自定义字段，放入查询条件中
        Map<String, Object> m = new HashMap<>();
        m.put("status", 1);
        m.put("associatedTable", "table_in_out_amount");//关联表名
        List<CustomField> list = customFieldService.queryList(m);
        if (list != null && list.size() > 0) {
            map.put("list", list);
        }
        List<Map<String, Object>> amountList = inOutAmountService.getInOutAmountList(map);
        if (amountList != null && amountList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(amountList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询并统计出入量列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public String statistics(@RequestBody(required = false) Map<String, Object> map) throws ParseException {
        return inOUtAmountStatisticsService.statistics(map);
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-保存出入量
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveData")
    @ResponseBody
    public String saveData(@RequestBody(required = false) Map<String, Object> map, HttpSession session) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "失败");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = (User) session.getAttribute("user");
        try {
            if (map != null && map.containsKey("id")) {
                //有id 编辑
                InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
                if (inOutAmount != null) {
                    inOutAmount.setStatus(map.containsKey("status") && StringUtils.isNotEmpty(map.get("status").toString()) ? Integer.valueOf(map.get("status").toString()) : 1);
                    inOutAmount.setDataTime(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString()) ? format.parse(map.get("dataTime").toString()) : null);
                    inOutAmount.setOrderCode(map.containsKey("orderCode") && StringUtils.isNotEmpty(map.get("orderCode").toString()) ? map.get("orderCode").toString() : null);
                    inOutAmount.setOrderNo(map.containsKey("orderNo") && StringUtils.isNotEmpty(map.get("orderNo").toString()) ? map.get("orderNo").toString() : null);
                    inOutAmount.setOrderSubNo(map.containsKey("orderSubNo") && StringUtils.isNotEmpty(map.get("orderSubNo").toString()) ? map.get("orderSubNo").toString() : null);
                    inOutAmount.setOrderName(map.containsKey("orderName") && StringUtils.isNotEmpty(map.get("orderName").toString()) ? map.get("orderName").toString() : null);
                    inOutAmount.setDosage(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString()) ? map.get("dosage").toString() : null);
                    inOutAmount.setAllowanceDosage(map.containsKey("allowanceDosage") && StringUtils.isNotEmpty(map.get("allowanceDosage").toString()) ? map.get("allowanceDosage").toString() : null);
                    inOutAmount.setPiss(map.containsKey("piss") && StringUtils.isNotEmpty(map.get("piss").toString()) ? map.get("piss").toString() : null);
                    inOutAmount.setFaces(map.containsKey("faces") && StringUtils.isNotEmpty(map.get("faces").toString()) ? map.get("faces").toString() : null);
                    inOutAmount.setFaces(map.containsKey("faces") && StringUtils.isNotEmpty(map.get("faces").toString()) ? map.get("faces").toString() : null);
                    inOutAmount.setDrainage(map.containsKey("drainage") && StringUtils.isNotEmpty(map.get("drainage").toString()) ? map.get("drainage").toString() : null);
                    inOutAmount.setRemark(map.containsKey("remark") && StringUtils.isNotEmpty(map.get("remark").toString()) ? map.get("remark").toString() : null);
                    inOutAmount.setSignature(map.containsKey("signature") && StringUtils.isNotEmpty(map.get("signature").toString()) ? map.get("signature").toString() : null);
                    inOutAmount.setCheckSignature(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString()) ? map.get("checkSignature").toString() : null);
                    inOutAmount.setIllnessRecords(map.containsKey("illnessRecords") && StringUtils.isNotEmpty(map.get("illnessRecords").toString()) ? map.get("illnessRecords").toString() : null);
                    inOutAmount.setBalance(map.containsKey("balance") && StringUtils.isNotEmpty(map.get("balance").toString()) ? map.get("balance").toString() : null);
                    if (user != null) {
                        inOutAmount.setOperatorCode(user.getLoginName());
                    }
                    inOutAmount.setType(map.containsKey("type") && StringUtils.isNotEmpty(map.get("type").toString()) ? Integer.valueOf(map.get("type").toString()) : 1);
                    inOutAmountService.update(inOutAmount);
                    //根据时间 病人主键id 表名 查询对象
                    List<CustomContent> list = customContentService.getCustomContentByTime(map);
                    if (list != null && list.size() > 0) {
                        CustomContent customContent = list.get(0);
                        if (customContent != null) {
                            customContent.setContentOne(map.containsKey("contentOne") && StringUtils.isNotEmpty(map.get("contentOne").toString()) ? map.get("contentOne").toString() : null);
                            customContent.setContentTwo(map.containsKey("contentTwo") && StringUtils.isNotEmpty(map.get("contentTwo").toString()) ? map.get("contentTwo").toString() : null);
                            customContent.setContentThree(map.containsKey("contentThree") && StringUtils.isNotEmpty(map.get("contentThree").toString()) ? map.get("contentThree").toString() : null);
                            customContent.setContentFour(map.containsKey("contentFour") && StringUtils.isNotEmpty(map.get("contentFour").toString()) ? map.get("contentFour").toString() : null);
                            customContent.setContentFive(map.containsKey("contentFive") && StringUtils.isNotEmpty(map.get("contentFive").toString()) ? map.get("contentFive").toString() : null);
                            customContent.setContentSix(map.containsKey("contentSix") && StringUtils.isNotEmpty(map.get("contentSix").toString()) ? map.get("contentSix").toString() : null);
                            customContent.setContentSeven(map.containsKey("contentSeven") && StringUtils.isNotEmpty(map.get("contentSeven").toString()) ? map.get("contentSeven").toString() : null);
                            customContent.setContentEight(map.containsKey("contentEight") && StringUtils.isNotEmpty(map.get("contentEight").toString()) ? map.get("contentEight").toString() : null);
                            customContent.setContentNine(map.containsKey("contentNine") && StringUtils.isNotEmpty(map.get("contentNine").toString()) ? map.get("contentNine").toString() : null);
                            customContent.setContentTen(map.containsKey("contentTen") && StringUtils.isNotEmpty(map.get("contentTen").toString()) ? map.get("contentTen").toString() : null);
                            customContentService.update(customContent);
                        } else {
                            CustomContent data = new CustomContent();
                            data.setId(UUIDUtil.getUUID());
                            data.setAssociatedTable(map.get("associatedTable").toString());
                            data.setPatientId(map.get("patientId").toString());
                            data.setDataTime(format.parse(map.get("dataTime").toString()));
                            data.setCreateTime(new Date());
                            data.setContentOne(map.containsKey("contentOne") && StringUtils.isNotEmpty(map.get("contentOne").toString()) ? map.get("contentOne").toString() : null);
                            data.setContentTwo(map.containsKey("contentTwo") && StringUtils.isNotEmpty(map.get("contentTwo").toString()) ? map.get("contentTwo").toString() : null);
                            data.setContentThree(map.containsKey("contentThree") && StringUtils.isNotEmpty(map.get("contentThree").toString()) ? map.get("contentThree").toString() : null);
                            data.setContentFour(map.containsKey("contentFour") && StringUtils.isNotEmpty(map.get("contentFour").toString()) ? map.get("contentFour").toString() : null);
                            data.setContentFive(map.containsKey("contentFive") && StringUtils.isNotEmpty(map.get("contentFive").toString()) ? map.get("contentFive").toString() : null);
                            data.setContentSix(map.containsKey("contentSix") && StringUtils.isNotEmpty(map.get("contentSix").toString()) ? map.get("contentSix").toString() : null);
                            data.setContentSeven(map.containsKey("contentSeven") && StringUtils.isNotEmpty(map.get("contentSeven").toString()) ? map.get("contentSeven").toString() : null);
                            data.setContentEight(map.containsKey("contentEight") && StringUtils.isNotEmpty(map.get("contentEight").toString()) ? map.get("contentEight").toString() : null);
                            data.setContentNine(map.containsKey("contentNine") && StringUtils.isNotEmpty(map.get("contentNine").toString()) ? map.get("contentNine").toString() : null);
                            data.setContentTen(map.containsKey("contentTen") && StringUtils.isNotEmpty(map.get("contentTen").toString()) ? map.get("contentTen").toString() : null);
                            customContentService.insert(data);
                        }
                    }
                    json.put("msg", "更新成功");
                    json.put("code", HttpCode.OK_CODE.getCode());
                }
            } else {
                //没id 新增
                InOutAmount inOutAmount = new InOutAmount();
                Date newDate = new Date();
                inOutAmount.setId(UUIDUtil.getUUID());
                inOutAmount.setCreateTime(newDate);
                inOutAmount.setStatus(1);
                inOutAmount.setVisitId(map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString()) ? map.get("visitId").toString() : null);
                inOutAmount.setVisitCode(map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString()) ? map.get("visitCode").toString() : null);
                inOutAmount.setPatientId(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString()) ? map.get("patientId").toString() : null);
                inOutAmount.setDataTime(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString()) ? format.parse(map.get("dataTime").toString()) : null);
                inOutAmount.setOrderCode(map.containsKey("orderCode") && StringUtils.isNotEmpty(map.get("orderCode").toString()) ? map.get("orderCode").toString() : null);
                inOutAmount.setOrderNo(map.containsKey("orderNo") && StringUtils.isNotEmpty(map.get("orderNo").toString()) ? map.get("orderNo").toString() : null);
                inOutAmount.setOrderSubNo(map.containsKey("orderSubNo") && StringUtils.isNotEmpty(map.get("orderSubNo").toString()) ? map.get("orderSubNo").toString() : null);
                inOutAmount.setOrderName(map.containsKey("orderName") && StringUtils.isNotEmpty(map.get("orderName").toString()) ? map.get("orderName").toString() : null);
                inOutAmount.setDosage(map.containsKey("dosage") && StringUtils.isNotEmpty(map.get("dosage").toString()) ? map.get("dosage").toString() : null);
                inOutAmount.setAllowanceDosage(map.containsKey("allowanceDosage") && StringUtils.isNotEmpty(map.get("allowanceDosage").toString()) ? map.get("allowanceDosage").toString() : null);
                inOutAmount.setPiss(map.containsKey("piss") && StringUtils.isNotEmpty(map.get("piss").toString()) ? map.get("piss").toString() : null);
                inOutAmount.setFaces(map.containsKey("faces") && StringUtils.isNotEmpty(map.get("faces").toString()) ? map.get("faces").toString() : null);
                inOutAmount.setFaces(map.containsKey("faces") && StringUtils.isNotEmpty(map.get("faces").toString()) ? map.get("faces").toString() : null);
                inOutAmount.setDrainage(map.containsKey("drainage") && StringUtils.isNotEmpty(map.get("drainage").toString()) ? map.get("drainage").toString() : null);
                inOutAmount.setRemark(map.containsKey("remark") && StringUtils.isNotEmpty(map.get("remark").toString()) ? map.get("remark").toString() : null);
                inOutAmount.setSignature(map.containsKey("signature") && StringUtils.isNotEmpty(map.get("signature").toString()) ? map.get("signature").toString() : null);
                inOutAmount.setCheckSignature(map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("checkSignature").toString()) ? map.get("checkSignature").toString() : null);
                inOutAmount.setIllnessRecords(map.containsKey("illnessRecords") && StringUtils.isNotEmpty(map.get("illnessRecords").toString()) ? map.get("illnessRecords").toString() : null);
                inOutAmount.setBalance(map.containsKey("balance") && StringUtils.isNotEmpty(map.get("balance").toString()) ? map.get("balance").toString() : null);
                if (user != null) {
                    inOutAmount.setOperatorCode(user.getLoginName());
                }
                inOutAmount.setType(map.containsKey("type") && StringUtils.isNotEmpty(map.get("type").toString()) ? Integer.valueOf(map.get("type").toString()) : 1);
                inOutAmountService.insert(inOutAmount);
                //新增自定义字段内容表
                if (map.containsKey("associatedTable") && StringUtils.isNotEmpty(map.get("associatedTable").toString())) {
                    CustomContent customContent = new CustomContent();
                    customContent.setId(UUIDUtil.getUUID());
                    customContent.setAssociatedTable(map.get("associatedTable").toString());
                    customContent.setPatientId(map.get("patientId").toString());
                    customContent.setDataTime(format.parse(map.get("dataTime").toString()));
                    customContent.setCreateTime(newDate);
                    customContent.setContentOne(map.containsKey("contentOne") && StringUtils.isNotEmpty(map.get("contentOne").toString()) ? map.get("contentOne").toString() : null);
                    customContent.setContentTwo(map.containsKey("contentTwo") && StringUtils.isNotEmpty(map.get("contentTwo").toString()) ? map.get("contentTwo").toString() : null);
                    customContent.setContentThree(map.containsKey("contentThree") && StringUtils.isNotEmpty(map.get("contentThree").toString()) ? map.get("contentThree").toString() : null);
                    customContent.setContentFour(map.containsKey("contentFour") && StringUtils.isNotEmpty(map.get("contentFour").toString()) ? map.get("contentFour").toString() : null);
                    customContent.setContentFive(map.containsKey("contentFive") && StringUtils.isNotEmpty(map.get("contentFive").toString()) ? map.get("contentFive").toString() : null);
                    customContent.setContentSix(map.containsKey("contentSix") && StringUtils.isNotEmpty(map.get("contentSix").toString()) ? map.get("contentSix").toString() : null);
                    customContent.setContentSeven(map.containsKey("contentSeven") && StringUtils.isNotEmpty(map.get("contentSeven").toString()) ? map.get("contentSeven").toString() : null);
                    customContent.setContentEight(map.containsKey("contentEight") && StringUtils.isNotEmpty(map.get("contentEight").toString()) ? map.get("contentEight").toString() : null);
                    customContent.setContentNine(map.containsKey("contentNine") && StringUtils.isNotEmpty(map.get("contentNine").toString()) ? map.get("contentNine").toString() : null);
                    customContent.setContentTen(map.containsKey("contentTen") && StringUtils.isNotEmpty(map.get("contentTen").toString()) ? map.get("contentTen").toString() : null);
                    customContentService.insert(customContent);
                }
                json.put("code", HttpCode.OK_CODE.getCode());
                json.put("msg", "成功");
            }
        } catch (Exception e) {
            logger.info("病人管理-护理文书-护理单文书-出入量-保存出入量:" + e);
        }
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表(新的直接查10个字段)
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryInOutAmountList", method = RequestMethod.POST)
    @ResponseBody
    public String queryInOutAmountList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map == null || !map.containsKey("patientId") || StringUtils.isEmpty(map.get("patientId").toString())) {
            json.put("code", HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg", "请选择病人");
            return json.toString();
        }
        if (map != null && map.containsKey("start")) {
            int totalCount = inOutAmountService.getInOutAmountNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        //先查询有哪些自定义字段，放入查询条件中
        Map<String, Object> m = new HashMap<>();
        m.put("status", 1);
        m.put("associatedTable", "table_in_out_amount");//关联表名
        List<CustomField> list = customFieldService.queryList(m);
        if (list != null && list.size() > 0) {
            map.put("list", list);
        }
        List<Map<String, Object>> amountList = inOutAmountService.queryInOutAmountList(map);
        if (amountList != null && amountList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(amountList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
