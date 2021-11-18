package com.bankSystem.BankSystem.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
public class CardLog extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="card_log_id")
    private Long id;

    @NotNull
    private int amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder
    public CardLog(Long id, int amount, Card card, Client client) {
        this.id = id;
        this.amount = amount;
        this.card = card;
        card.getCardLogs().add(this);
        this.client = client;
        client.getCardLogs().add(this);
    }
}
