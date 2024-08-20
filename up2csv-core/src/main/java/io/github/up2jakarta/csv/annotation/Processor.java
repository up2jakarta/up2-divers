package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.ConfigurableProcessor;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link ConfigurableProcessor} used for processor's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Processor {

    /**
     * The processor class must be à managed by {@link io.github.up2jakarta.csv.extension.BeanContext}.
     *
     * @return the class of the processor
     */
    Class<? extends ConfigurableProcessor<?>> value();

    /**
     * @return the top-level error type to be skipped
     */
    Class<? extends RuntimeException> skip() default RuntimeException.class;

}
