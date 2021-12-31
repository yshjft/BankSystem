package com.bankSystem.BankSystem.api.dto.user.save;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {
    private Long id;
    private String name;
    private String email;

    @Builder
    public UserSaveResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
