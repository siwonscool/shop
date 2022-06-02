package com.sample.shop.shared.aspect;

import com.sample.shop.config.jwt.JwtTokenProvider;
import com.sample.shop.login.domain.repository.RefreshTokenRedisRepository;
import com.sample.shop.login.service.TokenLoginService;
import com.sample.shop.shared.annotation.LoginCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckAspect {

    @Before("@annotation(com.sample.shop.shared.annotation.LoginCheck) && @annotation(loginCheck)")
    public void loginAuthorityCheck(LoginCheck loginCheck){
        String userType = loginCheck.type().toString();

        switch (userType){
            case "ROLE_USER" :
                break;
            case "ROLE_ADMIN" :
                break;
        }
    }

}
