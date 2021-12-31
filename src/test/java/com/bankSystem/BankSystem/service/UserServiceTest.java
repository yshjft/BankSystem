package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.testData.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();
    private User newUser = User.builder()
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.ENCODED_PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void 회원가입() {
        //given
        when(userRepository.isExist(TestUser.EMAIL)).thenReturn(0L);
        when(passwordEncoder.encode(TestUser.PASSWORD)).thenReturn(TestUser.ENCODED_PASSWORD);
        when(userRepository.save(userSaveRequestDto)).thenReturn(newUser);

        // when
        userService.join(userSaveRequestDto);

        // then
        verify(userRepository).isExist(TestUser.EMAIL);
        verify(passwordEncoder).encode(TestUser.PASSWORD);
        verify(userRepository).save(userSaveRequestDto);
    }

    void 회원가입_실패_이메일_중복() {


    }
}