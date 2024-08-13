package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link io.github.up2jakarta.csv.persistence.InputError#setOffset(Integer)}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Truncated {

    /**
     * The first column offset in the {@link io.github.up2jakarta.csv.persistence.InputRow#getColumns()}
     * that is being mapped automatically.
     *
     * @return the first column offset
     */
    int value();

}
