package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.user.UserDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.api.dto.user.UserSaveRequestDto;
import com.bankSystem.BankSystem.api.dto.user.UserSaveResponseDto;
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

        String encodedPassword = passwordEncoder.encode(userSaveRequestDto.getPassword());

        UserDto userDto= UserDto.builder()
                .name(userSaveRequestDto.getName())
                .birthDate(userSaveRequestDto.getBirthDate())
                .address(userSaveRequestDto.getAddress())
                .email(userSaveRequestDto.getEmail())
                .password(encodedPassword)
                .phoneNumber(userSaveRequestDto.getPhoneNumber())
                .build();

        User newUser = userRepository.save(userDto);

        return new UserSaveResponseDto(newUser);
    }
}
