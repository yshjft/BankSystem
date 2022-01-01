package com.bankSystem.BankSystem.exception.advice;

import com.bankSystem.BankSystem.api.dto.error.ErrorResponseDto;
import com.bankSystem.BankSystem.api.dto.error.ErrorsResponseDto;
import com.bankSystem.BankSystem.exception.customException.EmailAlreadyInUseException;
import com.bankSystem.BankSystem.exception.customException.LoginException;
import com.bankSystem.BankSystem.exception.customException.UnauthorizedAccessException;
import com.bankSystem.BankSystem.api.dto.error.ErrorCode;
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
    public ResponseEntity<ErrorResponseDto> typeMisMatchException(MismatchedInputException e) {
        String field = e.getPath().get(0).getFieldName();
        String message = e.getOriginalMessage();

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(ErrorCode.MISMATCHED_INPUT.getStatus())
                .message(ErrorCode.MISMATCHED_INPUT.getMessage())
                .code(ErrorCode.MISMATCHED_INPUT.getCode())
                .detail(field+" : "+message)
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponseDto> methodValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        ErrorsResponseDto errorsResponseDto = ErrorsResponseDto.builder()
                .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .code(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .build();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorsResponseDto.addError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorsResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponseDto> emailAlreadyInUseException(EmailAlreadyInUseException e) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(ErrorCode.EMAIL_ALREADY_IN_USE.getStatus())
                .message(ErrorCode.EMAIL_ALREADY_IN_USE.getMessage())
                .code(ErrorCode.EMAIL_ALREADY_IN_USE.getCode())
                .detail("email already in use")
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponseDto> loginException(LoginException e) {
        ErrorResponseDto errorResponseDto = null;
        switch (e.getMessage()) {
            case "EMAIL":
                errorResponseDto = ErrorResponseDto.builder()
                        .status(ErrorCode.LOGIN_FAIL_EMAIL.getStatus())
                        .message(ErrorCode.LOGIN_FAIL_EMAIL.getMessage())
                        .code(ErrorCode.LOGIN_FAIL_EMAIL.getCode())
                        .detail("wrong email")
                        .build();
                break;
            case "PASSWORD":
                errorResponseDto = ErrorResponseDto.builder()
                        .status(ErrorCode.LOGIN_FAIL_PASSWORD.getStatus())
                        .message(ErrorCode.LOGIN_FAIL_PASSWORD.getMessage())
                        .code(ErrorCode.LOGIN_FAIL_PASSWORD.getCode())
                        .detail("wrong password")
                        .build();
                break;
            default:
                errorResponseDto = ErrorResponseDto.builder()
                        .status(ErrorCode.LOGIN_FAIL.getStatus())
                        .message(ErrorCode.LOGIN_FAIL.getMessage())
                        .code(ErrorCode.LOGIN_FAIL.getCode())
                        .detail("something's wrong! check your email or password!")
                        .build();
                break;
        }

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto> loginException(UnauthorizedAccessException e) {
        log.error("wtf: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(ErrorCode.UNAUTHORIZED_ACCESS.getStatus())
                .message(ErrorCode.UNAUTHORIZED_ACCESS.getMessage())
                .code(ErrorCode.UNAUTHORIZED_ACCESS.getCode())
                .detail("unauthorized access")
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNAUTHORIZED);
    }

    // 필요에 따라서 알아서 추가할 것...

    // Exception e 핸들러 작성할 것 -> 서버 내부 문제
}
