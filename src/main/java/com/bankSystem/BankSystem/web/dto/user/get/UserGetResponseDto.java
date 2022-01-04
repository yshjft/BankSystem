package com.bankSystem.BankSystem.web.dto.user.get;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserGetResponseDto {
    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String phoneNumber;

    @Builder
    public UserGetResponseDto(String name, LocalDate birthDate, String address, String email, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
