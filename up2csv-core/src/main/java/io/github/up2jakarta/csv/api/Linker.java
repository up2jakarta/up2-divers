package io.github.up2jakarta.csv.api;

import java.lang.annotation.Target;

/**
 * Up2J Internal {@link ILinker} bean definition for business-object links.
 *
 * @see io.github.up2jakarta.csv.BusinessLink
 */
@Target({})
public @interface Linker {

    /**
     * The relationship linker must be managed by {@link Container}.
     *
     * @return the class of the linker
     */
    Class<? extends ILinker<?, ?>> value();

    /**
     * Returns the qualified name of the relationship linker, by default is <code>null</code>.
     * <p>
     * Useful when the {@link Container} contains many beans of the specified relationship linker.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
