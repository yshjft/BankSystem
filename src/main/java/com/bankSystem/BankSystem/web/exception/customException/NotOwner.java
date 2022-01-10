package com.bankSystem.BankSystem.web.exception.customException;

public class NotOwner extends RuntimeException{
    public NotOwner() {
    }

    public NotOwner(String message) {
        super(message);
    }

    public NotOwner(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOwner(Throwable cause) {
        super(cause);
    }

    public NotOwner(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
