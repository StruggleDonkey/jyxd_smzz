package com.jyxd.web.his.web.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import com.alibaba.fastjson.annotation.JSONField;

@Data
public class ResultResponse {

    @JsonProperty
    @JSONField(name = "Header")
    private ResultHeader header;


    @JSONField(name = "Body")
    private Body body;
}
