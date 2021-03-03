package com.spring.CourseElection.service.Impl;

import com.spring.CourseElection.dao.DepartmentDoMapper;
import com.spring.CourseElection.dao.PasswordDoMapper;
import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.entity.*;
import com.spring.CourseElection.model.request.LoginInfo;
import com.spring.CourseElection.model.request.UserUpdateInfo;
import com.spring.CourseElection.model.response.Result;
import com.spring.CourseElection.model.response.info.DepartmentInfoVO;
import com.spring.CourseElection.model.response.info.LoginResponse;
import com.spring.CourseElection.model.response.info.UserInfoRes;
import com.spring.CourseElection.security.MyUserDetailService;
import com.spring.CourseElection.service.UserService;
import com.spring.CourseElection.tools.AuthTool;
import com.spring.CourseElection.tools.JwtUtil;
import com.spring.CourseElection.tools.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeanUtils;
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
    private DepartmentDoMapper departmentDoMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDoMapper userDoMapper;

    @Resource
    private AuthTool authTool;

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

    @Override
    public Result info(String userId) {
        if(!userId.equals(authTool.getUserId())){
            return ResultTool.error(EmAllException.REQUEST_FORBIDDEN.getErrCode(), "仅能查询自己的id");
        }

        UserDo userDo = userDoMapper.selectByPrimaryKey(userId);
        if (userDo == null) {
            return ResultTool.error(EmAllException.USER_AND_PASSWORD_ERROR);
        } else {
            UserInfoRes userInfoRes = new UserInfoRes();
            BeanUtils.copyProperties(userDo, userInfoRes);
            userInfoRes.setPhone(userDo.getMobilePhone());
            userInfoRes.setIdentity(authTool.identityResolveCN(userDo.getIdentity()));
            return ResultTool.success(userInfoRes);
        }
    }

    @Override
    public Result departmentInfo(){
        try {
            UserDo userDo = authTool.getUser();
            //从部门表中获取对应名称的部门
            DepartmentDoExample departmentDoExample = new DepartmentDoExample();
            departmentDoExample.createCriteria()
                    .andDepartmentNameEqualTo(userDo.getDepartment());
            List<DepartmentDo> departmentDoList = departmentDoMapper.selectByExample(departmentDoExample);

            //若部门名不存在,返回错误并记录
            if (departmentDoList.isEmpty()) {
                throw new AllException(EmAllException.DATABASE_ERROR.setErrMsg("无法找到当前用户部门:" + userDo.getDepartment()));
            }

            //若存在同名部门,返回错误并记录
            if (departmentDoList.size() > 1) {
                throw new AllException(EmAllException.DATABASE_ERROR.setErrMsg("存在同名部门:" + userDo.getDepartment()));
            }

            //对部门信息视图进行赋值
            DepartmentDo departmentDo = departmentDoList.get(0);
            DepartmentInfoVO departmentInfoVO = new DepartmentInfoVO();
            BeanUtils.copyProperties(departmentDo, departmentInfoVO);
            return ResultTool.success(departmentInfoVO);
        } catch (AllException e) {
            log.error(e.getMsg());
            return ResultTool.error(500, e.getMsg());
        }
    }

    @Override
    public Result userInfoUpdate(UserUpdateInfo userUpdateInfo){
        //对User类进行赋值
        UserDo userUpdate = new UserDo();
        BeanUtils.copyProperties(userUpdateInfo, userUpdate);
        userUpdate.setMobilePhone(userUpdateInfo.getPhone());
        userUpdate.setUserId(authTool.getUserId());

        //若成功更新则返回成功，否则返回错误原因并记录
        if (userDoMapper.updateByPrimaryKeySelective(userUpdate) >= 1) {
            return ResultTool.success();
        } else {
            return ResultTool.error(EmAllException.DATABASE_ERROR);
        }
    }

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