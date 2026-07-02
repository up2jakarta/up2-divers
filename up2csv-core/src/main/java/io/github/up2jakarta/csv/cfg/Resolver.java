package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports {@link TypeResolver} used for resolver's shortcut annotations.
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Resolver {

    /**
     * The type-resolver must be managed by {@link io.github.up2jakarta.csv.api.Container}
     *
     * @return the class of type-resolver
     */
    Class<? extends TypeResolver<?, ?>> value();

    /**
     * Returns the qualified name of the type-resolver , by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified type-resolver.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
