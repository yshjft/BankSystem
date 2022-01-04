package com.bankSystem.BankSystem.domain.accountLog;

import com.bankSystem.BankSystem.domain.baseEntity.DataJpaBaseEntity;
import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.InputOrOutput;
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
    private InputOrOutput type;

    @NotNull
    private int amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public AccountLog(Long id, String info, InputOrOutput type, int amount, Account account) {
        this.id = id;
        this.info = info;
        this.type = type;
        this.amount = amount;
        this.account = account;
        account.getAccountLogs().add(this);
    }
}
