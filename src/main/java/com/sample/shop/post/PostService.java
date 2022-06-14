package com.sample.shop.post;

import com.sample.shop.post.dto.PostRequestDto;
import com.sample.shop.post.dto.PostUpdateResponseDto;
import com.sample.shop.shared.adaptor.PostAdaptor;

public interface PostService {

    PostAdaptor createNewPost(PostRequestDto postRequestDto);

    PostUpdateResponseDto updatePost(PostRequestDto postRequestDto, Long postId);

    PostAdaptor findById(Long postId);

    PostUpdateResponseDto deletePost(Long postId);
}
