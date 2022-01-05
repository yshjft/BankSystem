package com.bankSystem.BankSystem.web.dto.auth;

import com.bankSystem.BankSystem.web.dto.BaseResponseDto;
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
