package com.sample.shop.member.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoRequestDto {

    private String email;
    @Setter
    private String password;
    private String nickname;
}
