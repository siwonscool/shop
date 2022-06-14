package com.sample.shop.shared.enumeration;

public enum MemberType {
    USER, ADMIN;

    public static MemberType getEnumLevel(String level) {
        MemberType userLevel = Enum.valueOf(MemberType.class, level);
        return userLevel;
    }
}
