package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.*;
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
    protected final BeanNode<S, D> node;

    private final BusinessGetter parentId;
    private final BusinessGetter businessId;

    Mapper(Class<S> type, BeanContext context, DataTypeResolver<D> resolver) throws BeanException {
        final Truncated truncated = type.getAnnotation(Truncated.class);
        this.offset = (truncated != null) ? truncated.value() : 0;
        this.node = BeanNode.root(type, context, resolver);
        this.parentId = BusinessGetter.find(type, this.toList(), ParentId.class);
        this.businessId = BusinessGetter.find(type, this.toList(), BusinessId.class);
    }

    /**
     * Map input data to java bean depending on annotations like {@link Position} with fail-fast principle.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(IRecord, int, EventHandler)
     * @see EventHandler#failFast(boolean)
     */
    public final S map(final String... columns) throws BeanException {
        return map(failFast(true), offset, columns);
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
    public final <R extends IRecord<?>, V extends IError<R, ?, D>> S map(
            R row, EventHandler<R, ?, D, V> handler
    ) throws BeanException {
        return this.map(row, offset, handler);
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
    public final <R extends IRecord<?>, V extends IError<R, ?, D>> S map(
            EventHandler<R, ?, D, V> handler, String... columns
    ) throws BeanException {
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
     * @param <V>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public final <R extends IRecord<?>, V extends IError<R, ?, D>> S map(
            R row, int offset, EventHandler<R, ?, D, V> handler
    ) throws BeanException {
        if (row == null || row.getColumns() == null) {
            return null;
        }
        requireNonNull(handler, "handler is required");
        final R source = handler.getSource();
        if (source != null && source != row) {
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
     * @param <V>     the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public abstract <R extends IRecord<?>, V extends IError<R, ?, D>> S map(
            EventHandler<R, ?, D, V> handler, int offset, String... columns
    ) throws BeanException;

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
     * @param <V>     the input error type
     */
    protected abstract <R extends IRecord<?>, V extends IError<R, ?, D>> void validate(
            Object bean, int offset, BeanNode<?, D> node, EventHandler<R, ?, D, V> handler
    );

    /**
     * Gets and return the business-id of the given bean.
     *
     * @param bean the segment instance
     * @return the business identifier
     * @throws BeanException if the {@link BusinessId} property is not accessible for read
     */
    protected Object businessId(S bean) throws BeanException {
        return businessId.get(bean, null);
    }

    /**
     * Gets and return the paren-id of the given bean.
     *
     * @param bean       the segment instance
     * @param parentType the parent class
     * @return the parent identifier
     * @throws BeanException if the {@link ParentId} property is not accessible for read
     */
    protected Object parentId(S bean, Class<? extends Segment> parentType) throws BeanException {
        return parentId.get(bean, parentType);
    }

    @Override
    public final List<Property<?, D>> toList() {
        return node.toList();
    }

}
