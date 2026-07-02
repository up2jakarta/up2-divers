package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.TrimProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Shortcut Annotation for {@link Processor} that trims {@link String} to <code>null</code>.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(value = TrimProcessor.class)
public @interface Up2Trim {

    /**
     * @return The values that look like <code>null</code>}
     */
    String[] value() default {""};

}
