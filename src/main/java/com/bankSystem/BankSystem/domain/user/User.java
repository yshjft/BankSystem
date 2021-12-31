package com.bankSystem.BankSystem.domain.user;

import com.bankSystem.BankSystem.domain.account.Account;
import com.bankSystem.BankSystem.domain.BaseEntity;
import com.bankSystem.BankSystem.domain.CardLog.CardLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @Column(length = 30)
    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @Column(length = 200)
    @NotNull
    private String address;

    @Column(length = 30, unique = true)
    @NotNull
    private String email;

    @Column(length = 80)
    @NotNull
    private String password;

    @Column(length = 30)
    @NotNull
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CardLog> cardLogs = new ArrayList<>();

    @Builder
    public User(String name, LocalDate birthDate, String address, String email, String password, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
