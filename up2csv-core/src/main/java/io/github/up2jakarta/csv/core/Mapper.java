package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Truncated;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

import static io.github.up2jakarta.csv.core.Beans.getTypeArguments;
import static io.github.up2jakarta.csv.core.EventHandler.failFast;
import static java.util.Objects.requireNonNull;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
public abstract class Mapper<S extends Segment> {

    final static Logger LOGGER = LoggerFactory.getLogger(Mapper.class);

    protected final int offset;
    protected final Class<S> type;
    private final ValidationContext validation;

    Mapper(Class<S> type) throws BeanException {
        final Truncated truncated = type.getAnnotation(Truncated.class);
        this.offset = (truncated != null) ? truncated.value() : 0;
        this.type = type;
        this.validation = ValidationContext.of(type);
    }

    /**
     * Map input data to java bean depending on annotations like {@link Position} with fail-fast principle.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(InputRow, EventHandler)
     * @see EventHandler#failFast()
     */
    public final S map(final String... columns) throws BeanException {
        return map(failFast(), columns);
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
    public abstract <R extends InputRow, V extends InputError<R, ?>> S map(EventHandler<R, ?, V> handler, String... columns) throws BeanException;

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
    @SuppressWarnings("unchecked")
    public final <R extends InputRow, V extends InputError<R, ?>> S map(R row, EventHandler<R, ?, V> handler) throws BeanException {
        if (row == null || row.getColumns() == null) {
            return null;
        }
        requireNonNull(handler, "handler is required");
        final R source = handler.getSource();
        if (source != null && source != row) {
            throw new BeanException(EventHandler.class, "source", "does not match with row argument");
        }
        final S segment = map(handler, row.getColumns());
        if (segment instanceof Parsed<?> parsed) {
            final Class<? extends Segment> segmentType = segment.getClass();
            final Type[] arguments = getTypeArguments(segmentType, Parsed.class);
            if (arguments.length == 0 || row.getClass() != arguments[0]) {
                throw new BeanException(segment.getClass(), "must implements Parsed<" + row.getClass().getSimpleName() + ">");
            }
            //noinspection unchecked
            ((Parsed<R>) parsed).setRow(row);
        }
        if (validation.isEnabled()) {
            this.validate(segment, validation.getGroups(), handler);
        }
        return segment;
    }

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
    abstract <R extends InputRow, V extends InputError<R, ?>> void validate(Object bean, Class<?>[] groups, EventHandler<R, ?, V> collector);

}
