package io.github.up2jakarta.test.impl;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface InputType {
    GroupType value();
}
