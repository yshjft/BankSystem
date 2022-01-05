package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.service.AccountService;
import com.bankSystem.BankSystem.web.dto.account.AccountResponse;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountApiController {
    private final AccountService accountService;

    // 생성
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Validated AccountCreateRequestDto accountCreateRequestDto, HttpServletRequest request) {
        AccountCreateResponseDto accountCreateResponseDto = accountService.create(accountCreateRequestDto, request);

        return AccountResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("account created")
                .accountResponseDto(accountCreateResponseDto)
                .build();
    }


    // 전체 조회

    // 상세 조회

    // 입금

    // 출금

    // 송금

    // 삭제
}
