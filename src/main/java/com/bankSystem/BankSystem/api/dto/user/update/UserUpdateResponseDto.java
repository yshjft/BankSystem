package com.bankSystem.BankSystem.api.dto.user.update;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 뭔가 중복되는 느낌... 해결 방법을 찾아보자

@Getter
@NoArgsConstructor
public class UserUpdateResponseDto {
    private String name;
    private String email;
    private String message;

    @Builder
    public UserUpdateResponseDto(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
