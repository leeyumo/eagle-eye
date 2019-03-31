package com.leeyumo.eagleEye.config;

import com.leeyumo.adk.userCenter.api.UserApi;
import com.leeyumo.adk.userCenter.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCenterAdkConfig {
    @Value("${http.timeout}")
    private Integer timeout;

    @Bean
    public UserApi userApi(){
        return new UserApi(userCenterAdk());
    }

    @Bean
    public ApiClient userCenterAdk() {
        ApiClient apiClient = new ApiClient();
        apiClient.setConnectTimeout(timeout);
        return new ApiClient();
    }
}
