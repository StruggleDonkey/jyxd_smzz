package com.jyxd.web.his.data.patient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his住院登记 实体类
 * 住院登记成功后调用
 * 服务编码	T0017
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class InHospitalRequest {

    @JacksonXmlProperty(localName = "Header")
    private Header Header;

    @JacksonXmlProperty(localName = "Body")
    private Body Body;

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class Header{
        @JacksonXmlProperty(localName = "SourceSystem")
        private String SourceSystem;//消息来源
        @JacksonXmlProperty(localName = "MessageID")
        private String MessageID;//消息ID
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class Body{
        @JacksonXmlProperty(localName = "InpatientEncounterStartedRt",isAttribute = true)
        private InpatientEncounterStartedRt InpatientEncounterStartedRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class InpatientEncounterStartedRt{
        @JacksonXmlProperty(localName = "PATPatientID")
        private String PATPatientID;//患者主索引 不能为空
        @JacksonXmlProperty(localName = "PAADMVisitNumber")
        private String PAADMVisitNumber ;//就诊号码 每次就诊一个号 不能为空
        @JacksonXmlProperty(localName = "PAAdmTypeCode")
        private String PAAdmTypeCode ;//就诊类型代码 不能为空 固定I:住院
        @JacksonXmlProperty(localName = "PAAdmTypeDesc")
        private String PAAdmTypeDesc ;//就诊类型描述 不能为空
        @JacksonXmlProperty(localName = "FeeTypeCode")
        private String FeeTypeCode ;//费别代码 不能为空
        @JacksonXmlProperty(localName = "FeeTypeDesc")
        private String FeeTypeDesc ;//费别描述 不能为空
        @JacksonXmlProperty(localName = "PAADMVisitTimes")
        private String PAADMVisitTimes ;//住院次数
        @JacksonXmlProperty(localName = "PAADMAdmitDocCode")
        private String PAADMAdmitDocCode ;//入院医生代码 不能为空
        @JacksonXmlProperty(localName = "PAADMAdmitDocDesc")
        private String PAADMAdmitDocDesc ;//入院医生描述 不能为空
        @JacksonXmlProperty(localName = "PAADMStartDate")
        private Date PAADMStartDate ;//入院日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "PAADMStartTime")
        private Date PAADMStartTime ;//入院时间 hh:mm:ss 不能为空
        @JacksonXmlProperty(localName = "PAADMAdmDeptCode")
        private String PAADMAdmDeptCode ;//入院就诊科室代码 不能为空
        @JacksonXmlProperty(localName = "PAADMAdmDeptDesc")
        private String PAADMAdmDeptDesc ;//入院就诊科室描述 不能为空
        @JacksonXmlProperty(localName = "PAADMAdmWardCode")
        private String PAADMAdmWardCode ;//入院病区代码
        @JacksonXmlProperty(localName = "PAADMAdmWardDesc")
        private String PAADMAdmWardDesc ;//入院病区描述
        @JacksonXmlProperty(localName = "PAADMCurBedNo")
        private String PAADMCurBedNo ;//病床号

        @JacksonXmlProperty(localName = "PAADMDiagnoseList")
        private PAADMDiagnoseList PAADMDiagnoseList;//封装类

        @JacksonXmlProperty(localName = "UpdateUserCode")
        private String UpdateUserCode ;//最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "UpdateUserDesc")
        private String UpdateUserDesc ;//最后更新人描述 不能为空
        @JacksonXmlProperty(localName = "UpdateDate")
        private Date UpdateDate ;//最后更新日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "UpdateTime")
        private Date UpdateTime ;//最后更新时间 hh:mm:ss 不能为空
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PAADMDiagnoseList{
        @JacksonXmlProperty(localName = "PAADMDiagnose")
        public PAADMDiagnose PAADMDiagnose ;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PAADMDiagnose{
        @JacksonXmlProperty(localName = "PADDiagCode")
        private String PADDiagCode ;// 诊断代码 不能为空
        @JacksonXmlProperty(localName = "PADDiagDesc")
        private String PADDiagDesc ;//诊断描述 不能为空
        @JacksonXmlProperty(localName = "PADDiagType")
        private String PADDiagType ;//诊断类型 不能为空 主诊断、出院诊断、入院诊断
        @JacksonXmlProperty(localName = "PADDiagDocCode")
        private String PADDiagDocCode ;//诊断医生代码 不能为空
        @JacksonXmlProperty(localName = "PADDiagDocDesc")
        private String PADDiagDocDesc ;//诊断医生描述 不能为空
        @JacksonXmlProperty(localName = "PADDiagCategory")
        private String PADDiagCategory ;//诊断级别  1、主要诊断（默认第一条） 2、次要诊断
        @JacksonXmlProperty(localName = "PADDiagDate")
        private Date PADDiagDate ;//诊断日期YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "PADDiagTime")
        private Date PADDiagTime ;//诊断时间 hh:mm:ss 不能为空
        @JacksonXmlProperty(localName = "PADRemarks")
        private String PADRemarks ;//备注
    }

}
