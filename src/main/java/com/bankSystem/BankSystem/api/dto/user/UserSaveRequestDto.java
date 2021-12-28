package com.bankSystem.BankSystem.api.dto.user;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class UserSaveRequestDto extends UserCommonDto{
    @NotBlank
    @Length(min=4, max=20)
    private String password;

    @Builder
    public UserSaveRequestDto(String name, LocalDate birthDate, String address, String email, String password, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
