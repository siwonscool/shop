package com.sample.shop.login.dto;

import com.sample.shop.config.jwt.JwtHeaderUtilEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static TokenResponseDto of(String accessToken, String refreshToken){
        return TokenResponseDto.builder()
            .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
