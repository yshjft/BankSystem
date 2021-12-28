package com.bankSystem.BankSystem.api.dto.user;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class UserCommonDto {
    @NotBlank
    @Length(max=30)
    protected String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthDate;

    @NotBlank
    @Length(max=200)
    protected String address;

    @NotBlank
    @Length(max=30)
    @Email
    protected String email;

    @Pattern(regexp = "^[0-9]*$")
    @Length(max=30)
    protected String phoneNumber;
}
