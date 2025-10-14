package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.List;

import static io.github.up2jakarta.csv.core.ext.Path.getOverride;
import static java.util.Objects.requireNonNull;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
@SuppressWarnings("ClassEscapesDefinedScope")
public abstract class Mapper<S extends Segment, D extends DataType<D>> implements Collectable<Property<?, D>> {

    protected final int offset;
    protected final Class<S> type;
    protected final BeanNode<S, D> node;
    protected final PropertyGetter<S> parentId;
    protected final PropertyGetter<S> businessId;

    Mapper(Class<S> type, BeanContext context, DataTypeResolver<D> resolver) throws BeanException {
        this.type = type;
        final Truncated truncated = getOverride(type, Truncated.class);
        offset = (truncated != null) ? truncated.value() : 0;
        node = BeanNode.root(type, context, resolver);
        parentId = PropertyGetter.parentId(type, this.toCollection());
        businessId = PropertyGetter.businessId(type, this.toCollection());
    }

    /**
     * Map input data to java bean depending on annotations like {@link Position} with fail-fast principle.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(IRecord, int, EventHandler)
     * @see FastHandler#of(SeverityType)
     */
    public final S map(final String... columns) throws BeanException {
        return map(FastHandler.of(SeverityType.ERROR), offset, columns);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param row     the input data
     * @param handler the error collector, must not be null
     * @param <R>     the row type
     * @param <E>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends IRecord<?>, E extends IError<D>> S map(R row, EventHandler<R, D, E> handler) throws BeanException {
        return this.map(row, offset, handler);
    }

    /**
     * Map without validation input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param handler the error collector, must not be null
     * @param columns the input data
     * @param <R>     the row type
     * @param <E>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends IRecord<?>, E extends IError<D>> S map(EventHandler<R, D, E> handler, String... columns) throws BeanException {
        return this.map(handler, offset, columns);
    }

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param bean the java bean
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public final String[] unmap(S bean) throws BeanException {
        return this.unmap(bean, offset);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param row     the input data
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the error collector, must not be null
     * @param <R>     the row type
     * @param <E>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends IRecord<?>, E extends IError<D>> S map(R row, int offset, EventHandler<R, D, E> handler) throws BeanException {
        if (row == null || row.getColumns() == null) {
            return null;
        }
        requireNonNull(handler, "handler is required");
        if (!(handler instanceof FastHandler<?>) && handler.row != row) {
            throw new BeanException(EventHandler.class, "source", "does not match with row argument");
        }
        final S segment = map(handler, offset, row.getColumns());
        if (segment instanceof Recordable<?, ?> wrapper) {
            try {
                //noinspection unchecked
                ((Recordable<?, R>) wrapper).setRecord(row);
            } catch (RuntimeException ex) {
                throw new BeanException(segment.getClass(), "record", ex.getMessage());
            }
        }
        this.validate(segment, offset, node, handler);
        return segment;
    }

    /**
     * Map without validation input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param handler the error collector, must not be null
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param columns the input data
     * @param <R>     the row type
     * @param <E>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public abstract <R extends IRecord<?>, E extends IError<D>> S map(EventHandler<R, D, E> handler, int offset, String... columns) throws BeanException;

    /**
     * Flat-Map the given to segment to CSV record within formatting.
     *
     * @param bean   the java bean
     * @param offset the number of columns reserved {@link Truncated#value()}
     * @return the formatted array of strings
     * @throws BeanException for any problem configuring and reading fields of the input to bean properties
     */
    public abstract String[] unmap(S bean, int offset) throws BeanException;

    /**
     * Validates the given bean with the given JSR-303 validation groups and gathering
     * {@link jakarta.validation.ConstraintViolation} in the given handler.
     *
     * @param bean    the bean that is being validated
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param node    the property node
     * @param handler the event handler
     * @param <R>     the input row type
     * @param <E>     the input error type
     */
    protected abstract <R extends IRecord<?>, E extends IError<D>> void validate(
            Object bean, int offset, BeanNode<?, D> node, EventHandler<R, D, E> handler
    );

    /**
     * @return the value of {@link Truncated} if exists, or else <code>0</code>
     */
    public final int getOffset() {
        return offset;
    }

    @Override
    public final List<Property<?, D>> toCollection() {
        return node.toCollection();
    }

}
