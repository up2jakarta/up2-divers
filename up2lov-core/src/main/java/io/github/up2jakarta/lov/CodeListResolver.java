package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeContext;

/**
 * Up2J {@link TypeAdapter} resolver that supports {@link CodeList}.
 *
 * @param <T> the code-list type
 * @see Support
 */
@FunctionalInterface
public interface CodeListResolver<T extends CodeList<?>> {

    /**
     * Returns the list of values adapter the specified code-list <code>type</code> and <code>args</code>.
     *
     * @param type    the code-list type
     * @param context the code-list context
     * @return the list of values adapter
     * @throws AccessException for some reason the resolver cannot list values from the specified arguments
     */
    TypeAdapter<T> resolve(Class<T> type, TypeContext context) throws BeanException;

}
