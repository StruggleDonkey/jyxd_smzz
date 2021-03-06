package com.jyxd.web.his.web.webimplements;

import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.jyxd.web.data.basic.BedArrange;
import com.jyxd.web.data.basic.BedUseStatistics;
import com.jyxd.web.data.basic.MedOrderExec;
import com.jyxd.web.data.dictionary.BedDictionary;
import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.data.dictionary.WardDictionary;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.data.user.Role;
import com.jyxd.web.data.user.User;
import com.jyxd.web.his.data.commmon.*;
import com.jyxd.web.his.data.medOrderExec.AddOrdersRtRequest;
import com.jyxd.web.his.web.HisWebService;

import com.jyxd.web.service.basic.BedArrangeService;
import com.jyxd.web.service.basic.BedUseStatisticsService;
import com.jyxd.web.service.basic.MedOrderExecService;
import com.jyxd.web.service.dictionary.BedDictionaryService;
import com.jyxd.web.service.dictionary.DepartmentDictionaryService;
import com.jyxd.web.service.dictionary.WardDictionaryService;
import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.service.user.RoleService;
import com.jyxd.web.service.user.UserService;
import com.jyxd.web.util.MD5Util;
import com.jyxd.web.util.UUIDUtil;
import lombok.SneakyThrows;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.jws.WebService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.jyxd.web.util.DateUtil.*;
import static com.jyxd.web.util.ObjectUtil.castList;
import static com.jyxd.web.util.ObjectUtil.objectStrIsNull;

@Service
@WebService(serviceName = "HisWebService", // 与接口中指定的name一致
        targetNamespace = "http://xsdservice.business.mixpay.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.jyxd.web.his.web.HisWebService" // 接口地址
)
public class HisWebServiceImpl implements HisWebService {

    private static Logger logger = LoggerFactory.getLogger(HisWebServiceImpl.class);

    @Autowired
    private PatientService patientService;

    @Autowired
    private BedArrangeService bedArrangeService;

    @Autowired
    private BedDictionaryService bedDictionaryService;

    @Autowired
    private WardDictionaryService wardDictionaryService;

    @Autowired
    private DepartmentDictionaryService departmentDictionaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private MedOrderExecService medOrderExecService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BedUseStatisticsService bedUseStatisticsService;

