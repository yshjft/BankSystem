package com.bankSystem.BankSystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Card extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="card_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull @Min(0)
    @Column(name = "usage_limit")
    private int limit;

    // 주 테이블
    // 외래키 위치, 관계 주인
    @OneToOne(fetch = LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    @OneToMany(mappedBy = "card")
    private List<CardLog> cardLogs = new ArrayList<>();

    @Builder
    public Card(Long id, String name, int limit, Account account) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.account = account;
        account.setCard(this);
    }
}
