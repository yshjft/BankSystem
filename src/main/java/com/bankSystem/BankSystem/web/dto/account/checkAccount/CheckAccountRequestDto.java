package com.bankSystem.BankSystem.web.dto.account.checkAccount;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CheckAccountRequestDto {
    @NotNull
    private Long account_id;

    @Builder
    public CheckAccountRequestDto(Long account_id) {
        this.account_id = account_id;
    }
}
