package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.processor.TrimTransformer;

import java.lang.annotation.*;

/**
 * Up2 Shortcut Annotation for {@link Processor} that trims {@link String} to <code>null</code>.
 *
 * @see TrimTransformer
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = TrimTransformer.class)
public @interface Trim {

    /**
     * @return The values that look like <code>null</code>, overriding {@link Processor#arguments()}
     */
    @Processor.Override(TrimTransformer.class)
    String[] value();

}
