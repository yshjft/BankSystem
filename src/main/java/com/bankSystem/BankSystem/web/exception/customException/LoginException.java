package com.bankSystem.BankSystem.web.exception.customException;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
