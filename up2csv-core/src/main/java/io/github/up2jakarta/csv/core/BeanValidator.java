package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

import static io.github.up2jakarta.csv.core.BeanScanner.reverse;
import static io.github.up2jakarta.csv.core.ext.Path.getOverride;

/**
 * Map and validate input data to a configurable bean that supports only {@link String} type.
 *
 * @param <S> the segment type
 */
abstract class BeanValidator<S extends Segment, D extends DataType<D>> {

    protected final int length;
    protected final int offset;
    protected final Class<S> type;

    final Node<S, D> node;

    BeanValidator(Class<S> type, Node<S, D> node) throws BeanException {
        this.type = type;
        this.node = node;
        this.length = node.offset() + 1;
        final Truncated truncated = getOverride(type, Truncated.class);
        this.offset = (truncated != null) ? truncated.value() : 0;
        if (offset < 0) {
            throw new BeanException(type, "@Truncated[value] must be positive");
        }
    }

    /**
     * Validates the given bean with the given JSR-303 validation groups and gathering
     * {@link jakarta.validation.ConstraintViolation} in the given handler.
     *
     * @param bean    the bean that is being validated
     * @param offset  the number of columns reserved {@link Truncated#value()}
     * @param handler the event handler
     * @param <R>     the input row type
     * @param <E>     the input error type
     */
    public final <R extends IRecord<?>, E extends IError<D>> void validate(Object bean, int offset, EventHandler<R, D, E> handler) {
        node.validate(bean, offset, handler);
    }

    final List<Property<?, D>> toList() {
        return node.toList();
    }

    abstract static class Node<S extends Segment, D extends DataType<D>> {

        final boolean nullable;
        private final List<Property<?, D>> properties;
        private final Validator validator;
        private final BeanContext context;

        Node(Validator validator, BeanContext context, boolean nullable, List<Property<?, D>> properties) {
            this.properties = properties;
            this.validator = validator;
            this.context = context;
            this.nullable = nullable;
        }

        Node(Node<S, D> source) throws BeanException {
            this.context = source.context;
            this.nullable = source.nullable;
            this.validator = source.validator;
            this.properties = reverse(source.properties);
        }

        private Property<?, D> property(final ConstraintViolation<?> violation) {
            final String path = violation.getPropertyPath().toString();
            final String[] fieldNames = path.split("\\.");
            Property<?, D> property = null;
            List<Property<?, D>> properties = this.properties;
            for (final String fieldName : fieldNames) {
                property = properties.stream().filter(p -> fieldName.equals(p.getName())).findFirst().orElse(null);
                if (property instanceof PFProperty<?, ?> fp) {
                    //noinspection ALL
                    properties = ((PFProperty<?, D>) fp).node.properties;
                } else {
                    return property;
                }
            }
            return property;
        }

        private int offset() {
            int length = -1;
            int index;
            for (final Property<?, D> p : properties) {
                if (p instanceof PFProperty<?, ?>) {
                    //noinspection unchecked
                    index = ((PFProperty<?, D>) p).node.offset();
                } else {
                    index = p.offset;
                }
                if (index > length) {
                    length = index;
                }
            }
            return length;
        }

        <R extends IRecord<?>, V extends IError<D>> void validate(Object b, int o, EventHandler<R, D, V> h) {
            if (context.isEnabled()) {
                final Set<ConstraintViolation<Object>> violations = validator.validate(b, context.getGroups());
                for (final ConstraintViolation<?> cv : violations) {
                    final Property<?, D> p = this.property(cv);
                    if (p != null) {
                        h.handleEvent(p.dataType, p.offset + o, cv, p.error);
                    } else {
                        h.handleEvent(null, 0, cv, null);
                    }
                }
            }
        }

        final List<Property<?, D>> toList() {
            return this.properties;
        }
    }

}
