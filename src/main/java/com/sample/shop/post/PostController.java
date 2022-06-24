package com.sample.shop.post;

import com.sample.shop.member.MemberController;
import com.sample.shop.post.dto.PostRequestDto;
import com.sample.shop.post.dto.PostUpdateResponseDto;
import com.sample.shop.shared.adaptor.PostAdaptor;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.enumeration.MemberType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Post"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시물 업로드", notes = "제목, 내용, 카테고리를 입력해 글을 작성한다.")
    @LoginCheck(type = MemberType.USER)
    @PostMapping
    public ResponseEntity<PostAdaptor> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(postService.createNewPost(postRequestDto));
    }

    @ApiOperation(value = "게시물 업데이트", notes = "제목, 내용, 카테고리를 입력해 글을 수정한다.")
    @LoginCheck(type = MemberType.USER)
    @PatchMapping("/{id}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(
        @RequestBody PostRequestDto postRequestDto, @PathVariable Long id) {
        return ResponseEntity.ok(postService.updatePost(postRequestDto, id));
    }

    @ApiOperation(value = "게시물 1개 조회", notes = "게시물 id를 받아 게시물의 정보를 조회한다.")
    @LoginCheck(type = MemberType.USER)
    @GetMapping("/{id}")
    public ResponseEntity<PostAdaptor> findPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @ApiOperation(value = "게시물 삭제", notes = "게시물 id를 받아 게시물의 삭제여부를 true 로 업데이트 한다")
    @LoginCheck(type = MemberType.USER)
    @DeleteMapping("/{id}")
    public ResponseEntity<PostUpdateResponseDto> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }


}
