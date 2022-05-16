package com.sample.shop.member.service;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberTokenLoginService implements MemberService, UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
            .orElseThrow(()->new UsernameNotFoundException("사용자를 찾을수 없습니다."));
    }

    @Transactional
    public Long save(Member member){
        return memberRepository.save(member).getId();
    }

    @Transactional
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(()-> new IllegalArgumentException("가입되지 않은 e-mail 입니다."));
    }
}
