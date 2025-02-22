package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Field;
import java.util.Optional;

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
     * @param type  the segment type
     * @param field the property that is being scanned automatically
     * @param path  the path of the property from the root segment
     * @return the optional data type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<D> get(Class<? extends Segment> type, Field field, Field... path) throws BeanException;

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
