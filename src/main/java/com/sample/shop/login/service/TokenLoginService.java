package com.sample.shop.login.service;

import com.sample.shop.config.cache.CacheKey;
import com.sample.shop.config.jwt.JwtExpirationEnums;
import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.config.security.CustomUserDetails;
import com.sample.shop.login.domain.LogoutAccessToken;
import com.sample.shop.login.domain.RefreshToken;
import com.sample.shop.login.domain.repository.LogoutAccessTokenRedisRepository;
import com.sample.shop.login.domain.repository.RefreshTokenRedisRepository;
import com.sample.shop.login.dto.LoginRequestDto;
import com.sample.shop.login.dto.TokenResponseDto;
import com.sample.shop.member.domain.Member;
import com.sample.shop.member.domain.repository.MemberRepository;
import com.sample.shop.shared.adaptor.MemberAdaptor;
import java.security.Principal;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sample.shop.config.jwt.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenLoginService implements LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponseDto login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 e-mail 입니다."));

        this.checkPassword(loginRequestDto.getPassword(), member.getPassword());
        String username = member.getUsername();
        String accessToken = jwtTokenProvider.generateAccessToken(username);
        RefreshToken refreshToken = saveRefreshToken(username);

        log.info(this.getClientIp(request));
        log.info(member.getEmail());
        log.info("로그인을 성공하였습니다.");

        return TokenResponseDto.of(accessToken, refreshToken.getRefreshToken());
    }


    private void checkPassword(String dtoPassword, String memberPassword) {
        if (!passwordEncoder.matches(dtoPassword, memberPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public RefreshToken saveRefreshToken(String username) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
            jwtTokenProvider.generateRefreshToken(username),
            REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
    }

    private String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @CacheEvict(value = CacheKey.USER, key = "#username")
    public void logout(TokenResponseDto tokenResponseDto, String username) {
        String accessToken = resolveToken(tokenResponseDto.getAccessToken());
        long remainMilliSecond = jwtTokenProvider.getRemainMilliSeconds(accessToken);
        refreshTokenRedisRepository.deleteById(username);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken,username,remainMilliSecond));
    }

    public TokenResponseDto regeneration(String refreshToken) {
        refreshToken = resolveToken(refreshToken);
        String username = getCurrentUsername();
        log.info("username : " + username);
        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(username)
            .orElseThrow(NoSuchElementException::new);
        if (refreshToken.equals(redisRefreshToken.getRefreshToken())) {
            return regenerateRefreshToken(refreshToken, username);
        }
        throw new IllegalArgumentException("토큰이 일치하지 않습니다.");
    }

    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ((CustomUserDetails) principal).getUsername();
        /*if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }*/

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth name : "+ authentication.getName());
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return username;*/
    }

    private TokenResponseDto regenerateRefreshToken(String refreshToken, String username) {
        if (isRefreshTokenExpirationTimeLeft(refreshToken)) {
            String accessToken = jwtTokenProvider.generateAccessToken(username);
            return TokenResponseDto.of(accessToken,
                saveRefreshToken(username).getRefreshToken());
        }
        return TokenResponseDto.of(jwtTokenProvider.generateAccessToken(username), refreshToken);
    }

    private boolean isRefreshTokenExpirationTimeLeft(String refreshToken) {
        return jwtTokenProvider.getRemainMilliSeconds(refreshToken)
            < JwtExpirationEnums.REISSUE_EXPIRATION_TIME.getValue();
    }

    public String resolveToken(String accessToken) {
        return accessToken.substring(7);
    }
}
