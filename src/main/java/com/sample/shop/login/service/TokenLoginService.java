package com.sample.shop.login.service;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenLoginService implements LoginService, UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
        /*return memberRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을수 없습니다."));*/
    }

    @Transactional
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("가입되지 않은 e-mail 입니다."));
    }
}
