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
public class Client extends BaseEntity{
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="client_id")
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

    // '-'이 없이 숫자만 있어야 한다
    @Column(length = 30)
    @NotNull
    private String phoneNumber;

    @Column(length = 30)
    private String job;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();

    @Builder
    public Client(Long id, String name, LocalDate birthDate, String address, String email, String phoneNumber, String job) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.job = job;
    }
}
