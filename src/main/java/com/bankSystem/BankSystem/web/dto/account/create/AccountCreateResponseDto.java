package com.bankSystem.BankSystem.web.dto.account.create;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreateResponseDto {
    private Long accountId;
    private int balance;
    private Long ownerId;
    private String ownerName;

    @Builder
    public AccountCreateResponseDto(Long accountId, int balance, Long ownerId, String ownerName) {
        this.accountId = accountId;
        this.balance = balance;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }
}
