package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.api.dto.auth.LoginRequestDto;
import com.bankSystem.BankSystem.api.dto.auth.LoginResponseDto;
import com.bankSystem.BankSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Validated LoginRequestDto loginRequestDto, HttpServletRequest request) {
        return authService.loginService(loginRequestDto, request);
    }

}
