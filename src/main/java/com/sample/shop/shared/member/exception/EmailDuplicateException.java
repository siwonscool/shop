package com.sample.shop.shared.member.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@RequiredArgsConstructor
public class EmailDuplicateException {
    private final String errorMessage;
}
