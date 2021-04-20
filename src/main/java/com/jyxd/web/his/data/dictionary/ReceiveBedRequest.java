package com.jyxd.web.his.data.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his发送床位字典信息 实体类
 * 服务编码	MS028 (CT_Bed)
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class ReceiveBedRequest {

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
        private CT_BedList CT_BedList;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_BedList{
        @JacksonXmlProperty(localName = "CT_CareProv")
        private CT_Bed CT_Bed;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class CT_Bed{
        @JacksonXmlProperty(localName = "CTB_Rowid")
        private String CTB_Rowid;//床位id 不能为空
        @JacksonXmlProperty(localName = "CTB_BedType")
        private String CTB_BedType;//床位类型 不能为空
        @JacksonXmlProperty(localName = "CTB_Code")
        private String CTB_Code;//床位代码 不能为空
        @JacksonXmlProperty(localName = "CTB_CodesystemCode")
        private String CTB_CodesystemCode;//代码表类型 不能为空
        @JacksonXmlProperty(localName = "CTB_Desc")
        private String CTB_Desc;//床位描述
        @JacksonXmlProperty(localName = "CTB_HosCode")
        private String CTB_HosCode;//医院编号 不能为空
        @JacksonXmlProperty(localName = "CTB_Remarks")
        private String CTB_Remarks;//备注 不能为空
        @JacksonXmlProperty(localName = "CTB_RoomCode")
        private String CTB_RoomCode;//房间号 不能为空
        @JacksonXmlProperty(localName = "CTB_Status")
        private String CTB_Status;//状态 1启用0停用-1删除 不能为空
        @JacksonXmlProperty(localName = "CTB_UpdateUserCode")
        private String CTB_UpdateUserCode;//最后更新人编码 不能为空
        @JacksonXmlProperty(localName = "CTW_UpdateDate")
        private Date CTW_UpdateDate;//最后更新日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "CTW_UpdateTime")
        private Date CTW_UpdateTime;//最后更新时间 hh:mm:ss
        @JacksonXmlProperty(localName = "CTB_WardCode")
        private String CTB_WardCode;//所属病区 不能为空
    }

}
