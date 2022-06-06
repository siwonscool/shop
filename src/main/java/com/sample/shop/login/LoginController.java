package com.sample.shop.login;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.dto.LoginRequestDto;
import com.sample.shop.login.dto.TokenResponseDto;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.dto.MemberInfoRequestDto;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.annotation.LoginCheck.MemberType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenLoginService tokenLoginService;

    //"로그인이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 생성되야야 한다."
    //"로그인을 할때 로그인 기록을 남겨야 한다. 로그인 기록은 ClientIP, id, 로그인 성공 여부"
    //Oauth 방식으로 구현하려면 로그인도 토큰으로
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
        @RequestBody final LoginRequestDto loginRequestDto, HttpServletRequest request,
        HttpServletResponse response) {
        return ResponseEntity.ok(tokenLoginService.login(loginRequestDto, request, response));
    }

    //"로그아웃이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 삭제(expire상태) 되어야 한다."
    @PostMapping("/logout")
    @LoginCheck(type = MemberType.USER)
    public void logout(@RequestHeader("Authorization") String accessToken,
        @RequestHeader("RefreshToken") String refreshTokenRequest) {
        String username = jwtTokenProvider.getUsername(accessToken);
        tokenLoginService.logout(TokenResponseDto.of(accessToken, refreshTokenRequest), username);
    }


}
