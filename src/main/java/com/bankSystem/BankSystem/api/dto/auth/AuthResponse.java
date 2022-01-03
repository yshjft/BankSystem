package com.bankSystem.BankSystem.api.dto.auth;

import com.bankSystem.BankSystem.api.dto.common.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponse extends BaseResponseDto {
    @Builder
    public AuthResponse(int status, String message) {
        super(status, message);
    }
}
