package com.bankSystem.BankSystem.exception.handler.advice;

import com.bankSystem.BankSystem.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.exception.customException.LoginException;
import com.bankSystem.BankSystem.exception.customException.UnauthorizedAccessException;
import com.bankSystem.BankSystem.exception.handler.errorCode.ErrorCode;
import com.bankSystem.BankSystem.exception.handler.response.ErrorResponse;
import com.bankSystem.BankSystem.exception.handler.response.ErrorResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponses> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        BindingResult bindingResult = e.getBindingResult();
        ErrorResponses errorResponses = new ErrorResponses();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            StringBuilder message= new StringBuilder();
            message.append(fieldError.getField()).append(", ").append(fieldError.getDefaultMessage());
            errorResponses.addError(new ErrorResponse(ErrorCode.BAD_REQUEST, message.toString()));
        }

        return new ResponseEntity<ErrorResponses>(errorResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyInUseException(EmailAlreadyInUseException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.EMAIL_ALREADY_IN_USE, ErrorCode.EMAIL_ALREADY_IN_USE_MESSAGE);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> loginException(LoginException e) {
        ErrorResponse errorResponse = null;

        switch (e.getMessage()) {
            case "EMAIL":
                errorResponse = new ErrorResponse(ErrorCode.LOGIN_FAIL_EMAIL, ErrorCode.LOGIN_FAIL_EMAIL_MESSAGE);
                break;
            case "PASSWORD":
                errorResponse = new ErrorResponse(ErrorCode.LOGIN_FAIL_PASSWORD, ErrorCode.LOGIN_FAIL_PASSWORD_MESSAGE);
                break;
            default:
                errorResponse = new ErrorResponse(ErrorCode.LOGIN_FAIL, ErrorCode.LOGIN_FAIL_MESSAGE);
                break;
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> loginException(UnauthorizedAccessException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.UNAUTHORIZED_ACCESS_MESSAGE);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Exception e 핸들러 작성할 것
}
