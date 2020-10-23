package com.jyxd.web.util;

/**
 *  接口返回状态code
 */
public enum HttpCode {
    OK_CODE(200),FAILURE_CODE(1),LOGIN_FAILURE_CODE(2),LOGIN_TIMEOUT_CODE(3),PASSWORD_ERROR_CODE(4),EXISTING_CODE(5);
    //200:正常响应  1:响应失败  2:登录失败（密码错误）  3:登录超时（session过期） 4:修改密码（原密码错误） 5:新增字典失败（编码已存在）
    private int value;
    private HttpCode(int value){
        this.value=value;
    }

    public int getCode(){
        return value;
    }
}
