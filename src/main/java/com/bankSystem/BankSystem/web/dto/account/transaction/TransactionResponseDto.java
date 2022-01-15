package com.bankSystem.BankSystem.web.dto.account.transaction;

import com.bankSystem.BankSystem.domain.accountLog.InOrOut;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionResponseDto {
    private Long account_id;
    private int amount;
    private int balance;
    private InOrOut type;

    @Builder
    public TransactionResponseDto(Long account_id, int amount, int balance, InOrOut type) {
        this.account_id = account_id;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
    }
}
