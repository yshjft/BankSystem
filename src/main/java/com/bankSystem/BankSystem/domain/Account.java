package com.bankSystem.BankSystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_num")
    private Long id;

    @ColumnDefault("0") // 스키마 생성에만 관여
    @NotNull
    private int balance = 0;

    @ColumnDefault("TRUE")
    private boolean hasCard = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder
    public Account(Long id, int balance, boolean hasCard, Client client) {
        this.id = id;
        this.balance = balance;
        this.hasCard = hasCard;
        this.client = client;
        client.getAccounts().add(this);
    }
}
