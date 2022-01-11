package com.bankSystem.BankSystem.web.exception.customException;

public class AccountRemainException extends RuntimeException{
    public AccountRemainException() {
    }

    public AccountRemainException(String message) {
        super(message);
    }

    public AccountRemainException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountRemainException(Throwable cause) {
        super(cause);
    }

    public AccountRemainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
