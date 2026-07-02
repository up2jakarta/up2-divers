package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.InputProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports {@link InputProcessor} used for processor's shortcut annotations.
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface Processor {

    /**
     * The processor must be managed by {@link io.github.up2jakarta.csv.api.Container}.
     *
     * @return the class of the processor
     */
    Class<? extends InputProcessor<?>> value();

    /**
     * Returns the qualified name of the processor, by default is <code>null</code>.
     * <p>
     * Useful when the {@link io.github.up2jakarta.csv.api.Container} contains many beans of the specified processor.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

    /**
     * @return the top-level exception to be skipped
     */
    Class<? extends RuntimeException> skip() default RuntimeException.class;

}
