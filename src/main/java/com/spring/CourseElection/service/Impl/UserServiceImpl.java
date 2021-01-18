package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.PasswordDoMapper;
import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.PasswordDo;
import com.spring.CourseElection.model.entity.UserDo;
import com.spring.CourseElection.model.entity.UserDoExample;
import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.model.response.info.LoginResponse;
import com.spring.CourseElection.security.MyUserDetailService;
import com.spring.CourseElection.service.UserService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.JwtUtil;
import com.spring.CourseElection.tools.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: UserDoServiceImpl
 * @Description: 普通用户业务层
 * @Author: SoCMo
 * @Date: 2020/12/21
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private MyUserDetailService myUserDetailService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDoMapper userDoMapper;

    @Resource
    private AuthTool authTool;

    @Resource
    private PasswordDoMapper passwordDoMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Result login(LoginInfo loginInfo) {
        try {
            authenticate(loginInfo.getUserId(), loginInfo.getPassword());

            UserDo userDo = userDoMapper.selectByPrimaryKey(loginInfo.getUserId());

            return ResultTool.success(
                    setLoginResponse(
                            userDo
                    ));
        } catch (AllException e) {
            return ResultTool.error(e.getErrCode(), e.getMsg());
        }
    }

    /**
    * @program: UserServiceImpl
    * @Description: 验证身份
    * @Author: SoCMo
    * @Date: 2021/1/18
    */
    private void authenticate(String username, String password) throws AllException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AllException(EmAllException.NO_LOGIN_AUTHORIZATION);
        } catch (BadCredentialsException e) {
            throw new AllException(EmAllException.USER_AND_PASSWORD_ERROR);
        }
    }

    /**
     * @Description: 设置登录接口的返回信息
     * @Param: [userDo, identity]
     * @Return: com.shu.TechEthics.model.response.info.LoginResponse
     * @Author: SoCMo
     * @Date: 2020/12/24
     */
    private LoginResponse setLoginResponse(UserDo userDo) throws AllException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(userDo.getUserId());
        loginResponse.setName(userDo.getName());
        loginResponse.setIdentity(authTool.identityResolveCN(userDo.getIdentity()).toArray(new String[0])); // 用户身份
        loginResponse.setDepartment(userDo.getDepartment());
        loginResponse.setToken(jwtUtil.createJwt(
                userDo.getUserId(), //学号
                userDo.getName()   //姓名
        ));
        return loginResponse;
    }
}