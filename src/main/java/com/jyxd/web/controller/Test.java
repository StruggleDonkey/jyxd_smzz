package com.jyxd.web.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Test {

    @RequestMapping(value = "/sayHelloWorld")
    @ResponseBody
    public String sayHelloWorld(){
        return "Hello World";
    }

    @RequestMapping("/aaa")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public ModelAndView toIndex() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/session")
    @ResponseBody
    public String session(){
        JSONObject json=new JSONObject();
        json.put("code","4");
        json.put("msg","身份信息已失效，请重新登录");
        return json.toString();
    }
}
