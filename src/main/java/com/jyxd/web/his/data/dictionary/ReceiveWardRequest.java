package com.jyxd.web.his.data.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his发送病区字典信息 实体类
 * 服务编码	MS027 (CT_Ward)
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class ReceiveWardRequest {

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
        private CT_WardList CT_WardList;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_WardList{
        @JacksonXmlProperty(localName = "CT_CareProv")
        private CT_Ward CT_Ward;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_Ward{
        @JacksonXmlProperty(localName = "CTW_Code")
        private String CTW_Code;//病区代码 不能为空
        @JacksonXmlProperty(localName = "CTW_CodesystemCode")
        private String CTW_CodesystemCode;//代码表类型 CT_Ward 不能为空
        @JacksonXmlProperty(localName = "CTW_Desc")
        private String CTW_Desc;//科室描述 不能为空
        @JacksonXmlProperty(localName = "CTW_HosCode")
        private String CTW_HosCode;//医院编号 不能为空
        @JacksonXmlProperty(localName = "CTW_Remarks")
        private String CTW_Remarks;//备注
        @JacksonXmlProperty(localName = "CTW_Status")
        private String CTW_Status;//状态 1启用0停用-1删除 不能为空
        @JacksonXmlProperty(localName = "CTW_UpdateUserCode")
        private String CTW_UpdateUserCode;//最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "CTW_UpdateDate")
        private Date CTW_UpdateDate;//最后更新日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTW_UpdateTime")
        private Date CTW_UpdateTime;//最后更新时间 hh:mm:ss
    }

}
