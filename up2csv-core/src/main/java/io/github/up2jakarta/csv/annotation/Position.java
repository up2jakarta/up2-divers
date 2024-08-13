package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports the index of data in {@link io.github.up2jakarta.csv.persistence.InputRow#getColumns()}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Position {

    /**
     * The column offset in the input row that is being mapped automatically.
     *
     * @return the column offset
     */
    int value();

}
