package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.TemporalAmountResolver;

import java.lang.annotation.*;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link java.time.temporal.TemporalAmount} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(TemporalAmountResolver.class)
public @interface Up2TemporalAmount {
}
