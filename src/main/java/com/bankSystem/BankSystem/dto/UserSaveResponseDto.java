package com.bankSystem.BankSystem.dto;

import lombok.Getter;

@Getter
public class UserSaveResponseDto {
    private Long id;

    public UserSaveResponseDto(Long id) {
        this.id = id;
    }
}
