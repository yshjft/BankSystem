package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveResponseDto;
import com.bankSystem.BankSystem.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserSaveResponseDto join(UserSaveRequestDto userSaveRequestDto) {
        if(userRepository.isExist(userSaveRequestDto.getEmail()) > 0) {
            throw new EmailAlreadyInUseException();
        }

        String encodedPassword = passwordEncoder.encode(userSaveRequestDto.getPassword());

        UserSaveDto userSaveDto = UserSaveDto.builder()
                .name(userSaveRequestDto.getName())
                .birthDate(userSaveRequestDto.getBirthDate())
                .address(userSaveRequestDto.getAddress())
                .email(userSaveRequestDto.getEmail())
                .password(encodedPassword)
                .phoneNumber(userSaveRequestDto.getPhoneNumber())
                .build();

        User newUser = userRepository.save(userSaveDto);

        return new UserSaveResponseDto(newUser);
    }

    public UserGetResponseDto get(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Long userId = (Long)session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("ID = {}", userId);
        User user = userRepository.findUserById(userId);

        return UserGetResponseDto.builder()
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
