package com.sample.shop.member;

import com.sample.shop.member.domain.Member;
import com.sample.shop.member.dto.request.MemberInfoRequestDto;
import com.sample.shop.member.dto.response.MemberInfoResponseDto;
import com.sample.shop.shared.adaptor.MemberAdaptor;
import java.util.Optional;

public interface MemberService {

    MemberAdaptor save(final MemberInfoRequestDto memberInfoRequestDto);

    boolean updateMemberStatusActivate(Long id);

    boolean updateMemberStatusWithdrawal(Long id);

    Optional<Member> isDuplicateEmail(final MemberInfoRequestDto memberInfoRequestDto);

    boolean joinAdmin(MemberInfoRequestDto memberInfoRequestDto);

    MemberInfoResponseDto getMemberInfo(String email);

    Member findByUsername(String username);
}
