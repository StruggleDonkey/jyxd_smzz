package com.jyxd.web.util;

public enum HttpCode {
    OK_CODE(200),FAILURE_CODE(1),LOGIN_FAILURE_CODE(2),LOGIN_TIMEOUT_CODE(3);
    //200:正常响应  1:响应失败  2:登录失败（密码错误）  3:登录超时（session过期）
    private int value;
    private HttpCode(int value){
        this.value=value;
    }

    public int getCode(){
        return value;
    }
}
