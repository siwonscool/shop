package com.sample.shop.post.domain.enumeration;

import lombok.Getter;

public enum TradeStatus {
    SALE("판매중"),
    RESERVED("예약중"),
    SOLD_OUT("판매완료");

    @Getter
    private String status;

    TradeStatus(String status) {
        this.status = status;
    }
}
