package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserJpaRepository;
import com.bankSystem.BankSystem.SessionKey;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountCreateResponseDto create(AccountCreateRequestDto accountCreateRequestDto, HttpServletRequest request) {
        User user = getUser(request);
        Account account = accountRepository.save(accountCreateRequestDto.toEntity(user));

        return AccountCreateResponseDto.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .ownerId(account.getUser().getId())
                .ownerName(account.getUser().getName())
                .build();
    }

    public void getAccounts(int page, int perPage, HttpServletRequest request) {
        User user = getUser(request);

        // find by userId -> 근데 이거는 유저에서 찾는게 더 빠르지 않음?
        List<Account> accountList = user.getAccounts();
        for(Account a : accountList) {
            log.info("게좌 = {}", a.getId());
            log.info("잔액, = {}", a.getBalance());
            log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    // 중복, 리팩토링 필요
    // user가 없는 경우에 대한 예외 처리가 필요할 듯
    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long)session.getAttribute(SessionKey.LOGIN_MEMBER);

        return userRepository.findById(userId).orElse(null);
    }
}
