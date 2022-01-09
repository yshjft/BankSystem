package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.accountLog.AccountLogRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.web.dto.MetaData;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateResponseDto;
import com.bankSystem.BankSystem.web.dto.account.deposit.DepositRequestDto;
import com.bankSystem.BankSystem.web.dto.account.getAccounts.AccountGetResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService; // 순환 참조 조심할 것
    private final AccountRepository accountRepository;
    private final AccountLogRepository accountLogRepository;

    @Transactional
    public Map<String, Object> create(AccountCreateRequestDto accountCreateRequestDto, HttpServletRequest request) {
        User user = userService.getUser(request);
        Account account = accountRepository.save(accountCreateRequestDto.toEntity(user));

        Map<String, Object> result = new HashMap<>();
        result.put("account",
                AccountCreateResponseDto.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .ownerId(account.getUser().getId())
                .ownerName(account.getUser().getName())
                .build()
        );

        return result;
    }

    public Map<String, Object> getAccounts(int page, int perPage, HttpServletRequest request) {
        User user = userService.getUser(request);

        Page<Account> accounts = accountRepository.findByUser(user, PageRequest.of(page, perPage));

        Map<String, Object> result = new HashMap<>();
        result.put("metadata", MetaData.builder()
                .page(page)
                .page_count(accounts.getTotalPages())
                .per_page(perPage)
                .total_count(accounts.getTotalElements())
                .build()
        );
        result.put("accounts", accounts.getContent().stream()
                .map(a -> AccountGetResponseDto.builder()
                        .id(a.getId())
                        .balance(a.getBalance())
                        .build())
                .collect(Collectors.toList())
        );

        return result;
    }

    // 출금 시에도 dto 같을 듯 네이밍 고려해라
    public void deposit(DepositRequestDto depositRequestDto) {
        Account account = accountRepository.findById(depositRequestDto.getAccount_id()).orElseThrow(); // 에러 처리, 존재하지 않는 계좌
        account.depositMoney(depositRequestDto.getAmount());

        AccountLog accountLog = AccountLog.builder()
                .info(depositRequestDto.getInfo())
                .type(depositRequestDto.getType())
                .amount(depositRequestDto.getAmount())
                .balance(account.getBalance())
                .build();
        accountLog.setAccount(account);
        accountLogRepository.save(accountLog);

        // 반환 고려
    }
}
