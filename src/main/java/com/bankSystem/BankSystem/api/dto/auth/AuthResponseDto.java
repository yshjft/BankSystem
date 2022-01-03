package com.bankSystem.BankSystem.api.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String message;

    @Builder
    public AuthResponseDto(String message) {
        this.message = message;
    }
}
