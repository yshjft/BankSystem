package com.bankSystem.BankSystem.api.dto.user.update;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank
    @Length(max=30)
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    @Length(max=200)
    private String address;

    @Pattern(regexp = "^[0-9]*$")
    @Length(max=30)
    private String phoneNumber;

    @NotBlank
    @Length(min=4, max=20)
    private String password;

    @Builder
    public UserUpdateRequestDto(String name, LocalDate birthDate, String address, String phoneNumber, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
