package com.wafer.business.service.impl;

import com.wafer.business.constant.EnableEnum;
import com.wafer.business.dto.AuthenticateDto;
import com.wafer.business.dto.RegisterDto;
import com.wafer.business.entity.User;
import com.wafer.business.response.AuthenticateResponse;
import com.wafer.business.response.ServiceResponse;
import com.wafer.business.service.AuthService;
import com.wafer.business.utils.JwtUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ServiceResponse register(RegisterDto param) {

        ServiceResponse response = new ServiceResponse();

        //先保存到数据库
        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(param.getPassword());
        user.setEnabled(EnableEnum.on.getKey());
        userService.addUser(user);

        //生成token
        String accessToken = jwtUtils.generateToken(user);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setAccessToken(accessToken);

        response.setData(accessToken);

        return response;

    }

    @Override
    public ServiceResponse authenticate(AuthenticateDto param) {
        ServiceResponse response = new ServiceResponse();

        //先保存到数据库
        User user = new User();
        user.setUsername(param.getUsername());
        user.setPassword(param.getPassword());

        //生成token
        String accessToken = jwtUtils.generateToken(user);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setAccessToken(accessToken);

        response.setData(accessToken);

        return response;
    }
}
