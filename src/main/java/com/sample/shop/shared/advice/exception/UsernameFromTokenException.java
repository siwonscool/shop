package com.sample.shop.shared.advice.exception;

public class UsernameFromTokenException extends RuntimeException{
    public UsernameFromTokenException(String message) {
        super(message);
    }
}
