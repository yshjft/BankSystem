package com.bankSystem.BankSystem.dto;

import com.bankSystem.BankSystem.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    // 모두 필수 적으로 들어가야하는 값

    // 30자 넘지 않도록
    private String name;

    // 날짜 형식에 맞는 validation 사용할 것
    private LocalDate birthDate;

    // 200자 넘지 않도록
    private String address;

    // 이메일 형식에 맞도록
    // 30자 넘지 않도록
    private String email;

    // password 사용할 수 있는지 없는 한번만 확인
    private String password;

    // 중간 "-"없도록
    // 숫자 30자 넘지 안호도록
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
