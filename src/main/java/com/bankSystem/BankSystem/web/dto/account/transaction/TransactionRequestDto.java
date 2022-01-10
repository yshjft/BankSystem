package com.bankSystem.BankSystem.web.dto.account.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TransactionRequestDto {
    @NotNull
    private Long account_id;

    @NotNull
    @Min(100)
    private int amount;

    @NotBlank
    private String info;

    @Builder
    public TransactionRequestDto(Long account_id, int amount, String info) {
        this.account_id = account_id;
        this.amount = amount;
        this.info = info;
    }
}
