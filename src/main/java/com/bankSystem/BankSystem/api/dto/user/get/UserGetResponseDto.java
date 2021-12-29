package com.bankSystem.BankSystem.api.dto.user.get;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserGetResponseDto {
    private String address;
    private LocalDate birthDate;
    private String email;
    private String name;
    private String phoneNumber;

    @Builder
    public UserGetResponseDto(String address, LocalDate birthDate, String email, String name, String phoneNumber) {
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
