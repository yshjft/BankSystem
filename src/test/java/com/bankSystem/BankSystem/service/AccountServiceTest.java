package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserJpaRepository;
import com.bankSystem.BankSystem.SessionKey;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.testData.TestAccount;
import com.bankSystem.BankSystem.testData.TestUser;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private User user = User.builder()
            .id(TestUser.ID)
            .name(TestUser.NAME)
            .birthDate(TestUser.BIRTH_DATE)
            .address(TestUser.ADDRESS)
            .email(TestUser.EMAIL)
            .password(TestUser.ENCODED_PASSWORD)
            .phoneNumber(TestUser.PHONE_NUMBER)
            .build();
    private AccountCreateRequestDto accountCreateRequestDto = AccountCreateRequestDto.builder()
            .balance(TestAccount.INIT_BALANCE)
            .build();
    private Account account = Account.builder()
            .id(TestAccount.ID)
            .balance(TestAccount.INIT_BALANCE)
            .hasCard(false)
            .build();
    private MockHttpServletRequest request =new MockHttpServletRequest();
    private MockHttpSession session = (MockHttpSession) request.getSession();

    @InjectMocks
    AccountService accountService;
    @Mock
    UserRepository userRepository;
    @Mock
    AccountRepository accountRepository;

    @Test
    void 계좌_생성() {
        session.setAttribute(SessionKey.LOGIN_MEMBER, TestUser.ID);
        account.setAccountOwner(user);

        // given
        when(userRepository.findById(TestUser.ID)).thenReturn(Optional.of(user));
        when(accountRepository.save(any())).thenReturn(account);

        // when
        accountService.create(accountCreateRequestDto, request);

        // then
        verify(accountRepository).save(any());

    }
}