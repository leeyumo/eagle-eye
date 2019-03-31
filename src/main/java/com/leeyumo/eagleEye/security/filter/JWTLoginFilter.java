package com.leeyumo.eagleEye.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.leeyumo.common.exception.BusinessException;
import com.leeyumo.eagleEye.constants.EagleEyeCodeMsg;
import com.leeyumo.eagleEye.constants.SecurityConstants;
import com.leeyumo.eagleEye.security.authentication.dto.UserPwdLoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            UserPwdLoginDto userPwdLoginDto = new ObjectMapper().readValue(req.getInputStream(), UserPwdLoginDto.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userPwdLoginDto.getUsername(),
                            userPwdLoginDto.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new BusinessException(EagleEyeCodeMsg.passwordIncorrect);
        }
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // build the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            List roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }
            // 设置封装属性
            Map<String,Object> userInfo = Maps.newHashMap();
            userInfo.put("username",auth.getPrincipal());
            userInfo.put("roleList",roleList);
            token = Jwts.builder()
//                    .setClaims()
                    .setSubject(JSONObject.toJSONString(userInfo))
                    .setIssuedAt(new Date(System.currentTimeMillis()))//签发时间
                    .setExpiration(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRE_MILLIS))//过期时间
                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
