package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.InputProcessor;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link InputProcessor} used for processor's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Processor {

    /**
     * The processor class must be managed by {@link BeanContext}.
     *
     * @return the class of the processor
     */
    Class<? extends InputProcessor<?>> value();

    /**
     * @return the top-level error type to be skipped
     */
    Class<? extends RuntimeException> skip() default RuntimeException.class;

}
