package com.bankSystem.BankSystem.domain.account;

import com.bankSystem.BankSystem.domain.baseEntity.DataJpaBaseEntity;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.web.exception.customException.NotEnoughMoney;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Min(0)
    @NotNull
    private int balance;;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    private List<AccountLog> accountLogs= new ArrayList<>();

    @Builder
    public Account(Long id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getOwnerId() {
        return this.user.getId();
    }

    public String getOwnerName() {
        return this.user.getName();
    }

    public void setAccountOwner(User user) {
        this.user = user;
        user.getAccounts().add(this);
    }

    public void depositMoney(int amount) {
        this.balance += amount;
    }

    public void withDrawMoney(int amount) {
        if(this.balance < amount) {
            throw new NotEnoughMoney();
        }
        this.balance -= amount;
    }
}
