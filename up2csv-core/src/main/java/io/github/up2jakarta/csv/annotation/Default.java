package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.processor.DefaultValueTransformer;

import java.lang.annotation.*;

/**
 * Up2 Shortcut Annotation for {@link Processor} that sets {@link #value()} when the input data is <code>null</code>.
 *
 * @see DefaultValueTransformer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(DefaultValueTransformer.class)
public @interface Default {

    /**
     * @return the default value
     */
    @Processor.Override(DefaultValueTransformer.class)
    String value();

}
