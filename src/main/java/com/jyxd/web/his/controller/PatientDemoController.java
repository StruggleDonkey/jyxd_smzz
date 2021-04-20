package com.jyxd.web.his.controller;

import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.patient.PatientRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PatientDemoController {


    @PostMapping(value = "/getHisPatientMessage", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse test(@RequestBody PatientRequest patientRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            System.out.println(patientRequest.toString());
            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);

        }catch (Exception e){
            System.out.println(e);
        }
        return commonResponse;
    }

}
