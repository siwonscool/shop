package com.sample.shop.shared.advice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.shop.shared.advice.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
public class EmailDuplicateException extends RuntimeException{
    private final ErrorCode errorCode;

    public EmailDuplicateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public EmailDuplicateException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


}
