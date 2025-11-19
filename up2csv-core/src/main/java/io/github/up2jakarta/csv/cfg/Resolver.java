package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.lov.core.BeanContext;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link TypeResolver} used for resolver's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Resolver {

    /**
     * The type-resolver must be managed by {@link BeanContext}
     *
     * @return the class of type-resolver
     */
    Class<? extends TypeResolver<?>> value();

    /**
     * Returns the qualified name of the type-resolver , by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified type-resolver.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
