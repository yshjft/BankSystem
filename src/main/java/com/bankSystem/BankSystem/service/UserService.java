package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.web.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateResponseDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.web.dto.user.join.UserJoinRequestDto;
import com.bankSystem.BankSystem.web.dto.user.join.UserJoinResponseDto;
import com.bankSystem.BankSystem.web.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.SessionKey;
import com.bankSystem.BankSystem.web.exception.customException.NoUserException;
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
    private final HttpSession session;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserJoinResponseDto join(UserJoinRequestDto userJoinRequestDto) {
        if(userRepository.existsByEmail(userJoinRequestDto.getEmail())){
            throw new EmailAlreadyInUseException();
        }

        String encodedPassword = passwordEncoder.encode(userJoinRequestDto.getPassword());
        userJoinRequestDto.setEncodedPassword(encodedPassword);

        User newUser = userRepository.save(userJoinRequestDto.toEntity());

        return UserJoinResponseDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .birthDate(newUser.getBirthDate())
                .address(newUser.getAddress())
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .build();
    }

    public UserGetResponseDto get(HttpServletRequest request) {
        User user = getUser(request);

        return UserGetResponseDto.builder()
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserUpdateResponseDto update(UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {
        User user = getUser(request);
        String encodedPassword = passwordEncoder.encode(userUpdateRequestDto.getPassword());

        user.updateUser(userUpdateRequestDto.getName(), userUpdateRequestDto.getBirthDate(), userUpdateRequestDto.getAddress(), userUpdateRequestDto.getPhoneNumber(), encodedPassword);

        return UserUpdateResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    // 유저가 없는 경우 에러 발생
    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long)session.getAttribute(SessionKey.LOGIN_MEMBER);

        User user = userRepository.findById(userId).orElseThrow(()->new NoUserException());

        return user;
    }
}
