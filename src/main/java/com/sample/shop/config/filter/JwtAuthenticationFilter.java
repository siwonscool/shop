package com.sample.shop.config.filter;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.config.security.CustomUserDetailService;
import com.sample.shop.login.domain.repository.LogoutAccessTokenRedisRepository;
import com.sample.shop.shared.advice.exception.UsernameFromTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
//OncePerRequestFilter 말고 앞전에 쓴 필터도 있는데 차이점
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getToken(request);
        String username = null;

        if (accessToken != null) {
            try{
                checkLogout(accessToken);
                username = jwtTokenProvider.getUsername(accessToken);
                log.info("현재 토큰의 username : " + username);
            }catch (IllegalArgumentException e){
                log.error("Unable to get JWT token",e);
            }catch (ExpiredJwtException e){
                log.error("JWT Token has expired",e);
            }catch (UsernameFromTokenException e){
                log.error("현재 사용자의 username 을 불러오지 못했습니다.");
                throw new UsernameFromTokenException("username from token exception");
            }

            if (username != null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                equalsUsernameFromToken(userDetails.getUsername(), username);
                validateAccessToken(accessToken, userDetails);
                processSecurity(request, userDetails);
            }
        }else{
            log.warn("JWT token does not begin with Bearer String");
        }
        filterChain.doFilter(request, response);

        /*if (requestUri.equals("/login")) {
            filterChain.doFilter(req, res);
            // 로그인 로직을 여기서 해결??
        }*/
    }

    //헤더에서 JWT 를 'Bearer' 를 제외하여 가져오고 프론트에서 JWT 를 주지 않는 경우 null 을 반환
    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void checkLogout(String accessToken) {
        if (logoutAccessTokenRedisRepository.existsById(accessToken)) {
            throw new IllegalArgumentException("이미 로그아웃된 회원입니다.");
        }
    }

    private void equalsUsernameFromToken(String userDetailsUser, String tokenUserName) {
        if (!userDetailsUser.equals(tokenUserName)) {
            throw new IllegalArgumentException("username 과 토큰이 일치하지 않습니다.");
        }
    }

    private void validateAccessToken(String accessToken, UserDetails userDetails) {
        if (!jwtTokenProvider.validateToken(accessToken, userDetails)) {
            throw new IllegalArgumentException("토큰 검증 실패");
        }
    }

    private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }


}