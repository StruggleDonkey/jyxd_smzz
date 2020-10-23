package com.jyxd.web.util;

/**
 *  操作日志 操作类型code
 */
public enum LogTypeCode {

    ADD_CODE("新增"),UPDATE_CODE("修改"),DELETE_CODE("删除"),SYNCHRONY_CODE("同步"), CONFIRM_CODE("确认");

    private String value;
    private LogTypeCode(String value){
        this.value=value;
    }

    public String getCode(){
        return value;
    }
}
