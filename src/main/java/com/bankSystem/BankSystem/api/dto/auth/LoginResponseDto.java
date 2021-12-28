package com.bankSystem.BankSystem.api.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String code;

    public LoginResponseDto(String code) {
        this.code = code;
    }
}
