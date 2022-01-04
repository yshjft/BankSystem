package com.bankSystem.BankSystem.web.exception.advice;

import com.bankSystem.BankSystem.web.dto.error.ErrorResponse;
import com.bankSystem.BankSystem.web.dto.error.ErrorsResponse;
import com.bankSystem.BankSystem.web.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.web.exception.customException.LoginException;
import com.bankSystem.BankSystem.web.exception.customException.UnauthorizedAccessException;
import com.bankSystem.BankSystem.web.dto.error.ErrorCode;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {
    // type mismatch & wrong format
    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> typeMisMatchException(MismatchedInputException e) {
        String field = e.getPath().get(0).getFieldName();
        String message = e.getOriginalMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.MISMATCHED_INPUT.getStatus())
                .message(ErrorCode.MISMATCHED_INPUT.getMessage())
                .code(ErrorCode.MISMATCHED_INPUT.getCode())
                .detail(field+" : "+message)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> methodValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        ErrorsResponse errorsResponse = ErrorsResponse.builder()
                .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .code(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .build();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorsResponse.addError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> emailAlreadyInUseException(EmailAlreadyInUseException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.EMAIL_ALREADY_IN_USE.getStatus())
                .message(ErrorCode.EMAIL_ALREADY_IN_USE.getMessage())
                .code(ErrorCode.EMAIL_ALREADY_IN_USE.getCode())
                .detail("email already in use")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> loginException(LoginException e) {
        ErrorResponse errorResponse = null;
        switch (e.getMessage()) {
            case "EMAIL":
                errorResponse = ErrorResponse.builder()
                        .status(ErrorCode.LOGIN_FAIL_EMAIL.getStatus())
                        .message(ErrorCode.LOGIN_FAIL_EMAIL.getMessage())
                        .code(ErrorCode.LOGIN_FAIL_EMAIL.getCode())
                        .detail("wrong email")
                        .build();
                break;
            case "PASSWORD":
                errorResponse = ErrorResponse.builder()
                        .status(ErrorCode.LOGIN_FAIL_PASSWORD.getStatus())
                        .message(ErrorCode.LOGIN_FAIL_PASSWORD.getMessage())
                        .code(ErrorCode.LOGIN_FAIL_PASSWORD.getCode())
                        .detail("wrong password")
                        .build();
                break;
            default:
                errorResponse = ErrorResponse.builder()
                        .status(ErrorCode.LOGIN_FAIL.getStatus())
                        .message(ErrorCode.LOGIN_FAIL.getMessage())
                        .code(ErrorCode.LOGIN_FAIL.getCode())
                        .detail("something's wrong! check your email or password!")
                        .build();
                break;
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> loginException(UnauthorizedAccessException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.UNAUTHORIZED_ACCESS.getStatus())
                .message(ErrorCode.UNAUTHORIZED_ACCESS.getMessage())
                .code(ErrorCode.UNAUTHORIZED_ACCESS.getCode())
                .detail("unauthorized access")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // NOT FOUND (WRONG API)

    // USER NOT FOUND

    // Exception e 핸들러 작성할 것 -> 서버 내부 문제
}
