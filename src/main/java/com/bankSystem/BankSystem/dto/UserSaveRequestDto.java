package com.bankSystem.BankSystem.dto;

import com.bankSystem.BankSystem.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    // 모두 필수 적으로 들어가야하는 값
    private String name;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;

    @Builder
    public UserSaveRequestDto(String name, LocalDate birthDate, String address, String email, String password, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .birthDate(birthDate)
                .address(address)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
