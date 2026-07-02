package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports embeddable types in order to override {@link Position#value()}.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Fragment {

    /**
     * The initial index of the range to be mapped automatically, inclusive.
     *
     * @return the start offset
     */
    int value();

    /**
     * Enabling the trimming to <code>null</code> of the current fragment, by default is disabled.
     * <p>
     * This flag takes effect only during the mapping of flat-data to java-beans (parsing).
     * <ul>
     *     If set to <code>true</code>, the current fragment value will be <code>null</code> in the following cases:
     *     <li>All underlying properties are <code>null</code></li>
     *     <li>One property with {@link Position#required()} enabled have <code>null</code> as value</li>
     * </ul>
     *
     * @return the nullable flag
     */
    boolean nullable() default false;

    /**
     * Enables the export of default values even if the fragment value is <code>null</code>,
     * by default is disabled.
     * <p>
     * This flag takes effect only during the mapping of java-beans to flat-data (export).
     * <p>
     * If enabled and the fragment value is <code>null</code>, the exported flat-data corresponding to the underlying
     * properties will be {@link Position#defaultValue()} if exists
     *
     * @return the prototype flag
     */
    boolean prototype() default false;

}
