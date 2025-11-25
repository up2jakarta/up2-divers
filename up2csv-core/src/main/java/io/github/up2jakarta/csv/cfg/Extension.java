package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.lov.core.BeanContext;

import java.lang.annotation.*;

/**
 * Up2J Annotation that supports {@link io.github.up2jakarta.lov.TypeAdapter} for third-party types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Repeatable(Extension.List.class)
public @interface Extension {

    /**
     * The type-extension must be managed by {@link BeanContext}
     *
     * @return the class of type-extension
     */
    Class<? extends TypeExtension<?, ?>> value();

    /**
     * Returns the qualified name of the type-extension , by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified type-extension.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link Extension}.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the extensions
         */
        Extension[] value();

    }
}
