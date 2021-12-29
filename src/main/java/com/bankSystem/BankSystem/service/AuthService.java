package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.api.dto.auth.AuthResponseDto;
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

    public AuthResponseDto login(AuthLoginRequestDto authLoginRequestDto, HttpServletRequest request){
        try {
            User user = userRepository.findUserByEmail(authLoginRequestDto.getEmail());
            boolean isExact= passwordEncoder.matches(authLoginRequestDto.getPassword(), user.getPassword());
            if(!isExact) {
                throw new LoginException("PASSWORD");
            }

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, user.getId());

            return new AuthResponseDto("LOGIN_SUCCESS");
        }catch (EmptyResultDataAccessException e) {
            throw new LoginException("EMAIL");
        }
    }

    public AuthResponseDto logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return new AuthResponseDto("LOGOUT_SUCCESS");
    }
}
