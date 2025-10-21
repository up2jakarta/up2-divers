package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.Validator;

import java.lang.reflect.Constructor;
import java.util.List;

import static io.github.up2jakarta.csv.core.ext.Beans.getDefaultConstructor;
import static java.util.Objects.requireNonNull;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
public final class Up2Mapper<S extends Segment, D extends DataType<D>> extends BeanValidator<S, D> {

    final BSGetter<S> parentId;
    final BSGetter<S> businessId;

    Up2Mapper(Class<S> type, Node<S, D> node) throws BeanException {
        super(type, node);
        parentId = BSGetter.parentId(type, this.toList());
        businessId = BSGetter.businessId(type, this.toList());
    }

    /**
     * Map input data to java bean depending on annotations like {@link Position} with fail-fast principle.
     *
     * @param columns the input data
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     * @see #map(IRecord, EventHandler)
     * @see FastHandler#of(SeverityType)
     */
    public S map(final String... columns) throws BeanException {
        return map(FastHandler.of(SeverityType.ERROR), offset, columns);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param r   the input data
     * @param h   the error collector, must not be null
     * @param <R> the row type
     * @param <E> the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>, E extends IError<D>> S map(R r, EventHandler<R, D, E> h) throws BeanException {
        return this.map(r, offset, true, h);
    }

    /**
     * Map without validation input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param h   the error collector, must not be null
     * @param r   the input data
     * @param <R> the row type
     * @param <E> the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>, E extends IError<D>> S map(EventHandler<R, D, E> h, String... r) throws BeanException {
        return this.map(h, offset, r);
    }

    /**
     * Map and validate input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param r   the input data
     * @param o   the number of columns reserved {@link Truncated#value()}
     * @param v   validate automatically the bean after parsing
     * @param h   the error collector, must not be null
     * @param <R> the row type
     * @param <E> the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>, E extends IError<D>> S map(R r, int o, boolean v, EventHandler<R, D, E> h) throws BeanException {
        if (r == null || r.getColumns() == null) {
            return null;
        }
        requireNonNull(h, "handler is required");
        if (!(h instanceof FastHandler<?>) && h.row != r) {
            throw new BeanException(EventHandler.class, "source", "does not match with row argument");
        }
        final S segment = map(h, o, r.getColumns());
        if (segment instanceof Recordable<?, ?> wrapper) {
            try {
                //noinspection unchecked
                ((Recordable<?, R>) wrapper).setRecord(r);
            } catch (RuntimeException ex) {
                throw new BeanException(segment.getClass(), "record", ex.getMessage());
            }
        }
        if (v) {
            this.validate(segment, o, h);
        }
        return segment;
    }

    /**
     * Map without validation input data to java bean depending on annotations like {@link Position}.
     * and collect errors in the given collector after full-filling the error properties.
     *
     * @param h   the error collector, must not be null
     * @param o   the number of columns reserved {@link Truncated#value()}
     * @param r   the input data
     * @param <R> the row type
     * @param <E> the error type
     * @return the parsed segment
     * @throws BeanException for any problem configuring and assigning fields of the input to bean properties
     */
    public <R extends IRecord<?>, E extends IError<D>> S map(EventHandler<R, D, E> h, int o, String... r) throws BeanException {
        if (r == null) {
            return null;
        }
        requireNonNull(h, "handler is required");
        return ((Node<S, D>) node).parse(h, o, r);
    }

    public Up2Format<S, D> toFormat() throws BeanException {
        final Up2Format.Node<S, D> copy = new Up2Format.Node<>((Node<S, D>) node);
        return new Up2Format<>(type, copy);
    }

    final static class Node<S extends Segment, D extends DataType<D>> extends BeanValidator.Node<S, D> {

        private final Constructor<S> constructor;

        public Node(Class<S> type, Validator validator, BeanContext context, boolean nil, List<Property<?, D>> ps) throws BeanException {
            super(validator, context, nil, ps);
            this.constructor = getDefaultConstructor(type);
        }

        Node(Class<S> type, Up2Format.Node<S, D> source) throws BeanException {
            super(source);
            this.constructor = getDefaultConstructor(type);
        }

        private <V extends IError<D>> S parse(EventHandler<?, D, V> h, int o, String... d) throws BeanException {
            final S bean = Beans.newInstance(constructor);
            var empty = true;
            for (final Property<?, D> p : this.toList()) {
                if (p instanceof PFProperty<?, ?>) {
                    //noinspection unchecked
                    final PFProperty<Segment, D> fp = (PFProperty<Segment, D>) p;
                    final Segment value = ((Node<?, D>) fp.node).parse(h, o, d);
                    if (value != null) {
                        fp.node.validate(value, o, h);
                        fp.setValue(bean, value);
                        empty = false;
                    }
                } else if (p instanceof PProperty<?, D> pp) {
                    final int index = p.offset;
                    final String data = (index < d.length) ? d[index] : null;
                    final Object value = pp.parse(bean, data, o, h);
                    if (value != null) {
                        empty = false;
                    } else if (nullable && pp.required) {
                        return null;
                    }
                }
            }
            return (nullable && empty) ? null : bean;
        }
    }

}
