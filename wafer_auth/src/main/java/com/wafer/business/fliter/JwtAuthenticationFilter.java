package com.wafer.business.fliter;

import cn.hutool.core.util.StrUtil;
import com.wafer.business.config.DbUserDetailsManager;
import com.wafer.business.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private DbUserDetailsManager dbUserDetailsManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("token");

        // 没有token，去走登录流程
        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //验证token签名和过期时间
        boolean validateToken = jwtUtils.validateToken(token);


        //校验token通过
        if (validateToken){

            if (SecurityContextHolder.getContext().getAuthentication() == null){
                //根据token获取username
                String username = jwtUtils.extractUsernameByToken(token);
                if (!StrUtil.isBlank(username)){
                    //token的username不为空
                    try {
                        //根据token的username获取userDetails
                        UserDetails userDetails = dbUserDetailsManager.loadUserByUsername(username);
                        if (userDetails != null){
                            // 将用户信息存入 SecurityContextHolder ，以便本次在请求中使用
                            UsernamePasswordAuthenticationToken authenticationLoginUser = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                            SecurityContextHolder.getContext().setAuthentication(authenticationLoginUser);
                        }
                    }catch(Exception e){
                        //用户没找到,放行
                        log.warn("username没找到，username："+username);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);

    }
}

