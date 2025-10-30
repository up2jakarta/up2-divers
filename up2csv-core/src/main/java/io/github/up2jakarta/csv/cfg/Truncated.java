package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link io.github.up2jakarta.csv.api.IEvent#getOffset()}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Truncated {

    /**
     * The first column offset in the {@link io.github.up2jakarta.csv.api.IRecord#getColumns()}
     * that is being mapped automatically.
     *
     * @return the first column offset
     */
    int value();

}
