package com.bankSystem.BankSystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AccountLog extends BaseEntity{
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

    // account <- oneToMany -> accountLog
}
