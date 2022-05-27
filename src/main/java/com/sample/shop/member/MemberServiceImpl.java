package com.sample.shop.member;

import com.sample.shop.config.jwt.JwtExpirationEnums;
import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.domain.RefreshToken;
import com.sample.shop.login.domain.repository.RefreshTokenRedisRepository;
import com.sample.shop.login.dto.TokenResponseDto;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import com.sample.shop.member.dto.MemberInfoRequestDto;
import com.sample.shop.member.dto.MemberInfoResponseDto;
import com.sample.shop.shared.adaptor.MemberAdaptor;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenLoginService tokenLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public MemberAdaptor save(final MemberInfoRequestDto memberInfoRequestDto) {
        memberInfoRequestDto.setPassword(
            passwordEncoder.encode(memberInfoRequestDto.getPassword()));
        return convertMemberAdaptor(memberRepository.save(Member.ofUser(memberInfoRequestDto)));
    }

    public void updateMemberStatusActivate(final Long id) {
        Member member = this.findById(id);
        member.updateMemberStatusActivate();
    }

    public void updateMemberStatusWithdrawal(final Long id) {
        Member member = this.findById(id);
        member.updateMemberStatusWithdrawal();
    }

    public Optional<Member> isDuplicateEmail(final MemberInfoRequestDto memberInfoRequestDto) {
        return memberRepository.findByEmail(memberInfoRequestDto.getEmail());
    }

    private Member findById(final Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다. id = " + id));
    }

    private MemberAdaptor convertMemberAdaptor(final Member member) {
        return MemberAdaptor.of(member);
    }

    public void joinAdmin(MemberInfoRequestDto memberInfoRequestDto) {
    }

    public MemberInfoResponseDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new NoSuchElementException("가입한 회원이 아닙니다."));
        if (!member.getUsername().equals(TokenLoginService.getCurrentUsername())) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }
        return new MemberInfoResponseDto(member.getEmail(),member.getNickname());
    }
}
