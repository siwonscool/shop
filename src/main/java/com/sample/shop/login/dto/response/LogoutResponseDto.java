package com.sample.shop.login.dto.response;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LogoutResponseDto {

    private boolean result;

    @Builder
    private LogoutResponseDto(boolean result) {
        this.result = result;
    }

    public static LogoutResponseDto of(boolean result) {
        return LogoutResponseDto.builder()
            .result(result)
            .build();
    }
}
