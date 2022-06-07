package com.sample.shop.shared.advice;

import com.sample.shop.shared.advice.exception.EmailDuplicateException;
import com.sample.shop.shared.advice.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {EmailDuplicateException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(EmailDuplicateException e){
        return ErrorResponse.toResponseEntity(e);
    }
}
