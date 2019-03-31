package com.leeyumo.eagleEye.security.authentication;

import com.leeyumo.adk.userCenter.api.UserApi;
import com.leeyumo.adk.userCenter.invoker.ApiException;
import com.leeyumo.adk.userCenter.model.JsonResultUserVO;
import com.leeyumo.adk.userCenter.model.UserLoginDto;
import com.leeyumo.common.constants.BaseCodeMsg;
import com.leeyumo.common.exception.BusinessException;
import com.leeyumo.eagleEye.constants.EagleEyeCodeMsg;
import com.leeyumo.eagleEye.security.impl.GrantedAuthorityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomPwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserApi userApi;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 认证逻辑
        try {
            UserLoginDto loginDto = new UserLoginDto();
            loginDto
                    .username(name)
                    .password(password);
            JsonResultUserVO loginResult = userApi.checkUserInfo(loginDto);
            if(Objects.equals(loginResult.getCode(), BaseCodeMsg.Success.getCode())){
                // 认证成功，这里设置权限和角色
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN"));
//                authorities.add( new GrantedAuthorityImpl("AUTH_WRITE"));
                loginResult.getResult().getRoleCodeList().forEach(s -> authorities.add(new GrantedAuthorityImpl(s)));
                // 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
                Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
                return auth;
            }else {
                throw new BusinessException(EagleEyeCodeMsg.passwordIncorrect);
            }
        } catch (ApiException e) {
            throw new BusinessException(EagleEyeCodeMsg.requestFail);
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
