package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.lov.TypeAdapter;

import java.lang.annotation.*;

/**
 * Up2J Annotation that supports any {@link TypeAdapter}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Up2Converter {

    /**
     * The property-adapter must be managed by {@link BeanContext}.
     *
     * @return the class of the property-adapter
     */
    Class<? extends TypeAdapter<?>> value();

    /**
     * Returns the qualified name of the property-adapter, by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified property-adapter.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
