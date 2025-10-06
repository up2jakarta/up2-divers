package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.*;

import java.util.List;

import static io.github.up2jakarta.csv.core.Beans.getValue;

@SuppressWarnings("unchecked")
public abstract class PropertyGetter<S extends Segment> implements BusinessIdentifier<S> {

    private static final PropertyGetter<?> UNDEFINED = new PropertyGetter<>() {
        @Override
        boolean exists() {
            return false;
        }

        @Override
        boolean hasKey() {
            throw new UndefinedException();
        }

        @Override
        <P extends Segment> void checkType(Class<P> type, PropertyGetter<P> other) {
            throw new UndefinedException();
        }

        @Override
        public Object get(Segment bean) {
            throw new UndefinedException();
        }
    };

    private PropertyGetter() {
    }

    static <S extends Segment> BusinessIdentifier<S> of(Property<?, ?>[] path) {
        if (path.length == 1) {
            return b -> getValue(b, path[0].getter);
        }
        return b -> {
            Object bean = b;
            for (Property<?, ?> property : path) {
                bean = getValue(bean, property.getter);
                if (bean == null) {
                    break;
                }
            }
            return bean;
        };
    }

    static <S extends Segment> PropertyGetter<S> parentId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
        final Property<?, ?>[] pid = BeanSupport.uniquePath(type, ParentId.class, properties);
        if (pid == null) {
            return PropertyGetter.undefined();
        }
        return new IdGetter<>(pid[pid.length - 1], of(pid), false);
    }

    static <S extends Segment> PropertyGetter<S> businessId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
        final Property<?, ?>[] bid = BeanSupport.uniquePath(type, BusinessId.class, properties);
        if (bid == null) {
            if (BusinessObject.class.isAssignableFrom(type)) {
                final BusinessIdentifier<S> getter = b -> ((BusinessObject) b).getReference();
                return new IdGetter<>(String.class, false, "reference", getter);
            } else {
                return PropertyGetter.undefined();
            }
        } else {
            return new IdGetter<>(bid[bid.length - 1], of(bid));
        }
    }

    static <S extends Segment> PropertyGetter<S> undefined() {
        return (PropertyGetter<S>) UNDEFINED;
    }

    abstract boolean exists();

    abstract boolean hasKey();

    abstract <P extends Segment> void checkType(Class<P> type, PropertyGetter<P> other) throws BeanException;

    private static class IdGetter<S extends Segment> extends PropertyGetter<S> {

        private final BusinessIdentifier<S> getter;
        private final String locator;
        private final Class<?> type;
        private final boolean key;

        private IdGetter(Class<?> type, boolean key, String locator, BusinessIdentifier<S> getter) {
            this.type = type;
            this.getter = getter;
            this.key = key;
            this.locator = locator;
        }

        private IdGetter(Property<?, ?> p, BusinessIdentifier<S> getter, boolean key) {
            this(p.fieldType, key, p.field.getName(), getter);
        }

        private IdGetter(Property<?, ?> p, BusinessIdentifier<S> getter) {
            this(p.fieldType, p.offset == 0, p.field.getName(), getter);
        }

        @Override
        final boolean exists() {
            return true;
        }

        @Override
        final boolean hasKey() {
            return key;
        }

        @Override
        public final Object get(S bean) throws BeanException {
            return getter.get(bean);
        }

        final <P extends Segment> void checkType(Class<P> type, PropertyGetter<P> other) throws BeanException {
            if (other instanceof PropertyGetter.IdGetter<?> that) {
                if (this.type != that.type) {
                    throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
                }
            }
        }

    }

}
