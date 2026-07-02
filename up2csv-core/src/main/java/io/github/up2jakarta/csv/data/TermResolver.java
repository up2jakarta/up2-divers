package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * The business resolver of term types.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 */
public abstract class TermResolver<D extends ITerm<?>> {

    private static final TermResolver<HeaderType> HEADER = new TermResolver<>(HeaderType.class) {
        @Override
        public Optional<HeaderType> get(List<Class<? extends Segment>> stack, Field[] path, Field field) {
            final Header config = field.getAnnotation(Header.class);
            if (config != null) {
                return Optional.of(new HeaderType(config.code(), config.name()));
            }
            return Optional.empty();
        }

        @Override
        public Optional<HeaderType> get(Optional<Field> field, Class<? extends Segment> type) {
            if (field.isPresent()) {
                final Header config = field.get().getAnnotation(Header.class);
                if (config != null) {
                    return Optional.of(new HeaderType(config.code(), config.name()));
                }
            }
            return Optional.empty();
        }
    };

    private static final TermResolver<?> EMPTY = new TermResolver<>(ITerm.class) {
        @Override
        public Optional<? extends ITerm<?>> get(List<Class<? extends Segment>> stack, Field[] path, Field field) {
            return Optional.empty();
        }

        @Override
        public Optional<? extends ITerm<?>> get(Optional<Field> field, Class<? extends Segment> type) {
            return Optional.empty();
        }
    };

    private final Class<D> type;

    protected TermResolver(Class<D> type) {
        this.type = type;
    }

    /**
     * Returns an empty term-resolver that resolves anything to empty {@link ITerm}
     *
     * @param <D> the business term type
     * @return the empty resolver singleton
     */
    public static <D extends ITerm<D>> TermResolver<D> empty() {
        //noinspection unchecked
        return (TermResolver<D>) EMPTY;
    }

    /**
     * Returns the term-resolver that is able to resolve the {@link Header} annotation
     *
     * @return the header resolver singleton
     */
    public static TermResolver<HeaderType> header() {
        return HEADER;
    }

    /**
     * Returns the optional business term, if the given field is annotated with.
     *
     * @param stack the stack of segment-types, useful for override
     * @param path  the path of the property from the root segment, useful for override
     * @param field the property that is being scanned automatically
     * @return the optional business term
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<? extends D> get(List<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException;

    /**
     * Returns the optional business term, if the given field or type is annotated with.
     *
     * @param field the field annotated with {@link io.github.up2jakarta.csv.BusinessLink} or empty for root business-objects
     * @param type  the segment type
     * @return the optional business term
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<? extends D> get(Optional<Field> field, Class<? extends Segment> type) throws BeanException;

    /**
     * @return the class of the business term
     */
    public final Class<D> getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        return type.hashCode();
    }

}
