package com.spring.CourseElection.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.tools.JwtUtil;
import com.spring.CourseElection.tools.ResultTool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.spring.CourseElection.model.UserConstRepository.*;

/**
 * @program: JwtAuthenticationTokenFilter
 * @Description: 访问接口时的拦截器
 * @Author: SoCMo
 * @Date: 2020/12/24
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final String TOKEN_NAME = "Authorization";

    @Resource
    private JwtUtil jwtUtil;

    private final UserDetailsService myUserDetailService;

    public JwtAuthenticationTokenFilter(@Qualifier("myUserDetailService") UserDetailsService userDetailsService) {
        this.myUserDetailService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        String token = httpServletRequest.getHeader(TOKEN_NAME);
        if (token == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            token = token.substring(7);
            switch (jwtUtil.validateToken(token)) {
                case NORMAL_TOKEN:
                    DecodedJWT decodedJWT = JWT.decode(token);
                    String userId = decodedJWT.getSubject();
                    UserDetails userDetails = myUserDetailService.loadUserByUsername(userId);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    break;
                case SIGNATURE_VERIFICATION_EXCEPTION:
                    returnErrorMessage(httpServletResponse, "token签名内容失效", 401);
                    return;
                case TOKEN_EXPIRED_EXCEPTION:
                    returnErrorMessage(httpServletResponse, "token已超时", 401);
                    return;
                case FAKE_TOKEN:
                    returnErrorMessage(httpServletResponse, "虚假token", 401);
                    return;
                default:
                    break;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void returnErrorMessage(HttpServletResponse response, String msg, int code) throws IOException {
        Result result = ResultTool.error(code, msg);
        response.setContentType("application.yml/json;charset=utf-8");
        response.setStatus(403);
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String jsonOfRST = mapper.writeValueAsString(result);
        out.print(jsonOfRST);
        out.flush();
    }
}
