package com.jyxd.web.his.data.medOrderExec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his医嘱状态变更 实体类
 * 医嘱状态发生变化时调用
 * 服务编码	T0003
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class UpdateOrdersRtRequest {

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
        @JacksonXmlProperty(localName = "UpdateOrdersRt",isAttribute = true)
        private UpdateOrdersRt UpdateOrdersRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class UpdateOrdersRt{
        @JacksonXmlProperty(localName = "PATPatientID")
        private String PATPatientID;//患者主索引 不能为空
        @JacksonXmlProperty(localName = "PAADMVisitNumber")
        private String PAADMVisitNumber ;//就诊号码 不能为空
        @JacksonXmlProperty(localName = "PAAdmTypeCode")
        private String PAAdmTypeCode ;//就诊类型代码 不能为空 用已区分O:门诊、H:体检、E:急诊病人就诊信息

        @JacksonXmlProperty(localName = "OEORIInfoList")
        private OEORIInfoList OEORIInfoList;//封装类

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
    public static class OEORIInfoList{
        @JacksonXmlProperty(localName = "OEORIInfo")
        public OEORIInfo OEORIInfo ;//封装类

    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class OEORIInfo{
        @JacksonXmlProperty(localName = "OEORIOrderItemID")
        private String OEORIOrderItemID ;//医嘱明细ID HIS产生单条医嘱唯一标识 不能为空
        @JacksonXmlProperty(localName = "OEORIStatusCode")
        private String OEORIStatusCode ;//医嘱状态代码 V核实E执行D停止 C撤销 不能为空
        @JacksonXmlProperty(localName = "OEORIOrdSubCatCode")
        private String OEORIOrdSubCatCode ;//医嘱子类代码
        @JacksonXmlProperty(localName = "OEORIOrdSubCatDesc")
        private String OEORIOrdSubCatDesc ;//医嘱子类描述
        @JacksonXmlProperty(localName = "OEORIOrdCatCode")
        private String OEORIOrdCatCode ;//医嘱大类代码
        @JacksonXmlProperty(localName = "OEORIOrdCatDesc")
        private String OEORIOrdCatDesc ;//医嘱大类描述
        @JacksonXmlProperty(localName = "OEORIParentOrderID")
        private String OEORIParentOrderID ;//父医嘱ID
    }

}
