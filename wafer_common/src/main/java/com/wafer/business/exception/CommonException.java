package com.wafer.business.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException{
    private String code;

    private String msg;

    public CommonException() {
        super();
    }
    public CommonException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CommonException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
