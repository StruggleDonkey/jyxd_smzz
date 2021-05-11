package com.jyxd.web.his.web.webimplements;

import cn.hutool.json.XML;
import com.jyxd.web.controller.basic.OutputAmountController;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.his.data.commmon.*;
import com.jyxd.web.his.data.patient.PatientRequest;
import com.jyxd.web.his.web.HisWebService;

import com.jyxd.web.service.patient.PatientService;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jyxd.web.util.DateUtil.*;

@Service
@WebService(serviceName = "HisWebService", // 与接口中指定的name一致
        targetNamespace = "http://xsdservice.business.mixpay.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.jyxd.web.his.web.HisWebService" // 接口地址
)
public class HisWebServiceImpl implements HisWebService {

    private static Logger logger = LoggerFactory.getLogger(HisWebServiceImpl.class);

    @Autowired
    private PatientService patientService;

    @Override
    public CommonResponse hisService(String action, String hisRequestXml) throws ParseException {
        logger.info("action ===== --> :" + action);
        logger.info("hisRequestXml ----->> " + hisRequestXml);
        boolean isSaveData = false;
        BodyData bodyData = new BodyData("-1", "失败");
        switch (action) {
            case "T0004":
                isSaveData = patientRegistry(hisRequestXml);
                break;
            case "T0017":
                isSaveData = inpatientEncounterStarted(hisRequestXml);
                break;
            default:
                break;
        }
        if (isSaveData) {
            bodyData.setResultCode("0");
            bodyData.setResultContent("成功");
        }
        return new CommonResponse(getXmlHeader(hisRequestXml), bodyData);
    }

    private boolean inpatientEncounterStarted(String hisRequestXml) {
        Map<String, Object> inpatientEncounterStartedRtMap = gainXmlData(hisRequestXml, "InpatientEncounterStartedRt");

        return false;
    }


    /**
     * 患者基本信息接收
     *
     * @param hisRequestXml
     */
    private boolean patientRegistry(String hisRequestXml) throws ParseException {
        Map<String, Object> patientRegistryRtMap = gainXmlData(hisRequestXml, "PatientRegistryRt");
        Patient patient = new Patient();
        patient.setId(UUIDUtil.getUUID());
        patient.setCreateTime(new Date());
        patient.setVisitId(String.valueOf(patientRegistryRtMap.get("PATPatientID")));
        patient.setName(String.valueOf(patientRegistryRtMap.get("PATPatientName")));
        if (!objectIsNull(patientRegistryRtMap.get("PATDob"))) {
            patient.setBirthday(String.valueOf(patientRegistryRtMap.get("PATDob")));
            patient.setAge(getAge(yyyyMMddSdfToDate(String.valueOf(patientRegistryRtMap.get("PATDob")))));
        }
        patient.setSex((int) patientRegistryRtMap.get("PATSexCode"));
        if (!objectIsNull(patientRegistryRtMap.get("PATMaritalStatusCode"))) {
            patient.setMaritalState(String.valueOf(patientRegistryRtMap.get("PATMaritalStatusCode")));
        }
        if (!objectIsNull(patientRegistryRtMap.get("PATNationCode"))) {
            patient.setRace(String.valueOf(patientRegistryRtMap.get("PATNationCode")));//患者民族代码
        }
        if (!objectIsNull(patientRegistryRtMap.get("PATDeceasedDate"))
                && Objects.nonNull(patientRegistryRtMap.get("PATDeceasedTime"))) {
            String deceasedDate = String.valueOf(patientRegistryRtMap.get("PATDeceasedDate"));
            String deceasedTime = String.valueOf(patientRegistryRtMap.get("PATDeceasedTime"));
            patient.setDeathTime(yyyyMMddHHmmsSdfToDate(deceasedDate + " " + deceasedTime));
        }
        Map<String, Object> pATIdentityMap = getPATIdentityMap(patientRegistryRtMap);
        if (!objectIsNull(pATIdentityMap.get("PATIdentityNum"))) {
            patient.setIdCard(String.valueOf(pATIdentityMap.get("PATIdentityNum")));//患者证件号码
            patient.setAge(IdNOToAge(String.valueOf(pATIdentityMap.get("PATIdentityNum"))));//年龄
        }
        Map<String, Object> patRelationMap = getPATRelationMap(patientRegistryRtMap);
        if (!objectIsNull(patRelationMap.get("PATRelationName"))) {
            patient.setContactMan((String) patRelationMap.get("PATRelationName"));//患者联系人姓名
        }
        if (!objectIsNull(patRelationMap.get("PATRelationPhone"))) {
            patient.setContactMan(String.valueOf(patRelationMap.get("PATRelationPhone")));
        }
        return patientService.insert(patient);
    }

    /**
     * 获取患者身份信息数据
     *
     * @param patientRegistryRtMap
     * @return
     */
    private Map<String, Object> getPATIdentityMap(Map<String, Object> patientRegistryRtMap) {
        Map<String, Object> map = xmlToJsonMap(patientRegistryRtMap.get("PATIdentityList"));
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
     * 判断数据是否为空
     *
     * @param object
     * @return
     */
    private boolean objectIsNull(Object object) {
        if (Objects.isNull(object)) {
            return true;
        }
        if (StringUtils.isEmpty(String.valueOf(object))) {
            return true;
        }
        return false;
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
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
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
        Map<String, Object> patientRegistryRtMap = gainXmlData(xml, "PatientRegistryRt");
        System.out.println(patientRegistryRtMap);
        Map<String, Object> patRelationAddressMap = getPATRelationMap(patientRegistryRtMap);
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
        System.out.println(patRelationAddressMap);

        System.out.println(objectIsNull(patRelationAddressMap.get("PATRelationName")));

    }

    public static void main(String[] args) {
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
       /* HisWebService hisWebService = new HisWebServiceImpl();
        hisWebService.hisService("T0004",xml);*/

        /*JSONObject xmlJSONObj = JSONObject.fromObject(XML.toJSONObject(xml));

        Map<String, Object> messageMap = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(xmlJSONObj), HashMap.class);
        //String jsonObject2 = JSON.toJSONString(users);
        //System.out.println(messageMap);

        //System.out.println(messageMap.get("Request"));


        Map<String, Object> bodyMap = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(messageMap.get("Request")), HashMap.class);

        //System.out.println(bodyMap.get("Body"));

        Map<String, Object> patientRegistryRtMap = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(bodyMap.get("Body")), HashMap.class);

        System.out.println(patientRegistryRtMap.get("PatientRegistryRt"));*/

        HisWebServiceImpl hisWebService = new HisWebServiceImpl();
        hisWebService.test(xml);
        //PATPatientID

    }
}
