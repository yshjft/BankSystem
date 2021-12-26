package com.bankSystem.BankSystem.exception.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponses {
    private List<ErrorResponse> errors = new ArrayList<>();

    public void addError(ErrorResponse errorResponse) {
        errors.add(errorResponse);
    }
}
