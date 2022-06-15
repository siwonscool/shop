package com.sample.shop.login.service;

import com.sample.shop.login.domain.RefreshToken;
import com.sample.shop.login.dto.request.LoginRequestDto;
import com.sample.shop.login.dto.response.TokenResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    TokenResponseDto login(final LoginRequestDto loginRequestDto, HttpServletRequest request,
        HttpServletResponse response);

    RefreshToken saveRefreshToken(String username);

    boolean logout(TokenResponseDto tokenResponseDto, String username,
        HttpServletResponse response);

    TokenResponseDto regeneration(String refreshToken);

    String getCurrentUsername();

    String resolveToken(String accessToken);
}
