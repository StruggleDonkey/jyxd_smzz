package com.jyxd.web.his.web.webimplements;

import com.alibaba.fastjson.JSON;
import com.jyxd.web.his.data.commmon.*;
import com.jyxd.web.his.web.HisWebService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@WebService(serviceName = "HisWebService", // 与接口中指定的name一致
        targetNamespace = "http://xsdservice.business.mixpay.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.jyxd.web.his.web.HisWebService" // 接口地址
)
public class HisWebServiceImpl implements HisWebService {


    @Override
    public CommonResponse patientRegistry(String action, PatientRegistryRt patientRegistryRt) {
        /*System.out.println("action ===== --> :" + action);
        System.out.println("patientRegistryRt ----->> " + JSON.toJSONString(patientRegistryRt));
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setBody(new BodyData());
        resultResponse.setHeader(new HeaderData());
        System.out.println("resultResponse ->>>>> " + JSON.toJSONString(resultResponse));
        return resultResponse;*/
        System.out.println("action ===== --> :" + action);
        System.out.println("patientRegistryRt ----->> " + JSON.toJSONString(patientRegistryRt));
        CommonResponse commonResponse = new CommonResponse();
        HeaderData h = new HeaderData();
        h.setMessageID("1111");
        h.setSourceSystem("测试");
        commonResponse.setHeader(h);
        BodyData b = new BodyData();
        b.setResultCode("0");
        b.setResultContent("成功");
        commonResponse.setBody(b);
        System.out.println("resultResponse ->>>>> " + JSON.toJSONString(commonResponse));
        return commonResponse;
    }
}
