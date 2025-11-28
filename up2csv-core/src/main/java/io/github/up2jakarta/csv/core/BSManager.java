package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSOperator.BId;
import io.github.up2jakarta.csv.core.BSOperator.Factory;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.lov.core.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

import static io.github.up2jakarta.csv.core.BSAccessor.Mode.RO;
import static io.github.up2jakarta.csv.core.BSAccessor.Mode.WO;
import static io.github.up2jakarta.csv.core.BSOperator.BId.unknown;
import static io.github.up2jakarta.csv.core.ext.Beans.isInnerType;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static java.lang.String.join;
import static java.util.function.Predicate.not;

/**
 * Internal cache manager.
 */
class BSManager<D extends DataType<D>, S extends Segment> {

    private static final BSManager<?, ?> INSTANCE = new BSManager<>();
    private final Cache<Key<D, S>, Value<D, S>> cache = new WKCache<>();

    private BSManager() {
    }

    @SuppressWarnings("unchecked")
    private static <D extends DataType<D>, S extends Segment> BSManager<D, S> get() {
        return (BSManager<D, S>) INSTANCE;
    }

    static <D extends DataType<D>, S extends Segment> MBuilder<D, S> of(Factory<?> f, Class<S> t, DataResolver<D> r) {
        final BSManager<D, S> that = BSManager.get();
        final Key<D, S> key = new Key<>(t, r);
        return new MBuilder<>() {
            @Override
            public <M extends Pod<S, D, Bean<S, D, ?>>> M build(BCreator<D, S, M> c) throws BeanException {
                final Value<D, S> value = that.cache.get(key, (k, v) -> {
                    if (v instanceof RO<D, S> ro) {
                        return ro.complete();
                    }
                    return (v == null) ? new WO<>(that.build(key, f)) : v;
                });
                return c.apply(key, value.beanValue());
            }
        };
    }

    static <D extends DataType<D>, S extends Segment> MBuilder<D, S> of(Pod<S, D, Flat<S, D>> s) throws BeanException {
        final BSManager<D, S> that = BSManager.get();
        return new MBuilder<>() {
            @Override
            public <M extends Pod<S, D, Bean<S, D, ?>>> M build(BCreator<D, S, M> c) throws BeanException {
                final Value<D, S> value = that.cache.get(s.key, (k, v) -> {
                    if (v instanceof RO<D, S> ro) {
                        return ro.complete();
                    }
                    return (v == null) ? new RW<>(s.node) : v;
                });
                return c.apply(s.key, value.beanValue());
            }
        };
    }

    static <D extends DataType<D>, S extends Segment> FBuilder<D, S> ft(Factory<?> f, DataResolver<D> r, Class<S> t) {
        final BSManager<D, S> that = BSManager.get();
        final Key<D, S> key = new Key<>(t, r);
        return new FBuilder<>() {
            @Override
            public <M extends Pod<S, D, Flat<S, D>>> M build(FCreator<D, S, M> c) throws BeanException {
                final Value<D, S> value = that.cache.get(key, (k, v) -> {
                    if (v instanceof WO<D, S> wo) {
                        return wo.complete();
                    }
                    return (v == null) ? new RO<>(that.build(f, key)) : v;
                });
                return c.apply(key, value.flatValue());
            }
        };
    }

    static <D extends DataType<D>, S extends Segment> FBuilder<D, S> ft(Pod<S, D, Bean<S, D, ?>> s) throws BeanException {
        final BSManager<D, S> that = BSManager.get();
        return new FBuilder<>() {
            @Override
            public <M extends Pod<S, D, Flat<S, D>>> M build(FCreator<D, S, M> c) throws BeanException {
                final Value<D, S> value = that.cache.get(s.key, (k, v) -> {
                    if (v instanceof WO<D, S> wo) {
                        return wo.complete();
                    }
                    return (v == null) ? new RW<>(s.node) : v;
                });
                return c.apply(s.key, value.flatValue());
            }
        };
    }

    private Bean<S, D, ?> build(Key<D, S> key, Factory<?> factory) throws BeanException {
        final Class<S> type = key.type;
        final BSContext<D> mc = new BSContext<>(factory, type, WO, key.resolver);
        final List<BSProperty<?, ?, D>> ps = mc.build();
        final Constructor<S> cs = BSContext.from(type, ps);
        if (cs == null) {
            if (type.isRecord()) {
                return new Bean.JR<>(type, factory.validator, mc.context(), ps);
            }
            return new Bean.BM<>(type, factory.validator, mc.context(), ps);
        }
        return new Bean.JB<>(type, factory.validator, mc.context(), ps, cs);
    }

