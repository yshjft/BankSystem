package com.bankSystem.BankSystem.api.dto.user.update;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {
    private String message;
    private String name;
    private String email;

    @Builder
    public UserUpdateResponseDto(String message, String name, String email) {
        this.message = message;
        this.name = name;
        this.email = email;
    }
}
