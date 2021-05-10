package com.jyxd.web.his.web.webimplements;

import cn.hutool.http.HttpResponse;
import com.jyxd.web.data.user.User;
import com.jyxd.web.his.web.WebServiceDemoService;
import com.jyxd.web.his.web.data.Body;
import com.jyxd.web.his.web.data.ResultHeader;
import com.jyxd.web.his.web.data.ResultResponse;
import com.jyxd.web.his.web.data.WebResult;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@WebService(serviceName = "WebServiceDemoService", // 与接口中指定的name一致
        targetNamespace = "http://xsdservice.business.mixpay.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.jyxd.web.his.web.WebServiceDemoService" // 接口地址
)
public class WebServiceDemoServiceImpl implements WebServiceDemoService {

    @Override
    public ResultResponse hello(String action, User user) {
        System.out.println("action=" + action + "      " + "User:[id=" + user.getId() + "][name=" + user.getUserName() + "]");
        WebResult webResult = new WebResult();
        webResult.setResultCode(0);
        webResult.setResultContent("成功");

        Body body = new Body();
        body.setResultCode(0);
        body.setResultContent("成功");
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setBody(body);
        resultResponse.setHeader(new ResultHeader("ss","123yy"));

        return resultResponse;
    }
}