    private Flat<S, D> build(Factory<?> factory, Key<D, S> key) throws BeanException {
        final Class<S> type = key.type;
        final BSContext<D> mc = new BSContext<>(factory, type, RO, key.resolver);
        return new Flat<>(type, factory.validator, mc.context(), mc.build());
    }

    interface MBuilder<D extends DataType<D>, S extends Segment> {
        <M extends Pod<S, D, Bean<S, D, ?>>> M build(BCreator<D, S, M> creator) throws BeanException;
    }

    interface FBuilder<D extends DataType<D>, S extends Segment> {
        <M extends Pod<S, D, Flat<S, D>>> M build(FCreator<D, S, M> creator) throws BeanException;
    }

    interface BCreator<D extends DataType<D>, S extends Segment, M extends Pod<S, D, Bean<S, D, ?>>> {
        M apply(Key<D, S> key, Bean<S, D, ?> node) throws BeanException;
    }

    interface FCreator<D extends DataType<D>, S extends Segment, M extends Pod<S, D, Flat<S, D>>> {
        M apply(Key<D, S> key, Flat<S, D> node) throws BeanException;
    }

    /**
     * Internal Cache Key
     */
    static final class Key<D extends DataType<D>, S extends Segment> implements Comparable<Key<D, S>> {
        private final DataResolver<D> resolver;
        private final Class<S> type;

