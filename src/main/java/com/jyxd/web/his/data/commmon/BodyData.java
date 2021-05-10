package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
//防止重复序列化
@Data
public class BodyData {

    @JsonProperty("ResultCode")
    @JsonIgnore
    private String ResultCode;//响应码 0：成功 -1:失败
    @JsonIgnore
    private String ResultContent;//响应信息

    public BodyData() {
    }

    public BodyData(String resultCode, String resultContent) {
        ResultCode = resultCode;
        ResultContent = resultContent;
    }

}
