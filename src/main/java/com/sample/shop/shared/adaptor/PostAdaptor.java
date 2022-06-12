package com.sample.shop.shared.adaptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.shop.member.domain.Member;
import com.sample.shop.post.domain.Post;
import com.sample.shop.post.domain.enumeration.TradeStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Getter
public class PostAdaptor {
    private Long id;
    private String title;
    private Member author;
    private String content;
    private TradeStatus tradeStatus;
    private String category;

    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    @Builder
    private PostAdaptor(Long id, String title, Member author, String content,
        TradeStatus tradeStatus, String category, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.tradeStatus = tradeStatus;
        this.category = category;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public static PostAdaptor of(Post post){
        return PostAdaptor.builder()
            .id(post.getId())
            .title(post.getTitle())
            .author(post.getAuthor())
            .content(post.getContent())
            .tradeStatus(post.getTradeStatus())
            .category(post.getCategory())
            .createdTime(post.getCreatedTime())
            .modifiedTime(post.getModifiedTime())
            .build();
    }
}