    @SneakyThrows
    @Override
    public String hisService(String action, String hisRequestXml) {
        logger.info("action ===== --> :" + action);
        logger.info("hisRequestXml ----->> " + hisRequestXml);
        boolean isSaveData = false;
        BodyData bodyData = new BodyData("-1", "失败");
        try {
            switch (action) {
                case "T0004":
                    isSaveData = patientRegistry(hisRequestXml);
                    break;
                case "T0017":
                    isSaveData = inpatientEncounterStarted(hisRequestXml);
                    break;
                case "T0018":
                    isSaveData = inpatientEncounterCancel(hisRequestXml);
                    break;
                case "T0014":
                    isSaveData = admTransaction(hisRequestXml);
                    break;
                case "MS028":
                    isSaveData = receiveBed(hisRequestXml);
                    break;
                case "MS027":
                    isSaveData = receiveWard(hisRequestXml);
                    break;
                case "MS003":
                    isSaveData = receiveDepartment(hisRequestXml);
                    break;
                case "MS004":
                    isSaveData = receiveUser(hisRequestXml);
                    break;
                case "T0001":
                    isSaveData = addOrders(hisRequestXml);
                    break;
                case "T0003":
                    isSaveData = updateOrdersStatus(hisRequestXml);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error("接收his信息保存错误，错误信息：" + e.getMessage());
            e.printStackTrace();
        }
        if (isSaveData) {
            bodyData.setResultCode("0");
            bodyData.setResultContent("成功");
        }
        return getResult(bodyData);
    }

    /**
     * 获取对接返回值
     *
     * @param bodyData
     * @return
     */
    private String getResult(BodyData bodyData) {
        StringBuffer result = new StringBuffer();
        result.append("<Response>");
        result.append("<ResultCode>");
        result.append(bodyData.getResultCode());
        result.append("</ResultCode>");
        result.append("<ResultContent>");
        result.append(bodyData.getResultContent());
        result.append("</ResultContent>");
        result.append("</Response>");
        return result.toString();
    }

    /**
     * 接收修改医嘱状态
     *
     * @param hisRequestXml
     * @return
     */
    private boolean updateOrdersStatus(String hisRequestXml) {
        Map<String, Object> ordersRtMap = gainXmlData(hisRequestXml, "UpdateOrdersRt");
        return orderDataDispose(ordersRtMap, "update");
    }

    /**
     * 对医嘱数据进行修改或者新增
     *
     * @param ordersRtMap
     * @param updateOrAdd
     * @return
     */
    private boolean orderDataDispose(Map ordersRtMap, String updateOrAdd) {
        Patient patient = findPatient(ordersRtMap);
        if (Objects.isNull(patient)) {
            logger.error("该患者不存在系统，医嘱接收失败");
            return false;
        }
        Object OEORIInfoList = ordersRtMap.get("OEORIInfoList");
        JSONObject jsonObject = JSONObject.fromObject(OEORIInfoList);
        Object OEORIInfo = jsonObject.get("OEORIInfo");
        List<Object> ctCareProvMapList = castList(OEORIInfo, Object.class);
        if (CollectionUtils.isEmpty(ctCareProvMapList)) {
            logger.info("医嘱列表数据一条");
            if (Objects.isNull(OEORIInfo)) {
                logger.error("医嘱列表数据不存在");
                return false;
            }
            switch (updateOrAdd) {
                case "add":
                    saveMedOrderExec(ordersRtMap, OEORIInfo, patient.getId());
                    return true;
                case "update":
                    updateOrderData(OEORIInfo);
                    return true;
                default:
                    break;
            }
            return false;
        }
        AtomicBoolean isSaveData = new AtomicBoolean(false);
        ctCareProvMapList.forEach(oEORIInfo -> {
            switch (updateOrAdd) {
                case "add":
                    isSaveData.set(saveMedOrderExec(ordersRtMap, oEORIInfo, patient.getId()));
                    break;
                case "update":
                    isSaveData.set(updateOrderData(oEORIInfo));
                    break;
                default:
                    break;
            }
        });
        return isSaveData.get();
    }

    /**
     * 跟新医嘱信息到数据库
     *
     * @param oEORIInfo
     * @return
     */
    private boolean updateOrderData(Object oEORIInfo) {
        Map<String, Object> oEORIInfoMap = (Map) JSON.parse(String.valueOf(oEORIInfo));
        MedOrderExec medOrderExec = medOrderExecService.queryDataByOrderCode(String.valueOf(oEORIInfoMap.get("OEORIOrderItemID")));
        if (Objects.isNull(medOrderExec)) {
            logger.error("医嘱不存在，修改失败");
            return false;
        }
        /**
         * 医嘱状态代码  执行状态，V：核实；U：作废；H：ON Hold；D：停止 P：预开 E：执行 Q: In Queue
         *PO : Post-Operation Order S :Postponed
         *I:未审核 IP：处理中 C：撤销
         */
        medOrderExec.setOrderStatus(String.valueOf(oEORIInfoMap.get("OEORIStatusCode")));//医嘱状态代码  执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
        //TODO 父医嘱ID目前不是特别确定
        medOrderExec.setOrderSubNo(String.valueOf(oEORIInfoMap.get("OEORIParentOrderID")));
        return medOrderExecService.update(medOrderExec);
    }

    /**
     * 收医嘱信息 医生开完医嘱保存成功后调用
     *
     * @param hisRequestXml
     * @return
     */
    private boolean addOrders(String hisRequestXml) {
        Map<String, Object> addOrdersRtMap = gainXmlData(hisRequestXml, "AddOrdersRt");
        return orderDataDispose(addOrdersRtMap, "add");
    }

    /**
     * 保存医嘱信息到数据库
     *
     * @param addOrdersRtMap
     * @param oEORIInfo
     * @param patientId
     * @return
     */
    private boolean saveMedOrderExec(Map addOrdersRtMap, Object oEORIInfo, String patientId) {
        try {
            Map<String, Object> oEORIInfoMap = (Map) JSON.parse(String.valueOf(oEORIInfo));
            MedOrderExec medOrderExec = new MedOrderExec();
            medOrderExec.setId(UUIDUtil.getUUID());
            medOrderExec.setVisitId(String.valueOf(addOrdersRtMap.get("PATPatientID")));//addOrdersRt
            medOrderExec.setVisitCode(String.valueOf(addOrdersRtMap.get("PAADMVisitNumber")));//就诊号码
            medOrderExec.setPatientId(patientId);
            medOrderExec.setCreateTime(new Date());

            //TODO 医嘱id目前不是特别确定
            medOrderExec.setOrderCode(String.valueOf(oEORIInfoMap.get("OEORIOrderItemID")));
            medOrderExec.setOrderNo(String.valueOf(oEORIInfoMap.get("OEORIOEORIDR")));
            medOrderExec.setOrderSubNo(String.valueOf(oEORIInfoMap.get("OEORIParentOrderID")));

            medOrderExec.setOrderName(String.valueOf(oEORIInfoMap.get("OEORIARCItmMastDesc")));//医嘱项目描述
            medOrderExec.setSpecs(String.valueOf(oEORIInfoMap.get("OEORISpecification")));//医嘱规格
            switch (String.valueOf(oEORIInfoMap.get("OEORIClass"))) {//医嘱类别代码 检查类 西药类 中药类
                case "检查类":
                    medOrderExec.setDrugType(0);//是否为药嘱（0：否  1：是）
                    break;
                case "西药类":
                case "中药类":
                    medOrderExec.setDrugType(1);//是否为药嘱（0：否  1：是）
                    break;
            }
            medOrderExec.setOrderAttr(String.valueOf(oEORIInfoMap.get("OEORIDoseFormsDesc")));//剂型描述 补液类型（如：晶体液、胶体液等）
            if (!objectStrIsNull(oEORIInfoMap.get("OEORIRequireExecDate")) && !objectStrIsNull(oEORIInfoMap.get("OEORIRequireExecTime"))) {
                String defaultStart = String.valueOf(oEORIInfoMap.get("OEORIRequireExecDate"));//要求执行日期 YYYY-MM-DD
                String defaultEnd = String.valueOf(oEORIInfoMap.get("OEORIRequireExecTime"));//要求执行时间 hh:mm:ss
                medOrderExec.setDefaultTimePoint(yyyyMMddHHmmssSdfToDate(defaultStart + " " + defaultEnd));//计划执行时间
            }
            if (!objectStrIsNull(oEORIInfoMap.get("OEORIStopDate")) && !objectStrIsNull(oEORIInfoMap.get("OEORIStopTime"))) {
                String completeStart = String.valueOf(oEORIInfoMap.get("OEORIStopDate"));//医嘱停止日期 YYYY-MM-DD
                String completeEnd = String.valueOf(oEORIInfoMap.get("OEORIStopTime"));//医嘱停止时间 hh:mm:ss
                medOrderExec.setCompleteTimePoint(yyyyMMddHHmmssSdfToDate(completeStart + "" + completeEnd));//执行完成时间
            }
            if (!objectStrIsNull(oEORIInfoMap.get("OEORIStopDate")) && !objectStrIsNull(oEORIInfoMap.get("OEORIStopTime"))) {
                String completeStart = String.valueOf(oEORIInfoMap.get("OEORIStopDate"));//医嘱停止日期 YYYY-MM-DD
                String completeEnd = String.valueOf(oEORIInfoMap.get("OEORIStopTime"));//医嘱停止时间 hh:mm:ss
                medOrderExec.setCompleteTimePoint(yyyyMMddHHmmssSdfToDate(completeStart + "" + completeEnd));//执行完成时间
            }
            medOrderExec.setDosage(!objectStrIsNull(oEORIInfoMap.get("OEORIDoseQty")) ?
                    String.valueOf(oEORIInfoMap.get("OEORIDoseQty")) : null);//单次剂量
            medOrderExec.setDosageUnits(!objectStrIsNull(oEORIInfoMap.get("OEORIDoseUnitDesc")) ?
                    String.valueOf(oEORIInfoMap.get("OEORIDoseUnitDesc")) : null);//单次剂量单位描述
            medOrderExec.setAllDosage(String.valueOf(oEORIInfoMap.get("OEORIOrderQty")));//医嘱数量
            switch (String.valueOf(oEORIInfoMap.get("OEORIPriorityDesc"))) {//医嘱类型描述 长期、临时、自备长期、自备临时、出院带药
                case "长期":
                case "自备长期":
                case "出院带药":
                    medOrderExec.setRepeatIndicator(1);//医嘱类型，0：临时医嘱；1：长期医嘱
                    break;
                case "临时":
                case "自备临时":
                    medOrderExec.setRepeatIndicator(0);//医嘱类型，0：临时医嘱；1：长期医嘱
                    break;
            }
            medOrderExec.setUseMode(!objectStrIsNull(oEORIInfoMap.get("OEORIInstrCode")) ?
                    String.valueOf(oEORIInfoMap.get("OEORIInstrCode")) : null);//用药途径代码
            medOrderExec.setClassType(String.valueOf(oEORIInfoMap.get("OEORIClass")));//医嘱类别代码 检查类 西药类 中药类
            medOrderExec.setFrequency(String.valueOf(oEORIInfoMap.get("OEORIFreqDesc")));//频次描述
            medOrderExec.setPerformSpeed(null);//流速
            if (!objectStrIsNull(oEORIInfoMap.get("OEORIStatusCode"))) {
                medOrderExec.setOrderStatus(String.valueOf(oEORIInfoMap.get("OEORIStatusCode")));//医嘱状态代码  执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
            }
            medOrderExec.setRemark(!objectStrIsNull(oEORIInfoMap.get("OEORIRemarks")) ?
                    String.valueOf(oEORIInfoMap.get("OEORIRemarks")) : null);//医嘱备注信息
            medOrderExec.setUpdateTime(new Date());//记录最后修改时间
            medOrderExec.setIsSync(0);//是否已同步到护理单，0：未同步；1：已同步；
            medOrderExec.setOrderExecNum(1);//医嘱每天执行次数 默认1
            medOrderExec.setSyncNum(1);//剩余同步次数 默认1
            //更新同步时间为当前时间
            medOrderExec.setRecentSyncTime(new Date());//最新同步时间
            return medOrderExecService.insert(medOrderExec);
        } catch (Exception e) {
            logger.error("医嘱信息保存失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询患者对象
     *
     * @param dataMap
     * @return
     */
    private Patient findPatient(Map dataMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("visitId", dataMap.get("PATPatientID"));//患者主索引
        map.put("visitCode", dataMap.get("PAADMVisitNumber"));//就诊号码
        return patientService.getPatientByConditions(map);
    }

    /**
     * 接收医护人员字典信息
     *
     * @param hisRequestXml
     * @return
     */
    private boolean receiveUser(String hisRequestXml) {
        Map<String, Object> ctCareProvListMap = gainXmlData(hisRequestXml, "CT_CareProvList");
        List<Object> ctCareProvMapList = castList(ctCareProvListMap.get("CT_CareProv"), Object.class);
        if (CollectionUtils.isEmpty(ctCareProvMapList)) {
            logger.info("医护人员列表数据一条");
            Object ctCareProv = ctCareProvListMap.get("CT_CareProv");
            if (Objects.isNull(ctCareProv)) {
                logger.error("医护人员数据不存在");
                return false;
            }
            Map<String, Object> ctCareProvMap = (Map) JSON.parse(String.valueOf(ctCareProv));
            return saveUser(ctCareProvMap);
        }
        AtomicBoolean isSaveData = new AtomicBoolean(false);
        ctCareProvMapList.forEach(ctCareProv -> {
            Map<String, Object> ctCareProvMap = (Map) JSON.parse(String.valueOf(ctCareProv));
            isSaveData.set(saveUser(ctCareProvMap));
        });
        return isSaveData.get();
    }

    /**
     * 新增护士信息到数据库
     *
     * @param ctCareProvMap
     * @return
     */
    private boolean saveUser(Map<String, Object> ctCareProvMap) {
        User user = new User();
        user.setId(UUIDUtil.getUUID());
        user.setCreateTime(new Date());
        user.setUserName(String.valueOf(ctCareProvMap.get("CTCP_Desc")));//职工姓名
        user.setLoginName(String.valueOf(ctCareProvMap.get("CTCP_Desc")));//职工姓名
        try {
            //1 男  2女
            user.setSex(!objectStrIsNull(ctCareProvMap.get("CTCP_SexCode")) && StringUtils.equals("1", String.valueOf(ctCareProvMap.get("CTCP_SexCode"))) ?
                    1 : 0);
            user.setEnterTime(!objectStrIsNull(ctCareProvMap.get("CTCP_StartDate"))
                    ? yyyyMMddSdfToDate(String.valueOf(ctCareProvMap.get("CTCP_StartDate"))) : null);//有效开始日期

            user.setExitTime(!objectStrIsNull(ctCareProvMap.get("CTCP_EndDate"))
                    ? yyyyMMddSdfToDate(String.valueOf(ctCareProvMap.get("CTCP_EndDate"))) : null);//有效结束日期
            //1启用0停用-1删除
            switch (String.valueOf(ctCareProvMap.get("CTCP_Status"))) {
                case "1":
                    user.setStatus(1);
                    break;
                case "0":
                    user.setStatus(0);
                    break;
                case "-1":
                    user.setStatus(-1);
                    break;
            }
            // 默认123456
            user.setPassword(!objectStrIsNull(ctCareProvMap.get("CTCP_PassWord"))
                    ? MD5Util.string2MD5(String.valueOf(ctCareProvMap.get("CTCP_PassWord"))) : "e10adc3949ba59abbe56e057f20f883e");
            //职工代码
            user.setWorkNumber(String.valueOf(ctCareProvMap.get("CTCP_Code")));
            //姓名
            user.setSimplicity(!objectStrIsNull(ctCareProvMap.get("CTCP_Name"))
                    ? String.valueOf(ctCareProvMap.get("CTCP_Name")) : null);
            //是否参与排班（0：不参与 1：参与）
            user.setIsShedual(!objectStrIsNull(ctCareProvMap.get("CTCP_StaffType"))
                    && StringUtils.equals("NURSE", String.valueOf(ctCareProvMap.get("CTCP_StaffType"))) ?
                    1 : 0);
            user = setRole(ctCareProvMap.get("CTCP_StaffType"), user);
            if (Objects.isNull(user)) {
                return false;
            }
        } catch (Exception e) {
            logger.error("护士用户保存失败");
            return false;
        }
        return userService.insert(user);
    }

    private User setRole(Object object, User user) {
        if (!objectStrIsNull(object)) {
            Map<String, Object> map = new HashMap<>();
            map.put("roleName", String.valueOf(object));
            Role role = roleService.queryDataByName(map);
            if (Objects.isNull(role)) {
                logger.error("人员角色不存在数据库：" + object);
            } else {
                user.setRoleId(role.getId());
                user.setUserTypeCode(role.getUserTypeCode());
                return user;
            }
        }
        return null;
    }

    /**
     * 接收科室信息
     *
     * @param hisRequestXml
     * @return
     */
    private boolean receiveDepartment(String hisRequestXml) {
        Map<String, Object> receiveDepartmentMap = gainXmlData(hisRequestXml, "CT_DeptList");
        List<Object> ctDeptMapList = castList(receiveDepartmentMap.get("CT_Dept"), Object.class);
        if (CollectionUtils.isEmpty(ctDeptMapList)) {
            logger.info("科室信息列表数据一条");
            Object ctDept = receiveDepartmentMap.get("CT_Dept");
            if (Objects.isNull(ctDept)) {
                logger.error("科室信息数据不存在");
                return false;
            }
            Map<String, Object> ctDeptMap = (Map) JSON.parse(String.valueOf(ctDept));
            return saveDepartmentDictionary(ctDeptMap);
        }
        AtomicBoolean isSaveData = new AtomicBoolean(false);
        ctDeptMapList.forEach(ctDept -> {
            Map<String, Object> ctDeptMap = (Map) JSON.parse(String.valueOf(ctDept));
            isSaveData.set(saveDepartmentDictionary(ctDeptMap));
        });
        return isSaveData.get();
    }

    /**
     * 新增科室信息到数据库
     *
     * @param ctDeptMap
     * @return
     */
    private boolean saveDepartmentDictionary(Map<String, Object> ctDeptMap) {
        DepartmentDictionary departmentDictionary = new DepartmentDictionary();
        departmentDictionary.setId(UUIDUtil.getUUID());
        departmentDictionary.setDepartmentCode(String.valueOf(ctDeptMap.get("CTD_Code")));
        departmentDictionary.setDepartmentName(String.valueOf(ctDeptMap.get("CTD_Desc")));
        //1启用0停用-1删除
        switch (String.valueOf(ctDeptMap.get("CTD_Status"))) {
            case "1":
                departmentDictionary.setStatus(1);
                break;
            case "0":
                departmentDictionary.setStatus(0);
                break;
            case "-1":
                departmentDictionary.setStatus(-1);
                break;
        }
        return departmentDictionaryService.insert(departmentDictionary);
    }

    /**
     * 接收病区字典信息
     *
     * @param hisRequestXml
     * @return
     */
    private boolean receiveWard(String hisRequestXml) {
        Map<String, Object> ctWardCtWardMap = gainXmlData(hisRequestXml, "CT_WardList");
        List<Object> ctWardList = castList(ctWardCtWardMap.get("CT_Ward"), Object.class);
        if (CollectionUtils.isEmpty(ctWardList)) {
            logger.info("病区字典列表数据一条");
            Object ctWard = ctWardCtWardMap.get("CT_Ward");
            if (Objects.isNull(ctWard)) {
                logger.error("病区字典数据不存在");
                return false;
            }
            Map<String, Object> ctWardMap = (Map) JSON.parse(String.valueOf(ctWard));
            return saveWardDictionary(ctWardMap);
        }
        AtomicBoolean isSaveData = new AtomicBoolean(false);
        ctWardList.forEach(ctWard -> {
            Map<String, Object> ctWardMap = (Map) JSON.parse(String.valueOf(ctWard));
            isSaveData.set(saveWardDictionary(ctWardMap));
        });
        return isSaveData.get();
    }

    /**
     * 新增病区信息到数据库
     *
     * @param ctBedMap
     * @return
     */
    private boolean saveWardDictionary(Map<String, Object> ctBedMap) {
        WardDictionary wardDictionary = new WardDictionary();
        wardDictionary.setId(UUIDUtil.getUUID());
        wardDictionary.setStatus(1);
        wardDictionary.setWardCode(String.valueOf(ctBedMap.get("CTW_Code")));
        wardDictionary.setWardName(String.valueOf(ctBedMap.get("CTW_Desc")));
        switch (String.valueOf(ctBedMap.get("CTW_Status"))) {//1启用0停用-1删除
            case "1":
                wardDictionary.setStatus(1);
                break;
            case "0":
                wardDictionary.setStatus(0);
                break;
            case "-1":
                wardDictionary.setStatus(-1);
                break;
        }
        return wardDictionaryService.insert(wardDictionary);
    }

    /**
     * 接收床位字典信息
     *
     * @param hisRequestXml
     * @return
     */
    private boolean receiveBed(String hisRequestXml) {
        Map<String, Object> receiveBedMap = gainXmlData(hisRequestXml, "CT_BedList");
        List<Object> ctBedList = castList(receiveBedMap.get("CT_Bed"), Object.class);
        if (CollectionUtils.isEmpty(ctBedList)) {
            logger.info("床位列表数据一条");
            Object ctBed = receiveBedMap.get("CT_Bed");
            if (Objects.isNull(ctBed)) {
                logger.error("床位数据不存在");
                return false;
            }
            Map<String, Object> ctBedMap = (Map) JSON.parse(String.valueOf(ctBed));
            return saveBedDictionary(ctBedMap);
        }
        AtomicBoolean isSaveData = new AtomicBoolean(false);
        ctBedList.forEach(ctBed -> {
            Map<String, Object> ctBedMap = (Map) JSON.parse(String.valueOf(ctBed));
            isSaveData.set(saveBedDictionary(ctBedMap));
        });
        return isSaveData.get();
    }

    /**
     * 新增床位信息到数据库
     *
     * @param ctBedMap
     * @return
     */
    private boolean saveBedDictionary(Map<String, Object> ctBedMap) {
        BedDictionary bedDictionary = new BedDictionary();
        bedDictionary.setId(UUIDUtil.getUUID());
        switch (String.valueOf(ctBedMap.get("CTB_Status()"))) {//1启用0停用-1删除
            case "1":
                bedDictionary.setStatus(1);
                break;
            case "0":
                bedDictionary.setStatus(0);
                break;
            case "-1":
                bedDictionary.setStatus(-1);
                break;
        }
        bedDictionary.setBedCode(String.valueOf(ctBedMap.get("CTB_Code")));//床位代码
        if (!objectStrIsNull(ctBedMap.get("CTB_Desc"))) {
            bedDictionary.setBedName(String.valueOf(ctBedMap.get("CTB_Desc")));//床位描述
        }
        return bedDictionaryService.insert(bedDictionary);
    }

    /**
     * 接收转科信息
     *
     * @param hisRequestXml
     * @return
     */
    private boolean admTransaction(String hisRequestXml) throws ParseException {
        Map<String, Object> admTransactionRtMap = gainXmlData(hisRequestXml, "AdmTransactionRt");
        Patient patient = findPatient(admTransactionRtMap);
        if (Objects.isNull(patient)) {
            logger.error("该患者不存在系统，转科失败");
            return false;
        }
        if (!objectStrIsNull(admTransactionRtMap.get("PAADMTStartDate")) && !objectStrIsNull(admTransactionRtMap.get("PAADMTStartTime"))) {
            String exitDate = String.valueOf(admTransactionRtMap.get("PAADMTStartDate"));//转出日期
            String exitTime = String.valueOf(admTransactionRtMap.get("PAADMTStartTime"));//转出时间
            patient.setExitTime(yyyyMMddHHmmssSdfToDate(exitDate + " " + exitTime));//出科时间
        }
        patient.setToDepartmentCode(String.valueOf(admTransactionRtMap.get("PAADMTOrigDeptCode")));//转出科室代码

        if (!objectStrIsNull(admTransactionRtMap.get("PAADMTEndDate")) && !objectStrIsNull(admTransactionRtMap.get("PAADMTEndTime"))) {
            String enterDate = String.valueOf(admTransactionRtMap.get("PAADMTEndDate"));//转入日期
            String enterTime = String.valueOf(admTransactionRtMap.get("PAADMTEndTime"));//转入时间
            patient.setEnterTime(yyyyMMddHHmmssSdfToDate(enterDate + " " + enterTime));//入科时间
        }
        patient.setDepartmentCode(String.valueOf(admTransactionRtMap.get("PAADMTTargDeptCode")));//转入科室代码
        patient.setWardCode(String.valueOf(admTransactionRtMap.get("PAADMTTargWardCode")));//转入病区代码
        if (StringUtils.equals(patient.getDepartmentCode(), "81")
                && StringUtils.equals(patient.getWardCode(), "23")) {
            patient.setBedCode(String.valueOf(admTransactionRtMap.get("PAADMTTargBedCode")));//转入床位代码
        }
        patient.setDoctorCode(String.valueOf(admTransactionRtMap.get("PAADMTTargDocCode")));//转入医生代码
        BedArrange bedArrange;
        switch (String.valueOf(admTransactionRtMap.get("PAADMTState"))) {
            //01转科、02转床、03首次分床、04换医生
            case "01":
                bedArrange = bedArrangeService.queryDataByPatientId(patient.getId());
                if (StringUtils.equals(patient.getDepartmentCode(), "81")
                        && StringUtils.equals(patient.getWardCode(), "23")) {
                    patient.setFlag(1);//在院标志（0：出科；1：在科）
                    patient.setExitType("转入科");//出科方式 (出院、转入科、转出科、死亡、放弃、转院)
                    if (!isBed(patient.getBedCode())) {
                        logger.error("床位不存在，病人转入科室接收失败");
                        return false;
                    }
                    bedArrange = bedArrangeService.queryDataByBedCode(patient.getBedCode());
                    if (Objects.isNull(bedArrange)) {
                        setBedArrange(patient);
                        break;
                    }
                    saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 1);
                    bedArrange.setPatientId(patient.getId());
                    bedArrangeService.update(bedArrange);
                    break;
                } else {
                    patient.setFlag(0);//在院标志（0：出科；1：在科）
                    patient.setExitType("转出科");//出科方式 (出院、转入科、转出科、死亡、放弃、转院)
                    if (Objects.isNull(bedArrange)) {
                        logger.error("患者不存在诊室，转出科室科失败");
                        return false;
                    }
                    saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 2);
                    bedArrange.setPatientId(null);
                    patient.setBedCode(null);
                    bedArrangeService.update(bedArrange);
                    break;
                }
            case "02":
                if (StringUtils.equals(patient.getDepartmentCode(), "81")
                        && StringUtils.equals(patient.getWardCode(), "23")) {
                    bedArrange = bedArrangeService.queryDataByPatientId(patient.getId());
                    if (Objects.isNull(bedArrange)) {
                        logger.error("床位原始床位不存在，病人转床失败");
                        return false;
                    }
                    saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 2);
                    bedArrange.setPatientId(null);
                    bedArrangeService.update(bedArrange);
                }
            case "03":
                if (StringUtils.equals(patient.getDepartmentCode(), "81")
                        && StringUtils.equals(patient.getWardCode(), "23")) {
                    patient.setFlag(1);
                    if (!isBed(patient.getBedCode())) {
                        logger.error("床位不存在，病人入院接收失败");
                        return false;
                    }
                    bedArrange = bedArrangeService.queryDataByBedCode(patient.getBedCode());
                    if (Objects.isNull(bedArrange)) {
                        setBedArrange(patient);
                        break;
                    }
                    saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 1);
                    bedArrange.setPatientId(patient.getId());
                    bedArrangeService.update(bedArrange);
                    break;
                }
            case "04":
                patient.setFlag(1);//在院标志（0：出科；1：在科）
                break;
            case "05":
                bedArrange = bedArrangeService.queryDataByPatientId(patient.getId());
                if (Objects.isNull(bedArrange)) {
                    logger.error("床位原始床位不存在，病人移出床位失败");
                    return false;
                }
                saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 2);
                bedArrange.setPatientId(null);
                bedArrangeService.update(bedArrange);
                patient.setBedCode(null);
                break;
            default:
                break;
        }

