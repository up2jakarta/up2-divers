package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.NumberResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports non-decimal {@link Number} types.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(NumberResolver.class)
public @interface Up2Number {
}
