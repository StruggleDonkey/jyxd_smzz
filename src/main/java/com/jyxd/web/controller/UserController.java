package com.jyxd.web.controller;

import com.jyxd.web.data.User;
import com.jyxd.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(){
        User user=new User();
        user.setCreateTime(new Date());
        userService.insert(user);
        return "OK";
    }

    @RequestMapping(value = "/login",method= RequestMethod.POST)
    public ModelAndView login(@RequestBody Map<String,Object> map){
        logger.info("userName:"+map.get("userName").toString());
        logger.info("password:"+map.get("password").toString());
        return new ModelAndView("index");
    }
}
