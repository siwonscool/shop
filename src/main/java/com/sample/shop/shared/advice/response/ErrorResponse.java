package com.sample.shop.shared.advice.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.shop.shared.advice.ErrorCode;
import com.sample.shop.shared.advice.exception.RestApiException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Getter
@Slf4j
public class ErrorResponse {

    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final int status;
    private final String errorType;
    private final String errorCode;
    @Setter
    private String description;

    @Builder
    private ErrorResponse(int status, String errorType, String errorCode,
        String description) {
        this.status = status;
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.description = description;
    }

    public static ErrorResponse of(ErrorCode errorCode){
        return ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .errorType(errorCode.getHttpStatus().name())
            .errorCode(errorCode.name())
            .description(errorCode.getDescription())
            .build();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(RestApiException e) {
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
