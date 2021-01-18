package com.spring.CourseElection.tools;

import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.exception.EmAllException;
import com.spring.CourseElection.model.UserConstRepository;
import com.spring.CourseElection.model.entity.UserDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 上海大学登录接口
 * @author: 0GGmr0
 * @create: 2019-12-01 21:40
 */
@Slf4j
@Component
public class AuthTool {
    @Resource
    private UserDoMapper userDoMapper;

    /**
     * @Description: 用户身份识别
     * @Param: [identity]
     * @Return: java.lang.String[]
     * @Author: SoCMo
     * @Date: 2020/12/24
     */
    public List<String> identityResolve(String identity) {
        List<String> identities = new ArrayList<>();
        for (int i = 0; i < UserConstRepository.IDENTITY.length; i++) {
            if (identity.charAt(identity.length() - i - 1) == '1') {
                identities.add(UserConstRepository.IDENTITY[i]);
            }
        }
        return identities;
    }

    /**
     * @Description: 用户身份识别
     * @Param: [identity]
     * @Return: java.lang.String[]
     * @Author: SoCMo
     * @Date: 2020/12/24
     */
    public List<String> identityResolveCN(String identity) {
        List<String> identities = new ArrayList<>();

        for (int i = 0; i < UserConstRepository.IDENTITY_CN.length; i++) {
            if (identity.charAt(identity.length() - i - 1) == '1') {
                identities.add(UserConstRepository.IDENTITY_CN[i]);
            }
        }
        return identities;
    }


    /**
     * @Description: 获取当前用户对象
     * @Param: []
     * @Return: com.spring.CourseElection.model.entity.UserDo
     * @Author: SoCMo
     * @Date: 2021/1/4
     */
    public UserDo getUser() throws AllException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDo userDo = userDoMapper.selectByPrimaryKey(userId);
        if (userDo == null) {
            throw new AllException(EmAllException.DATABASE_ERROR, "凭证对应的用户在系统中不存在！");
        }
        return userDo;
    }

    /**
     * @Description: 获取当前用户id
     * @Param: []
     * @Return: java.lang.String
     * @Author: SoCMo
     * @Date: 2021/1/6
     */
    public String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

