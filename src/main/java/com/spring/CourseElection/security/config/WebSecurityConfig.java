package com.spring.CourseElection.security.config;

import com.spring.CourseElection.model.UserConstRepository;
import com.spring.CourseElection.security.JwtAccessDeniedHandler;
import com.spring.CourseElection.security.JwtAuthenticationEntryPoint;
import com.spring.CourseElection.security.JwtAuthenticationTokenFilter;
import com.spring.CourseElection.security.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @program: WebSecurityConfig
 * @Description: spring security配置类
 * @Author: SoCMo
 * @Date: 2020/12/24
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private MyUserDetailService myUserDetailService;

    @Resource
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Resource
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //对请求进行认证
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(UserConstRepository.LOGIN_URL).permitAll()
                .antMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/secretary/**").hasRole("SECRETARY")
                .antMatchers("/member/**").hasRole("MEMBER")
                .antMatchers("/leader/**").hasRole("LEADER")
                .antMatchers("/chairman/**").hasRole("CHAIRMAN");

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //对于没有登录的用户，要返回401
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        //对于登录了但是权限有问题的用户，要返回403
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }
}
