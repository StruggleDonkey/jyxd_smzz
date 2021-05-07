package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class HeaderData {

    public String SourceSystem;//消息来源

    public String MessageID;//消息ID


    public HeaderData() {
    }

    public HeaderData(String sourceSystem, String messageID ) {
        SourceSystem = sourceSystem;
        MessageID = messageID;
    }

    @JacksonXmlProperty(localName = "SourceSystem")
    public String getSourceSystem() {
        return SourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        SourceSystem = sourceSystem;
    }

    @JacksonXmlProperty(localName = "MessageID")
    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

}