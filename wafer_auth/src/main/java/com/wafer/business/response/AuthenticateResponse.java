package com.wafer.business.response;

import lombok.Data;

@Data
public class AuthenticateResponse {

    private String accessToken;

    private String refreshToken;
}
