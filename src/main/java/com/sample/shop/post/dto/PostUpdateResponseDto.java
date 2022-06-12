package com.sample.shop.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostUpdateResponseDto {
    private Long id;
    private String title;
    private String author;
    private String email;
    private String content;
    private String status;
    private String category;

    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
}
