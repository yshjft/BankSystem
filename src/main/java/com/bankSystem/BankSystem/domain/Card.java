package com.bankSystem.BankSystem.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Card extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="card_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int limit;
}
