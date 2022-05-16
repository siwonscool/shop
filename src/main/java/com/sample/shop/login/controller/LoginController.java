package com.sample.shop.login.controller;

import com.sample.shop.login.domain.JwtTokenProvider;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.request.MemberInfoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LoginController {

    //TokenLoginService 에서 사용하면 순환참조 문제 발생
    //TokenLoginService -> JwtTokenProvider -> UserDetailsService -> TokenLoginService
    //TokenLoginService 에서 동작하게 해서 결합도를 낮추고 싶은데 방법이 있을까..
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    //Config 에 DIP, OCP 만족하는 아키텍처로 수정하고싶음
    private final TokenLoginService tokenLoginService;

    //로그인
    @PostMapping
    public String login(@RequestBody final MemberInfoRequestDto memberInfoRequestDto) {
        Member member = tokenLoginService.findByEmail(memberInfoRequestDto.getEmail());
        if (!passwordEncoder.matches(memberInfoRequestDto.getPw(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

}
