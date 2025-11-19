package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.lov.core.BeanContext;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link TypeListener} for segment checking.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Repeatable(Checker.List.class)
public @interface Checker {

    /**
     * The type-listener must be managed by {@link BeanContext}
     *
     * @return the class of the type-listener
     */
    Class<? extends TypeListener> value();

    /**
     * Returns the qualified name of the type-listener, by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified type-listener.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * Up2 Annotation that supports {@link Repeatable} {@link Checker}.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the segment checkers list
         */
        Checker[] value();

    }
}
