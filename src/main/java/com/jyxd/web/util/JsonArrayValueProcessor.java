package com.jyxd.web.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonArrayValueProcessor implements JsonValueProcessor {

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    private Object process(Object value){
        if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(value);
        } else if (value instanceof Timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }

}
