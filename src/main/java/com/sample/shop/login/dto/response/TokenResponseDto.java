package com.sample.shop.login.dto.response;

import com.sample.shop.config.jwt.JwtHeaderUtilEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {

    @ApiModelProperty(example = "JWT 타입 / Bearer")
    private String grantType;
    @ApiModelProperty(example = "accessToken")
    private String accessToken;
    @ApiModelProperty(example = "refreshToken")
    private String refreshToken;

    @Builder
    public TokenResponseDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponseDto of(String accessToken, String refreshToken) {
        return TokenResponseDto.builder()
            .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
