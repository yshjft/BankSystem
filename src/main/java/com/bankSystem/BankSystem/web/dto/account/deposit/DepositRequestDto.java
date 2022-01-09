package com.bankSystem.BankSystem.web.dto.account.deposit;

import com.bankSystem.BankSystem.domain.InputOrOutput;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class DepositRequestDto {
    @NotNull
    private Long account_id;

    @NotNull
    @Min(100)
    private int amount;

    @NotNull
    private InputOrOutput type;

    @NotBlank
    private String info;

    @Builder
    public DepositRequestDto(Long account_id, int amount, InputOrOutput type, String info) {
        this.account_id = account_id;
        this.amount = amount;
        this.type = type;
        this.info = info;
    }
}
