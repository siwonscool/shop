package com.sample.shop.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.shop.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRequestDto {
    private String title;
    private String content;
    private String category;

    @Builder
    private PostRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Post toEntity(){
        return Post.builder()
            .title(title)
            .content(content)
            .category(category)
            .build();
    }
}
