package com.wafer.business.config;


import com.wafer.business.fliter.JwtAuthenticationFilter;
import com.wafer.business.service.impl.UserServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Data
public class SecurityChainConfig {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
        // 鉴权相关配置
        http.authorizeHttpRequests((requests) ->
                requests.
                requestMatchers( "/auth/register","/auth/authenticate").permitAll().
                anyRequest().authenticated()); // 其它只需要身份认证通过即可，不需要其它特殊权限
        // 登录相关配置
        http.formLogin(formLogin -> formLogin
                .loginPage("/auth/login")// 自定义登录页面，不再使用内置的自动生成页面
                .loginProcessingUrl("/auth/dologin")
                .permitAll() // 允许自定义页面的匿名访问，不需要认证和鉴权
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable); // 仅在确定不会带来安全问题时禁用 CSRF


        return http.build(); // 返回构建的SecurityFilterChain实例
    }






}
