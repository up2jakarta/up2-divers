package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.prc.AnonymiseProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Shortcut Annotation for {@link Processor} that anonymise confidential data like IBAN and CB.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(AnonymiseProcessor.class)
public @interface Up2Anonymise {

    /**
     * @return the mask character.
     */
    char value() default '*';

    /**
     * @return the number of characters to ignore from the beginning of sequence.
     */
    int from() default 2;

    /**
     * @return the number of characters to ignore from the ending of sequence.
     */
    int until() default 4;

}
