package com.bankSystem.BankSystem.web.dto.user.join;

import com.bankSystem.BankSystem.domain.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.websocket.OnMessage;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class UserJoinRequestDto {
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

    @Pattern(regexp = "^[0-9]*$", message = "number only")
    @Length(max=30)
    protected String phoneNumber;

    @NotNull
    @Length(min=4, max=20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "only character and number")
    private String password;

    private String encodedPassword;

    @Builder
    public UserJoinRequestDto(String name, LocalDate birthDate, String address, String email, String password, String phoneNumber) {
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
                .password(encodedPassword)
                .phoneNumber(phoneNumber)
                .build();
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}
