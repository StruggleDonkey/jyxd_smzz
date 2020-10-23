package com.jyxd.web.util;

/**
 *  操作日志 操作位置code
 */
public enum MenuCode {
    BJSZ_CODE("报警设置"),HLD_CODE("护理单"),TWD_CODE("体温单"),HLDKJLR_CODE("护理单快捷录入"),
    XTDKJLR_CODE("血糖单快捷录入"),TWDKJLR_CODE("体温单快捷录入");

    private String value;
    private MenuCode(String value){
        this.value=value;
    }

    public String getCode(){
        return value;
    }
}
