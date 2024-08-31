package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link Repeatable} {@link Checker}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Checkers {

    /**
     * @return the segment checkers list
     */
    Checker[] value();

}
