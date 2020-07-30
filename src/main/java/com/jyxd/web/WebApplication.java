package com.jyxd.web;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(basePackages = { "com.jyxd.web.dao" }, sqlSessionFactoryRef = "sqlSessionFactory")
@SpringBootApplication
@ServletComponentScan
public class WebApplication {

    private static Logger logger= LoggerFactory.getLogger(WebApplication.class);
    public static void main(String[] args) {
        logger.info("开始启动");
        SpringApplication.run(WebApplication.class, args);
    }

}
