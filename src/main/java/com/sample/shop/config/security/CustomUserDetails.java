package com.sample.shop.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.shop.member.domain.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String email;
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    private String nickname;

    public static UserDetails of(Member member){
        return CustomUserDetails.builder()
            .username(member.getUsername())
            .email(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRoles())
            .nickname(member.getNickname())
            .build();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }
}
