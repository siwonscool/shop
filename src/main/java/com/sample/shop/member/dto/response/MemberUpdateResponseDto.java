package com.sample.shop.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberUpdateResponseDto {

    @ApiModelProperty(example = "실행결과")
    private boolean result;

    @Builder
    private MemberUpdateResponseDto(boolean result) {
        this.result = result;
    }

    public static MemberUpdateResponseDto of(boolean result) {
        return MemberUpdateResponseDto.builder()
            .result(result)
            .build();
    }
}
