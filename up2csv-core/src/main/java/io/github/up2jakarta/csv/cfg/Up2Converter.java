package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.lov.TypeAdapter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports any {@link TypeAdapter}.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Up2Converter {

    /**
     * The property-adapter must be managed by {@link io.github.up2jakarta.csv.api.Container}.
     *
     * @return the class of the property-adapter
     */
    Class<? extends TypeAdapter<?>> value();

    /**
     * Returns the qualified name of the property-adapter, by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified property-adapter.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
