package com.bankSystem.BankSystem.web.dto.account;

import com.bankSystem.BankSystem.web.dto.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class AccountResponse extends BaseResponseDto {
    private Map<String, Object> result;

    @Builder
    public AccountResponse(int status, String message, Map<String, Object> result) {
        super(status, message);
        this.result = result;
    }
}
