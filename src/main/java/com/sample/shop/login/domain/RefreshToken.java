package com.sample.shop.login.domain;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String id;

    @Getter
    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String username, String refreshToken, Long remainingMilliSeconds){
        return RefreshToken.builder()
            .id(username)
            .refreshToken(refreshToken)
            .expiration(remainingMilliSeconds)
            .build();
    }
}
