package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.core.CodeListResolver;
import io.github.up2jakarta.csv.misc.CodeListConverter;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} resolver
 * that supports {@link io.github.up2jakarta.csv.extension.CodeList} types.
 *
 * @see CodeListConverter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(CodeListResolver.class)
public @interface Up2CodeList {

}
