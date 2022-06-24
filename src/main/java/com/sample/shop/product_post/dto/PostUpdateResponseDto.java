package com.sample.shop.product_post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    public static PostUpdateResponseDto of(boolean result) {
        return PostUpdateResponseDto.builder()
            .result(result)
            .build();
    }
}
