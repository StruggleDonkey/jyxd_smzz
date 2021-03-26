package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName ="Response")
public class CommonResponse {

    private HeaderData Header;

    private BodyData Body;

    public HeaderData getHeader() {
        return Header;
    }

    public void setHeader(HeaderData header) {
        Header = header;
    }

    public BodyData getBody() {
        return Body;
    }

    public void setBody(BodyData body) {
        Body = body;
    }

}
