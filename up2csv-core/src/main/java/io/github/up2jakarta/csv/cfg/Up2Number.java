package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.slv.NumberResolver;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} resolver that supports non-decimal {@link Number} types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(NumberResolver.class)
public @interface Up2Number {

}
