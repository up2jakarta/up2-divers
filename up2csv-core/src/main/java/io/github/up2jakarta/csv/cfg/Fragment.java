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
     * <ul>
     *     If set to <code>true</code> during the mapping, the fragment value will be <code>null</code> when one of the following assertions is true
     *     <li>all properties are <code>null</code></li>
     *     <li>one property with {@link Position#required()} enabled is <code>null</code></li>
     * </ul>
     *
     * <ul>
     *     During the formatting, the data corresponding to the underlying properties will be <code>null</code> if disabled</code> or else
     *     <li>the default value configured by {@link Position#defaultValue()} always takes precedence</li>
     *     <li>the default value provided by the prototype when {@link #defaultValues()} is enabled unless {@link Position#defaultValue()}</li>
     * </ul>
     *
     * @return the nullable flag
     */
    boolean nullable() default false;

    /**
     * Enables the default values initialized by the bean itself after constructed.
     * <p>
     * The related processing is activated only if {@link #nullable()} enabled
     *
     * @return the processing of java default, aka primitive or initial values
     */
    boolean defaultValues() default false;

}
