package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.accountLog.AccountLogRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.SessionKey;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.testData.TestAccount;
import com.bankSystem.BankSystem.testData.TestAccountLog;
import com.bankSystem.BankSystem.testData.TestUser;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.deposit.DepositRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
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
            .build();
    private DepositRequestDto depositRequestDto = DepositRequestDto.builder()
            .account_id(TestAccount.ID)
            .amount(1000)
            .type(TestAccountLog.TYPE_IN)
            .info(TestAccountLog.INFO)
            .build();
    private AccountLog accountLogForDeposit = AccountLog.builder()
            .id(TestAccountLog.ID_IN)
            .info(TestAccountLog.INFO)
            .type(TestAccountLog.TYPE_IN)
            .amount(1000)
            .balance(2000)
            .build();
    private AccountLog accountLogForWithdraw = AccountLog.builder()
            .id(TestAccountLog.ID_OUT)
            .info(TestAccountLog.INFO)
            .type(TestAccountLog.TYPE_OUT)
            .amount(1000)
            .balance(0)
            .build();

    private MockHttpServletRequest request =new MockHttpServletRequest();

    @InjectMocks
    AccountService accountService;
    @Mock
    UserService userService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountLogRepository accountLogRepository;

    @Test
    void 계좌_생성() {
        account.setAccountOwner(user);

        // given
        when(userService.getUser(request)).thenReturn(user);
        when(accountRepository.save(any())).thenReturn(account);

        // when
        accountService.create(accountCreateRequestDto, request);

        // then
        verify(accountRepository).save(any());
    }

    @Test
    void 전체_계좌_조회() {
        int page = 0;
        int perPage = 5;
        Pageable pageable = PageRequest.of(page, perPage);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        Page<Account> accounts = new PageImpl<>(accountList);

        // given
        when(userService.getUser(request)).thenReturn(user);
        when(accountRepository.findByUser(user, pageable)).thenReturn(accounts);

        // when
        accountService.getAccounts(page, perPage, request);

        // then
        verify(userService).getUser(request);
        verify(accountRepository).findByUser(user, PageRequest.of(page, perPage));
    }

    // 입금
    void 계좌_입금() {
        // given(stub)
        when(accountRepository.findById(TestAccount.ID)).thenReturn(Optional.of(account));
        when(accountLogRepository.save(accountLogForDeposit)).thenReturn(accountLogForDeposit);

        // when
        // accountService.deposit(depositRequestDto);

        // then
        verify(accountRepository).findById(TestAccount.ID);
        verify(accountLogRepository).save(accountLogForDeposit);
    }

    // 출금

    // 송금

    // 상세 조회
}