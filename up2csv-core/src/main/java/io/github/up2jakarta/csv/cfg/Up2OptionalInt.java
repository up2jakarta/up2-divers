package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.OptionalIntResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link java.util.OptionalInt} type.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(OptionalIntResolver.class)
public @interface Up2OptionalInt {
}
