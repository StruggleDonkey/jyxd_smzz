package com.jyxd.web.his.data.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his发送科室字典信息 实体类
 * 服务编码	MS003  (CT_Dept)
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class ReceiveDepartmentRequest {

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
        @JacksonXmlProperty(localName = "CT_DeptList",isAttribute = true)
        private CT_DeptList CT_DeptList;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_DeptList{
        @JacksonXmlProperty(localName = "CT_Dept")
        private CT_Dept CT_Dept;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_Dept{
        @JacksonXmlProperty(localName = "CTD_CategoryCode")
        private String CTD_CategoryCode;//标识分类代码
        @JacksonXmlProperty(localName = "CTD_Code")
        private String CTD_Code;//科室代码 科室编码 不能为空
        @JacksonXmlProperty(localName = "CTD_CodesystemCode")
        private String CTD_CodesystemCode;//代码表类型 CT_Dept 不能为空
        @JacksonXmlProperty(localName = "CTD_Desc")
        private String CTD_Desc;//科室描述 科室名称 不能为空
        @JacksonXmlProperty(localName = "CTD_Spell")
        private String CTD_Spell;//拼音
        @JacksonXmlProperty(localName = "CTD_Category")
        private String CTD_Category;//科室类别 OR：诊室 OP：手术室 W：护理单元 EM：急诊科室E：临床科室 D：药房 O：管理科室 C：收费科室 不能为空
        @JacksonXmlProperty(localName = "CTD_Property")
        private String CTD_Property;//科室性质
        @JacksonXmlProperty(localName = "CTD_Rank")
        private String CTD_Rank;//科室级次 1、2、3、4 不能为空
        @JacksonXmlProperty(localName = "CTD_DepartNature")
        private String CTD_DepartNature;//部门属性 0 大科 1 科室 2 医疗组 3门诊医疗组末级 4住院医疗组末 5 其他医疗组末级 不能为空
        @JacksonXmlProperty(localName = "CTD_EndDate")
        private Date CTD_EndDate;//科有效结束日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTD_HosCode")
        private String CTD_HosCode;//医院编号 415807818 不能为空
        @JacksonXmlProperty(localName = "CTD_OfficeAddress")
        private String CTD_OfficeAddress;//工作地址
        @JacksonXmlProperty(localName = "CTD_OfficePhone")
        private String CTD_OfficePhone;//工作联系电话
        @JacksonXmlProperty(localName = "CTD_ParentDeptCode")
        private String CTD_ParentDeptCode;//上级科室代码
        @JacksonXmlProperty(localName = "CTD_GroupCode")
        private String CTD_GroupCode;//科室部门组代码
        @JacksonXmlProperty(localName = "CTD_GroupDesc")
        private String CTD_GroupDesc;//科室部门组描述
        @JacksonXmlProperty(localName = "CTD_Remarks")
        private String CTD_Remarks;//备注
        @JacksonXmlProperty(localName = "CTD_StartDate")
        private Date CTD_StartDate;//有效开始日期 YYYY-MM-DD 不能为空
        @JacksonXmlProperty(localName = "CTD_Status")
        private String CTD_Status;//状态 1启用0停用-1删除 不能为空
        @JacksonXmlProperty(localName = "CTD_UpdateUserCode")
        private String CTD_UpdateUserCode;//有最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "CTD_UpdateDate")
        private Date CTD_UpdateDate;//最后更新日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTD_UpdateTime")
        private Date CTD_UpdateTime;//最后更新时间 hh:mm:ss 不能为空
    }

}
