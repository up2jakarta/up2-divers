package io.github.up2jakarta.lov;

import java.lang.annotation.Target;

/**
 * Up2J Internal Parameter Definition for {@link Support}.
 */
@Target({})
public @interface Parameter {

    /**
     * @return the parameter name.
     */
    String value();

    /**
     * @return the default value
     */
    String defaultValue() default "";

    /**
     * @return the required flag.
     */
    boolean required() default true;
}
