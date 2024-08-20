package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.processor.DefaultProcessor;

import java.lang.annotation.*;

/**
 * Up2 Shortcut Annotation for {@link Processor} that sets {@link #value()} when the input data is <code>null</code>.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(DefaultProcessor.class)
public @interface Up2Default {

    /**
     * @return the default value
     */
    String value();

}
