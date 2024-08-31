package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.resolver.NumberResolver;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} resolver that supports non-decimal {@link Number} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(NumberResolver.class)
public @interface Up2Number {

}
