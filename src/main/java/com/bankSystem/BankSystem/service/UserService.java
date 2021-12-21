package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.User;
import com.bankSystem.BankSystem.dto.UserSaveRequestDto;
import com.bankSystem.BankSystem.dto.UserSaveResponseDto;
import com.bankSystem.BankSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserSaveResponseDto save(UserSaveRequestDto userSaveRequestDto) {
        // 중복된 이메일은 안된다 -> 방법을 찾아보자 -> 이에 대한 대응을 어떻게 할지도 생각해보자
        if(userRepository.isExist(userSaveRequestDto.getEmail()) > 0) {
            // 예외처리
        }

        Long id = userRepository.save(userSaveRequestDto).getId();
        return new UserSaveResponseDto(id);
    }
}
