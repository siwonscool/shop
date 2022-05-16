package com.sample.shop.member.service;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberSignUpService implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member){
        return memberRepository.save(member).getId();
    }
}
