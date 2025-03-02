package io.github.up2jakarta.cii.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Schema {

    /**
     * @return Schema agency.
     */
    String agency();

    /**
     * @return Schema version.
     */
    String version();

    /**
     * @return Schema date.
     */
    String date();

}

