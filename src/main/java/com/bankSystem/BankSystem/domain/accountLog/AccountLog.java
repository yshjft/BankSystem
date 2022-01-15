package com.bankSystem.BankSystem.domain.accountLog;

import com.bankSystem.BankSystem.domain.baseEntity.DataJpaBaseEntity;
import com.bankSystem.BankSystem.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class AccountLog extends DataJpaBaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_log_id")
    private Long id;

    @NotNull
    private String info;

    @Enumerated(EnumType.STRING)
    @NotNull
    private InOrOut type;

    @NotNull
    private int amount;

    @NotNull
    private int balance;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public AccountLog(Long id, String info, InOrOut type, int amount, int balance) {
        this.id = id;
        this.info = info;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.getAccountLogs().add(this);
    }
}
