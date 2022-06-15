package com.sample.shop.shared.advice.response;

import com.sample.shop.shared.advice.ErrorCode;
import com.sample.shop.shared.advice.exception.EmailDuplicateException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponse {

    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final int status;
    private final String errorType;
    private final String code;
    private final String errorCode;
    private final String description;

    @Builder
    private ErrorResponse(int status, String errorType, String code, String errorCode,
        String description) {
        this.status = status;
        this.errorType = errorType;
        this.code = code;
        this.errorCode = errorCode;
        this.description = description;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(EmailDuplicateException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .errorType(errorCode.getHttpStatus().name())
                .errorCode(errorCode.name())
                .description(errorCode.getDescription())
                .build());
    }
}
