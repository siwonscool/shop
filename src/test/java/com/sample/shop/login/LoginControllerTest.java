package com.sample.shop.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginControllerTest {

    @Test
    @DisplayName("로그인이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 생성되야야 한다.")
    public void login_test() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("로그인을 할때 로그인 기록을 남겨야 한다. 로그인 기록은 ClientIP, id, 로그인 성공 여부")
    public void login_history_test() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("로그아웃이 되어야한다. 성공적으로 처리되면 HttpStatus 200이 나와야 하며 Token값이 삭제(expire상태) 되어야 한다.")
    public void logout_test() {
        // given

        // when

        // then
    }
}