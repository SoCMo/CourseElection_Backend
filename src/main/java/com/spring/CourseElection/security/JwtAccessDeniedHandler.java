package com.spring.CourseElection.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.CourseElection.tools.ResultTool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @program: JwtAccessDeniedHandler
* @Description: 没有权限时显示403错误
* @Author: SoCMo
* @Date: 2020/12/24
*/
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), ResultTool.error(HttpServletResponse.SC_FORBIDDEN, "没有权限"));
    }
}
