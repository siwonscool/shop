package com.sample.shop.shared.advice;

import com.sample.shop.shared.advice.exception.RestApiException;
import com.sample.shop.shared.advice.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {RestApiException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(RestApiException e) {
        return ErrorResponse.toResponseEntity(e);
    }
}
