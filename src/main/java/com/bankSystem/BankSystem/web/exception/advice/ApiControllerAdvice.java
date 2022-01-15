package com.bankSystem.BankSystem.web.exception.advice;

import com.bankSystem.BankSystem.web.dto.error.ErrorResponse;
import com.bankSystem.BankSystem.web.dto.error.ErrorsResponse;
import com.bankSystem.BankSystem.web.exception.customException.*;
import com.bankSystem.BankSystem.web.dto.error.ErrorCode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {
    // json이 제대로 읽혀지지 않을 때 발생하는 예외
    // ex. 잘못된 json 포맷, 잘못된 날짜 포맷
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        String detail = null;

        if(cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            detail = jpe.getOriginalMessage();
        }else if(cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if(mie.getPath() != null && mie.getPath().size() > 0) {
                String field = mie.getPath().get(0).getFieldName();
                detail = field + " : " + mie.getOriginalMessage();
            }else{
                detail = "wrong json format";
            }
        }else if(cause instanceof JsonMappingException) {
            JsonMappingException jpe = (JsonMappingException) cause;
            if(jpe.getPath() != null && jpe.getPath().size() > 0) {
                String field = jpe.getPath().get(0).getFieldName();
                detail = field + " : " + jpe.getOriginalMessage();
            }else{
                detail = "wrong json format";
            }
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.WRONG_FORMAT.getStatus())
                .message(ErrorCode.WRONG_FORMAT.getMessage())
                .code(ErrorCode.WRONG_FORMAT.getCode())
                .detail(detail)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 잘 못된 Method parameter에 대하 발생하는 오류이다. query parameter 또는 path variable에서 발생한다.
    // ex. int에 String을 전달하는 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String detail = e.getName()+" : "+e.getValue()+" is not "+e.getRequiredType();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .code(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .detail(detail)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // bean validation이 지켜지지 않은 필드가 있는 경우 예외 전달
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorsResponse> bindException(BindException e){
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
    public ResponseEntity<ErrorResponse> loginException(UnauthorizedAccessException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.UNAUTHORIZED_ACCESS.getStatus())
                .message(ErrorCode.UNAUTHORIZED_ACCESS.getMessage())
                .code(ErrorCode.UNAUTHORIZED_ACCESS.getCode())
                .detail("unauthorized access")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // USER NOT FOUND (NoUserException)
    @ExceptionHandler(NoUserException.class)
    public ResponseEntity<ErrorResponse> noUserException(NoUserException e, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.NO_USER.getStatus())
                .message(ErrorCode.NO_USER.getMessage())
                .code(ErrorCode.NO_USER.getCode())
                .detail("non-existent user")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAccountException.class)
    public ResponseEntity<ErrorResponse> noAccountException(NoAccountException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.NO_ACCOUNT.getStatus())
                .message(ErrorCode.NO_ACCOUNT.getMessage())
                .code(ErrorCode.NO_ACCOUNT.getCode())
                .detail("wrong account number")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<ErrorResponse> notEnoughMoney(NotEnoughMoney e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.NOT_ENOUGH_MONEY.getStatus())
                .message(ErrorCode.NOT_ENOUGH_MONEY.getMessage())
                .code(ErrorCode.NOT_ENOUGH_MONEY.getCode())
                .detail("lack of balance")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotOwner.class)
    public ResponseEntity<ErrorResponse> notOwner(NotOwner e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.UNAUTHORIZED_ACCESS.getStatus())
                .message(ErrorCode.UNAUTHORIZED_ACCESS.getMessage())
                .code(ErrorCode.UNAUTHORIZED_ACCESS.getCode())
                .detail("not your account")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountRemainException.class)
    public ResponseEntity<ErrorResponse> accountRemain(AccountRemainException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.ACCOUNT_REMAIN.getStatus())
                .message(ErrorCode.ACCOUNT_REMAIN.getMessage())
                .code(ErrorCode.ACCOUNT_REMAIN.getCode())
                .detail("remove all accounts")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.NO_HANDLER_FOUND.getStatus())
                .message(ErrorCode.NO_HANDLER_FOUND.getMessage())
                .code(ErrorCode.NO_HANDLER_FOUND.getCode())
                .detail("api does not exist")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .detail("internal server error")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
