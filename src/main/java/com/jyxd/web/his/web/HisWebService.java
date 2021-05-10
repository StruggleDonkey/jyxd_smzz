package com.jyxd.web.his.web;

import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.PatientRegistryRt;
import com.jyxd.web.his.data.commmon.ResultResponse;
import com.jyxd.web.his.data.patient.PatientRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://xsdebservice.betaone.com/")
public interface HisWebService {

    @WebResult(name = "Response")
    @WebMethod(operationName = "patientRegistry")
    CommonResponse patientRegistry(@WebParam(name = "action") String action, @WebParam(name = "patientRegistryRt") PatientRegistryRt patientRegistryRt);
}
