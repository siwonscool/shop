package com.sample.shop.config.jwt;

import com.sample.shop.config.security.CustomUserDetailService;
import com.sample.shop.login.domain.repository.LogoutAccessTokenRedisRepository;
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
        // if 무시하고 지나가야하는 url 만나면 return

        FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getToken(request);
        if (accessToken != null) {
            checkLogout(accessToken);
            String username = jwtTokenProvider.getUsername(accessToken);
            if (username != null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                equalsUsernameFromTokenAndUserDetails(userDetails.getUsername(), username);
                validateAccessToken(accessToken, userDetails);
                processSecurity(request, userDetails);
                filterChain.doFilter(request, response);
            }
        }

        // 여기에는 response 응답객체 (에러메세지 http 상태코드)
    }

    //헤더에서 JWT 를 'Bearer' 를 제외하여 가져오고 프론트에서 JWT 를 주지 않는 경우 null 을 반환
    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    private void checkLogout(String accessToken) {
        if (logoutAccessTokenRedisRepository.existsById(accessToken)){
            throw new IllegalArgumentException("이미 로그아웃된 회원입니다.");
        }
    }

    private void equalsUsernameFromTokenAndUserDetails(String userDetailsUser, String tokenUserName) {
        if (!userDetailsUser.equals(tokenUserName)){
            throw new IllegalArgumentException("username 과 토큰이 일치하지 않습니다.");
        }
    }

    private void validateAccessToken(String accessToken, UserDetails userDetails) {
        if (!jwtTokenProvider.validateToken(accessToken,userDetails)){
            throw new IllegalArgumentException("토큰 검증 실패");
        }
    }

    private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
