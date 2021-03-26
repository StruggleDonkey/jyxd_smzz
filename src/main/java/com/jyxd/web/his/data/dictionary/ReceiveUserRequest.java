package com.jyxd.web.his.data.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his发送医护人员字典信息 实体类
 * 服务编码	MS004 (CT_CareProv)
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class ReceiveUserRequest {

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
        @JacksonXmlProperty(localName = "CT_CareProvList",isAttribute = true)
        private CT_CareProvList CT_CareProvList;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_CareProvList{
        @JacksonXmlProperty(localName = "CT_CareProv")
        private CT_CareProv CT_CareProv;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_CareProv{
        @JacksonXmlProperty(localName = "CTCP_BirthDate")
        private Date CTCP_BirthDate;//出生日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTCP_BirthPlace")
        private String CTCP_BirthPlace;//出生地
        @JacksonXmlProperty(localName = "CTCP_Code")
        private String CTCP_Code;//代码 职工代码 不能为空
        @JacksonXmlProperty(localName = "CTCP_CodesystemCode")
        private String CTCP_CodesystemCode;//代码表类型 CT_CareProv 不能为空
        @JacksonXmlProperty(localName = "CTCP_DeptCode")
        private String CTCP_DeptCode;//所属科室
        @JacksonXmlProperty(localName = "CTCP_Desc")
        private String CTCP_Desc;//描述 职工姓名 不能为空
        @JacksonXmlProperty(localName = "CTCP_EndDate")
        private Date CTCP_EndDate;//有效结束日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTCP_HosCode")
        private String CTCP_HosCode;//医疗机构代码 415807818 不能为空
        @JacksonXmlProperty(localName = "CTCP_IDCardNO")
        private String CTCP_IDCardNO;//身份证号
        @JacksonXmlProperty(localName = "CTCP_StaffType")
        private String CTCP_StaffType;//医护人员类型 DOCTOR医生 NURSE护士 Technician技术人员 Pharmacist药剂师
        @JacksonXmlProperty(localName = "CTCP_Name")
        private String CTCP_Name;//姓名
        @JacksonXmlProperty(localName = "CTCP_PassWord")
        private String CTCP_PassWord;//密码
        @JacksonXmlProperty(localName = "CTCP_Remarks")
        private String CTCP_Remarks;//备注
        @JacksonXmlProperty(localName = "CTCP_SexCode")
        private String CTCP_SexCode;//性别 1 男  2女
        @JacksonXmlProperty(localName = "CTCP_TitleOfTechCode")
        private String CTCP_TitleOfTechCode;//职称 汉字
        @JacksonXmlProperty(localName = "CTCP_Position")
        private String CTCP_Position;//职位 汉字
        @JacksonXmlProperty(localName = "CTCP_Title")
        private String CTCP_Title;//职称 嘉和用该字段
        @JacksonXmlProperty(localName = "CTCP_StartDate")
        private Date CTCP_StartDate;//有效开始日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "CTCP_Status")
        private String CTCP_Status;//状态 1启用0停用-1删除 不能为空
        @JacksonXmlProperty(localName = "CTCP_UpdateUserCode")
        private String CTCP_UpdateUserCode;//有最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "CTCP_UpdateDate")
        private Date CTCP_UpdateDate;//最后更新日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTCP_UpdateTime")
        private Date CTCP_UpdateTime;//最后更新时间 hh:mm:ss 不能为空
    }

}
