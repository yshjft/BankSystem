package com.bankSystem.BankSystem.web.dto.account.checkAccount;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckAccountResponseDto {
    private Long account_id;
    private String owner;

    @Builder
    public CheckAccountResponseDto(Long account_id, String owner) {
        this.account_id = account_id;
        this.owner = owner;
    }
}
