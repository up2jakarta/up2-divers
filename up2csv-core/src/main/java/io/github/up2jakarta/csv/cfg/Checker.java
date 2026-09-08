package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeListener;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports {@link TypeListener} for segments checking when scanning beans.
 * <p>
 * Note that check can be disabled by the system property {@link io.github.up2jakarta.csv.api.Container#ENABLE_CHECK}.
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
@Repeatable(Checker.List.class)
public @interface Checker {

    /**
     * The type-listener must be managed by {@link io.github.up2jakarta.csv.api.Container}
     *
     * @return the class of the type-listener
     */
    Class<? extends TypeListener> value();

    /**
     * Returns the qualified name of the type-listener, by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified type-listener.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link Checker}.
     */
    @Documented
    @Retention(RUNTIME)
    @Target(ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the segment checkers list
         */
        Checker[] value();

    }
}
