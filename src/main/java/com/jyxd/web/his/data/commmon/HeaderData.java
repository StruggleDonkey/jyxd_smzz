package com.jyxd.web.his.data.commmon;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
//防止重复序列化
public class HeaderData {

    private String sourceSystem;//消息来源

    private String messageID;//消息ID


    public HeaderData() {
    }

    public HeaderData(String sourceSystem, String messageID) {
        this.sourceSystem = sourceSystem;
        this.messageID = messageID;
    }

    @XmlElement(name = "SourceSystem")
    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    @XmlElement(name = "MessageID")
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
