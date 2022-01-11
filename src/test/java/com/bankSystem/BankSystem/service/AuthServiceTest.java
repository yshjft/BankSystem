package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.web.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.SessionUtil;
import com.bankSystem.BankSystem.testData.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

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
        session.setAttribute(SessionUtil.LOGIN_MEMBER, TestUser.ID);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        //when
        when(userRepository.findByEmail(TestUser.EMAIL)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(TestUser.PASSWORD, TestUser.ENCODED_PASSWORD)).thenReturn(true);

        //given
        authService.login(authLoginRequestDto);

        //then
        verify(userRepository).findByEmail(TestUser.EMAIL);
        verify(passwordEncoder).matches(TestUser.PASSWORD, TestUser.ENCODED_PASSWORD);
    }

    @Test
    void 로그아웃() {
        session.setAttribute(SessionUtil.LOGIN_MEMBER, TestUser.ID);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // given

        // when
        authService.logout();
    }
}