package com.wafer.business.constant;

import lombok.Data;

public enum EnableEnum {

    on(1),
    off(0);
    private int key;

    EnableEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
