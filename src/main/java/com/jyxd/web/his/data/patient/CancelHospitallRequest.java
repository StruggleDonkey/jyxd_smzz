package com.jyxd.web.his.data.patient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his取消住院登记 实体类
 * 取消住院登记成功后调用
 * 服务编码	T0018
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class CancelHospitallRequest {

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
        @JacksonXmlProperty(localName = "InpatientEncounterCancelRt",isAttribute = true)
        private InpatientEncounterCancelRt InpatientEncounterCancelRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class InpatientEncounterCancelRt{
        @JacksonXmlProperty(localName = "PATPatientID")
        private String PATPatientID;//患者主索引 不能为空
        @JacksonXmlProperty(localName = "PAADMVisitNumber")
        private String PAADMVisitNumber ;//就诊号码 每次就诊一个号 不能为空
        @JacksonXmlProperty(localName = "PAADMDeptCode")
        private String PAADMDeptCode ;//门诊就诊科室代码 不能为空
        @JacksonXmlProperty(localName = "PAADMDeptDesc")
        private String PAADMDeptDesc ;//门诊就诊科室描述 不能为空

        @JacksonXmlProperty(localName = "UpdateUserCode")
        private String UpdateUserCode ;//最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "UpdateUserDesc")
        private String UpdateUserDesc ;//最后更新人描述 不能为空
        @JacksonXmlProperty(localName = "UpdateDate")
        private Date UpdateDate ;//最后更新日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "UpdateTime")
        private Date UpdateTime ;//最后更新时间 hh:mm:ss 不能为空
    }

}
