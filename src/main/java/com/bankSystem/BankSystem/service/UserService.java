package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.api.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.api.dto.user.update.UserUpdateResponseDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveResponseDto;
import com.bankSystem.BankSystem.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionKey;
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
        userSaveRequestDto.setEncodedPassword(encodedPassword);

        User newUser = userRepository.save(userSaveRequestDto);

        return UserSaveResponseDto.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .build();
    }

    public UserGetResponseDto get(HttpServletRequest request) {
        User user = getUser(request);

        return UserGetResponseDto.builder()
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserUpdateResponseDto update(UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {
        User user = getUser(request);
        String encodedPassword = passwordEncoder.encode(userUpdateRequestDto.getPassword());

        user.updateUser(userUpdateRequestDto.getName(), userUpdateRequestDto.getBirthDate(), userUpdateRequestDto.getAddress(), userUpdateRequestDto.getPhoneNumber(), encodedPassword);

        return UserUpdateResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .message("update success")
                .build();
    }

    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long)session.getAttribute(SessionKey.LOGIN_MEMBER);

        return userRepository.findUserById(userId);
    }
}
