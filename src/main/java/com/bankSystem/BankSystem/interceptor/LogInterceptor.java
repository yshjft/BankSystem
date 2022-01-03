package com.bankSystem.BankSystem.interceptor;

import com.bankSystem.BankSystem.session.SessionKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        String uri = request.getRequestURI();
        String method = request.getMethod();
        HttpSession session = request.getSession(false);

        log.info("[ID: {}] REQUEST {} {}", uuid, method, uri);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uuid = request.getAttribute(LOG_ID).toString();
        String uri = request.getRequestURI();
        String method = request.getMethod();

        log.info("[ID: {}] RESPONSE {} {}", uuid, method, uri);
    }
}