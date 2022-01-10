package com.bankSystem.BankSystem.web.dto.account.sendMoney;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SendMoneyRequestDto {
    @NotNull
    private Long from_id;

    @NotNull
    private Long to_id;

    @NotNull
    @Min(100)
    private int amount;

    @NotBlank
    private String memo;

    @Builder
    public SendMoneyRequestDto(Long from_id, Long to_id, int amount, String memo) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.amount = amount;
        this.memo = memo;
    }
}
