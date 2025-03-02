package io.github.up2jakarta.cii.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Documented {

    /**
     * @return Code list name.
     */
    String value();

    /**
     * @return Code list agency.
     */
    Agency agency();

    /**
     * @return Code list version.
     */
    String version();

}

