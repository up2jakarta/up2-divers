package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link Repeatable} {@link Processor}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Processors {

    /**
     * @return the processors
     */
    Processor[] value();

}
