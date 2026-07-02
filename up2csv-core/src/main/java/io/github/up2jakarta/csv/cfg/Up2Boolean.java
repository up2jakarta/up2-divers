package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.BooleanResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link Boolean} types.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(BooleanResolver.class)
public @interface Up2Boolean {

    /**
     * @return the sequence corresponding to {@link Boolean#TRUE}
     */
    String trueValue() default "true";

    /**
     * @return the sequence corresponding to {@link Boolean#FALSE}
     */
    String falseValue() default "false";

}
