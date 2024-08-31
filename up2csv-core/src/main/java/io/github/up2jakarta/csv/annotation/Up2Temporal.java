package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.resolver.TemporalResolver;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} resolver that supports {@link java.time.temporal.Temporal} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(TemporalResolver.class)
public @interface Up2Temporal {

}

