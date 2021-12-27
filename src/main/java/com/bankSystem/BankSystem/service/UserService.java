package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.api.dto.UserSaveRequestDto;
import com.bankSystem.BankSystem.api.dto.UserSaveResponseDto;
import com.bankSystem.BankSystem.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // password 인코딩이 필요하다
        // dto 변경 필요 => 길이가 달라서 2개를 만들거나 상속을 해야한다.
        // Entity도 변경 필요 => 길이 변경 필요

        User newUser = userRepository.save(userSaveRequestDto);

        return new UserSaveResponseDto(newUser);
    }
}
