package com.sample.shop.shared.aop;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.annotation.LoginCheck.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthCheckAspect {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenLoginService tokenLoginService;

    @Before("@annotation(com.sample.shop.shared.annotation.LoginCheck) && @annotation(target)")
    private void loginCheck(LoginCheck target){
        if (MemberType.USER.equals(target.type())){
            userLoginCheck();
        }else if (MemberType.STORE.equals(target.type())){
            storeLoginCheck();
        }else if (MemberType.ADMIN.equals(target.type())){
            adminLoginCheck();
        }
    }

    private void userLoginCheck() {
        String username = tokenLoginService.getCurrentUsername();
        if (username == null){
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED,"No_Login") {};
        }
    }

    private void storeLoginCheck() {
    }

    private void adminLoginCheck() {
    }

}
