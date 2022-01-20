package com.bankSystem.BankSystem.web.dto.user.update;

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

    @NotBlank
    @Length(max=30)
    @Pattern(regexp = "^[0-9]*$", message = "number only")
    private String phoneNumber;

    @Length(min=4, max=20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "only character and number")
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
