package com.wafer.business.controller;

import com.wafer.business.exception.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/order")
public class orderCreate {

    @Value("${server.port}")
    String portValue;

    public void orderAdd(){

    }

    @GetMapping("/detail")
    public String getOrderDetail(@RequestParam String orderId){
        return "orderId:"+orderId + ";port："+portValue;
    }


}
