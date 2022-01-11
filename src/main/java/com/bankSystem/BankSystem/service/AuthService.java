package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.web.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.web.dto.auth.AuthResponseDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.web.exception.customException.LoginException;
import com.bankSystem.BankSystem.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthLoginRequestDto authLoginRequestDto){
        User user = userRepository.findByEmail(authLoginRequestDto.getEmail()).orElseThrow(()->new LoginException("EMAIL"));

        boolean isExact= passwordEncoder.matches(authLoginRequestDto.getPassword(), user.getPassword());
        if(!isExact) {
            throw new LoginException("PASSWORD");
        }

        HttpSession session = SessionUtil.getSession(true);
        session.setAttribute(SessionUtil.LOGIN_MEMBER, user.getId());

        return AuthResponseDto.builder()
                .message("login success")
                .build();
    }

    public AuthResponseDto logout() {
        HttpSession session = SessionUtil.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return AuthResponseDto.builder()
                .message("logout success")
                .build();
    }
}
