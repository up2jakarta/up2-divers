package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.DefaultProcessor;

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
