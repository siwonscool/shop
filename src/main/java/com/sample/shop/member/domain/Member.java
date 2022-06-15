package com.sample.shop.member.domain;

import com.sample.shop.member.dto.request.MemberInfoRequestDto;
import com.sample.shop.post.domain.Post;
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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

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

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> post = new ArrayList<>();

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
