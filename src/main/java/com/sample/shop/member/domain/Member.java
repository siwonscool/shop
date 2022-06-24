package com.sample.shop.member.domain;

import com.sample.shop.member.dto.request.MemberInfoRequestDto;
import com.sample.shop.product_post.domain.post.Post;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;


@Entity
@SequenceGenerator(
    name="MEMBER_SEQ_GEN",
    sequenceName = "MEMBER_SEQ",
    allocationSize = 1
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "MEMBER_SEQ_GEN")
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberStatus memberStatus;


    @Builder
    public Member(Long id, String username, String email, String password, String nickname,
        MemberStatus memberStatus, Set<Authority> authorities,
        List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.memberStatus = memberStatus;
        this.authorities = authorities;
        this.posts = posts;
    }

    public static Member ofUser(MemberInfoRequestDto memberInfoRequestDto) {
        Member member = Member.builder()
            .username(UUID.randomUUID().toString())
            .email(memberInfoRequestDto.getEmail())
            .password(memberInfoRequestDto.getPassword())
            .nickname(memberInfoRequestDto.getNickname())
            .memberStatus(MemberStatus.READY)
            .build();
        member.addAuthority(Authority.ofUser(member));
        return member;
    }

    public static Member ofAdmin(MemberInfoRequestDto memberInfoRequestDto) {
        Member member = Member.builder()
            .username(UUID.randomUUID().toString())
            .email(memberInfoRequestDto.getEmail())
            .password(memberInfoRequestDto.getPassword())
            .nickname(memberInfoRequestDto.getNickname())
            .memberStatus(MemberStatus.READY)
            .build();
        member.addAuthority(Authority.ofAdmin(member));
        return member;
    }

    private void addAuthority(Authority authority) {
        authorities.add(authority);
    }

    public List<String> getRoles() {
        return authorities.stream()
            .map(Authority::getRole)
            .collect(toList());
    }

    public Member updateMemberStatusActivate() {
        this.memberStatus = MemberStatus.ACTIVATE;
        return this;
    }

    public Member updateMemberStatusWithdrawal() {
        this.memberStatus = MemberStatus.WITHDRAWAL;
        return this;
    }

}
