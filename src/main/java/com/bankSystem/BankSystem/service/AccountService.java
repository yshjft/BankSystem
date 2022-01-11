package com.bankSystem.BankSystem.service;

import com.bankSystem.BankSystem.SessionKey;
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
import com.bankSystem.BankSystem.web.dto.account.delete.AccountDeleteResponseDto;
import com.bankSystem.BankSystem.web.dto.account.getAccount.AccountDetailResponseDto;
import com.bankSystem.BankSystem.web.dto.account.getAccount.AccountLogResponseDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyRequestDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyResponseDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionRequestDto;
import com.bankSystem.BankSystem.web.dto.account.getAccounts.AccountGetResponseDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionResponseDto;
import com.bankSystem.BankSystem.web.exception.customException.NoAccountException;
import com.bankSystem.BankSystem.web.exception.customException.NotOwner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final AccountLogRepository accountLogRepository;

    public Map<String, Object> create(AccountCreateRequestDto accountCreateRequestDto) {
        User user = userService.getUser();
        Account account = accountRepository.save(accountCreateRequestDto.toEntity(user));

        Map<String, Object> result = new HashMap<>();
        result.put("account",
                AccountCreateResponseDto.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .ownerId(account.getOwnerId())
                .ownerName(account.getOwnerName())
                .build()
        );

        return result;
    }

    public Map<String, Object> getAccounts(int page, int perPage) {
        User user = userService.getUser();

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

    public Map<String, Object> getAccount(int page, int perPage, Long accountId) {
        Account account = findAccountById(accountId);
        checkAccountAccessRight(account);

        Page<AccountLog> accountLogs = accountLogRepository.findByAccount(account, PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdDate")));

        Map<String, Object> result = new HashMap<>();
        result.put("account", AccountDetailResponseDto.builder()
                .account_id(account.getId())
                .owner(account.getOwnerName())
                .balance(account.getBalance())
                .createdAt(account.getCreatedDate())
                .build()
        );
        result.put("metadata", MetaData.builder()
                .page(page)
                .page_count(accountLogs.getTotalPages())
                .per_page(perPage)
                .total_count(accountLogs.getTotalElements())
                .build()
        );
        result.put("account_logs", accountLogs.getContent().stream()
                .map(acl -> AccountLogResponseDto.builder()
                        .type(acl.getType())
                        .amount(acl.getAmount())
                        .info(acl.getInfo())
                        .createdAt(acl.getCreatedDate())
                        .balance(acl.getBalance())
                        .build())
                .collect(Collectors.toList())
        );

        return result;
    }

    public Map<String, Object> deposit(TransactionRequestDto transactionRequestDto) {
        Account account = findAccountById(transactionRequestDto.getAccount_id());
        checkAccountAccessRight(account);

        account.depositMoney(transactionRequestDto.getAmount());

        AccountLog accountLog = AccountLog.builder()
                .info(transactionRequestDto.getInfo())
                .type(InOrOut.IN)
                .amount(transactionRequestDto.getAmount())
                .balance(account.getBalance())
                .build();
        accountLog.setAccount(account);

        Map<String, Object> result = new HashMap<>();
        result.put("deposit", TransactionResponseDto.builder()
                .account_id(account.getId())
                .balance(account.getBalance())
                .amount(transactionRequestDto.getAmount())
                .type(InOrOut.IN)
                .build());

        return result;
    }

    public Map<String, Object> withdraw(TransactionRequestDto transactionRequestDto) {
        Account account = findAccountById(transactionRequestDto.getAccount_id());
        checkAccountAccessRight(account);

        account.withDrawMoney(transactionRequestDto.getAmount());

        AccountLog accountLog = AccountLog.builder()
                .info(transactionRequestDto.getInfo())
                .type(InOrOut.OUT)
                .amount(transactionRequestDto.getAmount())
                .balance(account.getBalance())
                .build();
        accountLog.setAccount(account);

        Map<String, Object> result = new HashMap<>();
        result.put("withdraw", TransactionResponseDto.builder()
                .account_id(account.getId())
                .balance(account.getBalance())
                .amount(transactionRequestDto.getAmount())
                .type(InOrOut.OUT)
                .build());

        return result;
    }

    public Map<String, Object> checkAccount(CheckAccountRequestDto checkAccountRequestDto) {
        Account account = findAccountById(checkAccountRequestDto.getAccount_id());

        Map<String, Object> result = new HashMap<>();
        result.put("account", CheckAccountResponseDto.builder()
                .account_id(account.getId())
                .owner(account.getOwnerName())
                .build());

        return result;
    }

    public Map<String, Object> sendMoney(SendMoneyRequestDto sendMoneyRequestDto) {
        Account from = findAccountById(sendMoneyRequestDto.getFrom_id());
        checkAccountAccessRight(from);

        Account to = findAccountById(sendMoneyRequestDto.getTo_id());
        from.withDrawMoney(sendMoneyRequestDto.getAmount());
        to.depositMoney(sendMoneyRequestDto.getAmount());

        AccountLog fromAccountLog = AccountLog.builder()
                .info(makeLogInfo(to.getOwnerName(), sendMoneyRequestDto.getMemo()))
                .type(InOrOut.OUT)
                .amount(sendMoneyRequestDto.getAmount())
                .balance(from.getBalance())
                .build();
        fromAccountLog.setAccount(from);
        AccountLog toAccountLog = AccountLog.builder()
                .info(makeLogInfo(from.getOwnerName(), sendMoneyRequestDto.getMemo()))
                .type(InOrOut.IN)
                .amount(sendMoneyRequestDto.getAmount())
                .balance(to.getBalance())
                .build();
        toAccountLog.setAccount(to);

        Map<String, Object> result = new HashMap<>();
        result.put("send_money", SendMoneyResponseDto.builder()
                .from(sendMoneyRequestDto.getFrom_id())
                .to(sendMoneyRequestDto.getTo_id())
                .amount(sendMoneyRequestDto.getAmount())
                .balance(from.getBalance())
                .memo(sendMoneyRequestDto.getMemo())
                .build());

        return result;
    }

    public Map<String, Object> deleteAccount(Long accountId) {
        Account account = findAccountById(accountId);
        checkAccountAccessRight(account);

        accountLogRepository.bulkDeleteByAccount(account.getId());
        accountRepository.deleteById(accountId);

        Map<String, Object> result = new HashMap<>();
        result.put("account", AccountDeleteResponseDto.builder()
                .account_id(accountId)
                .balance(account.getBalance())
                .deletedAt(LocalDateTime.now())
                .build());

        return result;
    }

    private String makeLogInfo(String name, String memo) {
        StringBuilder info = new StringBuilder();
        info.append(name).append("(").append(memo).append(")");
        return info.toString();
    }

    private Account findAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(()->new NoAccountException());
        return account;
    }

    private void checkAccountAccessRight(Account account) {
        ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servletRequestAttribute.getRequest().getSession(false);

        long userId = (Long)session.getAttribute(SessionKey.LOGIN_MEMBER);
        long ownerId = account.getOwnerId();

        if(userId != ownerId) throw new NotOwner();
    }
}
