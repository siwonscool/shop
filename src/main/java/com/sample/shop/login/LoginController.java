package com.sample.shop.login;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.dto.MemberInfoRequestDto;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
public class LoginController {

    //TokenLoginService 에서 사용하면 순환참조 문제 발생
    //TokenLoginService -> JwtTokenProvider -> UserDetailsService -> TokenLoginService
    //TokenLoginService 에서 동작하게 해서 결합도를 낮추고 싶은데 방법이 있을까..
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final TokenLoginService tokenLoginService;

    //"로그인이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 생성되야야 한다."
    //"로그인을 할때 로그인 기록을 남겨야 한다. 로그인 기록은 ClientIP, id, 로그인 성공 여부"
    @PostMapping("/login")
    public ResponseEntity<String> login(
        @RequestBody final MemberInfoRequestDto memberInfoRequestDto, HttpServletRequest request) {
        Member member = tokenLoginService.findByEmail(memberInfoRequestDto.getEmail());

        if (!passwordEncoder.matches(memberInfoRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }else{
            log.info(this.getClientIp(request));
            log.info(member.getEmail());
            log.info("로그인을 성공하였습니다.");
        }

        return ResponseEntity.ok(
            jwtTokenProvider.generateAccessToken(member.getUsername()));
    }

    private String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    //"로그아웃이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 삭제(expire상태) 되어야 한다."
    //JWT 는 서버쪽에서 로그아웃 할 수 없으므로 Redis 를 사용하여 토큰을 DB에 저장하고 요청시마다 검사해야함... 후..



}
