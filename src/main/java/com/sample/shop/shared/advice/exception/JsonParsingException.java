package com.sample.shop.shared.advice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.shop.shared.advice.ErrorCode;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class JsonParsingException extends RuntimeException{
    private final ErrorCode errorCode;

    public JsonParsingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public JsonParsingException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
