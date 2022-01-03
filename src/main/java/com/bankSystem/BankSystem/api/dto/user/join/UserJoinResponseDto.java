package com.bankSystem.BankSystem.api.dto.user.join;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserJoinResponseDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String phoneNumber;

    @Builder
    public UserJoinResponseDto(Long id, String name, LocalDate birthDate, String address, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
