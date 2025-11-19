package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Stack;

/**
 * The business resolver of data types.
 *
 * @param <D> the business data type
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 */
public abstract class DataTypeResolver<D extends DataType<D>> {

    private final Class<D> type;

    protected DataTypeResolver(Class<D> type) {
        this.type = type;
    }

    public static <D extends DataType<D>> DataTypeResolver<D> empty(Class<D> type) {
        return new DataTypeResolver<>(type) {
            @Override
            public Optional<D> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) {
                return Optional.empty();
            }
        };
    }

    public static DataTypeResolver<DynamicType> dynamic() {
        return new DataTypeResolver<>(DynamicType.class) {
            @Override
            public Optional<DynamicType> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) {
                final Definition def = field.getAnnotation(Definition.class);
                if (def != null) {
                    return Optional.of(new DynamicType(def.code(), def.value()));
                }
                return Optional.empty();
            }
        };
    }

    /**
     * Returns the optional business data type, if the given field is annotated with.
     *
     * @param stack the stack of segment-types
     * @param path  the path of the property from the root segment
     * @param field the property that is being scanned automatically
     * @return the optional business data type
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
            throw new BeanException(value.getClass(), "class", "invalid business data type");
        }
    }

    public final DataTypeResolver<D> or(D defaultValue) {
        if (defaultValue == null) {
            return this;
        }
        final Optional<D> dv = Optional.of(defaultValue);
        final DataTypeResolver<D> delegate = this;
        return new DataTypeResolver<>(type) {
            @Override
            public Optional<D> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException {
                final Optional<D> value = delegate.get(stack, path, field);
                if (value.isEmpty()) {
                    return dv;
                }
                return value;
            }
        };
    }

}
