package com.bankSystem.BankSystem.api.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String code;

    @Builder
    public AuthResponseDto(String code) {
        this.code = code;
    }
}
