package com.sample.shop.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor
public class EmptyJsonResponseDto {
    private final String errorMessage;
}
