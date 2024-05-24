package com.wafer.business.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
public final class JwtConstant {

    public static final String SECRET_KEY = "123";

    // 12小时
    public static final Integer EXPIRE_HOUR = 12;

    public static final String HEADER_NAME = "token";

    public static final String HEADER_PREFIX_NAME = "Bearer ";
}
