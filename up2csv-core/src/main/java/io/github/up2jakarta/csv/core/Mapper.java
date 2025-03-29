package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Listable;

import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.failFast;
import static java.util.Objects.requireNonNull;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
@SuppressWarnings("ClassEscapesDefinedScope")
public abstract class Mapper<S extends Segment, D extends DataType<D>> implements Listable<Property<?, D>> {

    protected final int offset;
    protected final Class<S> type;
    protected final List<Property<?, D>> properties;
    private final ValidationContext validation;

    Mapper(Class<S> type, BeanContext context, DataTypeResolver<D> resolver) throws BeanException {
        final Truncated truncated = type.getAnnotation(Truncated.class);
        this.offset = (truncated != null) ? truncated.value() : 0;
        this.type = type;
        this.validation = ValidationContext.from(type);
        final MapperContext<D> mapperContext = new MapperContext<>(context, type, resolver, validation);
        this.properties = new BeanSupport<>(mapperContext).build(type);
        mapperContext.getChecker().afterSegment();
    }

    /**
     * Map input data to java bean depending on annotations like {@link Position} with fail-fast principle.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(InputSegment, EventHandler)
     * @see EventHandler#failFast()
     */
    public final S map(final String... columns) throws BeanException {
        return map(failFast(), columns);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param row     the input data
     * @param handler the error collector, must not be null
     * @param <R>     the row type
     * @param <V>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends InputSegment<?>, V extends InputError<R, ?, D>> S map(R row, EventHandler<R, ?, D, V> handler) throws BeanException {
        if (row == null || row.getColumns() == null) {
            return null;
        }
        requireNonNull(handler, "handler is required");
        final R source = handler.getSource();
        if (source != null && source != row) {
            throw new BeanException(EventHandler.class, "source", "does not match with row argument");
        }
        final S segment = map(handler, row.getColumns());
        if (segment instanceof Parsed<?, ?> parsed) {
            //noinspection unchecked
            ((Parsed<?, R>) parsed).setRecord(row);
        }
        if (validation.isEnabled()) {
            this.validate(segment, this.properties, validation.getGroups(), handler);
        }
        return segment;
    }

    /**
     * Map without validation input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param handler the error collector, must not be null
     * @param columns the input data
     * @param <R>     the row type
     * @param <V>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public abstract <R extends InputSegment<?>, V extends InputError<R, ?, D>> S map(EventHandler<R, ?, D, V> handler, String... columns) throws BeanException;

    /**
     * Validates the given bean with the given JSR-303 validation groups and gathering
     * {@link jakarta.validation.ConstraintViolation} in the given handler.
     *
     * @param bean      the bean that is being validated
     * @param groups    the validation groups
     * @param collector the event handler
     * @param <R>       the input row type
     * @param <V>       the input error type
     */
    abstract <R extends InputSegment<?>, V extends InputError<R, ?, D>> void validate(Object bean, List<Property<?, D>> properties, Class<?>[] groups, EventHandler<R, ?, D, V> collector);

    @Override
    public final List<Property<?, D>> toList() {
        return this.properties;
    }

}
