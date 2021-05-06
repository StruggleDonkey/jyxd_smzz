package com.jyxd.web.his.service;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface MyService {
    @WebResult
    @WebMethod
    String show();
}
