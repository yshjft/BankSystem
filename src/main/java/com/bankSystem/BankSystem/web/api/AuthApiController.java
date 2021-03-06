package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.web.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.web.dto.auth.AuthResponse;
import com.bankSystem.BankSystem.web.dto.auth.AuthResponseDto;
import com.bankSystem.BankSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Validated AuthLoginRequestDto authLoginRequestDto) {
        AuthResponseDto authResponseDto = authService.login(authLoginRequestDto);

        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message(authResponseDto.getMessage())
                .build();
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse logout() {
        AuthResponseDto authResponseDto = authService.logout();

        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message(authResponseDto.getMessage())
                .build();
    }
}
