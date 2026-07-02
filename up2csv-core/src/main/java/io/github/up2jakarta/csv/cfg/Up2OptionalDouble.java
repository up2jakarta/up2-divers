package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.OptionalDoubleResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.RoundingMode;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link java.util.OptionalDouble} type.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(OptionalDoubleResolver.class)
public @interface Up2OptionalDouble {

    /**
     * @return the decimal scale
     */
    int value();

    /**
     * @return The rounding mode to apply
     */
    RoundingMode roundingMode() default RoundingMode.HALF_EVEN;

}
