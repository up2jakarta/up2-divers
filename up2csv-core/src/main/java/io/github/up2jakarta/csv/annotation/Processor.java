package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link ConfigurableTransformer} or shortcut annotations for processors.
 * Also supports {@link Processor#arguments()} overriding.
 *
 * @see Trim
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Repeatable(Processors.class)
public @interface Processor {

    /**
     * The processor class must be à managed by {@link io.github.up2jakarta.csv.extension.BeanContext}.
     *
     * @return the class of the processor
     */
    Class<? extends ConfigurableTransformer> value();

    /**
     * @return The processor arguments
     */
    String[] arguments() default {};

    /**
     * Up2 Annotation that allows shortcut annotations for processors to override {@link Processor#arguments()}.
     *
     * @see Trim
     * @see Default
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface Override {
        /**
         * @return the processor identified by its transformer class.
         */
        Class<? extends ConfigurableTransformer> value();
    }

}
