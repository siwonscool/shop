package com.sample.shop.config.security;

import com.sample.shop.config.cache.CacheKey;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Cacheable(value = CacheKey.USER, key = "#username", unless = "#result == null")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsernameWithAuthority(username)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
        return CustomUserDetails.of(member);
        //return new User(member.getUsername(), member.getPassword(),new ArrayList<>());
    }
}
