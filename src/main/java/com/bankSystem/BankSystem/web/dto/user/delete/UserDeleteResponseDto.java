package com.bankSystem.BankSystem.web.dto.user.delete;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteResponseDto {
    private Long id;
    private String name;

    @Builder
    public UserDeleteResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
