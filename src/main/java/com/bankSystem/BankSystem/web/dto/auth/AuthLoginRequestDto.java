package com.bankSystem.BankSystem.web.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AuthLoginRequestDto {
    @NotBlank
    @Length(max=30)
    @Email
    private String email;

    @NotBlank
    @Length(min=4, max=20)
    private String password;

    @Builder
    public AuthLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
