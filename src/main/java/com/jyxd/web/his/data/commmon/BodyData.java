package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class BodyData {

    public String ResultCode;//响应码 0：成功 -1:失败

    public String ResultContent;//响应信息

    public BodyData() {
    }

    public BodyData(String resultCode, String resultContent) {
        ResultCode = resultCode;
        ResultContent = resultContent;
    }

    @JacksonXmlProperty(localName = "ResultCode")
    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    @JacksonXmlProperty(localName = "ResultContent")
    public String getResultContent() {
        return ResultContent;
    }

    public void setResultContent(String resultContent) {
        ResultContent = resultContent;
    }
}
