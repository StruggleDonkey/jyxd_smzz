package com.jyxd.web.hl7.socket;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放全局变量 比如 监护仪socket客户端
 */
public class ForeverData {

    public static Map<Object,Object> map=new HashMap<>();

    public ForeverData(Map<Object,Object> map){
        this.map=map;
    }

}
