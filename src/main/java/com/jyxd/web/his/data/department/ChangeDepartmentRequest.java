package com.jyxd.web.his.data.department;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his接收转科信息 实体类
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class ChangeDepartmentRequest {

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
        @JacksonXmlProperty(localName = "AdmTransactionRt",isAttribute = true)
        private AdmTransactionRt AdmTransactionRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class AdmTransactionRt{
        @JacksonXmlProperty(localName = "PATPatientID")
        private String PATPatientID;//患者主索引 不能为空
        @JacksonXmlProperty(localName = "PAADMVisitNumber")
        private String PAADMVisitNumber ;//就诊号码 不能为空
        @JacksonXmlProperty(localName = "PAADMTStartDate")
        private Date PAADMTStartDate ;//患转出日期    YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "PAADMTStartTime")
        private Date PAADMTStartTime ;//转出时间    hh:mm:ss 不能为空
        @JacksonXmlProperty(localName = "PAADMTOrigDocCode")
        private String PAADMTOrigDocCode ;//转出医生代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTOrigDeptCode")
        private String PAADMTOrigDeptCode ;//转出科室代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTOrigWardCode")
        private String PAADMTOrigWardCode ;//转出病区代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTOrigRoomCode")
        private String PAADMTOrigRoomCode ;//转出房间代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTOrigBedCode")
        private String PAADMTOrigBedCode ;//转出床位代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTEndDate")
        private Date PAADMTEndDate ;//转入日期 不能为空 YYYY-MM-DD
        @JacksonXmlProperty(localName = "PAADMTEndTime")
        private Date PAADMTEndTime ;//患者健康卡号 不能为空 hh:mm:ss
        @JacksonXmlProperty(localName = "PAADMTTargDocCode")
        private String PAADMTTargDocCode ;//转入医生代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTTargDeptCode")
        private String PAADMTTargDeptCode ;//转入科室代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTTargWardCode")
        private String PAADMTTargWardCode ;//转入病区代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTTargRoomCode")
        private String PAADMTTargRoomCode ;//转入房间代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTTargBedCode")
        private String PAADMTTargBedCode ;//转入床位代码 不能为空
        @JacksonXmlProperty(localName = "PAADMTState")
        private String PAADMTState ;//转移状态 不能为空 01转出02转入03换床04换医生
        @JacksonXmlProperty(localName = "UpdateUserCode")
        private String UpdateUserCode ;//最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "UpdateDate")
        private Date UpdateDate ;//最后更新日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "UpdateTime")
        private Date UpdateTime ;//最后更新时间 hh:mm:ss 不能为空
    }

}
