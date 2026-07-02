package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.DecimalResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.RoundingMode;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports decimal {@link Number} types.
 *
 * @see java.math.BigDecimal#setScale(int, RoundingMode)
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(DecimalResolver.class)
public @interface Up2Decimal {

    /**
     * @return the decimal scale
     */
    int value();

    /**
     * @return The rounding mode to apply
     */
    RoundingMode roundingMode() default RoundingMode.HALF_EVEN;

}
