package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Collectable;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

abstract class BeanNode<S extends Segment, D extends DataType<D>> implements Collectable<Property<?, D>> {

    private final ValidationContext context;
    private final Constructor<S> constructor;
    private final List<Property<?, D>> properties;

    private BeanNode(Constructor<S> constructor, ValidationContext context, List<Property<?, D>> properties) {
        this.properties = properties;
        this.constructor = constructor;
        this.context = context;
    }

    static <S extends Segment, D extends DataType<D>> BeanNode<S, D> root(
            Class<S> type, BeanContext context, DataTypeResolver<D> resolver
    ) throws BeanException {
        final ValidationContext vc = ValidationContext.from(type);
        final MapperContext<D> mc = new MapperContext<>(context, type, resolver, vc);
        mc.getChecker().afterSegment();
        final Constructor<S> constructor = Beans.getDefaultConstructor(type);
        final List<Property<?, D>> properties = new BeanSupport<>(mc).build(type);
        return new RootContainer<>(constructor, vc, properties);
    }

    static <S extends Segment, D extends DataType<D>> BeanNode<S, D> node(
            Class<S> type, ValidationContext context, boolean nullable, List<Property<?, D>> properties
    ) throws BeanException {
        final Constructor<S> constructor = Beans.getDefaultConstructor(type);
        return new NodeContainer<>(constructor, context, nullable, properties);
    }

    abstract S trim(S bean, boolean empty);

    abstract boolean isNullable();

    private Property<?, D> property(final ConstraintViolation<?> violation) {
        final String path = violation.getPropertyPath().toString();
        final String[] fieldNames = path.split("\\.");
        Property<?, D> property = null;
        List<Property<?, D>> properties = this.properties;
        for (final String fieldName : fieldNames) {
            property = properties.stream().filter(p -> fieldName.equals(p.field.getName())).findFirst().orElse(null);
            if (property instanceof BeanProperty<?, ?> fp) {
                //noinspection ALL
                properties = ((BeanProperty<?, D>) fp).node.properties;
            } else {
                return property;
            }
        }
        return property;
    }

    final int offset() {
        int length = -1;
        int index;
        for (final Property<?, D> p : properties) {
            if (p instanceof BeanProperty<?, ?>) {
                //noinspection unchecked
                index = ((BeanProperty<?, D>) p).node.offset();
            } else {
                index = p.offset;
            }
            if (index > length) {
                length = index;
            }
        }
        return length;
    }

    final void format(String[] result, int offset, Segment bean) throws BeanException {
        for (final Property<?, D> p : properties) {
            if (p instanceof BeanProperty<?, ?> fp) {
                //noinspection unchecked
                final BeanProperty<?, D> fragment = (BeanProperty<?, D>) fp;
                var value = fragment.getValue(bean);
                fragment.node.format(result, offset, value);
            } else {
                //noinspection unchecked
                final PositionProperty<Object, D> pp = (PositionProperty<Object, D>) p;
                var value = pp.getValue(bean);
                result[offset + p.offset] = pp.format(value);
            }
        }
    }

    protected <R extends IRecord<?>, V extends IError<D>> void validate(
            Object bean, int offset, Validator validator, EventHandler<R, D, V> handler
    ) {
        if (context.isEnabled()) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean, context.getGroups());
            for (final ConstraintViolation<?> v : violations) {
                final Property<?, D> p = this.property(v);
                if (p != null) {
                    final Error config = p.field.getAnnotation(Error.class);
                    handler.handleEvent(p.dataType, p.offset + offset, v, config);
                } else {
                    handler.handleEvent(null, -1, v, null);
                }
            }
        }
    }

    final <R extends IRecord<?>, V extends IError<D>> S parse(
            Validator validator, EventHandler<R, D, V> handler, int offset, String... columns
    ) throws BeanException {
        final S bean = this.newInstance();
        var empty = true;
        for (final Property<?, D> p : properties) {
            if (p instanceof BeanProperty<?, ?>) {
                //noinspection unchecked
                final BeanProperty<?, D> fp = (BeanProperty<?, D>) p;
                final Segment fragment = fp.node.parse(validator, handler, offset, columns);
                if (fragment != null) {
                    fp.node.validate(fragment, offset, validator, handler);
                    Beans.setValue(bean, fragment, fp.setter);
                    empty = false;
                }
            } else if (p instanceof PositionProperty<?, D> pp) {
                final int index = p.offset;
                final String value = (index < columns.length) ? columns[index] : null;
                var pv = pp.parse(bean, value, offset, handler);
                if (pv != null) {
                    empty = false;
                } else if (this.isNullable() && pp.required) {
                    return null;
                }
            }
        }
        return this.trim(bean, empty);
    }

    final S newInstance() throws BeanException {
        return Beans.newInstance(constructor);
    }

    @Override
    public final List<Property<?, D>> toCollection() {
        return this.properties;
    }

    private static class NodeContainer<S extends Segment, D extends DataType<D>> extends BeanNode<S, D> {

        private final boolean nullable;

        NodeContainer(Constructor<S> constructor, ValidationContext context, boolean nullable, List<Property<?, D>> ps) {
            super(constructor, context, ps);
            this.nullable = nullable;
        }

        @Override
        S trim(S bean, boolean empty) {
            if (nullable && empty) {
                return null;
            }
            return bean;
        }

        @Override
        boolean isNullable() {
            return true;
        }
    }

    private static class RootContainer<S extends Segment, D extends DataType<D>> extends BeanNode<S, D> {

        RootContainer(Constructor<S> constructor, ValidationContext context, List<Property<?, D>> properties) {
            super(constructor, context, properties);
        }

        @Override
        S trim(S bean, boolean empty) {
            return bean;
        }

        @Override
        boolean isNullable() {
            return false;
        }
    }

}
