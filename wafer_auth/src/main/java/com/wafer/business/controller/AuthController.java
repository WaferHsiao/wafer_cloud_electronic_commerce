package com.wafer.business.controller;

import com.alibaba.fastjson2.JSON;
import com.wafer.business.dto.AuthenticateDto;
import com.wafer.business.dto.RegisterDto;
import com.wafer.business.response.ServiceResponse;
import com.wafer.business.service.AuthService;
import com.wafer.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());


    @Autowired
    public UserService userService;

    @Autowired
    public AuthService authService;

    @PostMapping("/register")
    @ResponseBody
    public ServiceResponse register(@RequestBody RegisterDto param){
        return authService.register(param);
    }



    @PostMapping("/authenticate")
    @ResponseBody
    public ServiceResponse authenticate(@RequestBody AuthenticateDto param){
        return authService.authenticate(param);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/list")
    @ResponseBody
    public String list(){
        return JSON.toJSONString(userService.list());
    }

}
