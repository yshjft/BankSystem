package com.bankSystem.BankSystem.web.dto.account.sendMoney;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMoneyResponseDto {
    private Long from;
    private Long to;
    private int amount;
    private int balance;
    private String memo;

    @Builder
    public SendMoneyResponseDto(Long from, Long to, int amount, int balance, String memo) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.balance = balance;
        this.memo = memo;
    }
}
