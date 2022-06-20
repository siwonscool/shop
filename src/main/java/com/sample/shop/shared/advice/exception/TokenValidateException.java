package com.sample.shop.shared.advice.exception;

public class TokenValidateException extends RuntimeException{

    public TokenValidateException(String message) {
        super(message);
    }
}
