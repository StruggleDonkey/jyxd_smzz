package com.jyxd.web.his.service.impl;

import com.jyxd.web.his.service.MyService;

import javax.jws.WebService;

@WebService(endpointInterface = "com.jyxd.web.his.service.MyService")
public class MyServiceImpl implements MyService {

    @Override
    public String show() {
        System.out.println("MyServiceImpl中的show方法被调用！");
        return "【调用成功！】";
    }
}
