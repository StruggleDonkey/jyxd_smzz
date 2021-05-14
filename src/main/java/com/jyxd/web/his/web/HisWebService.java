package com.jyxd.web.his.web;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://xsdebservice.betaone.com/")
public interface HisWebService {

    @WebResult(name = "Response")
    @WebMethod(operationName = "hisService")
    String hisService(@WebParam(name = "action") String action, @WebParam(name = "hisRequestXml") String hisRequestXml);
}
