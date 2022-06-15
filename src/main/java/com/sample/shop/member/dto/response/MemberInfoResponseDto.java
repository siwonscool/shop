package com.sample.shop.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponseDto {

    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "닉네임")
    private String nickname;
}
