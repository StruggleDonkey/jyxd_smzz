package com.jyxd.web.his.controller;


import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.patient.PatientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HisController {

    private static Logger logger= LoggerFactory.getLogger(HisController.class);

    @PostMapping(value = "/HIPMessageServer", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse HIPMessageServer(@RequestBody String action, @RequestBody PatientRequest patientRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
            logger.info("action==============================================:"+action);
            logger.info("patientRequest=======================================:"+patientRequest.toString());
            logger.info("--------------------------------------------------------------------");
            logger.info("--------------------------------------------------------------------");
        }catch (Exception e){
            logger.error("+++++++++++++++接受his接口异常+++++++++++++++++"+e);
        }
        return commonResponse;
    }
}
