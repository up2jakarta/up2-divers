package io.github.up2jakarta.csv.cfg;

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

    /**
     * Enabling the trim processing of embeddable fragments, by default is disabled.
     * <p>
     * If <code>true</code> and all properties are <code>null</code>
     * then the fragment value will be <code>null</code> instead of empty one.
     * <p>
     * If <code>true</code> and one property with {@link Position#required()} enabled is <code>null</code>
     * then the fragment value will be <code>null</code>.
     *
     * @return the nullable flag
     */
    boolean nullable() default false;

}
