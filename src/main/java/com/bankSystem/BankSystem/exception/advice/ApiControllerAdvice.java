package com.bankSystem.BankSystem.exception.advice;

import com.bankSystem.BankSystem.exception.response.ErrorResponse;
import com.bankSystem.BankSystem.exception.response.ErrorResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice("com.bankSystem.BankSystem.api")
public class ApiControllerAdvice {
    public static final String BAD_REQUEST = "BAD_REQUEST";

    // typeMismatch를 어떻게 해결할 것인가(보류)
    /*
       methodValidException 한정!
       {
           errors: [
              {
                code: WRONG_REQUEST
                message: 에러 내용
              }
           ]
       }
    */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponses> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        BindingResult bindingResult = e.getBindingResult();
        ErrorResponses  errorResponses = new ErrorResponses();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            StringBuilder message= new StringBuilder();
            message.append(fieldError.getField());
            message.append(", ");
            message.append(fieldError.getDefaultMessage());
            errorResponses.addError(new ErrorResponse(BAD_REQUEST, message.toString()));
        }

        return new ResponseEntity<ErrorResponses>(errorResponses, HttpStatus.BAD_REQUEST);
    }
}
