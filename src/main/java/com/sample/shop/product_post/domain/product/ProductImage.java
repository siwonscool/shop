package com.sample.shop.product_post.domain.product;

import com.sample.shop.product_post.domain.post.Post;
import com.sample.shop.shared.enumeration.FileType;
import com.sample.shop.shared.enumeration.YorN;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@SequenceGenerator(
    name = "POST_IMAGE_SEQ_GEN",
    sequenceName = "POST_IMAGE_SEQ"
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "POST_IMAGE_SEQ_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private String originFilename;

    private String storeFilename;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Enumerated(EnumType.STRING)
    private YorN deleteYn;

    @Builder
    public ProductImage(Long id, String originFilename, String storeFilename,
        FileType fileType, Product product) {
        this.id = id;
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.fileType = fileType;
        this.product = product;
        this.deleteYn=YorN.N;
    }

    public void removeProductImage() {
        this.deleteYn = YorN.Y;
    }
}
