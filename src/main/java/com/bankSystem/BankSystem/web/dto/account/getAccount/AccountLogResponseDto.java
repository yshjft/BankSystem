package com.bankSystem.BankSystem.web.dto.account.getAccount;

import com.bankSystem.BankSystem.domain.InOrOut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AccountLogResponseDto {
    private InOrOut type;
    private int amount;
    private String info;
    private LocalDateTime createdAt;
    private int balance;

    @Builder
    public AccountLogResponseDto(InOrOut type, int amount, String info, LocalDateTime createdAt, int balance) {
        this.type = type;
        this.amount = amount;
        this.info = info;
        this.createdAt = createdAt;
        this.balance = balance;
    }
}
