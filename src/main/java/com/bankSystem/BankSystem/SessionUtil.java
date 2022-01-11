package com.bankSystem.BankSystem;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static final String LOGIN_MEMBER="LOGIN_MEMBER";

    public static HttpSession getSession(boolean create){
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttribute.getRequest().getSession(create);
    }
}
