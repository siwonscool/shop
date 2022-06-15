package com.sample.shop.shared.annotation;

import com.sample.shop.shared.enumeration.MemberType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {

    MemberType type();
}
