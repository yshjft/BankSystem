package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.api.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.api.dto.auth.AuthResponseDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionKey;
import com.bankSystem.BankSystem.testData.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    private User user = User.builder()
            .id(TestUser.ID)
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.ENCODED_PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();
    private AuthLoginRequestDto authLoginRequestDto  = AuthLoginRequestDto.builder()
            .email(TestUser.EMAIL)
            .password(TestUser.PASSWORD)
            .build();
    private MockHttpServletRequest request =new MockHttpServletRequest();
    private MockHttpSession session = (MockHttpSession) request.getSession();



    @InjectMocks
    AuthService authService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void 로그인() {
        //when
        session.setAttribute(SessionKey.LOGIN_MEMBER, TestUser.ID);
        when(userRepository.findUserByEmail(TestUser.EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(TestUser.PASSWORD, TestUser.ENCODED_PASSWORD)).thenReturn(true);

        //given
        authService.login(authLoginRequestDto, request);

        //then
        verify(userRepository).findUserByEmail(TestUser.EMAIL);
        verify(passwordEncoder).matches(TestUser.PASSWORD, TestUser.ENCODED_PASSWORD);
    }

    @Test
    void 로그아웃() {
        // given
        session.setAttribute(SessionKey.LOGIN_MEMBER, TestUser.ID);

        // when
        authService.logout(request);
    }
}