package com.bankSystem.BankSystem.web.dto.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MISMATCHED_INPUT(HttpStatus.BAD_REQUEST.value(), "E000", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "E001", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    EMAIL_ALREADY_IN_USE(HttpStatus.BAD_REQUEST.value(), "E002", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    LOGIN_FAIL_EMAIL(HttpStatus.BAD_REQUEST.value(), "E003", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    LOGIN_FAIL_PASSWORD(HttpStatus.BAD_REQUEST.value(), "E004", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST.value(), "E005", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), "E006", HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    NO_USER(HttpStatus.NOT_FOUND.value(), "E007", HttpStatus.NOT_FOUND.getReasonPhrase()),
    NO_ACCOUNT(HttpStatus.NOT_FOUND.value(), "E008", HttpStatus.NOT_FOUND.getReasonPhrase()),
    NOT_ENOUGH_MONEY(HttpStatus.BAD_REQUEST.value(), "E009", HttpStatus.BAD_REQUEST.getReasonPhrase()), ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
