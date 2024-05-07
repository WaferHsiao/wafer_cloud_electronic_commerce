package com.wafer.business.response;

import lombok.Data;

@Data
public class ServiceResponse<T> {

    /**
     * 返回code
     */
    String code;

    /**
     * 返回msg
     */
    String msg;

    /**
     * 返回数据
     */
    T data;
}
