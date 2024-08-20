package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.resolver.BooleanResolver;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} resolver that supports {@link Boolean} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(BooleanResolver.class)
public @interface Up2Boolean {

    /**
     * @return the sequence corresponding to {@link Boolean#TRUE}
     */
    String value() default "true";

}

