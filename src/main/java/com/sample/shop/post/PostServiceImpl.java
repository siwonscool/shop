package com.sample.shop.post;

import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.MemberServiceImpl;
import com.sample.shop.member.domain.Member;
import com.sample.shop.post.domain.Post;
import com.sample.shop.post.domain.repository.PostRepository;
import com.sample.shop.post.dto.PostRequestDto;
import com.sample.shop.post.dto.PostUpdateResponseDto;
import com.sample.shop.shared.adaptor.PostAdaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TokenLoginService tokenLoginService;
    private final MemberServiceImpl memberServiceImpl;

    @Override
    @Transactional
    public PostAdaptor createNewPost(PostRequestDto postRequestDto) {
        try {
            Post post = postRepository.save(postRequestDto.toEntity());
            String username = tokenLoginService.getCurrentUsername();
            Member member = memberServiceImpl.findByUsername(username);
            post.setAuthor(member);
            Post insertMemberPost = postRepository.save(post);
            return convertPostAdaptor(postRepository.save(insertMemberPost));
        } catch (Exception e) {
            log.info("게시물 업로드에 실패하였습니다.");
            return null;
        }
    }

    @Override
    @Transactional
    public PostUpdateResponseDto updatePost(PostRequestDto postRequestDto, Long postId) {
        try {
            return null;
        } catch (Exception e) {
            log.info("게시물 업데이트에 실패하였습니다. Post Id : {}", postId, e);
            return null;
        }
    }

    @Override
    @Transactional
    public PostAdaptor findById(Long postId) {
        return null;
    }

    @Override
    @Transactional
    public PostUpdateResponseDto deletePost(Long postId) {
        return null;
    }

    private PostAdaptor convertPostAdaptor(final Post post) {
        return PostAdaptor.of(post);
    }
}
