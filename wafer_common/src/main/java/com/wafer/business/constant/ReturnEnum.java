package com.wafer.business.constant;

public enum ReturnEnum {

    SUCCESS(200, "Success"),
    SYSTEM_ERROR(999, "Bad Request");


    private final int code;
    private final String msg;

    ReturnEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
