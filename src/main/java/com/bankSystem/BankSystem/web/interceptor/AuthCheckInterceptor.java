package com.bankSystem.BankSystem.web.interceptor;

import com.bankSystem.BankSystem.web.exception.customException.UnauthorizedAccessException;
import com.bankSystem.BankSystem.session.SessionKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionKey.LOGIN_MEMBER) == null) {
            throw new UnauthorizedAccessException();
        }

        return true;
    }
}
