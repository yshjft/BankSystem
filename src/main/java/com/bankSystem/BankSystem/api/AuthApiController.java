package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.api.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.api.dto.auth.AuthResponseDto;
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
    public AuthResponseDto login(@RequestBody @Validated AuthLoginRequestDto authLoginRequestDto, HttpServletRequest request) {
        return authService.login(authLoginRequestDto, request);
    }

    @PostMapping("/logout")
    public AuthResponseDto logout(HttpServletRequest request) {
        return authService.logout(request);
    }
}
