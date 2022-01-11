package com.bankSystem.BankSystem.web.dto.account.delete;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AccountDeleteResponseDto {
    private Long account_id;
    private int balance;
    private LocalDateTime deletedAt;

    @Builder
    public AccountDeleteResponseDto(Long account_id, int balance, LocalDateTime deletedAt) {
        this.account_id = account_id;
        this.balance = balance;
        this.deletedAt = deletedAt;
    }
}
