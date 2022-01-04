package com.bankSystem.BankSystem.web.dto.user.update;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    @Builder
    public UserUpdateResponseDto(Long id, String name, LocalDate birthDate, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