        return patientService.update(patient);
    }

    private BedArrange setBedArrange(Patient patient) {
        BedArrange bedArrange = new BedArrange();
        bedArrange.setId(UUIDUtil.getUUID());
        bedArrange.setPatientId(patient.getId());
        bedArrange.setBedCode(patient.getBedCode());
        bedArrangeService.insert(bedArrange);
        saveBedUseStatistic(patient.getId(), bedArrange.getBedCode(), 1);
        return bedArrange;
    }

    /**
     * 保存入床出床记录信息
     *
     * @param patientId 患者id
     * @param bedCode   床位号id
     * @param exitCod   出入code 入床传1，出床传2
     */
    private void saveBedUseStatistic(String patientId, String bedCode, Integer exitCod) {
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", patientId);
        map.put("state", 1);
        map.put("bedCode", bedCode);
        BedUseStatistics bedUseStatistics = bedUseStatisticsService.queryByPatientIdAndBedCodeAndState(map);
        if (Objects.isNull(bedUseStatistics) && Objects.equals(exitCod, 1)) {
            bedUseStatistics = new BedUseStatistics();
            bedUseStatistics.setId(UUIDUtil.getUUID());
            bedUseStatistics.setPatientId(patientId);
            bedUseStatistics.setBedCode(bedCode);
            bedUseStatistics.setState(1);
            bedUseStatistics.setStartDate(new Date());
            bedUseStatistics.setCreateTime(new Date());
            bedUseStatisticsService.insert(bedUseStatistics);
        }
        if (Objects.nonNull(bedUseStatistics) && Objects.equals(exitCod, 2)) {
            bedUseStatistics.setEndDate(new Date());
            bedUseStatistics.setStatisticsDate(new Date());
            Long duration = bedUseStatistics.getEndDate().getTime() - bedUseStatistics.getStartDate().getTime();
            bedUseStatistics.setState(2);
            bedUseStatistics.setTimeLong(duration.floatValue());
            bedUseStatisticsService.update(bedUseStatistics);
        }
    }

    /**
     * 取消住院登记
     *
     * @param hisRequestXml
     * @return
     */
    private boolean inpatientEncounterCancel(String hisRequestXml) throws ParseException {
        Map<String, Object> inpatientEncounterCancelRtMap = gainXmlData(hisRequestXml, "InpatientEncounterCancelRt");
        Patient patient = findPatient(inpatientEncounterCancelRtMap);
        if (Objects.isNull(patient)) {
            return false;
        }
        patient.setFlag(0);
        patient.setExitType("出院");
        patient.setBedCode(null);
        String date = String.valueOf(inpatientEncounterCancelRtMap.get("UpdateDate"));
        String time = String.valueOf(inpatientEncounterCancelRtMap.get("UpdateTime"));
        patient.setExitTime(yyyyMMddHHmmssSdfToDate(date + " " + time));
        BedArrange bedArrange = bedArrangeService.queryDataByPatientId(patient.getId());
        if (Objects.isNull(bedArrange)) {
            logger.info("未查询到床位安排，患者已经移出床位出院成功");
            return patientService.update(patient);
        }
        bedArrange.setPatientId(null);
        //更新床位信息并且更新患者信息
        bedArrangeService.update(bedArrange);
        return patientService.update(patient);
    }

    /**
     * 入院登记信息接收
     *
     * @param hisRequestXml
     * @return
     * @throws ParseException
     */
    private boolean inpatientEncounterStarted(String hisRequestXml) throws ParseException {
        Map<String, Object> inpatientEncounterStartedRtMap = gainXmlData(hisRequestXml, "InpatientEncounterStartedRt");
        Patient patient = patientService.getPatientByVisitIdAndFlagAndStatus(String.valueOf(inpatientEncounterStartedRtMap.get("PATPatientID")), "1", "1");
        if (Objects.isNull(patient)) {
            logger.info("inpatientEncounterStarted -> 患者在系统不存在，先执行入院登记");
            patient = new Patient();
            patient.setId(UUIDUtil.getUUID());
            patient.setCreateTime(new Date());
            patient.setVisitId(String.valueOf(inpatientEncounterStartedRtMap.get("PATPatientID")));
            patient = setPatient(patient, inpatientEncounterStartedRtMap);
            if (StringUtils.equals(patient.getWardCode(), "重症医学科病区") || StringUtils.equals(patient.getWardCode(), "23")) {
                return patientService.insert(patient);
            }
            logger.info("非重症病区入院，不执行保存操作");
            return false;
        }
        patient = setPatient(patient, inpatientEncounterStartedRtMap);
        if (StringUtils.equals(patient.getWardCode(), "重症医学科病区") || StringUtils.equals(patient.getWardCode(), "23")) {
            return patientService.update(patient);
        }
        logger.info("非重症病区入院，不执行修改操作");
        return false;
    }

    /**
     * 患者信息赋值
     *
     * @param patient
     * @param inpatientEncounterStartedRtMap
     * @return
     * @throws ParseException
     */
    private Patient setPatient(Patient patient, Map<String, Object> inpatientEncounterStartedRtMap) throws ParseException {
        patient.setVisitCode(String.valueOf(inpatientEncounterStartedRtMap.get("PAADMVisitNumber")));
        patient.setFlag(1);//在院标志（0：出科；1：在科）
        patient.setStatus(1);
        patient.setWardCode(String.valueOf(inpatientEncounterStartedRtMap.get("PAADMAdmWardCode")));//入院病区代码
        patient.setBedCode(String.valueOf(inpatientEncounterStartedRtMap.get("PAADMCurBedNo")));//病床号
        patient.setDepartmentCode(String.valueOf(inpatientEncounterStartedRtMap.get("PAADMAdmDeptCode")));
        String date = String.valueOf(inpatientEncounterStartedRtMap.get("PAADMStartDate"));
        String time = String.valueOf(inpatientEncounterStartedRtMap.get("PAADMStartTime"));
        patient.setVisitTime(yyyyMMddHHmmssSdfToDate(date + " " + time));//入院日期时间
        patient.setEnterTime(yyyyMMddHHmmssSdfToDate(date + " " + time));//入科时间
        /** 诊断信息赋值 **/
        Map<String, Object> pAASMDiagnoseMap = getPAADMDiagnoseMap(inpatientEncounterStartedRtMap);
        if (!CollectionUtils.isEmpty(pAASMDiagnoseMap)) {
            patient.setDiagnosisCode(String.valueOf(pAASMDiagnoseMap.get("PADDiagCode")));//诊断代码
            patient.setDiagnosisName(String.valueOf(pAASMDiagnoseMap.get("PADDiagDesc")));//诊断描述
            patient.setDoctorCode(String.valueOf(pAASMDiagnoseMap.get("PADDiagDocCode")));//诊断医生代码
        }
        return patient;
    }

    /**
     * 查询床位是否存在
     *
     * @param bedCode
     * @return
     */
    private boolean isBed(String bedCode) {
        Map<String, Object> bedRequestMap = new HashMap<>();
        bedRequestMap.put("bedCode", bedCode);
        BedDictionary bedDictionary = bedDictionaryService.queryDataByBedCode(bedRequestMap);
        if (Objects.isNull(bedDictionary)) {
            logger.error("床位不存在，病人入院接收失败");
            return false;
        }
        return true;
    }

    /**
     * 获取诊断信息
     *
     * @param inpatientEncounterStartedRtMap
     * @return
     */
    private Map<String, Object> getPAADMDiagnoseMap(Map<String, Object> inpatientEncounterStartedRtMap) {
        Map<String, Object> map = xmlToJsonMap(inpatientEncounterStartedRtMap.get("PAADMDiagnoseList"));
        return xmlToJsonMap(map.get("PAADMDiagnose"));
    }

    /**
     * 患者基本信息接收
     *
     * @param hisRequestXml
     */
    private boolean patientRegistry(String hisRequestXml) throws ParseException {
        Map<String, Object> patientRegistryRtMap = gainXmlData(hisRequestXml, "PatientRegistryRt");
        if (objectStrIsNull(patientRegistryRtMap.get("PATPatientID"))) {
            logger.error("患者PATPatientID为空，接收失败");
            return false;
        }
        Patient patient = patientService.getPatientByVisitIdAndFlagAndStatus(String.valueOf(patientRegistryRtMap.get("PATPatientID")), "1", "1");
        if (Objects.isNull(patient)) {
            /*patient = new Patient();
            patient.setId(UUIDUtil.getUUID());
            patient.setCreateTime(new Date());
            return patientService.insert(setPatientRegistry(patient, patientRegistryRtMap));*/
            logger.info("患者未进行住院登记，不进行患者基本信息保存");
            return false;
        }
        return patientService.update(setPatientRegistry(patient, patientRegistryRtMap));
    }

    /**
     * 赋值患者基本信息
     *
     * @param patient
     * @param patientRegistryRtMap
     * @return
     * @throws ParseException
     */
    private Patient setPatientRegistry(Patient patient, Map patientRegistryRtMap) throws ParseException {
        patient.setVisitId(String.valueOf(patientRegistryRtMap.get("PATPatientID")));
        patient.setName(String.valueOf(patientRegistryRtMap.get("PATName")));
        if (!objectStrIsNull(patientRegistryRtMap.get("PATDob"))) {
            patient.setBirthday(String.valueOf(patientRegistryRtMap.get("PATDob")));
            patient.setAge(getAge(yyyyMMddSdfToDate(String.valueOf(patientRegistryRtMap.get("PATDob")))));
        }
        patient.setSex(Integer.valueOf(String.valueOf(patientRegistryRtMap.get("PATSexCode"))));
        if (!objectStrIsNull(patientRegistryRtMap.get("PATMaritalStatusCode"))) {
            patient.setMaritalState(String.valueOf(patientRegistryRtMap.get("PATMaritalStatusCode")));
        }
        if (!objectStrIsNull(patientRegistryRtMap.get("PATNationCode"))) {
            patient.setRace(String.valueOf(patientRegistryRtMap.get("PATNationCode")));//患者民族代码
        }
        if (!objectStrIsNull(patientRegistryRtMap.get("PATDeceasedDate"))
                && Objects.nonNull(patientRegistryRtMap.get("PATDeceasedTime"))) {
            String deceasedDate = String.valueOf(patientRegistryRtMap.get("PATDeceasedDate"));
            String deceasedTime = String.valueOf(patientRegistryRtMap.get("PATDeceasedTime"));
            patient.setDeathTime(yyyyMMddHHmmssSdfToDate(deceasedDate + " " + deceasedTime));
        }
        Map<String, Object> pATIdentityMap = getPATIdentityMap(patientRegistryRtMap);
        if (Objects.nonNull(pATIdentityMap)) {
            if (!objectStrIsNull(pATIdentityMap.get("PATIdentityNum"))) {
                patient.setIdCard(String.valueOf(pATIdentityMap.get("PATIdentityNum")));//患者证件号码
                patient.setAge(IdNOToAge(String.valueOf(pATIdentityMap.get("PATIdentityNum"))));//年龄
            }
            if (!objectStrIsNull(pATIdentityMap.get("PATTelephone"))) {
                patient.setPhone(String.valueOf(pATIdentityMap.get("PATTelephone")));//患者电话
            }
        }
        Map<String, Object> patRelationMap = getPATRelationMap(patientRegistryRtMap);
        if (!objectStrIsNull(patRelationMap.get("PATRelationName"))) {
            patient.setContactMan((String) patRelationMap.get("PATRelationName"));//患者联系人姓名
        }
        if (!objectStrIsNull(patRelationMap.get("PATRelationPhone"))) {
            patient.setContactPhone(String.valueOf(patRelationMap.get("PATRelationPhone")));
        }
        patient.setStatus(1);
        patient.setFlag(1);
        logger.info(patient.getName() + "：病人基本信息记录成功");
        return patient;
    }

    /**
     * 获取患者身份信息数据
     *
     * @param patientRegistryRtMap
     * @return
     */
    private Map<String, Object> getPATIdentityMap(Map<String, Object> patientRegistryRtMap) {
        Map<String, Object> map = xmlToJsonMap(patientRegistryRtMap.get("PATIdentityList"));
        if (Objects.isNull(map)) {
            logger.info("患者身份信息不存在");
            return null;
        }
        return xmlToJsonMap(map.get("PATIdentity"));
    }

    /**
     * 获取患者联系人数据
     *
     * @param patientRegistryRtMap
     * @return
     */
    private Map<String, Object> getPATRelationMap(Map<String, Object> patientRegistryRtMap) {
        Map<String, Object> map = xmlToJsonMap(patientRegistryRtMap.get("PATRelationList"));
        return xmlToJsonMap(map.get("PATRelation"));
    }

    /**
     * 获取xml数据
     *
     * @param xml
     * @param xmlLabel
     * @return
     */
    private Map<String, Object> gainXmlData(String xml, String xmlLabel) {
        JSONObject xmlJSONObj = JSONObject.fromObject(XML.toJSONObject(xml));
        Map<String, Object> messageMap = xmlToJsonMap(xmlJSONObj);
        Map<String, Object> bodyMap = xmlToJsonMap(messageMap.get("Request"));
        Map<String, Object> dateRtMap = xmlToJsonMap(bodyMap.get("Body"));
        Map<String, Object> specificRtMap = xmlToJsonMap(dateRtMap.get(xmlLabel));
        return specificRtMap;
    }

    private HeaderData getXmlHeader(String xml) {
        JSONObject xmlJSONObj = JSONObject.fromObject(XML.toJSONObject(xml));
        Map<String, Object> messageMap = xmlToJsonMap(xmlJSONObj);
        Map<String, Object> bodyMap = xmlToJsonMap(messageMap.get("Request"));
        Map<String, Object> dateRtMap = xmlToJsonMap(bodyMap.get("Header"));
        HeaderData headerData = new HeaderData();
        headerData.setSourceSystem((String) dateRtMap.get("SourceSystem"));
        headerData.setMessageID(String.valueOf(dateRtMap.get("MessageID")));
        return headerData;
    }

    /**
     * xml转JsonMap
     *
     * @param xmlJSONObj
     * @return
     */
    private Map<String, Object> xmlToJsonMap(Object xmlJSONObj) {
        return com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(xmlJSONObj), HashMap.class);
    }

    /**
     * 通过身份证号计算年龄
     *
     * @param IdNO
     * @return
     */
    public String IdNOToAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return String.valueOf(u);
        } else {
            dates = IdNO.substring(6, 8);
            return dates;
        }
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    private String getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return String.valueOf(age);
    }

    public void test(String xml) {
        //Map<String, Object> patientRegistryRtMap = gainXmlData(xml, "PatientRegistryRt");
        // HeaderData headerData = getXmlHeader(xml);
        /*System.out.println(patientRegistryRtMap);
        List<Object> ctBed = castList(patientRegistryRtMap.get("CT_Bed"), Object.class);
        System.out.println(ctBed.size());*/
        //System.out.println(headerData.getMessageID());
        /*ctBed.forEach(object -> {
            System.out.println(object);

            Map<String, Object> maps = (Map) JSON.parse(String.valueOf(object));
            System.out.println(maps.get("CTB_CodesystemCode"));
        });
*/
        Map<String, Object> ctCareProvListMap = gainXmlData(xml, "AddOrdersRt");

        //System.out.println(ctCareProvListMap);
        //   System.out.println(ctCareProvListMap.get("CT_CareProv"));
        Object oeoriInfoList = ctCareProvListMap.get("OEORIInfoList");
        JSONObject jsonObject = JSONObject.fromObject(oeoriInfoList);
        Object oeoriInfo = jsonObject.get("OEORIInfo");
        List<Object> ctCareProvMapList = castList(oeoriInfo, Object.class);
        if (CollectionUtils.isEmpty(ctCareProvMapList)) {
            //System.out.println(jsonObject);
            System.out.println(oeoriInfo);
        } else {
            //JSONObject json = (JSONObject) JSON.toJSON(o);
            System.out.println(ctCareProvMapList.size());
       /* Map<String, Object> map = xmlToJsonMap(patientRegistryRtMap.get("PATRelationList"));
        Map<String, Object> pATRElationAddressMap = xmlToJsonMap(map.get("PATRelation"));
        Map<String, Object> pATRelationAddressListMap = xmlToJsonMap(pATRElationAddressMap.get("PATRelationAddressList"));
        Map<String, Object> pATRelationAddressMap = xmlToJsonMap(pATRelationAddressListMap.get("PATRelationAddress"));*/
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            //System.out.println(map);
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
            // System.out.println(pATRElationAddressMap);
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
//        System.out.println(patRelationAddressMap.get("PATIdentityNum"));
            System.out.println("-------------------------------------");
            System.out.println("-------------------------------------");
       /* System.out.println(patRelationAddressMap);

        System.out.println(objectStrIsNull(patRelationAddressMap.get("PATRelationName")));*/
        }
    }

    /*public static void main(String[] args) {
        String ctXml = "<Request>\n" +
                "\t<Header>\n" +
                "\t\t<SourceSystem>02</SourceSystem>\n" +
                "\t\t<MessageID>23950</MessageID>\n" +
                "\t</Header>\n" +
                "\t<Body>\n" +
                "\t\t<CT_BedList>\n" +
                "\t\t\t\t<CT_Bed>\n" +
                "\t\t\t\t\t<CTB_BedType>床位类型</CTB_BedType>\n" +
                "\t\t\t\t\t<CTB_Code>123</CTB_Code>\n" +
                "\t\t\t\t\t<CTB_CodesystemCode>代码表类型</CTB_CodesystemCode>\n" +
                "\t\t\t\t\t<CTB_Desc>描述</CTB_Desc>\n" +
                "\t\t\t\t\t<CTB_HosCode>医院编号</CTB_HosCode>\n" +
                "\t\t\t\t\t<CTB_Remarks>备注</CTB_Remarks>\n" +
                "\t\t\t\t\t<CTB_RoomCode>房间号</CTB_RoomCode>\n" +
                "\t\t\t\t\t<CTB_Status>状态（1启用0停用-1删除）</CTB_Status>\n" +
                "\t\t\t\t\t<CTB_UpdateDate>最后更新日期</CTB_UpdateDate>\n" +
                "\t\t\t\t\t<CTB_UpdateTime>最后更新时间</CTB_UpdateTime>\n" +
                "\t\t\t\t\t<CTB_UpdateUserCode>最后更新人编码</CTB_UpdateUserCode>\n" +
                "\t\t\t\t\t<CTB_WardCode>所属病区</CTB_WardCode>\n" +
                "\t\t\t\t</CT_Bed>\n" +
                "\t\t\t\t<CT_Bed>\n" +
                "\t\t\t\t\t<CTB_BedType>床位类型</CTB_BedType>\n" +
                "\t\t\t\t\t<CTB_Code>456</CTB_Code>\n" +
                "\t\t\t\t\t<CTB_CodesystemCode>代码表类型</CTB_CodesystemCode>\n" +
                "\t\t\t\t\t<CTB_Desc>描述</CTB_Desc>\n" +
                "\t\t\t\t\t<CTB_HosCode>医院编号</CTB_HosCode>\n" +
                "\t\t\t\t\t<CTB_Remarks>备注</CTB_Remarks>\n" +
                "\t\t\t\t\t<CTB_RoomCode>房间号</CTB_RoomCode>\n" +
                "\t\t\t\t\t<CTB_Status>状态（1启用0停用-1删除）</CTB_Status>\n" +
                "\t\t\t\t\t<CTB_UpdateDate>最后更新日期</CTB_UpdateDate>\n" +
                "\t\t\t\t\t<CTB_UpdateTime>最后更新时间</CTB_UpdateTime>\n" +
                "\t\t\t\t\t<CTB_UpdateUserCode>最后更新人编码</CTB_UpdateUserCode>\n" +
                "\t\t\t\t\t<CTB_WardCode>所属病区</CTB_WardCode>\n" +
                "\t\t\t\t</CT_Bed>\n" +
                "\t\t</CT_BedList>\n" +
                "\t</Body>\n" +
                "</Request>";

        HisWebServiceImpl hisWebService = new HisWebServiceImpl();
        //hisWebService.test(ctXml);

        String xml = "<Request>\n" +
                "\t<Header>\n" +
                "\t\t<SourceSystem>02</SourceSystem>\n" +
                "\t\t<MessageID>23950</MessageID>\n" +
                "\t</Header>\n" +
                "\t<Body>\n" +
                "\t\t<PatientRegistryRt>\n" +
                "\t\t\t<BusinessFieldCode>00002</BusinessFieldCode>\n" +
                "\t\t\t<HospitalCode></HospitalCode>\n" +
                "\t\t\t<PATPatientID>0000106464</PATPatientID>\n" +
                "\t\t\t<PATName>苏恒</PATName>\n" +
                "\t\t\t<PATDob>1993-04-21</PATDob>\n" +
                "\t\t\t<PATSexCode>1</PATSexCode>\n" +
                "\t\t\t<PATSexDesc>男</PATSexDesc>\n" +
                "\t\t\t<PATMaritalStatusCode></PATMaritalStatusCode>\n" +
                "\t\t\t<PATMaritalStatusDesc></PATMaritalStatusDesc>\n" +
                "\t\t\t<PATDocumentNo></PATDocumentNo>\n" +
                "\t\t\t<PATNationCode>01</PATNationCode>\n" +
                "\t\t\t<PATNationDesc>汉族</PATNationDesc>\n" +
                "\t\t\t<PATCountryCode>156</PATCountryCode>\n" +
                "\t\t\t<PATCountryDesc>中国</PATCountryDesc>\n" +
                "\t\t\t<PATDeceasedDate></PATDeceasedDate>\n" +
                "\t\t\t<PATDeceasedTime></PATDeceasedTime>\n" +
                "\t\t\t<PATHealthCardID></PATHealthCardID>\n" +
                "\t\t\t<PATMotherID></PATMotherID>\n" +
                "\t\t\t<PATOccupationCode></PATOccupationCode>\n" +
                "\t\t\t<PATOccupationDesc></PATOccupationDesc>\n" +
                "\t\t\t<PATWorkPlaceName></PATWorkPlaceName>\n" +
                "\t\t\t<PATWorkPlaceTelNum></PATWorkPlaceTelNum>\n" +
                "\t\t\t<PATAddressList>\n" +
                "\t\t\t\t<PATAddress>\n" +
                "\t\t\t\t\t<PATAddressType>06</PATAddressType>\n" +
                "\t\t\t\t\t<PATAddressDesc></PATAddressDesc>\n" +
                "\t\t\t\t\t<PATHouseNum></PATHouseNum>\n" +
                "\t\t\t\t\t<PATVillage></PATVillage>\n" +
                "\t\t\t\t\t<PATCountryside></PATCountryside>\n" +
                "\t\t\t\t\t<PATCountyCode></PATCountyCode>\n" +
                "\t\t\t\t\t<PATCountyDesc></PATCountyDesc>\n" +
                "\t\t\t\t\t<PATCityCode></PATCityCode>\n" +
                "\t\t\t\t\t<PATCityDesc></PATCityDesc>\n" +
                "\t\t\t\t\t<PATProvinceCode>140000</PATProvinceCode>\n" +
                "\t\t\t\t\t<PATProvinceDesc>山西省</PATProvinceDesc>\n" +
                "\t\t\t\t\t<PATPostalCode></PATPostalCode>\n" +
                "\t\t\t\t</PATAddress>\n" +
                "\t\t\t\t<PATAddress>\n" +
                "\t\t\t\t\t<PATAddressType>09</PATAddressType>\n" +
                "\t\t\t\t\t<PATAddressDesc>山西省吕梁市离石区城北街道办苏家崖村369-2</PATAddressDesc>\n" +
                "\t\t\t\t\t<PATHouseNum></PATHouseNum>\n" +
                "\t\t\t\t\t<PATVillage></PATVillage>\n" +
                "\t\t\t\t\t<PATCountryside></PATCountryside>\n" +
                "\t\t\t\t\t<PATCountyCode></PATCountyCode>\n" +
                "\t\t\t\t\t<PATCountyDesc></PATCountyDesc>\n" +
                "\t\t\t\t\t<PATCityCode></PATCityCode>\n" +
                "\t\t\t\t\t<PATCityDesc></PATCityDesc>\n" +
                "\t\t\t\t\t<PATProvinceCode>140000</PATProvinceCode>\n" +
                "\t\t\t\t\t<PATProvinceDesc>山西省</PATProvinceDesc>\n" +
                "\t\t\t\t\t<PATPostalCode></PATPostalCode>\n" +
                "\t\t\t\t</PATAddress>\n" +
                "\t\t\t\t<PATAddress>\n" +
                "\t\t\t\t\t<PATAddressType>01</PATAddressType>\n" +
                "\t\t\t\t\t<PATAddressDesc></PATAddressDesc>\n" +
                "\t\t\t\t\t<PATHouseNum></PATHouseNum>\n" +
                "\t\t\t\t\t<PATVillage></PATVillage>\n" +
                "\t\t\t\t\t<PATCountryside></PATCountryside>\n" +
                "\t\t\t\t\t<PATCountyCode></PATCountyCode>\n" +
                "\t\t\t\t\t<PATCountyDesc></PATCountyDesc>\n" +
                "\t\t\t\t\t<PATCityCode></PATCityCode>\n" +
                "\t\t\t\t\t<PATCityDesc></PATCityDesc>\n" +
                "\t\t\t\t\t<PATProvinceCode>140000</PATProvinceCode>\n" +
                "\t\t\t\t\t<PATProvinceDesc>山西省</PATProvinceDesc>\n" +
                "\t\t\t\t\t<PATPostalCode></PATPostalCode>\n" +
                "\t\t\t\t</PATAddress>\n" +
                "\t\t\t</PATAddressList>\n" +
                "\t\t\t<PATIdentityList>\n" +
                "\t\t\t\t<PATIdentity>\n" +
                "\t\t\t\t\t<PATIdentityNum>142330199304216213</PATIdentityNum>\n" +
                "\t\t\t\t\t<PATIdTypeCode>01</PATIdTypeCode>\n" +
                "\t\t\t\t\t<PATIdTypeDesc>居民身份证</PATIdTypeDesc>\n" +
                "\t\t\t\t</PATIdentity>\n" +
                "\t\t\t</PATIdentityList>\n" +
                "\t\t\t<PATRelationList>\n" +
                "\t\t\t\t<PATRelation>\n" +
                "\t\t\t\t\t<PATRelationCode></PATRelationCode>\n" +
                "\t\t\t\t\t<PATRelationDesc></PATRelationDesc>\n" +
                "\t\t\t\t\t<PATRelationName></PATRelationName>\n" +
                "\t\t\t\t\t<PATRelationPhone></PATRelationPhone>\n" +
                "\t\t\t\t\t<PATRelationAddressList>\n" +
                "\t\t\t\t\t\t<PATRelationAddress>\n" +
                "\t\t\t\t\t\t\t<PATRelationAddressDesc></PATRelationAddressDesc>\n" +
                "\t\t\t\t\t\t\t<PATRelationHouseNum></PATRelationHouseNum>\n" +
                "\t\t\t\t\t\t\t<PATRelationVillage></PATRelationVillage>\n" +
                "\t\t\t\t\t\t\t<PATRelationCountryside></PATRelationCountryside>\n" +
                "\t\t\t\t\t\t\t<PATRelationCountyCode></PATRelationCountyCode>\n" +
                "\t\t\t\t\t\t\t<PATRelationCountyDesc></PATRelationCountyDesc>\n" +
                "\t\t\t\t\t\t\t<PATRelationCityCode></PATRelationCityCode>\n" +
                "\t\t\t\t\t\t\t<PATRelationCityDesc></PATRelationCityDesc>\n" +
                "\t\t\t\t\t\t\t<PATRelationProvinceCode></PATRelationProvinceCode>\n" +
                "\t\t\t\t\t\t\t<PATRelationProvinceDesc></PATRelationProvinceDesc>\n" +
                "\t\t\t\t\t\t\t<PATRelationPostalCode></PATRelationPostalCode>\n" +
                "\t\t\t\t\t\t</PATRelationAddress>\n" +
                "\t\t\t\t\t</PATRelationAddressList>\n" +
                "\t\t\t\t</PATRelation>\n" +
                "\t\t\t</PATRelationList>\n" +
                "\t\t\t<PATTelephone>17803587771</PATTelephone>\n" +
                "\t\t\t<PATRemarks></PATRemarks>\n" +
                "\t\t\t<UpdateUserCode>-</UpdateUserCode>\n" +
                "\t\t\t<UpdateUserDesc>-</UpdateUserDesc>\n" +
                "\t\t\t<UpdateDate>2021-05-10</UpdateDate>\n" +
                "\t\t\t<UpdateTime>17:29:33</UpdateTime>\n" +
                "\t\t</PatientRegistryRt>\n" +
                "\t</Body>\n" +
                "</Request>";


        String userXml = "<Request>\n" +
                "<Header><SourceSystem>02</SourceSystem><MessageID>20462</MessageID></Header>\n" +
                "<Body>\n" +
                "<AddOrdersRt>\n" +
                "\t<BusinessFieldCode>00002</BusinessFieldCode>\n" +
                "\t<HospitalCode>LLSRMYY</HospitalCode>\n" +
                "\t<PATPatientID>0000112877</PATPatientID>\n" +
                "\t<PAADMVisitNumber>195493</PAADMVisitNumber>\n" +
                "\t<PAADMEncounterTypeCode>I</PAADMEncounterTypeCode>\n" +
                "\t<OEORIInfoList>\n" +
                "\t\t<OEORIInfo>\n" +
                "\t\t\t<BusinessFieldCode>00002</BusinessFieldCode>\n" +
                "\t\t\t<HospitalCode>LLSRMYY</HospitalCode>\n" +
                "\t\t\t<OEORIOrderItemID>193692||323</OEORIOrderItemID>\n" +
                "\t\t\t<OEORIOEORIDR></OEORIOEORIDR>\n" +
                "\t\t\t<OEORIARCItmMastCode>XY20200689</OEORIARCItmMastCode>\n" +
                "\t\t\t<OEORIARCItmMastDesc>盐酸艾司洛尔注射液[0.1g:10ml]\n" +
                "\n" +
                "</OEORIARCItmMastDesc>\n" +
                "\t\t\t<OEORIPriorityCode>NORM</OEORIPriorityCode>\n" +
                "\t\t\t<OEORIPriorityDesc>临时医嘱</OEORIPriorityDesc>\n" +
                "\t\t\t<OEORIStatusCode>V</OEORIStatusCode>\n" +
                "\t\t\t<OEORIStatusDesc>核实</OEORIStatusDesc>\n" +
                "\t\t\t<OEORIClass>西药类</OEORIClass>\n" +
                "\t\t\t<OEORIClassDesc>西药类</OEORIClassDesc>\n" +
                "\t\t\t<OEORIPrescNo>I210520002309</OEORIPrescNo>\n" +
                "\t\t\t<OEORIDoseFormsCode>注射液(玻璃安瓿)</OEORIDoseFormsCode>\n" +
                "\t\t\t<OEORIDoseFormsDesc>注射液(玻璃安瓿)</OEORIDoseFormsDesc>\n" +
                "\t\t\t<OEORIDoseQty>0.5</OEORIDoseQty>\n" +
                "\t\t\t<OEORIDoseUnitCode>g</OEORIDoseUnitCode>\n" +
                "\t\t\t<OEORIDoseUnitDesc>g</OEORIDoseUnitDesc>\n" +
                "\t\t\t<OEORIFreqCode>ONCE</OEORIFreqCode>\n" +
                "\t\t\t<OEORIFreqDesc>每日一次</OEORIFreqDesc>\n" +
                "\t\t\t<OEORIInstrCode>静脉泵入</OEORIInstrCode>\n" +
                "\t\t\t<OEORIInstrDesc>静脉泵入</OEORIInstrDesc>\n" +
                "\t\t\t<OEORIDurationCode>1天</OEORIDurationCode>\n" +
                "\t\t\t<OEORIDurationDesc>1天</OEORIDurationDesc>\n" +
                "\t\t\t<OEORIOrderQty>5</OEORIOrderQty>\n" +
                "\t\t\t<OEORIResultStatusCode>A</OEORIResultStatusCode>\n" +
                "\t\t\t<OEORIResultStatusDesc>Awaiting</OEORIResultStatusDesc>\n" +
                "\t\t\t<OEORIRemarks></OEORIRemarks>\n" +
                "\t\t\t<OEORIEnterDocCode>00383</OEORIEnterDocCode>\n" +
                "\t\t\t<OEORIEnterDocDesc>陈祥慧</OEORIEnterDocDesc>\n" +
                "\t\t\t<OEORIEnterDate>2021-05-20</OEORIEnterDate>\n" +
                "\t\t\t<OEORIEnterTime>17:45:56</OEORIEnterTime>\n" +
                "\t\t\t<OEORIEnterDeptCode>重症医学科</OEORIEnterDeptCode>\n" +
                "\t\t\t<OEORIEnterDeptDesc>重症医学科</OEORIEnterDeptDesc>\n" +
                "\t\t\t<OEORIExecDeptCode>ZYYF</OEORIExecDeptCode>\n" +
                "\t\t\t<OEORIExecDeptDesc>住院药房</OEORIExecDeptDesc>\n" +
                "\t\t\t<OEORIRequireExecDate>2021-05-20</OEORIRequireExecDate>\n" +
                "\t\t\t<OEORIRequireExecTime>17:45:56</OEORIRequireExecTime>\n" +
                "\t\t\t<OEORIStopDate></OEORIStopDate>\n" +
                "\t\t\t<OEORIStopTime></OEORIStopTime>\n" +
                "\t\t\t<OEORIStopDocCode></OEORIStopDocCode>\n" +
                "\t\t\t<OEORIStopDocDesc></OEORIStopDocDesc>\n" +
                "\t\t\t<OEORIIsSkinTest>N</OEORIIsSkinTest>\n" +
                "\t\t\t<OEORIISEmergency>N</OEORIISEmergency>\n" +
                "\t\t\t<OEORIParentOrderID></OEORIParentOrderID>\n" +
                "\t\t\t<OEORISpecimenID></OEORISpecimenID>\n" +
                "\t\t\t<OEORISpecimenCode></OEORISpecimenCode>\n" +
                "\t\t\t<MaterialBarcode></MaterialBarcode>\n" +
                "\t\t\t<OEORIPrice>0</OEORIPrice>\n" +
                "\t\t\t<OEORISpecification>0.1g:10ml</OEORISpecification>\n" +
                "\t\t\t</OEORIInfo>\n" +

                "\t\t</OEORIInfoList>\n" +
                "\t\t<UpdateUserCode>demo</UpdateUserCode>\n" +
                "\t\t<UpdateUserDesc>demo</UpdateUserDesc>\n" +
                "\t\t<UpdateDate>2021-05-20</UpdateDate>\n" +
                "\t\t<UpdateTime>17:46:20</UpdateTime>\n" +
                "\t\t</AddOrdersRt>\n" +
                "\t\t</Body>\n" +
                "\t\t</Request>";
        hisWebService.test(userXml);
    }*/
}
