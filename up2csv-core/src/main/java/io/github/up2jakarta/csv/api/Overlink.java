package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.BusinessLink;

import java.lang.annotation.Target;

/**
 * Up2J Internal Annotation that allow the override existing {@link io.github.up2jakarta.csv.BusinessLink}
 * defined at property level {@link Overlink#value()}.
 */
@Target({})
public @interface Overlink {

    /**
     * Returns the override of an existing business-link defined at property level
     * and identified by {@link io.github.up2jakarta.csv.BusinessLink#value()}.
     *
     * @return the override of an existing business-link defined at property level.
     */
    BusinessLink value();

    /**
     * Returns the exclusions of underlying links identified by {@link io.github.up2jakarta.csv.BusinessLink#value()}.
     *
     * @return the sub-links of {@link #value()} to be excluded in multi-segment format.
     */
    String[] excludes() default {};

    /**
     * Returns the replacements of underlying-links identified by {@link io.github.up2jakarta.csv.BusinessLink#value()}.
     *
     * @return the sub-links of {@link #value()} to be reidentified in multi-segment format.
     */
    Sublink[] replaces() default {};

}
