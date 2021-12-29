package com.bankSystem.BankSystem.config;

import com.bankSystem.BankSystem.interceptor.AuthCheckInterceptor;
import com.bankSystem.BankSystem.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

        registry.addInterceptor(new AuthCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/add", "/api/auth/login");
    }
}
