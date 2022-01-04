package com.bankSystem.BankSystem;

import com.bankSystem.BankSystem.web.interceptor.AuthCheckInterceptor;
import com.bankSystem.BankSystem.web.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

        registry.addInterceptor(new AuthCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/join", "/api/auth/login", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable();
    }
}
