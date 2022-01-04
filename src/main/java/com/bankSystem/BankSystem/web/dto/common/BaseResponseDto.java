package com.bankSystem.BankSystem.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {
    private int status;
    private String message;
}
