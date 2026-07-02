package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.TokenProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Shortcut Annotation for {@link Processor} that clean up XML <code>xs:token</code>.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(TokenProcessor.class)
public @interface Up2Token {

    /**
     * @return The values that look like <code>null</code>}
     */
    String[] value() default {""};

}
