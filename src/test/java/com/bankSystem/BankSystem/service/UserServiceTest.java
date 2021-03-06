package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.web.dto.user.join.UserJoinRequestDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.SessionUtil;
import com.bankSystem.BankSystem.testData.TestUser;
import com.bankSystem.BankSystem.web.exception.customException.EmailAlreadyInUseException;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();
    private User user = User.builder()
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.ENCODED_PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();
    private UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder().build();
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpSession session = (MockHttpSession) request.getSession();

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void ????????????() {
        //given
        when(userRepository.existsByEmail(TestUser.EMAIL)).thenReturn(false);
        when(passwordEncoder.encode(TestUser.PASSWORD)).thenReturn(TestUser.ENCODED_PASSWORD);
        when(userRepository.save(any())).thenReturn(user);

        // when
        userService.join(userJoinRequestDto);

        // then
        verify(userRepository).existsByEmail(TestUser.EMAIL);
        verify(passwordEncoder).encode(TestUser.PASSWORD);
        verify(userRepository).save(any());
    }

    // ?????? ?????? ????????? ??????
    @Test
    void ????????????_??????_?????????_??????() {
        try {
            // given
            when(userRepository.existsByEmail(TestUser.EMAIL)).thenThrow(new EmailAlreadyInUseException());

            // when
            userService.join(userJoinRequestDto);
        }catch (EmailAlreadyInUseException e) {
            // then
            verify(userRepository).existsByEmail(TestUser.EMAIL);
            verify(userRepository, never()).save(userJoinRequestDto.toEntity());
        }
    }

    @Test
    void ??????_??????() {
        session.setAttribute(SessionUtil.LOGIN_MEMBER, TestUser.ID);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // given
        when(userRepository.findById(TestUser.ID)).thenReturn(Optional.of(user));

        // when
        userService.get();

        // then
        verify(userRepository).findById(TestUser.ID);
    }

    @Test
    void ??????_??????() {
        session.setAttribute(SessionUtil.LOGIN_MEMBER, TestUser.ID);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // given
        when(userRepository.findById(TestUser.ID)).thenReturn(Optional.of(user));

        // when
        userService.update(userUpdateRequestDto);

        // then
        verify(userRepository).findById(TestUser.ID);
    }

}