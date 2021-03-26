package com.jyxd.web.his.controller;

import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.TicketRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    private static CommonResponse cr;

    @PostMapping(value = "/getHisMessage", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse test(@RequestBody TicketRequest ticketRequest){
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
        }catch (Exception e){

        }
        return commonResponse;
    }

    private  static  CommonResponse getCommonResponse(){
        if(cr==null){
            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            cr.setHeader(h);

            BodyData b=new BodyData();
            b.setResultContent("失败");
            cr.setBody(b);
        }
        return cr;
    }
}
