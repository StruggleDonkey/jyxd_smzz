package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.PrimaryCare;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.*;
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

import static com.jyxd.web.util.DateUtil.*;

@Controller
@RequestMapping(value = "/primaryCare")
public class PrimaryCareController {

    private static Logger logger = LoggerFactory.getLogger(PrimaryCareController.class);

    @Autowired
    private PrimaryCareService primaryCareService;

    @Autowired
    private CustomContentService customContentService;


    /**
     * 增加一条出入量表（二表合一）记录
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody PrimaryCare primaryCare) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "添加失败");
        primaryCare.setId(UUIDUtil.getUUID());
        primaryCare.setCreateTime(new Date());
        primaryCareService.insert(primaryCare);
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
            PrimaryCare primaryCare = primaryCareService.queryData(map.get("id").toString());
            if (primaryCare != null) {
                primaryCare.setStatus((int) map.get("status"));
                primaryCareService.update(primaryCare);
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
            PrimaryCare primaryCare = primaryCareService.queryData(map.get("id").toString());
            if (primaryCare != null) {
                primaryCare.setStatus((int) map.get("status"));
                primaryCareService.update(primaryCare);
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
            PrimaryCare primaryCare = primaryCareService.queryData(map.get("id").toString());
            if (primaryCare != null) {
                primaryCare.setStatus(-1);
                primaryCareService.update(primaryCare);
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
            PrimaryCare primaryCare = primaryCareService.queryData(map.get("id").toString());
            if (primaryCare != null) {
                json.put("msg", "查询成功");
                json.put("data", JSONObject.fromObject(primaryCare));
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询基础护理表(新版-吕梁医院使用)列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPrimaryCareList", method = RequestMethod.POST)
    @ResponseBody
    public String getPrimaryCareList(@RequestBody(required = false) Map<String, Object> map) {
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
            int totalCount = primaryCareService.getPrimaryCareNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<Map<String, Object>> primaryCareList = primaryCareService.getPrimaryCareList(map);
        if (primaryCareList != null && primaryCareList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(primaryCareList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询基础护理表(新版-吕梁医院使用)列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getPrimaryCareListByStartTime", method = RequestMethod.POST)
    @ResponseBody
    public String getPrimaryCareListByStartTime(@RequestBody(required = false) Map<String, Object> map) throws ParseException {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map == null || !map.containsKey("patientId") || StringUtils.isEmpty(map.get("patientId").toString())) {
            json.put("code", HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg", "请选择病人");
            return json.toString();
        }
        if (!map.containsKey("hour")) {
            json.put("msg", "查询时间跨度小时为空，查询失败");
            return json.toString();
        }
        if (!map.containsKey("startTime")) {
            json.put("msg", "查询开始时间为空，查询失败");
            return json.toString();
        }
        Date endTime = getLaterHoursDate(yyyyMMddHHmmssSdfToDate(String.valueOf(map.get("startTime"))), Long.valueOf(String.valueOf(map.get("hour"))));
        map.put("endTime", yyyyMMddSdfToString(endTime));
        if (map.containsKey("start")) {
            int totalCount = primaryCareService.getPrimaryCareNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<Map<String, Object>> primaryCareList = primaryCareService.getPrimaryCareList(map);
        if (primaryCareList != null && primaryCareList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(primaryCareList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }


    /**
     * 病人管理-护理文书-护理单文书-基础护理-保存基础护理表(新版-吕梁医院使用)
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
                PrimaryCare primaryCare = primaryCareService.queryData(map.get("id").toString());
                if (primaryCare != null) {
                    primaryCare.setStatus(map.containsKey("status") && StringUtils.isNotEmpty(map.get("status").toString()) ? Integer.valueOf(map.get("status").toString()) : 1);
                    primaryCare.setDataTime(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString()) ? format.parse(map.get("dataTime").toString()) : null);
                    primaryCare.setStrengthLeftTop(map.containsKey("strengthLeftTop") && StringUtils.isNotEmpty(map.get("strengthLeftTop").toString()) ? map.get("strengthLeftTop").toString() : null);
                    primaryCare.setStrengthLeftDown(map.containsKey("strengthLeftDown") && StringUtils.isNotEmpty(map.get("strengthLeftDown").toString()) ? map.get("strengthLeftDown").toString() : null);
                    primaryCare.setStrengthRightTop(map.containsKey("strengthRightTop") && StringUtils.isNotEmpty(map.get("strengthRightTop").toString()) ? map.get("strengthRightTop").toString() : null);
                    primaryCare.setStrengthRightDown(map.containsKey("strengthRightDown") && StringUtils.isNotEmpty(map.get("strengthRightDown").toString()) ? map.get("strengthRightDown").toString() : null);
                    primaryCare.setOral(map.containsKey("oral") && StringUtils.isNotEmpty(map.get("oral").toString()) ? map.get("oral").toString() : null);
                    primaryCare.setUrethral(map.containsKey("urethral") && StringUtils.isNotEmpty(map.get("urethral").toString()) ? map.get("urethral").toString() : null);
                    primaryCare.setRollOver(map.containsKey("rollOver") && StringUtils.isNotEmpty(map.get("rollOver").toString()) ? map.get("rollOver").toString() : null);
                    primaryCare.setTakeBack(map.containsKey("takeBack") && StringUtils.isNotEmpty(map.get("takeBack").toString()) ? map.get("takeBack").toString() : null);
                    primaryCare.setMassage(map.containsKey("massage") && StringUtils.isNotEmpty(map.get("massage").toString()) ? map.get("massage").toString() : null);
                    primaryCare.setNasalFeeding(map.containsKey("nasalFeeding") && StringUtils.isNotEmpty(map.get("nasalFeeding").toString()) ? map.get("nasalFeeding").toString() : null);
                    primaryCare.setWetPhlegm(map.containsKey("wetPhlegm") && StringUtils.isNotEmpty(map.get("wetPhlegm").toString()) ? map.get("wetPhlegm").toString() : null);
                    primaryCare.setPhlegmCharacter(map.containsKey("phlegmCharacter") && StringUtils.isNotEmpty(map.get("phlegmCharacter").toString()) ? map.get("phlegmCharacter").toString() : null);
                    primaryCare.setRowCharacter(map.containsKey("rowCharacter") && StringUtils.isNotEmpty(map.get("rowCharacter").toString()) ? map.get("rowCharacter").toString() : null);
                    primaryCare.setAtomizationInhalation(map.containsKey("atomizationInhalation") && StringUtils.isNotEmpty(map.get("atomizationInhalation").toString()) ? map.get("atomizationInhalation").toString() : null);
                    primaryCare.setBlanketIceCap(map.containsKey("blanketIceCap") && StringUtils.isNotEmpty(map.get("blanketIceCap").toString()) ? map.get("blanketIceCap").toString() : null);
                    primaryCare.setSkin(map.containsKey("skin") && StringUtils.isNotEmpty(map.get("skin").toString()) ? map.get("skin").toString() : null);
                    primaryCare.setLowerLimbsCure(map.containsKey("lowerLimbsCure") && StringUtils.isNotEmpty(map.get("lowerLimbsCure").toString()) ? map.get("lowerLimbsCure").toString() : null);
                    primaryCare.setTubeLength(map.containsKey("tubeLength") && StringUtils.isNotEmpty(map.get("tubeLength").toString()) ? map.get("tubeLength").toString() : null);
                    primaryCare.setTubeUnobstructed(map.containsKey("tubeUnobstructed") && StringUtils.isNotEmpty(map.get("tubeUnobstructed").toString()) ? map.get("tubeUnobstructed").toString() : null);
                    primaryCare.setUrineTube(map.containsKey("urineTube") && StringUtils.isNotEmpty(map.get("urineTube").toString()) ? map.get("urineTube").toString() : null);
                    primaryCare.setDrainageTubeOne(map.containsKey("drainageTubeOne") && StringUtils.isNotEmpty(map.get("drainageTubeOne").toString()) ? map.get("drainageTubeOne").toString() : null);
                    primaryCare.setDrainageTubeTwo(map.containsKey("drainageTubeTwo") && StringUtils.isNotEmpty(map.get("drainageTubeTwo").toString()) ? map.get("drainageTubeTwo").toString() : null);
                    primaryCare.setDrainageTubeThree(map.containsKey("drainageTubeThree") && StringUtils.isNotEmpty(map.get("drainageTubeThree").toString()) ? map.get("drainageTubeThree").toString() : null);
                    primaryCare.setDrainageTubeFour(map.containsKey("drainageTubeFour") && StringUtils.isNotEmpty(map.get("drainageTubeFour").toString()) ? map.get("drainageTubeFour").toString() : null);
                    primaryCare.setDrainageTubeFive(map.containsKey("drainageTubeFive") && StringUtils.isNotEmpty(map.get("drainageTubeFive").toString()) ? map.get("drainageTubeFive").toString() : null);
                    primaryCare.setDrainageTubeSix(map.containsKey("drainageTubeSix") && StringUtils.isNotEmpty(map.get("drainageTubeSix").toString()) ? map.get("drainageTubeSix").toString() : null);
                    primaryCare.setRiskAssessmentOne(map.containsKey("riskAssessmentOne") && StringUtils.isNotEmpty(map.get("riskAssessmentOne").toString()) ? map.get("riskAssessmentOne").toString() : null);
                    primaryCare.setRiskAssessmentTwo(map.containsKey("riskAssessmentTwo") && StringUtils.isNotEmpty(map.get("riskAssessmentTwo").toString()) ? map.get("riskAssessmentTwo").toString() : null);
                    primaryCare.setRiskAssessmentThree(map.containsKey("riskAssessmentThree") && StringUtils.isNotEmpty(map.get("riskAssessmentThree").toString()) ? map.get("riskAssessmentThree").toString() : null);
                    primaryCare.setRiskAssessmentFour(map.containsKey("riskAssessmentFour") && StringUtils.isNotEmpty(map.get("riskAssessmentFour").toString()) ? map.get("riskAssessmentFour").toString() : null);
                    primaryCare.setRiskAssessmentFive(map.containsKey("riskAssessmentFive") && StringUtils.isNotEmpty(map.get("riskAssessmentFive").toString()) ? map.get("riskAssessmentFive").toString() : null);
                    primaryCare.setRiskAssessmentSix(map.containsKey("riskAssessmentSix") && StringUtils.isNotEmpty(map.get("riskAssessmentSix").toString()) ? map.get("riskAssessmentSix").toString() : null);
                    primaryCare.setRiskAssessmentSeven(map.containsKey("riskAssessmentSeven") && StringUtils.isNotEmpty(map.get("riskAssessmentSeven").toString()) ? map.get("riskAssessmentSeven").toString() : null);
                    primaryCare.setSignature(map.containsKey("signature") && StringUtils.isNotEmpty(map.get("signature").toString()) ? map.get("signature").toString() : null);
                    primaryCare.setSignatureAgain(map.containsKey("signatureAgain") && StringUtils.isNotEmpty(map.get("signatureAgain").toString()) ? map.get("signatureAgain").toString() : null);
                    if (user != null) {
                        primaryCare.setOperatorCode(user.getLoginName());
                    }
                    primaryCareService.update(primaryCare);
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
                PrimaryCare primaryCare = new PrimaryCare();
                Date newDate = new Date();
                primaryCare.setId(UUIDUtil.getUUID());
                primaryCare.setCreateTime(newDate);
                primaryCare.setStatus(1);
                primaryCare.setVisitId(map.containsKey("visitId") && StringUtils.isNotEmpty(map.get("visitId").toString()) ? map.get("visitId").toString() : null);
                primaryCare.setVisitCode(map.containsKey("visitCode") && StringUtils.isNotEmpty(map.get("visitCode").toString()) ? map.get("visitCode").toString() : null);
                primaryCare.setPatientId(map.containsKey("patientId") && StringUtils.isNotEmpty(map.get("patientId").toString()) ? map.get("patientId").toString() : null);
                primaryCare.setDataTime(map.containsKey("dataTime") && StringUtils.isNotEmpty(map.get("dataTime").toString()) ? format.parse(map.get("dataTime").toString()) : null);
                primaryCare.setStrengthLeftTop(map.containsKey("strengthLeftTop") && StringUtils.isNotEmpty(map.get("strengthLeftTop").toString()) ? map.get("strengthLeftTop").toString() : null);
                primaryCare.setStrengthLeftDown(map.containsKey("strengthLeftDown") && StringUtils.isNotEmpty(map.get("strengthLeftDown").toString()) ? map.get("strengthLeftDown").toString() : null);
                primaryCare.setStrengthRightTop(map.containsKey("strengthRightTop") && StringUtils.isNotEmpty(map.get("strengthRightTop").toString()) ? map.get("strengthRightTop").toString() : null);
                primaryCare.setStrengthRightDown(map.containsKey("strengthRightDown") && StringUtils.isNotEmpty(map.get("strengthRightDown").toString()) ? map.get("strengthRightDown").toString() : null);
                primaryCare.setOral(map.containsKey("oral") && StringUtils.isNotEmpty(map.get("oral").toString()) ? map.get("oral").toString() : null);
                primaryCare.setUrethral(map.containsKey("urethral") && StringUtils.isNotEmpty(map.get("urethral").toString()) ? map.get("urethral").toString() : null);
                primaryCare.setRollOver(map.containsKey("rollOver") && StringUtils.isNotEmpty(map.get("rollOver").toString()) ? map.get("rollOver").toString() : null);
                primaryCare.setTakeBack(map.containsKey("takeBack") && StringUtils.isNotEmpty(map.get("takeBack").toString()) ? map.get("takeBack").toString() : null);
                primaryCare.setMassage(map.containsKey("massage") && StringUtils.isNotEmpty(map.get("massage").toString()) ? map.get("massage").toString() : null);
                primaryCare.setNasalFeeding(map.containsKey("nasalFeeding") && StringUtils.isNotEmpty(map.get("nasalFeeding").toString()) ? map.get("nasalFeeding").toString() : null);
                primaryCare.setWetPhlegm(map.containsKey("wetPhlegm") && StringUtils.isNotEmpty(map.get("wetPhlegm").toString()) ? map.get("wetPhlegm").toString() : null);
                primaryCare.setPhlegmCharacter(map.containsKey("phlegmCharacter") && StringUtils.isNotEmpty(map.get("phlegmCharacter").toString()) ? map.get("phlegmCharacter").toString() : null);
                primaryCare.setRowCharacter(map.containsKey("rowCharacter") && StringUtils.isNotEmpty(map.get("rowCharacter").toString()) ? map.get("rowCharacter").toString() : null);
                primaryCare.setAtomizationInhalation(map.containsKey("atomizationInhalation") && StringUtils.isNotEmpty(map.get("atomizationInhalation").toString()) ? map.get("atomizationInhalation").toString() : null);
                primaryCare.setBlanketIceCap(map.containsKey("blanketIceCap") && StringUtils.isNotEmpty(map.get("blanketIceCap").toString()) ? map.get("blanketIceCap").toString() : null);
                primaryCare.setSkin(map.containsKey("skin") && StringUtils.isNotEmpty(map.get("skin").toString()) ? map.get("skin").toString() : null);
                primaryCare.setLowerLimbsCure(map.containsKey("lowerLimbsCure") && StringUtils.isNotEmpty(map.get("lowerLimbsCure").toString()) ? map.get("lowerLimbsCure").toString() : null);
                primaryCare.setTubeLength(map.containsKey("tubeLength") && StringUtils.isNotEmpty(map.get("tubeLength").toString()) ? map.get("tubeLength").toString() : null);
                primaryCare.setTubeUnobstructed(map.containsKey("tubeUnobstructed") && StringUtils.isNotEmpty(map.get("tubeUnobstructed").toString()) ? map.get("tubeUnobstructed").toString() : null);
                primaryCare.setUrineTube(map.containsKey("urineTube") && StringUtils.isNotEmpty(map.get("urineTube").toString()) ? map.get("urineTube").toString() : null);
                primaryCare.setDrainageTubeOne(map.containsKey("drainageTubeOne") && StringUtils.isNotEmpty(map.get("drainageTubeOne").toString()) ? map.get("drainageTubeOne").toString() : null);
                primaryCare.setDrainageTubeTwo(map.containsKey("drainageTubeTwo") && StringUtils.isNotEmpty(map.get("drainageTubeTwo").toString()) ? map.get("drainageTubeTwo").toString() : null);
                primaryCare.setDrainageTubeThree(map.containsKey("drainageTubeThree") && StringUtils.isNotEmpty(map.get("drainageTubeThree").toString()) ? map.get("drainageTubeThree").toString() : null);
                primaryCare.setDrainageTubeFour(map.containsKey("drainageTubeFour") && StringUtils.isNotEmpty(map.get("drainageTubeFour").toString()) ? map.get("drainageTubeFour").toString() : null);
                primaryCare.setDrainageTubeFive(map.containsKey("drainageTubeFive") && StringUtils.isNotEmpty(map.get("drainageTubeFive").toString()) ? map.get("drainageTubeFive").toString() : null);
                primaryCare.setDrainageTubeSix(map.containsKey("drainageTubeSix") && StringUtils.isNotEmpty(map.get("drainageTubeSix").toString()) ? map.get("drainageTubeSix").toString() : null);
                primaryCare.setRiskAssessmentOne(map.containsKey("riskAssessmentOne") && StringUtils.isNotEmpty(map.get("riskAssessmentOne").toString()) ? map.get("riskAssessmentOne").toString() : null);
                primaryCare.setRiskAssessmentTwo(map.containsKey("riskAssessmentTwo") && StringUtils.isNotEmpty(map.get("riskAssessmentTwo").toString()) ? map.get("riskAssessmentTwo").toString() : null);
                primaryCare.setRiskAssessmentThree(map.containsKey("riskAssessmentThree") && StringUtils.isNotEmpty(map.get("riskAssessmentThree").toString()) ? map.get("riskAssessmentThree").toString() : null);
                primaryCare.setRiskAssessmentFour(map.containsKey("riskAssessmentFour") && StringUtils.isNotEmpty(map.get("riskAssessmentFour").toString()) ? map.get("riskAssessmentFour").toString() : null);
                primaryCare.setRiskAssessmentFive(map.containsKey("riskAssessmentFive") && StringUtils.isNotEmpty(map.get("riskAssessmentFive").toString()) ? map.get("riskAssessmentFive").toString() : null);
                primaryCare.setRiskAssessmentSix(map.containsKey("riskAssessmentSix") && StringUtils.isNotEmpty(map.get("riskAssessmentSix").toString()) ? map.get("riskAssessmentSix").toString() : null);
                primaryCare.setRiskAssessmentSeven(map.containsKey("riskAssessmentSeven") && StringUtils.isNotEmpty(map.get("riskAssessmentSeven").toString()) ? map.get("riskAssessmentSeven").toString() : null);
                primaryCare.setSignature(map.containsKey("signature") && StringUtils.isNotEmpty(map.get("signature").toString()) ? map.get("signature").toString() : null);
                primaryCare.setSignatureAgain(map.containsKey("signatureAgain") && StringUtils.isNotEmpty(map.get("signatureAgain").toString()) ? map.get("signatureAgain").toString() : null);
                if (user != null) {
                    primaryCare.setOperatorCode(user.getLoginName());
                }
                primaryCareService.insert(primaryCare);
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
            logger.info("病人管理-护理文书-护理单文书-基础护理-保存基础护理表(新版-吕梁医院使用):" + e);
        }
        return json.toString();
    }
}
