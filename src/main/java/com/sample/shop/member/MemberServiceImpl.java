package com.sample.shop.member;

import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.MemberStatus;
import com.sample.shop.member.domain.repository.MemberRepository;
import com.sample.shop.member.dto.request.MemberInfoRequestDto;
import com.sample.shop.member.dto.response.MemberInfoResponseDto;
import com.sample.shop.shared.adaptor.MemberAdaptor;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenLoginService tokenLoginService;

    public MemberAdaptor save(final MemberInfoRequestDto memberInfoRequestDto) {
        memberInfoRequestDto.setPassword(
            passwordEncoder.encode(memberInfoRequestDto.getPassword()));
        return convertMemberAdaptor(memberRepository.save(Member.ofUser(memberInfoRequestDto)));
    }

    public boolean updateMemberStatusActivate(Long id) {
        try {
            Member member = this.findById(id);
            member.updateMemberStatusActivate();
            Member memberFromDb = memberRepository.save(member);
            return memberFromDb.getMemberStatus() == MemberStatus.ACTIVATE;
        } catch (Exception e) {
            log.error("회원정보 활성화에 실패했습니다. member id :{}", id, e);
            return false;
        }
    }

    public boolean updateMemberStatusWithdrawal(Long id) {
        try {
            Member member = this.findById(id);
            member.updateMemberStatusWithdrawal();
            Member memberFromDb = memberRepository.save(member);
            return (memberFromDb.getMemberStatus() == MemberStatus.WITHDRAWAL);
        } catch (Exception e) {
            log.error("회원정보 삭제에 실패했습니다. member id :{}", id, e);
            return false;
        }
    }

    public Optional<Member> isDuplicateEmail(final MemberInfoRequestDto memberInfoRequestDto) {
        return memberRepository.findByEmail(memberInfoRequestDto.getEmail());
    }

    private Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다. id = " + id));
    }

    private MemberAdaptor convertMemberAdaptor(final Member member) {
        return MemberAdaptor.of(member);
    }

    public boolean joinAdmin(MemberInfoRequestDto memberInfoRequestDto) {
        return false;
    }

    public MemberInfoResponseDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("가입한 회원이 아닙니다."));
        if (!member.getUsername().equals(tokenLoginService.getCurrentUsername())) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }
        return new MemberInfoResponseDto(member.getEmail(), member.getNickname());
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username);
    }
}
