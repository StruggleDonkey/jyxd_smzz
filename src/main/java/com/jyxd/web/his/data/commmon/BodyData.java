package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
//防止重复序列化
public class BodyData {

    private String resultCode;//响应码 0：成功 -1:失败

    private String resultContent;//响应信息

    public BodyData() {
    }

    public BodyData(String resultCode, String resultContent) {
        this.resultCode = resultCode;
        this.resultContent = resultContent;
    }

    @XmlElement(name = "ResultCode")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @XmlElement(name = "ResultContent")
    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
}
