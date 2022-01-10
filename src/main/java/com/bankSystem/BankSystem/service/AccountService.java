package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.domain.InOrOut;
import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.account.AccountRepository;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.accountLog.AccountLogRepository;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.web.dto.MetaData;
import com.bankSystem.BankSystem.web.dto.account.checkAccount.CheckAccountRequestDto;
import com.bankSystem.BankSystem.web.dto.account.checkAccount.CheckAccountResponseDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateResponseDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyRequestDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyResponseDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionRequestDto;
import com.bankSystem.BankSystem.web.dto.account.getAccounts.AccountGetResponseDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionResponseDto;
import com.bankSystem.BankSystem.web.exception.customException.NoAccountException;
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

    @Transactional
    public Map<String, Object> deposit(TransactionRequestDto transactionRequestDto) {
        Account account = findAccountById(transactionRequestDto.getAccount_id());
        account.depositMoney(transactionRequestDto.getAmount());

        AccountLog accountLog = AccountLog.builder()
                .info(transactionRequestDto.getInfo())
                .type(InOrOut.IN)
                .amount(transactionRequestDto.getAmount())
                .balance(account.getBalance())
                .build();
        accountLog.setAccount(account);
        accountLogRepository.save(accountLog);

        Map<String, Object> result = new HashMap<>();
        result.put("deposit", TransactionResponseDto.builder()
                .account_id(account.getId())
                .balance(account.getBalance())
                .amount(transactionRequestDto.getAmount())
                .type(InOrOut.IN)
                .build());

        return result;
    }

    @Transactional
    public Map<String, Object> withdraw(TransactionRequestDto transactionRequestDto) {
        Account account = findAccountById(transactionRequestDto.getAccount_id());
        account.withDrawMoney(transactionRequestDto.getAmount());

        AccountLog accountLog = AccountLog.builder()
                .info(transactionRequestDto.getInfo())
                .type(InOrOut.OUT)
                .amount(transactionRequestDto.getAmount())
                .balance(account.getBalance())
                .build();
        accountLog.setAccount(account);
        accountLogRepository.save(accountLog);

        Map<String, Object> result = new HashMap<>();
        result.put("withdraw", TransactionResponseDto.builder()
                .account_id(account.getId())
                .balance(account.getBalance())
                .amount(transactionRequestDto.getAmount())
                .type(InOrOut.OUT)
                .build());

        return result;
    }

    // 게좌 확인
    public Map<String, Object> checkAccount(CheckAccountRequestDto checkAccountRequestDto) {
        Account account = findAccountById(checkAccountRequestDto.getAccount_id());

        Map<String, Object> result = new HashMap<>();
        result.put("account", CheckAccountResponseDto.builder()
                .account_id(account.getId())
                .owner(account.getUser().getName())
                .build());

        return result;
    }

    // 개선 -> 꼭 따로 따로 가져와야 하나?
    @Transactional
    public Map<String, Object> sendMoney(SendMoneyRequestDto sendMoneyRequestDto) {
        Account fromAccount = findAccountById(sendMoneyRequestDto.getFrom_id());
        Account toAccount = findAccountById(sendMoneyRequestDto.getTo_id());
        fromAccount.withDrawMoney(sendMoneyRequestDto.getAmount());
        toAccount.depositMoney(sendMoneyRequestDto.getAmount());

        StringBuilder fromAccountInfo = new StringBuilder();
        fromAccountInfo.append(toAccount.getUser().getName()).append("(").append(sendMoneyRequestDto.getMemo()).append(")");
        AccountLog fromAccountLog = AccountLog.builder()
                .info(fromAccountInfo.toString())
                .type(InOrOut.OUT)
                .amount(sendMoneyRequestDto.getAmount())
                .balance(fromAccount.getBalance())
                .build();
        fromAccountLog.setAccount(fromAccount);

        StringBuilder toAccountInfo = new StringBuilder();
        fromAccountInfo.append(fromAccount.getUser().getName()).append("(").append(sendMoneyRequestDto.getMemo()).append(")");
        AccountLog toAccountLog = AccountLog.builder()
                .info(toAccountInfo.toString())
                .type(InOrOut.IN)
                .amount(sendMoneyRequestDto.getAmount())
                .balance(toAccount.getBalance())
                .build();
        toAccountLog.setAccount(toAccount);

        accountLogRepository.save(fromAccountLog);
        accountLogRepository.save(toAccountLog);

        Map<String, Object> result = new HashMap<>();
        result.put("send_money", SendMoneyResponseDto.builder()
                .from(sendMoneyRequestDto.getFrom_id())
                .to(sendMoneyRequestDto.getTo_id())
                .amount(sendMoneyRequestDto.getAmount())
                .balance(fromAccount.getBalance())
                .memo(sendMoneyRequestDto.getMemo())
                .build());

        return result;
    }

    public Account findAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(()->new NoAccountException());
        return account;
    }
}
