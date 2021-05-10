package com.jyxd.web.his.web.data;

import lombok.Data;

@Data
public class ResultHeader {

    private String sourceSystem;

    private String messageID;

    public ResultHeader(){}

    public ResultHeader(String sourceSystem,String messageID){
        this.sourceSystem = sourceSystem;
        this.messageID = messageID;
    }
}
