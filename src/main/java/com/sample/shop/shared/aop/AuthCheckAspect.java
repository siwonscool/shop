package com.sample.shop.shared.aop;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.shared.annotation.LoginCheck;
import com.sample.shop.shared.enumeration.MemberType;
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
    private final TokenLoginService tokenLoginService;

    @Before("@annotation(com.sample.shop.shared.annotation.LoginCheck) && @annotation(target)")
    private void loginCheck(LoginCheck target){
        String memberType = String.valueOf(target.type());
        switch (memberType) {
            case "USER":
                userLoginCheck();
                break;
            case "ADMIN":
                adminLoginCheck();
                break;
            default:
                break;
        }
    }

    private void userLoginCheck() {
        String username = tokenLoginService.getCurrentUsername();
        if (username == null){
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED,"No_Login") {};
        }
    }

    private void adminLoginCheck() {
    }

}
