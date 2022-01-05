package com.bankSystem.BankSystem.web.dto.account;

import com.bankSystem.BankSystem.web.dto.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AccountResponse<T> extends BaseResponseDto {
    private AccountResult result;

    @Builder
    public AccountResponse(int status, String message, T accountResponseDto) {
        super(status, message);
        this.result = AccountResult.builder()
                .account(accountResponseDto)
                .build();
    }

    @Getter
    @NoArgsConstructor
    private static class AccountResult<T> {
        private T account;

        @Builder
        public AccountResult(T account) {
            this.account = account;
        }
    }
}
