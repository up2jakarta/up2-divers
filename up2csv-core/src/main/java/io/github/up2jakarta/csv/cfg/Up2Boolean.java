package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.BooleanResolver;

import java.lang.annotation.*;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link Boolean} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
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
