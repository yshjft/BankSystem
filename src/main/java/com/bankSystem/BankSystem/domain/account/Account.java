package com.bankSystem.BankSystem.domain.account;

import com.bankSystem.BankSystem.domain.card.Card;
import com.bankSystem.BankSystem.domain.baseEntity.DataJpaBaseEntity;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Account extends DataJpaBaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @Min(0)
    @NotNull
    private int balance;

    @NotNull
    private boolean hasCard;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account")
    private List<AccountLog> accountLogs= new ArrayList<>();

    @OneToOne(fetch = LAZY, mappedBy = "account")
    private Card card;

    @Builder
    public Account(Long id, int balance, boolean hasCard) {
        this.id = id;
        this.balance = balance;
        this.hasCard = hasCard;
    }

    public void setAccountOwner(User user) {
        this.user = user;
        user.getAccounts().add(this);
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
