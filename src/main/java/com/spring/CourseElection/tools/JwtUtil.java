package com.spring.CourseElection.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring.CourseElection.dao.UserDoMapper;
import com.spring.CourseElection.exception.AllException;
import com.spring.CourseElection.model.UserConstRepository;
import com.spring.CourseElection.model.entity.UserDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import static com.spring.CourseElection.model.UserConstRepository.*;

/**
 * @description: jwt工具类
 * @author: 0GGmr0
 * @create: 2019-12-01 21:42
 */

@Slf4j
@Component
public class JwtUtil {
    @Value("${ENCODE_KEY}")
    private String ENCODE_KEY;

    private static JWTVerifier jwtVerifier;

    @Resource
    private UserDoMapper userMapper;

    private static String encode(String str){
        return URLEncoder.encode(str);
    }

    /**
     * @Description: 创建token
     * @Author: 0GGmr0
     * @Date: 2019-04-15
     */
    public String createJwt(String userId, String name) throws AllException {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, NORMAL_TOKEN_VALIDITY_DAY);
        name = encode(name);
        Algorithm algorithm = Algorithm.HMAC256(ENCODE_KEY.getBytes());
        return UserConstRepository.TOKEN_HEADER + JWT.create()
                .withIssuedAt(currentDate)
                .withExpiresAt(calendar.getTime())
                .withSubject(userId)
                .withClaim("name", name)
                .sign(algorithm);
    }

    /**
     * @Description: 验证token的有效性
     * @Author: 0GGmr0
     * @Date: 2019-04-15
     */
    public int validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(ENCODE_KEY.getBytes());
            if (jwtVerifier == null) {
                // 创建校验器
                jwtVerifier = JWT.require(algorithm).build();
            }
            jwtVerifier.verify(token);
        } catch (SignatureVerificationException e) {
            // 签名内容失效
            log.info("token签名内容失效，报错信息为{}", e.toString());
            return SIGNATURE_VERIFICATION_EXCEPTION;
        } catch (TokenExpiredException e1) {
            // 时间失效
            log.info("token时间过期，报错信息为{}", e1.toString());
            return TOKEN_EXPIRED_EXCEPTION;
        } catch (JWTDecodeException | IllegalArgumentException e2) {
            log.info("token格式有误，报错信息为{}", e2.toString());
            return FAKE_TOKEN;
        }
        DecodedJWT decodedJWT = JWT.decode(token);
        String userId = decodedJWT.getSubject();
        UserDo user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return FAKE_TOKEN;
        }
        String name = URLDecoder.decode(
                decodedJWT.getClaim("name").asString());
        if (name.equals(user.getName())) {
            // 如果nickname正常，说明是正确的token
            return NORMAL_TOKEN;
        } else {
            // 这是一个伪造的token
            return FAKE_TOKEN;
        }
    }

}
