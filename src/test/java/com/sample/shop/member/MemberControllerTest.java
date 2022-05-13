package com.sample.shop.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberControllerTest {

    @Test
    @DisplayName("회원가입을 처리한다. 성공적으로 처리되면 HttpStatus 201이 나와야 하며 member의 id값이 null이 아니여야 한며 member의 상태는 대기중(READY) 이여야 한다.")
    public void member_join_test() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("회원가입후 가입대기중인 회원을 대상으로 회원상태를 활성(ACTIVATE) 상태로 바뀌어야 한다.")
    public void member_certification_test() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("회원 가입시에 email 중복 체크 기능이 있어야 한다. 중복일경우 Duplicate Exception이 발생하며 API Response 에 그 내용이 기술되어야 한다. (이유, 오류 메시지)")
    public void member_email_check_test() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("등록된 회원에 한하여 회원 탈퇴가 가능해야 한다. 탈퇴가 되면 삭제가 아닌 상태를 (WITHDRAWAL)로 변경해야 한다.")
    public void member_withdrawal_test() {
        // given

        // when

        // then
    }
}