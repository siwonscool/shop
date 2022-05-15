package com.sample.shop.web;

import com.sample.shop.member.domain.JwtTokenProvider;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.service.MemberTokenLoginService;
import com.sample.shop.web.dto.MemberInfoRequestDto;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "/member",produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
public class MemberController {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberTokenLoginService memberTokenLoginService;

    //회원가입
    @PostMapping("/join")
    public Long join(@RequestBody MemberInfoRequestDto memberInfoRequestDto){
        return memberTokenLoginService.save(Member.builder()
            .email(memberInfoRequestDto.getEmail())
            .pw(passwordEncoder.encode(memberInfoRequestDto.getPw()))
            .roles(Collections.singletonList("ROLE_USER"))
            .build());
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody MemberInfoRequestDto memberInfoRequestDto){
        log.info("Email : " + memberInfoRequestDto.getEmail());
        Member member = memberTokenLoginService.findByEmail(memberInfoRequestDto.getEmail());

        log.info("dto : " + memberInfoRequestDto.getPw());
        log.info("db : " + member.getPassword());
        if (!passwordEncoder.matches(memberInfoRequestDto.getPw(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

}