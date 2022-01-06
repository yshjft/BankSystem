package com.bankSystem.BankSystem.web.dto.account.getAccounts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountGetResponseDto {
    private Long id;
    private int balance;

    @Builder
    public AccountGetResponseDto(Long id, int balance) {
        this.id = id;
        this.balance = balance;
    }
}
