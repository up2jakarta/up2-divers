package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.TrimProcessor;

import java.lang.annotation.*;

/**
 * Up2J Shortcut Annotation for {@link Processor} that trims {@link String} to <code>null</code>.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = TrimProcessor.class)
public @interface Up2Trim {

    /**
     * @return The values that look like <code>null</code>}
     */
    String[] value() default {""};

}
