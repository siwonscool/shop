package com.sample.shop.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.shop.member.dto.response.MemberUpdateResponseDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostUpdateResponseDto {
    private boolean result;

    @Builder
    private PostUpdateResponseDto(boolean result) {
        this.result = result;
    }

    public static PostUpdateResponseDto of(boolean result){
        return PostUpdateResponseDto.builder()
            .result(result)
            .build();
    }
}
