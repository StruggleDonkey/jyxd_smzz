package com.jyxd.web.his.data.medOrderExec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his接收医嘱信息 实体类
 * 医生开完医嘱保存成功后调用
 * 服务编码	T0001
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class AddOrdersRtRequest {

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
        @JacksonXmlProperty(localName = "AddOrdersRt",isAttribute = true)
        private AddOrdersRt AddOrdersRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class AddOrdersRt{
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
        @JacksonXmlProperty(localName = "OEORIOEORIDR")
        private String OEORIOEORIDR ;//医嘱组号 HIS产生同一次就诊有相同的医嘱组号 不能为空
        @JacksonXmlProperty(localName = "OEORIARCItmMastCode")
        private String OEORIARCItmMastCode ;//医嘱项目代码 不能为空
        @JacksonXmlProperty(localName = "OEORIARCItmMastDesc")
        private String OEORIARCItmMastDesc ;//医嘱项目描述不能为空
        @JacksonXmlProperty(localName = "OEORIPriorityCode")
        private String OEORIPriorityCode ;//医嘱类型代码 不能为空
        @JacksonXmlProperty(localName = "OEORIPriorityDesc")
        private String OEORIPriorityDesc ;//医嘱类型描述 不能为空 长期、临时、自备长期、自备临时、出院带药
        @JacksonXmlProperty(localName = "OEORIStatusCode")
        private String OEORIStatusCode ;//医嘱状态代码 不能为空
        @JacksonXmlProperty(localName = "OEORIStatusDesc")
        private String OEORIStatusDesc ;//医嘱状态描述 不能为空
        @JacksonXmlProperty(localName = "OEORIClass")
        private String OEORIClass ;//医嘱类别代码 不能为空 检查类 西药类 中药类
        @JacksonXmlProperty(localName = "OEORIClassDesc")
        private String OEORIClassDesc ;//医嘱类别描述 不能为空
        @JacksonXmlProperty(localName = "OEORIPrescNo")
        private String OEORIPrescNo ;//处方号
        @JacksonXmlProperty(localName = "OEORIDoseFormsCode")
        private String OEORIDoseFormsCode ;//剂型代码
        @JacksonXmlProperty(localName = "OEORIDoseFormsDesc")
        private String OEORIDoseFormsDesc ;//剂型描述
        @JacksonXmlProperty(localName = "OEORIDoseQty")
        private String OEORIDoseQty ;//单次剂量
        @JacksonXmlProperty(localName = "OEORIDoseUnitCode")
        private String OEORIDoseUnitCode ;//单次剂量单位代码
        @JacksonXmlProperty(localName = "OEORIDoseUnitDesc")
        private String OEORIDoseUnitDesc ;//单次剂量单位描述
        @JacksonXmlProperty(localName = "OEORIFreqCode")
        private String OEORIFreqCode ;//频次代码 不能为空
        @JacksonXmlProperty(localName = "OEORIFreqDesc")
        private String OEORIFreqDesc ;//频次描述 不能为空
        @JacksonXmlProperty(localName = "OEORIInstrCode")
        private String OEORIInstrCode ;//用药途径代码
        @JacksonXmlProperty(localName = "OEORIInstrDesc")
        private String OEORIInstrDesc ;//用药途径描述
        @JacksonXmlProperty(localName = "OEORIDurationCode")
        private String OEORIDurationCode ;//疗程代码
        @JacksonXmlProperty(localName = "OEORIDurationDesc")
        private String OEORIDurationDesc ;//疗程描述
        @JacksonXmlProperty(localName = "OEORIOrderQty")
        private String OEORIOrderQty ;//医嘱数量 不能为空
        @JacksonXmlProperty(localName = "OEORIResultStatusCode")
        private String OEORIResultStatusCode ;//医嘱结果状态代码
        @JacksonXmlProperty(localName = "OEORIResultStatusDesc")
        private String OEORIResultStatusDesc ;//医嘱结果状态描述
        @JacksonXmlProperty(localName = "OEORIRemarks")
        private String OEORIRemarks ;//医嘱备注信息
        @JacksonXmlProperty(localName = "OEORIEnterDocCode")
        private String OEORIEnterDocCode ;//医嘱开立者代码 不能为空
        @JacksonXmlProperty(localName = "OEORIEnterDocDesc")
        private String OEORIEnterDocDesc ;//医嘱开立者描述 不能为空
        @JacksonXmlProperty(localName = "OEORIEnterDate")
        private Date OEORIEnterDate ;//医嘱开立日期 不能为空 YYYY-MM-DD
        @JacksonXmlProperty(localName = "OEORIEnterTime")
        private Date OEORIEnterTime ;//医嘱开立时间 不能为空 hh:mm:ss
        @JacksonXmlProperty(localName = "OEORIEnterDeptCode")
        private String OEORIEnterDeptCode ;//医嘱开立科室代码 不能为空
        @JacksonXmlProperty(localName = "OEORIEnterDeptDesc")
        private String OEORIEnterDeptDesc ;//医嘱开立科室
        @JacksonXmlProperty(localName = "OEORIExecDeptCode")
        private String OEORIExecDeptCode ;//医嘱执行科室代码 不能为空
        @JacksonXmlProperty(localName = "OEORIExecDeptDesc")
        private String OEORIExecDeptDesc ;//医嘱执行科室
        @JacksonXmlProperty(localName = "OEORIRequireExecDate")
        private Date OEORIRequireExecDate ;//要求执行日期  不能为空 YYYY-MM-DD
        @JacksonXmlProperty(localName = "OEORIRequireExecTime")
        private Date OEORIRequireExecTime ;//要求执行时间 不能为空 hh:mm:ss
        @JacksonXmlProperty(localName = "OEORIStopDate")
        private Date OEORIStopDate ;//医嘱停止日期 不能为空 YYYY-MM-DD
        @JacksonXmlProperty(localName = "OEORIStopTime")
        private Date OEORIStopTime ;//医嘱停止时间 不能为空 hh:mm:ss
        @JacksonXmlProperty(localName = "OEORIStopDocCode")
        private String OEORIStopDocCode ;//停止医嘱者代码
        @JacksonXmlProperty(localName = "OEORIStopDocDesc")
        private String OEORIStopDocDesc ;//停止医嘱者描述
        @JacksonXmlProperty(localName = "OEORIIsSkinTest")
        private boolean OEORIIsSkinTest ;//是否皮试 1/0
        @JacksonXmlProperty(localName = "OEORIISEmergency")
        private boolean OEORIISEmergency ;//是否紧急
        @JacksonXmlProperty(localName = "OEORIParentOrderID")
        private String OEORIParentOrderID ;//父医嘱ID 主医嘱ID
        @JacksonXmlProperty(localName = "OEORISpecimenID")
        private String OEORISpecimenID ;//标本号
        @JacksonXmlProperty(localName = "OEORISpecimenCode")
        private String OEORISpecimenCode ;//标本代码
        @JacksonXmlProperty(localName = "MaterialBarcode")
        private String MaterialBarcode ;//材料条形码
        @JacksonXmlProperty(localName = "OEORIPrice")
        private String OEORIPrice ;//医嘱价格
        @JacksonXmlProperty(localName = "OEORISpecification")
        private String OEORISpecification ;//医嘱规格
        @JacksonXmlProperty(localName = "OEORIIsAntiDrug")
        private String OEORIIsAntiDrug ;//是否抗菌药物
        @JacksonXmlProperty(localName = "OEORIAntiPurpose")
        private String OEORIAntiPurpose ;//OEORIAntiPurpose

    }

}
