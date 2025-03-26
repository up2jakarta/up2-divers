package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.BeanException;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Stack;

/**
 * The business resolver data type.
 *
 * @param <D> the business data type
 * @see io.github.up2jakarta.csv.input.InputError#setType(DataType)
 */
public abstract class DataTypeResolver<D extends DataType<D>> {

    private final Class<D> type;

    protected DataTypeResolver(Class<D> type) {
        this.type = type;
    }

    /**
     * Returns the optional data type, if the given field is annotated with.
     *
     * @param stack the stack of segment-types
     * @param path  the path of the property from the root segment
     * @param field the property that is being scanned automatically
     * @return the optional data type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<D> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException;

    /**
     * Checks the given <code>value</code> type is well assignable from the declared {@link #type}.
     *
     * @param value the data-type value
     * @throws BeanException if {@link ClassCastException}
     */
    public final void check(DataType<?> value) throws BeanException {
        if (value != null && !type.isAssignableFrom(value.getClass())) {
            throw new BeanException(value.getClass(), "class", "invalid data type");
        }
    }

}
