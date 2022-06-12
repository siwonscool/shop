package com.sample.shop.shared.adaptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sample.shop.member.domain.Authority;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.MemberStatus;
import com.sample.shop.post.domain.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Getter
public class MemberAdaptor {
    private Long id;
    private String username;
    private String email;
    private String password;
    private MemberStatus memberStatus;
    private List<String> roles = new ArrayList<>();
    private Set<Authority> authorities;
    private List<Post> posts = new ArrayList<>();

    @Setter
    private String errorMessage;

    @Builder
    private MemberAdaptor(Long id, String username, String email, String password,
        MemberStatus memberStatus, List<String> roles,
        Set<Authority> authorities, String errorMessage,List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.memberStatus = memberStatus;
        this.roles = roles;
        this.authorities = authorities;
        this.errorMessage = errorMessage;
        this.posts = posts;
    }

    public static MemberAdaptor of(Member member){
        return MemberAdaptor.builder()
            .id(member.getId())
            .email(member.getEmail())
            .password(member.getPassword())
            .memberStatus(member.getMemberStatus())
            .roles(member.getRoles())
            .posts(member.getPost())
            .build();
    }
}
