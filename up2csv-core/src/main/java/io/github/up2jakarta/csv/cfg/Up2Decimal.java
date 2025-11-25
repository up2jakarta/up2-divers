package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.DecimalResolver;

import java.lang.annotation.*;
import java.math.RoundingMode;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports decimal {@link Number} types.
 *
 * @see java.math.BigDecimal#setScale(int, RoundingMode)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
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
