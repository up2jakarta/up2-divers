package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.data.*;

import java.util.List;

@SuppressWarnings("unchecked")
abstract class BSGetter<S extends Segment> implements BusinessIdentifier<S> {

    private static final BSGetter<?> UNDEFINED = new BSGetter<>() {
        @Override
        boolean exists() {
            return false;
        }

        @Override
        <P extends Segment> void checkType(Class<P> type, BSGetter<P> other) {
            throw new UndefinedException();
        }

        @Override
        public Object get(Segment bean) {
            throw new UndefinedException();
        }
    };

    private BSGetter() {
    }

    static <S extends Segment> BSGetter<S> parentId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
        final Property<?, ?>[] pid = BeanScanner.uniquePath(type, ParentId.class, properties);
        if (pid == null) {
            return BSGetter.undefined();
        }
        return new KeyGetter<>(pid[pid.length - 1], Property.id(pid));
    }

    static <S extends Segment> BSGetter<S> businessId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
        final Property<?, ?>[] bid = BeanScanner.uniquePath(type, BusinessId.class, properties);
        if (bid == null) {
            if (Referencable.class.isAssignableFrom(type)) {
                final BusinessIdentifier<S> getter = b -> ((Referencable) b).getReference();
                return new KeyGetter<>(String.class, "reference", getter);
            } else {
                return BSGetter.undefined();
            }
        } else {
            return new KeyGetter<>(bid[bid.length - 1], Property.id(bid));
        }
    }

    static <S extends Segment> BSGetter<S> undefined() {
        return (BSGetter<S>) UNDEFINED;
    }

    abstract boolean exists();

    abstract <P extends Segment> void checkType(Class<P> type, BSGetter<P> other) throws BeanException;

    private static class KeyGetter<S extends Segment> extends BSGetter<S> {
        private final BusinessIdentifier<S> getter;
        private final String locator;
        private final Class<?> type;

        private KeyGetter(Class<?> type, String locator, BusinessIdentifier<S> getter) {
            this.type = type;
            this.getter = getter;
            this.locator = locator;
        }

        private KeyGetter(Property<?, ?> p, BusinessIdentifier<S> getter) {
            this(p.getType(), p.getName(), getter);
        }

        @Override
        final boolean exists() {
            return true;
        }

        @Override
        public final Object get(S bean) throws BeanException {
            return getter.get(bean);
        }

        final <P extends Segment> void checkType(Class<P> type, BSGetter<P> other) throws BeanException {
            if (other instanceof BSGetter.KeyGetter<?> that) {
                if (this.type != that.type) {
                    throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
                }
            }
        }
    }

    private static final class UndefinedException extends RuntimeException {
    }

}
