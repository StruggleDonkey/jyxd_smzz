package com.jyxd.web.his.web;

import com.jyxd.web.data.user.User;
import com.jyxd.web.his.web.data.ResultResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://xsdebservice.betaone.com/")
public interface WebServiceDemoService {

    @WebResult(name = "Response")
    @WebMethod(operationName = "hello")
    ResultResponse hello(@WebParam(name = "action") String action,@WebParam(name = "user") User user);

}
