package io.github.up2jakarta.csv.api;

import java.lang.annotation.Target;

/**
 * Up2J Internal Annotation that allow the replacement of {@link io.github.up2jakarta.csv.BusinessLink#value()}
 * with new one {@link #with()}
 *
 * @see io.github.up2jakarta.csv.api.Overlink#replaces()
 */
@Target({})
public @interface Sublink {

    /**
     * The existing code of {@link io.github.up2jakarta.csv.BusinessLink} defined at property level.
     * <p>
     * Note that this code must matches the {@link IType#getCode()}.
     *
     * @return the existing code to be replaced.
     */
    String value();

    /**
     * The new code of {@link io.github.up2jakarta.csv.BusinessLink} to be used by the engine.
     * <p>
     * Note that this code must matches the {@link IType#getCode()}.
     *
     * @return the new code to be used.
     * @see io.github.up2jakarta.csv.BusinessLink#value()
     */
    String with();

}
