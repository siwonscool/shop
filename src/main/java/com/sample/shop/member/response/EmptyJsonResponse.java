package com.sample.shop.member.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor
public class EmptyJsonResponse {
    private final String errorMessage;
}
