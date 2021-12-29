package com.bankSystem.BankSystem.api.dto.user.save;

import com.bankSystem.BankSystem.domain.user.User;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class UserSaveDto extends UserSaveBaseDto {
    @NotBlank
    @Length(max=80)
    private String password;

    @Builder
    public UserSaveDto(String name, LocalDate birthDate, String address, String email, String password, String phoneNumber) {
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
