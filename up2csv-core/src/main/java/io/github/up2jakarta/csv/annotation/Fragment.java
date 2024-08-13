package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports embeddable types in order to override {@link Position#value()}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Fragment {

    /**
     * The initial index of the range to be mapped automatically, inclusive.
     *
     * @return the start offset
     */
    int value();

}
