package com.bankSystem.BankSystem.web.dto.account.create;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AccountCreateRequestDto {
    @Min(1000)
    @NotNull
    private int balance;

    @Builder
    public AccountCreateRequestDto(int balance) {
        this.balance = balance;
    }

    public Account toEntity(User user) {
        Account account = Account.builder()
                .balance(this.balance)
                .build();
        account.setAccountOwner(user);

        return account;
    }
}
