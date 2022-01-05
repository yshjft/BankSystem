package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionKey;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    // 중복되는 기능이 존재한다. → 리팩토링 해야 하지 않을까?
    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long)session.getAttribute(SessionKey.LOGIN_MEMBER);

        return userRepository.findUserById(userId);
    }
}
