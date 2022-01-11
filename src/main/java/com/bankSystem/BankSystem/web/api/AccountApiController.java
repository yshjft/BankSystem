package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.service.AccountService;
import com.bankSystem.BankSystem.web.dto.account.AccountResponse;
import com.bankSystem.BankSystem.web.dto.account.checkAccount.CheckAccountRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyRequestDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Validated AccountCreateRequestDto accountCreateRequestDto) {
        Map<String, Object> result = accountService.create(accountCreateRequestDto);

        return AccountResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("account created")
                .result(result)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccounts(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="perPage", defaultValue = "5") int perPage
    ) {
        Map<String, Object> result = accountService.getAccounts(page, perPage);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccounts(
            @PathVariable(value = "accountId") Long accountId,
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="perPage", defaultValue = "5") int perPage
    ) {
        Map<String, Object> result = accountService.getAccount(page, perPage, accountId);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse deposit(@RequestBody @Validated TransactionRequestDto transactionRequestDto) {
        Map<String, Object> result = accountService.deposit(transactionRequestDto);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse withdraw(@RequestBody @Validated TransactionRequestDto transactionRequestDto) {
        Map<String, Object> result = accountService.withdraw(transactionRequestDto);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @GetMapping("/checkAccount")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse checkAccount(@RequestBody @Validated CheckAccountRequestDto checkAccountRequestDto) {
        Map<String, Object> result = accountService.checkAccount(checkAccountRequestDto);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @PostMapping("/sendMoney")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse sendMoney(@RequestBody @Validated SendMoneyRequestDto sendMoneyRequestDto) {
        Map<String, Object> result = accountService.sendMoney(sendMoneyRequestDto);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccounts(@PathVariable(value = "accountId") Long accountId) {
        Map<String, Object> result = accountService.deleteAccount(accountId);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

}
