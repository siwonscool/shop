package com.sample.shop.shared.advice.exception;

public class LogoutException extends RuntimeException{
    public LogoutException(String message) {
        super(message);
    }
}
