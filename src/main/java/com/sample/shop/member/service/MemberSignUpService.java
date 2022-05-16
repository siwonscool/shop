package com.sample.shop.member.service;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import com.sample.shop.member.request.MemberInfoRequestDto;
import com.sample.shop.shared.member.MemberAdaptor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberSignUpService implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberAdaptor save(Member member) {
        member.setPw(passwordEncoder.encode(member.getPassword()));
        return this.convertMemberAdaptor(memberRepository.save(member));
    }

    @Transactional
    public void updateMemberStatusActivate() {
        memberRepository.updateMemberStatusActivate();
    }

    @Transactional
    public Optional<Member> isDuplicateEmail(MemberInfoRequestDto memberInfoRequestDto) {
        return memberRepository.findByEmail(memberInfoRequestDto.getEmail());
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다. id = " + id));
        memberRepository.updateMemberStatusWITHDRAWAL(id);
    }

    private MemberAdaptor convertMemberAdaptor(final Member member) {
        return MemberAdaptor.builder()
            .id(member.getId())
            .email(member.getEmail())
            .pw(member.getPw())
            .memberStatus(member.getMemberStatus())
            .roles(member.getRoles())
            .build();
    }

}
