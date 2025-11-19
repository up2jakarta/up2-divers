package io.github.up2jakarta.lov;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface DuplicatedList {

    /**
     * @return the related duplications
     */
    Duplicated[] value();

}

