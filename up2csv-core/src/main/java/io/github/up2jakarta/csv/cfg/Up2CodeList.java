package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.CodeListResolver;
import io.github.up2jakarta.lov.CodeListConverter;
import io.github.up2jakarta.lov.CodeListProvider;
import io.github.up2jakarta.lov.DefaultProvider;
import io.github.up2jakarta.lov.core.BeanContext;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.lov.TypeAdapter} resolver
 * that supports {@link io.github.up2jakarta.lov.CodeList} types.
 *
 * @see CodeListConverter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(CodeListResolver.class)
public @interface Up2CodeList {

    /**
     * The provider must be managed by {@link BeanContext}.
     *
     * @return the code-list provider.
     */
    Class<? extends CodeListProvider<?>> value() default DefaultProvider.class;

    /**
     * Returns the qualified name of the provider, by default is <code>null</code>.
     * <p>
     * Useful when the {@link BeanContext} contains many beans of the specified provider.
     *
     * @return the qualified name
     * @see jakarta.inject.Named
     */
    String name() default "";

}
