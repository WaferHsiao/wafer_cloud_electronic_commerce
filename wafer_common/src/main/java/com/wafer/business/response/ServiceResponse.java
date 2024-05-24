package com.wafer.business.response;

import com.wafer.business.constant.ReturnEnum;
import lombok.Data;

@Data
public class ServiceResponse<T> {

    /**
     * 返回code
     */
    int code;

    /**
     * 返回msg
     */
    String msg;

    /**
     * 返回数据
     */
    T data;

    public ServiceResponse() {
        this.code = ReturnEnum.SUCCESS.getCode();
        this.msg = ReturnEnum.SUCCESS.getMsg();
    }
}
