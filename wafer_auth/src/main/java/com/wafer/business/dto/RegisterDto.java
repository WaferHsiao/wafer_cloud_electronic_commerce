package com.wafer.business.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String username;

    private String password;

    private String email;

    private String phoneNum;
}
