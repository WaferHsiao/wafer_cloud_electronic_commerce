package com.wafer.business.service;

import com.wafer.business.dto.AuthenticateDto;
import com.wafer.business.dto.RegisterDto;
import com.wafer.business.response.ServiceResponse;

public interface AuthService {

    ServiceResponse register(RegisterDto param);

    ServiceResponse authenticate(AuthenticateDto param);
}
