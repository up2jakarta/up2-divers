package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.resolver.TemporalAmountResolver;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} resolver that supports {@link java.time.temporal.TemporalAmount} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(TemporalAmountResolver.class)
public @interface Up2TemporalAmount {

}

