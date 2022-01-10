package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.service.AccountService;
import com.bankSystem.BankSystem.web.dto.account.AccountResponse;
import com.bankSystem.BankSystem.web.dto.account.checkAccount.CheckAccountRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.PageAttributeDto;
import com.bankSystem.BankSystem.web.dto.account.sendMoney.SendMoneyRequestDto;
import com.bankSystem.BankSystem.web.dto.account.transaction.TransactionRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountApiController {
    private final AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Validated AccountCreateRequestDto accountCreateRequestDto, HttpServletRequest request) {
        Map<String, Object> result = accountService.create(accountCreateRequestDto, request);

        return AccountResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("account created")
                .result(result)
                .build();
    }

    // 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccounts(@ModelAttribute @Validated PageAttributeDto pageAttributeDto, HttpServletRequest request) {
        Map<String, Object> result = accountService.getAccounts(pageAttributeDto.getPage(), pageAttributeDto.getPerPage(), request);

        return AccountResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .result(result)
                .build();
    }

    // 상세 조회

    // 입금
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

    // 출금
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

    // 계좌 확인
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

    // 송금
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


    // 삭제
}
