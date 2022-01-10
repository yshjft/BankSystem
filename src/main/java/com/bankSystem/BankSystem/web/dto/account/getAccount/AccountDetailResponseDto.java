package com.bankSystem.BankSystem.web.dto.account.getAccount;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AccountDetailResponseDto {
    private Long account_id;
    private String owner;
    private int balance;
    private LocalDateTime createdAt;

    @Builder
    public AccountDetailResponseDto(Long account_id, String owner, int balance, LocalDateTime createdAt) {
        this.account_id = account_id;
        this.owner = owner;
        this.balance = balance;
        this.createdAt = createdAt;
    }
}
