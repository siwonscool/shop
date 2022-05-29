package com.sample.shop.login.dto;

import com.sample.shop.config.jwt.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenResponseDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponseDto of(String accessToken, String refreshToken){
        return TokenResponseDto.builder()
            .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
