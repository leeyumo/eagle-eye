package com.leeyumo.eagleEye.config;


import com.leeyumo.adk.learningSpring.api.SubjectApi;
import com.leeyumo.adk.learningSpring.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LearningSpringAdkConfig {
    @Value("${http.timeout}")
    private Integer timeout;

    @Bean
    public SubjectApi subjectApi(){
        return new SubjectApi(learningSpringAdk());
    }

    @Bean
    public ApiClient learningSpringAdk() {
        ApiClient apiClient = new ApiClient();
        apiClient.setConnectTimeout(timeout);
        return new ApiClient();
    }
}
