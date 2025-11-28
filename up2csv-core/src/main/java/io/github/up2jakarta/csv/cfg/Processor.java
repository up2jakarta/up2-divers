package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.core.BeanContext;

import java.lang.annotation.*;

/**
 * Up2J Annotation that supports {@link InputProcessor} used for processor's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Processor {

    /**
     * The processor must be managed by {@link BeanContext}.
     *
     * @return the class of the processor
     */
    Class<? extends InputProcessor<?>> value();

    /**
     * Returns the qualified name of the processor, by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified processor.
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
