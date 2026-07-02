package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeExtension;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports {@link io.github.up2jakarta.lov.TypeAdapter} for third-party types.
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
@Repeatable(Extension.List.class)
public @interface Extension {

    /**
     * The type-extension must be managed by {@link io.github.up2jakarta.csv.api.Container}
     *
     * @return the class of type-extension
     */
    Class<? extends TypeExtension<?, ?>> value();

    /**
     * Returns the qualified name of the type-extension , by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified type-extension.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link Extension}.
     */
    @Documented
    @Retention(RUNTIME)
    @Target(ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the extensions
         */
        Extension[] value();

    }
}
