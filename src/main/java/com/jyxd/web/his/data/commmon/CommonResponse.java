package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;

@JacksonXmlRootElement(localName = "Response")
public class CommonResponse {

    private HeaderData header;

    private BodyData body;

    public CommonResponse() {
    }

    public CommonResponse(HeaderData header, BodyData body) {
        this.header = header;
        this.body = body;
    }

    @XmlElement(name = "Header")
    public HeaderData getHeader() {
        return header;
    }

    public void setHeader(HeaderData header) {
        this.header = header;
    }

    @XmlElement(name = "Body")
    public BodyData getBody() {
        return body;
    }

    public void setBody(BodyData body) {
        this.body = body;
    }

}
