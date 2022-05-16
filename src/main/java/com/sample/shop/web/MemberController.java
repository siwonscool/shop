package com.sample.shop.web;

import com.sample.shop.login.domain.JwtTokenProvider;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.service.MemberSignUpService;
import com.sample.shop.member.request.MemberInfoRequestDto;
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
    //TokenLoginService 에서 사용하면 순환참조 문제 발생
    //TokenLoginService -> JwtTokenProvider -> UserDetailsService -> TokenLoginService
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //Config 에 DIP, OCP 만족하는 아키텍처로 수정하고싶음
    private final MemberSignUpService memberTokenLoginService;
    private final TokenLoginService tokenLoginService;

    /*
    * 기능 구현은 완료 했으니 TestCase 를 충족해야함
    * */

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
        Member member = tokenLoginService.findByEmail(memberInfoRequestDto.getEmail());
        if (!passwordEncoder.matches(memberInfoRequestDto.getPw(),member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

}