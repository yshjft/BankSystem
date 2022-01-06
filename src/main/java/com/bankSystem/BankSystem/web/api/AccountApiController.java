package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.service.AccountService;
import com.bankSystem.BankSystem.web.dto.account.AccountResponse;
import com.bankSystem.BankSystem.web.dto.account.create.AccountCreateRequestDto;
import com.bankSystem.BankSystem.web.dto.PageAttributeDto;
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

    // 출금

    // 송금

    // 삭제
}
