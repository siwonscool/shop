package com.sample.shop.member;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import com.sample.shop.member.dto.MemberInfoRequestDto;
import com.sample.shop.shared.member.MemberAdaptor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Member save(final MemberInfoRequestDto memberInfoRequestDto) {
        memberInfoRequestDto.setPassword(passwordEncoder.encode(memberInfoRequestDto.getPassword()));
        return memberRepository.save(Member.ofUser(memberInfoRequestDto));
    }

    @Transactional
    public void updateMemberStatusActivate(final Long id) {
        Member member = this.findById(id);
        member.updateMemberStatusActivate();
    }

    @Transactional
    public void updateMemberStatusWithdrawal(final Long id) {
        Member member = this.findById(id);
        member.updateMemberStatusWithdrawal();
    }

    @Transactional
    public Optional<Member> isDuplicateEmail(final MemberInfoRequestDto memberInfoRequestDto) {
        return memberRepository.findByEmail(memberInfoRequestDto.getEmail());
    }


    public Member findById(Long id){
        return memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다. id = " + id));
    }

    private MemberAdaptor convertMemberAdaptor(final Member member) {
        return MemberAdaptor.builder()
            .id(member.getId())
            .email(member.getEmail())
            .pw(member.getPassword())
            .memberStatus(member.getMemberStatus())
            .roles(member.getRoles())
            .build();
    }

    public void joinAdmin(MemberInfoRequestDto memberInfoRequestDto) {
    }
}
