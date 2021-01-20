package com.spring.CourseElection.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.CourseElection.tools.ResultTool;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
* @program: JwtAuthenticationEntryPoint
* @Description: 未登录时显示403错误
* @Author: SoCMo
* @Date: 2020/12/24
*/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), ResultTool.error(HttpServletResponse.SC_UNAUTHORIZED, "请先登录"));

    }
}
