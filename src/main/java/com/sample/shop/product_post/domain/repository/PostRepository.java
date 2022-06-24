package com.sample.shop.product_post.domain.repository;

import com.sample.shop.product_post.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
