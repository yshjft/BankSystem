package com.bankSystem.BankSystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @ColumnDefault("0")
    @Min(0)
    private int balance = 0;

    private boolean hasCard = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<AccountLog> accountLogs= new ArrayList<>();

    // 대상 테이블
    @OneToOne(fetch = LAZY, mappedBy = "account")
    private Card card;

    @Builder
    public Account(Long id, int balance, boolean hasCard, User client) {
        this.id = id;
        this.balance = balance;
        this.hasCard = hasCard;
        this.user = user;
        client.getAccounts().add(this);
    }

    public void setCard(Card card) {
        this.card = card;
    }
}