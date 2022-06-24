package com.sample.shop.post.domain;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.sample.shop.member.domain.Member;
import com.sample.shop.post.domain.enumeration.TradeStatus;
import com.sample.shop.post.dto.PostRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;
import reactor.util.annotation.Nullable;

@Entity
@SequenceGenerator(
    name="POST_SEQ_GEN",
    sequenceName = "POST_SEQ",
    allocationSize = 1
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "POST_SEQ_GEN")
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @Lob
    private String content;

    @Column(name = "IS_REMOVED")
    private boolean removed = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @Setter
    private Member author;

    @Builder
    public Post(Long id, String title, String category,
        TradeStatus tradeStatus, String content, boolean removed,
        Member author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.tradeStatus = tradeStatus;
        this.content = content;
        this.removed = removed;
        this.author = author;
    }


    public void updatePost(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
    }

    public void removePost() {
        this.removed = true;
    }
}
