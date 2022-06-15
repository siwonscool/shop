package com.sample.shop.login;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.dto.request.LoginRequestDto;
import com.sample.shop.login.dto.response.LogoutResponseDto;
import com.sample.shop.login.dto.response.TokenResponseDto;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.enumeration.MemberType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Login"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenLoginService tokenLoginService;

    @ApiOperation(value = "로그인", notes = "이메일과 비밀번호를 입력받아 로그인한후 토큰을 쿠키에담아 발급한다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
        @RequestBody final LoginRequestDto loginRequestDto, HttpServletRequest request,
        HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(tokenLoginService.login(loginRequestDto, request, response));
    }

    @ApiOperation(value = "로그아웃", notes = "토큰을 입력받아 토큰을 삭제하고 로그아웃 토큰을 쿠키에담아 발급한다.")
    @PostMapping("/logout")
    @LoginCheck(type = MemberType.USER)
    public ResponseEntity<LogoutResponseDto> logout(
        @RequestHeader("Authorization") String accessToken,
        @RequestHeader("RefreshToken") String refreshTokenRequest, HttpServletResponse response) {
        String username = jwtTokenProvider.getUsername(accessToken);
        boolean result = tokenLoginService.logout(
            TokenResponseDto.of(accessToken, refreshTokenRequest), username, response);
        return ResponseEntity.status(HttpStatus.OK).body(LogoutResponseDto.of(result));
    }


}
