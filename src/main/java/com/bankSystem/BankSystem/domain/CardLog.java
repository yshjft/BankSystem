package com.bankSystem.BankSystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CardLog extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="card_log_id")
    private Long id;

    @NotNull
    private int amount;

    // card <- oneToMany -> cardLog
    // client <- oneToMany -> cardLog
}