        private Key(Class<S> type, DataResolver<D> resolver) {
            this.resolver = notNull(resolver, Up2Factory.class, "resolver");
            this.type = notNull(type, Up2Factory.class, "type");
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Key<?, ?> that)) {
                return false;
            }
            return (this.type == that.type) && (this.resolver == that.resolver);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, resolver);
        }

        @Override
        public int compareTo(Key<D, S> that) {
            final Class<S> type = that.type;
            if (this.type == that.type) {
                final DataResolver<D> resolver = that.resolver;
                if (this.resolver == resolver) {
                    return 0;
                }
                return Integer.compare(this.resolver.hashCode(), resolver.hashCode());
            }
            return this.type.getName().compareTo(type.getName());
        }

        @Override
        public String toString() {
            return getTypeName(type);
        }
    }

    /**
     * Internal Cache Value
     */
    static abstract sealed class Value<D extends DataType<D>, S extends Segment> permits RO, WO, RW {
        abstract Flat<S, D> flatValue();

        abstract Bean<S, D, ?> beanValue();

        abstract Class<S> getType();

        @Override
        public final String toString() {
            return getTypeName(this.getType());
        }
    }

    private static final class RO<D extends DataType<D>, S extends Segment> extends Value<D, S> {
        private final Flat<S, D> value;

        private RO(Flat<S, D> value) {
            this.value = value;
        }

        private RW<D, S> complete() throws BeanException {
            return new RW<>(value);
        }

        @Override
        public Flat<S, D> flatValue() {
            return value;
        }

        @Override
        Bean<S, D, ?> beanValue() {
            throw new AccessException(RO.class, "unsupported write operation");
        }

        @Override
        Class<S> getType() {
            return value.type;
        }
    }

    private static final class WO<D extends DataType<D>, S extends Segment> extends Value<D, S> {
        private final Bean<S, D, ?> value;

        private WO(Bean<S, D, ?> value) {
            this.value = value;
        }

        private RW<D, S> complete() throws BeanException {
            return new RW<>(value);
        }

        @Override
        Flat<S, D> flatValue() {
            throw new AccessException(WO.class, "unsupported read operation");
        }

        @Override
        public Bean<S, D, ?> beanValue() {
            return value;
        }

        @Override
        Class<S> getType() {
            return value.type;
        }
    }

    private static final class RW<D extends DataType<D>, S extends Segment> extends Value<D, S> {
        private final Bean<S, D, ?> bValue;
        private final Flat<S, D> fValue;

        private RW(Bean<S, D, ?> value) throws BeanException {
            this.fValue = value.reverse();
            this.bValue = value;
        }

        private RW(Flat<S, D> value) throws BeanException {
            this.bValue = value.reverse();
            this.fValue = value;
        }

        @Override
        public Bean<S, D, ?> beanValue() {
            return bValue;
        }

        @Override
        public Flat<S, D> flatValue() {
            return fValue;
        }

        @Override
        Class<S> getType() {
            return bValue.type;
        }
    }

    /**
     * Internal Segment Processor.
     */
    abstract sealed static class Pod<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> implements MEP permits Up2Mapper, Up2Flatter, Node {
        final int length;
        final int offset;
        final T node;
        private final Key<D, S> key;

        Pod(Key<D, S> key, T node) throws BeanException {
            this.key = key;
            this.node = node;
            this.length = max(node) + 1;
            final Truncated truncated = Overrides.get(node.type, Segment.class, Truncated.class);
            this.offset = (truncated != null) ? truncated.value() : 0;
            if (offset < 0) {
                throw new BeanException(node.type, "@Truncated[value] must be positive");
            }
        }

        private static void check(Stack<Bean<?, ?, ?>> stack, Bean<?, ?, ?> node) throws BeanException {
            final Class<?> t = node.type;
            if (isInnerType(node.type)) {
                final Class<?> et = node.type.getEnclosingClass();
                final Optional<Bean<?, ?, ?>> parent = stack.stream().filter(n -> n.type.equals(et)).findAny();
                if (parent.isEmpty()) {
                    var cn = stack.stream().filter(not(Bean.BC.class::isInstance)).map(n -> getTypeName(n.type)).toList();
                    throw new BeanException(t, "inner class is not allowed outside enclosing segments: " + join(", ", cn));
                }
                if (parent.get() instanceof Bean.BC<?, ?> n) {
                    throw new BeanException(t, "inner class is not allowed inside enclosing segment: " + getTypeName(n.type));
                }
            }
            stack.push(node);
            for (final BSProperty<?, ?, ?> p : node.properties) {
                if (p instanceof BSProperty.PFragment<?, ?, ?> fp) {
                    check(stack, (Bean<?, ?, ?>) fp.node);
                }
            }
        }

        static int max(BSNode<?, ?> node) {
            int max = -1;
            for (final BSProperty<?, ?, ?> p : node.properties) {
                final int offset;
                if (p instanceof BSProperty.PFragment<?, ?, ?> fp) {
                    offset = max(fp.node);
                } else {
                    offset = p.offset;
                }
                max = Math.max(max, offset);
            }
            return max;
        }

        static int min(BSNode<?, ?> node) {
            int min = Integer.MAX_VALUE;
            for (final BSProperty<?, ?, ?> p : node.properties) {
                final int offset;
                if (p instanceof BSProperty.PFragment<?, ?, ?> fp) {
                    offset = max(fp.node);
                } else {
                    offset = p.offset;
                }
                min = Math.min(min, offset);
            }
            return min;
        }

        protected void check(Bean<S, D, ?> node) throws BeanException {
            check(new Stack<>(), node);
        }
    }

    /**
     * Internal Business Processor.
     */
    static abstract sealed class Node<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> extends Pod<S, D, T> permits Mapper, Format {
        final BId<Segment, Object> businessId;
        final boolean hasBusinessId;

        Node(BSAccessor.Mode mode, Key<D, S> key, T node) throws BeanException {
            super(key, node);
            this.businessId = this.id(BusinessId.class, mode).or(() -> unknown(node.type));
            this.hasBusinessId = this.businessId.supports(BSAccessor.Mode.RO);
        }

        <A extends Annotation> Wrapper<BId<Segment, Object>> id(Class<A> type, BSAccessor.Mode mode) throws BeanException {
            final Wrapper<BId<Segment, Object>> result = new Wrapper<>();
            BSBuilder.id(node, type, (fs, pp) -> {
                if (result.isPresent()) {
                    throw new BeanException(node.type, "multiple @" + getTypeName(type) + " are found");
                }
                result.accept(BSProperty.id(mode, fs, pp));
            });
            return result;
        }
    }

    /**
     * Internal Business Flatter.
     */
    static final class Format<S extends Segment, D extends DataType<D>> extends Node<S, D, Flat<S, D>> {
        Format(Key<D, S> key, Flat<S, D> node) throws BeanException {
            super(BSAccessor.Mode.RO, key, node);
        }

        Mapper<S, D> reverse() throws BeanException {
            return BSManager.of(this).build(Mapper::new);
        }
    }

    /**
     * Internal Business Mapper.
     */
    static final class Mapper<S extends Segment, D extends DataType<D>> extends Node<S, D, Bean<S, D, ?>> {
        final BId<Segment, Object> parentId;
        final boolean hasParentId;

        Mapper(Key<D, S> key, Bean<S, D, ?> node) throws BeanException {
            super(BSAccessor.Mode.WO, key, node);
            this.check(node);
            this.parentId = this.id(ParentId.class, BSAccessor.Mode.WO).or(BId::undefined);
            this.hasParentId = parentId.supports(BSAccessor.Mode.RO);
        }

        <R extends IRecord<?>> S map(R record, int offset, boolean validate, BusinessHandler<D> handler) {
            var data = record.getData();
            if (data == null) {
                data = new String[0];
            }
            final S bean = node.parse(handler, offset, data);
            node.update(bean, record);
            if (validate) {
                node.validate(bean, offset, handler);
            }
            return bean;
        }

        public Format<S, D> reverse() throws BeanException {
            return BSManager.ft(this).build(Format::new);
        }
    }
}
