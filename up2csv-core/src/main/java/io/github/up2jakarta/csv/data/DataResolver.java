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
public abstract class DataResolver<D extends DataType<?>> {

    private static final DataResolver<DynamicType> DYNAMIC = new DataResolver<>(DynamicType.class) {
        @Override
        public Optional<DynamicType> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) {
            final Definition def = field.getAnnotation(Definition.class);
            if (def != null) {
                return Optional.of(new DynamicType(def.code(), def.value()));
            }
            return Optional.empty();
        }
    };

    private static final DataResolver<?> EMPTY = new DataResolver<>(DataType.class) {
        @Override
        public Optional<? extends DataType<?>> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) {
            return Optional.empty();
        }
    };

    private final Class<D> type;

    protected DataResolver(Class<D> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public static <D extends DataType<D>> DataResolver<D> empty() {
        return (DataResolver<D>) EMPTY;
    }

    public static DataResolver<DynamicType> dynamic() {
        return DYNAMIC;
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
    public abstract Optional<? extends D> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException;

    /**
     * Checks the given <code>value</code> type is well assignable from the declared {@link #type}.
     *
     * @param value the data-type value
     * @throws BeanException if {@link ClassCastException}
     */
    public final void check(DataType<?> value) throws BeanException {
        if (value != null && !type.isAssignableFrom(value.getClass())) {
            throw new BeanException(value.getClass(), "invalid business data type");
        }
    }

}
