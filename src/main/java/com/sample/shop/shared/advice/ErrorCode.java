package com.sample.shop.shared.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "중복되는 이메일이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String description;
}
