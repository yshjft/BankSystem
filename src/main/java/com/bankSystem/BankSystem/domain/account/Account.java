package com.bankSystem.BankSystem.domain.account;

import com.bankSystem.BankSystem.domain.baseEntity.DataJpaBaseEntity;
import com.bankSystem.BankSystem.domain.accountLog.AccountLog;
import com.bankSystem.BankSystem.domain.user.User;
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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @Min(0)
    @NotNull
    private int balance;;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account")
    private List<AccountLog> accountLogs= new ArrayList<>();

    @Builder
    public Account(Long id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void setAccountOwner(User user) {
        this.user = user;
        user.getAccounts().add(this);
    }
}
