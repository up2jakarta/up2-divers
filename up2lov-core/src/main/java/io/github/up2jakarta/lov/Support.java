package io.github.up2jakarta.lov;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Runtime Annotation for {@link CodeListResolver} that documents the supported parameters and types.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Support {

    /**
     * @return the supported parameters
     */
    Parameter[] value() default {};

    /**
     * @return the unsupported code-list types
     */
    Class<? extends CodeList>[] excludes() default {};

}
