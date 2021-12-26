package com.bankSystem.BankSystem.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
