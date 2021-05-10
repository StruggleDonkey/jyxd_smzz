package com.jyxd.web.config;

import com.jyxd.web.his.web.HisWebService;
import com.jyxd.web.his.web.WebServiceDemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {


    @Autowired
    private WebServiceDemoService webServiceDemoService;

    @Autowired
    private HisWebService hisWebService;

    /**
     * 注入servlet  bean name不能dispatcherServlet 否则会覆盖dispatcherServlet
     *
     * @return
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/jyxd/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 注册WebServiceDemoService接口到webservice服务
     *
     * @return
     */
    @Bean(name = "WebServiceDemoEndpoint")
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), webServiceDemoService);
        endpoint.publish("/service");
        return endpoint;
    }

    /**
     * 注册WebServiceDemoService接口到webservice服务
     *
     * @return
     */
    @Bean(name = "hisWebService")
    public Endpoint hisWebService() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), hisWebService);
        endpoint.publish("/hisService");
        return endpoint;
    }

}
