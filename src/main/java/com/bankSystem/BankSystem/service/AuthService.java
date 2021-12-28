package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.auth.LoginRequestDto;
import com.bankSystem.BankSystem.api.dto.auth.LoginResponseDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.exception.customException.LoginException;
import com.bankSystem.BankSystem.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public LoginResponseDto loginService(LoginRequestDto loginRequestDto, HttpServletRequest request){
        try {
            // 이메일 대조
            User user = userRepository.findUserByEmail(loginRequestDto.getEmail());

            // 패스워드 대조
            boolean isExact= passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
            if(!isExact) {
                throw new LoginException("LOGIN_FAIL_PASSWORD");
            }

            // 세션 생성 및 리턴
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, user.getId());

            // 성공
            return new LoginResponseDto("LOGIN_SUCCESS");
        }catch (EmptyResultDataAccessException e) {
            throw new LoginException("LOGIN_FAIL_EMAIL");
        }
    }
}
