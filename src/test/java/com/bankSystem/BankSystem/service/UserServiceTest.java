package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.web.dto.user.join.UserJoinRequestDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionKey;
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
    private MockHttpServletRequest request =new MockHttpServletRequest();
    private MockHttpSession session = (MockHttpSession) request.getSession();

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
        when(userRepository.save(userJoinRequestDto)).thenReturn(user);

        // when
        userService.join(userJoinRequestDto);

        // then
        verify(userRepository).isExist(TestUser.EMAIL);
        verify(passwordEncoder).encode(TestUser.PASSWORD);
        verify(userRepository).save(userJoinRequestDto);
    }

    // 예외 발생 테스트 코드
    @Test
    void 회원가입_실패_이메일_중복() {
        try {
            // given
            when(userRepository.isExist(TestUser.EMAIL)).thenThrow(new EmailAlreadyInUseException());

            // when
            userService.join(userJoinRequestDto);
        }catch (EmailAlreadyInUseException e) {
            // then
            verify(userRepository).isExist(TestUser.EMAIL);
            verify(userRepository, never()).save(userJoinRequestDto);
        }
    }

    @Test
    void 유저_조회() {
        // given
        session.setAttribute(SessionKey.LOGIN_MEMBER, TestUser.ID);
        when(userRepository.findUserById(TestUser.ID)).thenReturn(user);

        // when
        userService.get(request);

        // then
        verify(userRepository).findUserById(TestUser.ID);
    }

    @Test
    void 정보_수정() {
        // 정보를 저장된 id로 조회
        when(userRepository.findUserById(TestUser.ID)).thenReturn(user);
        session.setAttribute(SessionKey.LOGIN_MEMBER, TestUser.ID);

        userService.update(userUpdateRequestDto, request);

        // then
        verify(userRepository).findUserById(TestUser.ID);
    }

}