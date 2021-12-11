package com.bankSystem.BankSystem.domain;

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
public class User extends BaseEntity{
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

    // 이메일 형식을 지켜야만 한다.
    @Column(length = 30)
    @NotNull
    private String email;

    @Column(length = 20)
    @NotNull
    private String password;

    // '-'이 없이 숫자만 있어야 한다
    @Column(length = 30)
    @NotNull
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CardLog> cardLogs = new ArrayList<>();

    @Builder
    public User(Long id, String name, LocalDate birthDate, String address, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
