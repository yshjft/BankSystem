package com.bankSystem.BankSystem.api.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String code;

    public AuthResponseDto(String code) {
        this.code = code;
    }
}
