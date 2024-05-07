package com.wafer.business.handler;

import com.wafer.business.constant.ReturnEnum;
import com.wafer.business.response.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServiceResponse<Object> handleException(Exception e) {
        logger.error("system error:",e);
        ServiceResponse<Object> serviceResponse = new ServiceResponse<>();
        serviceResponse.setMsg("system error：" + e.getMessage());
        serviceResponse.setCode(ReturnEnum.SYSTEM_ERROR.getMsg());
        return serviceResponse;
    }

}
