package com.sample.shop.member;

import com.sample.shop.login.dto.response.TokenResponseDto;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.dto.request.MemberInfoRequestDto;
import com.sample.shop.member.dto.response.MemberInfoResponseDto;
import com.sample.shop.member.dto.response.MemberUpdateResponseDto;
import com.sample.shop.shared.adaptor.MemberAdaptor;
import com.sample.shop.shared.advice.ErrorCode;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.advice.exception.EmailDuplicateException;
import com.sample.shop.shared.enumeration.MemberType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Member"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TokenLoginService tokenLoginService;

    @ApiOperation(value = "회원가입", notes = "이메일, 비밀번호, 닉네임을 입력해 회원가입 한다.")
    @PostMapping("/join")
    public ResponseEntity<MemberAdaptor> join(
        @RequestBody final MemberInfoRequestDto memberInfoRequestDto) {
        if (!memberService.isDuplicateEmail(memberInfoRequestDto).isEmpty()) {
            throw new EmailDuplicateException(ErrorCode.EMAIL_DUPLICATE);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.save(memberInfoRequestDto));
        }
    }

    @ApiOperation(value = "관리자 회원가입", notes = "기능구현 x")
    @PostMapping("/join/admin")
    public ResponseEntity<String> joinAdmin(
        @RequestBody final MemberInfoRequestDto memberInfoRequestDto) {
        memberService.joinAdmin(memberInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("관리자 회원가입 완료");
    }

    @ApiOperation(value = "회원상태 활성화", notes = "회원의 id로 회원가입한 회원의 상태를 활성(ACTIVATE)으로 바꾼다.")
    @PatchMapping("/{id}")
    @LoginCheck(type = MemberType.USER)
    public ResponseEntity<MemberUpdateResponseDto> updateMemberStatus(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.updateMemberStatusActivate(id));
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원의 id로 회원가입한 회원의 상태를 탈퇴(WITHDRAWAL)로 바꾼다.")
    @DeleteMapping("/{id}")
    @LoginCheck(type = MemberType.USER)
    public ResponseEntity<MemberUpdateResponseDto> deleteMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.updateMemberStatusWithdrawal(id));
    }

    @ApiOperation(value = "회원정보 조회", notes = "회원의 email 로 회원가입한 회원의 정보를 받는다.")
    @GetMapping("/{email}")
    @LoginCheck(type = MemberType.USER)
    public ResponseEntity<MemberInfoResponseDto> getMemberAdaptor(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberInfo(email));
    }

    @ApiOperation(value = "토큰 재발급", notes = "토큰이 만료되면 토큰을 재발급 한다.")
    @PostMapping("/regenerate")
    @LoginCheck(type = MemberType.USER)
    public ResponseEntity<TokenResponseDto> regenerateToken(
        @RequestHeader("RefreshToken") String refreshToken) {
        log.info("refreshToken : " + refreshToken);
        return ResponseEntity.ok(tokenLoginService.regeneration(refreshToken));
    }
}