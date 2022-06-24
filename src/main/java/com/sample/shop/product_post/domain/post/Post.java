package com.sample.shop.product_post.domain.post;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.sample.shop.member.domain.Member;
import com.sample.shop.product_post.domain.enumeration.TradeStatus;
import com.sample.shop.product_post.domain.product.Product;
import com.sample.shop.product_post.dto.PostRequestDto;
import com.sample.shop.shared.entity.BaseTimeEntity;
import com.sample.shop.shared.enumeration.YorN;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @Setter
    private Member author;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    private TradeStatus tradeStatus;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private YorN deleteYn;


    @Builder
    public Post(Long id, String title, String category,
        TradeStatus tradeStatus, String content, boolean removed,
        Member author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.tradeStatus = tradeStatus;
        this.content = content;
        this.author = author;
        this.deleteYn=YorN.N;
    }


    public void updatePost(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
    }

    public void removePost() {
        this.deleteYn = YorN.Y;
    }
}
