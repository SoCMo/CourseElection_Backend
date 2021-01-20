package com.spring.CourseElection.security;

import com.spring.CourseElection.dao.PasswordDoMapper;
import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.model.entity.PasswordDo;
import com.spring.CourseElection.model.entity.UserDo;
import com.spring.CourseElection.tools.AuthTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @program: MyUserDetailService
* @Description: 获取用户权限
* @Author: SoCMo
* @Date: 2020/12/24
*/
@Slf4j
@Component
public class MyUserDetailService implements UserDetailsService {
    @Resource
    private AuthTool authTool;

    @Resource
    private UserDoMapper userDoMapper;

    @Resource
    private PasswordDoMapper passwordDoMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //从数据库中获取这个用户
        UserDo userDo = userDoMapper.selectByPrimaryKey(userId);
        if(userDo == null) {
            log.info("登录用户：" + userId + " 不存在.");
            throw new UsernameNotFoundException("账号或密码错误!");
        }
        PasswordDo passwordDo = passwordDoMapper.selectByPrimaryKey(userId);
        if(passwordDo == null) {
            log.info("登录用户：" + userId + " 密码不存在.");
            throw new UsernameNotFoundException("账号或密码错误!");
        }

        List<String> identities = authTool.identityResolve(userDo.getIdentity());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(String identity : identities){
            grantedAuthorities.add(new SimpleGrantedAuthority(identity));
        }

        return new JwtUser(userDo.getUserId(), passwordDo.getPassword(), userDo.getIdentity(), grantedAuthorities);
    }


}
