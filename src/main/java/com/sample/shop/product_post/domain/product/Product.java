package com.sample.shop.product_post.domain.product;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

import com.sample.shop.product_post.domain.post.Post;
import com.sample.shop.shared.enumeration.YorN;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
    name = "PRODUCT_SEQ_GEN",
    sequenceName = "PRODUCT_SEQ"
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PRODUCT_SEQ_GEN")
    @Column(name = "PRODUCT_ID")
    private Long id;

    @OneToMany(mappedBy = "product", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> productImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Post post;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private YorN deleteYn;

    public void removeProduct() {
        this.deleteYn = YorN.Y;
    }
}
