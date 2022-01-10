package com.bankSystem.BankSystem.web.exception.customException;

public class NotEnoughMoney extends RuntimeException{
    public NotEnoughMoney() {
    }

    public NotEnoughMoney(String message) {
        super(message);
    }

    public NotEnoughMoney(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMoney(Throwable cause) {
        super(cause);
    }

    public NotEnoughMoney(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
