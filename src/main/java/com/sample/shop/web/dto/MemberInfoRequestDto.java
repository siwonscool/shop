package com.sample.shop.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfoRequestDto {

    private String email;
    private String pw;

}
