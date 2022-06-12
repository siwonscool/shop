package com.sample.shop.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRequestDto {
    private String title;
    private String content;
    private String category;
}
