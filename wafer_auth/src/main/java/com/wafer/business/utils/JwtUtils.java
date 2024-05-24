package com.wafer.business.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.wafer.business.constant.JwtConstant;
import com.wafer.business.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class JwtUtils {

    // token的前缀
    private final String headerPrefix = "Bearer ";

    public String generateToken(User user){
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR,JwtConstant.EXPIRE_HOUR);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now.getTime());
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime.getTime());
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now.getTime());
        // 内容
        payload.put("username", user.getUsername());
        payload.put("password", user.getPassword());
        String token = JWTUtil.createToken(payload, JwtConstant.SECRET_KEY.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }

    public boolean validateToken(String token){
        try {
            JWT jwt = JWTUtil.parseToken(token);
            boolean validateTokenSign = jwt.setKey(JwtConstant.SECRET_KEY.getBytes()).verify();
            if (validateTokenSign){
                //签名验证通过后，校验时间
                long expTime = jwt.getPayload().getClaimsJson().getLong(JWTPayload.EXPIRES_AT);
                DateTime expirationDate = DateUtil.date(expTime) ;
                if (null != expirationDate){
                    if (expirationDate.isBefore(DateUtil.date())) {
                        log.error("Token is expired");
                        return false;
                    }
                }
                return true;
            }else {
                log.error("token 验证不通过");
                return false;
            }

        } catch (JWTException e) {
            log.error("Token parsing or verification failed: " + e.getMessage());
            return false;
        }
    }

    public String extractUsernameByToken(String token) {
        try {
            //校验token的有效性
            boolean validateToken = validateToken(token);
            if (validateToken){
                JWT jwt = JWTUtil.parseToken(token);
                return (String) jwt.getPayload("username");
            }else {
                log.error("token已过期,token:"+token);
                return null;
            }

        } catch (JWTException e) {
            // 处理解析或验证过程中的异常
            log.error("Failed to extract username from token: " + e.getMessage());
            return null;
        }
    }










}


