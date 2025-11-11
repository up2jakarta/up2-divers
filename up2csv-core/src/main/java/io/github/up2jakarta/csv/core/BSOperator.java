package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSNode.BFNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode;
import io.github.up2jakarta.csv.core.BSOperator.Computer;
import io.github.up2jakarta.csv.core.BSProperty.FProperty;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.PNAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PProperty;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.csv.slv.CodeListResolver;
import io.github.up2jakarta.xml.api.Wrapper;
import jakarta.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.AccessMode.RO;
import static io.github.up2jakarta.csv.core.AccessMode.WO;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.*;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor.PMProperty;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor.PSProperty;
import static io.github.up2jakarta.csv.core.BSOperator.Computer.BSFormat;
import static io.github.up2jakarta.csv.core.BSOperator.Computer.BSMapper;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.core.ext.PPath.getOverride;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Internal business-mapping implementation for aggregation and segregation processing.
 */
abstract sealed class BSOperator<B extends DataType<B>, I extends IType<B, I>, P extends Computer<Segment, B, ?>, S extends Computer<Segment, B, ?>> permits BusinessExporter, BusinessImporter {
    protected final I root;
    protected final int offset;
    protected final ModeType mode;

    final List<I> nodes;
    final Up2Factory<B> factory;
    final Map<IType<B, I>, P> mappers;
    final Map<IType<B, I>, Set<I>> joins;
    final BAccessor<Segment, Object> bid;

    BSOperator(Up2Factory<B> factory, Class<?> type, ModeType mode, I root, I[] nodes) throws BeanException {
        requireNonNull(factory, "factory is required");
        requireNonNull(root, "root is required");
        this.nodes = List.of(nodes);
        CodeListResolver.checkUnique(type, this.nodes);
        if (!type.equals(root.getClassType())) {
            throw new BeanException(type, "Invalid business typing");
        }
        this.root = root;
        this.mode = mode;
        this.factory = factory;
        final Map<I, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(new Stack<>(), root, joins::put);
        this.joins = unmodifiableMap(joins);
        final P rootMapper = mappers.get(root);
        this.offset = offset(rootMapper, mode);
        this.bid = rootMapper.businessId;
    }

    BSOperator(BSOperator<B, I, S, P> source) throws BeanException {
        this.bid = source.bid;
        this.root = source.root;
        this.mode = source.mode;
        this.joins = source.joins;
        this.nodes = source.nodes;
        this.offset = source.offset;
        this.factory = source.factory;
        this.mappers = this.joins(root, source.mappers);
    }

    private static int offset(Processor<?, ?, ?> mapper, ModeType mode) throws BeanException {
        final int min = mode.getBeanIdIndex();
        if (mapper.offset != 0) {
            if (mapper.offset < min) {
                throw new BeanException(mapper.node.type, "@Truncated[value] must be greater or equals to " + min);
            }
            return mapper.offset;
        }
        return min;
    }

    private Map<IType<B, I>, P> joins(I rn, Map<IType<B, I>, S> source) throws BeanException {
        final Map<IType<B, I>, P> result = new LinkedHashMap<>();
        result.put(rn, this.build(factory, rn, source.get(rn)));
        for (final I node : joins.getOrDefault(rn, Set.of())) {
            final Map<IType<B, I>, P> mappers = this.joins(node, source);
            result.putAll(mappers);
        }
        return unmodifiableMap(result);
    }

    private Map<IType<B, I>, P> joins(Stack<I> cp, I rn, BiConsumer<I, Set<I>> cb) throws BeanException {
        final Map<IType<B, I>, P> result = new LinkedHashMap<>();
        final P pm = this.build(factory, rn, null);
        cp.push(rn);
        result.put(rn, pm);
        final Set<I> children = new LinkedHashSet<>();
        for (final I node : nodes) {
            if (rn.holds(node)) {
                if (cp.contains(node)) {
                    final String p = cp.stream().map(i -> getTypeName(i.getClassType())).collect(joining(" > "));
                    throw new BeanException(node.getClass(), node.getCode(), "cyclic segment is not allowed: " + p);
                }
                children.add(node);
                final Map<IType<B, I>, P> mappers = this.joins(cp, node, cb);
                result.putAll(mappers);
            }
        }
        cp.pop();
        cb.accept(rn, unmodifiableSet(children));
        return unmodifiableMap(result);
    }

    abstract P build(Up2Factory<B> factory, I type, S source) throws BeanException;

    /**
     * Internal Segment Processor.
     */
    abstract sealed static class Processor<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> permits Up2Mapper, Up2Format, Computer {
        final int length;
        final int offset;
        final T node;

        Processor(T node) throws BeanException {
            this.node = node;
            this.length = max(node) + 1;
            final Truncated truncated = getOverride(node.type, Truncated.class);
            this.offset = (truncated != null) ? truncated.value() : 0;
            if (offset < 0) {
                throw new BeanException(node.type, "@Truncated[value] must be positive");
            }
        }

        static int max(BSNode<?, ?> node) {
            int max = -1;
            for (final BSProperty<?, ?, ?> p : node.properties) {
                final int offset;
                if (p instanceof FProperty<?, ?, ?> fp) {
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
                if (p instanceof FProperty<?, ?, ?> fp) {
                    offset = max(fp.node);
                } else {
                    offset = p.offset;
                }
                min = Math.min(min, offset);
            }
            return min;
        }
    }

    /**
     * Internal Business Factory.
     */
    static sealed abstract class Factory permits Up2Factory {

        final BeanContext context;
        final Validator validator;

        Factory(BeanContext context, Validator validator) {
            this.validator = requireNonNull(validator);
            this.context = requireNonNull(context);
        }

        <S extends Segment, B extends DataType<B>> BSMapper<S, B> build(DataTypeResolver<B> dr, Class<S> type) throws BeanException {
            return new BSMapper<>(BSContext.build(type, this, dr));
        }

        <S extends Segment, B extends DataType<B>> BSFormat<S, B> format(DataTypeResolver<B> dr, Class<S> type) throws BeanException {
            return new BSFormat<>(BSContext.format(type, this, dr));
        }
    }

    /**
     * Internal Business Processor.
     */
    static abstract sealed class Computer<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> extends Processor<S, D, T> permits BSMapper, BSFormat {
        final BAccessor<Segment, Object> businessId;
        final boolean hasBusinessId;

        Computer(AccessMode mode, T node) throws BeanException {
            super(node);
            this.businessId = this.id(BusinessId.class, mode).orElseGet(() -> unknown(node.type));
            this.hasBusinessId = this.businessId.supports(RO);
        }

        <A extends Annotation> Optional<BAccessor<Segment, Object>> id(Class<A> type, AccessMode mode) throws BeanException {
            final Wrapper<BAccessor<Segment, Object>> result = new Wrapper<>();
            BSBuilder.id(mode, node, type, (fs, pp) -> {
                if (!result.isEmpty()) {
                    throw new BeanException(node.type, "multiple @" + getTypeName(type) + " are found");
                }
                result.set(BSProperty.id(fs, pp));
            });
            return result.safe();
        }

        /**
         * Internal Business Format.
         */
        static final class BSFormat<S extends Segment, D extends DataType<D>> extends Computer<S, D, BFNode<S, D>> {
            BSFormat(BFNode<S, D> node) throws BeanException {
                super(RO, node);
            }
        }

        /**
         * Internal Business Mapper.
         */
        static final class BSMapper<S extends Segment, D extends DataType<D>> extends Computer<S, D, BPNode<S, D, ?>> {
            final BAccessor<Segment, Object> parentId;
            final boolean hasParentId;

            BSMapper(BPNode<S, D, ?> node) throws BeanException {
                super(WO, node);
                this.parentId = this.id(ParentId.class, WO).orElseGet(BAccessor::undefined);
                this.hasParentId = parentId.supports(RO);
            }

            <R extends IRecord<?>, E extends IEvent<D>> S map(R r, int o, boolean v, BusinessHandler<R, D, E, ?> h) {
                if (r == null || r.getColumns() == null) {
                    return null;
                }
                if (h.getSource() != r) {
                    throw new AccessException(EventHandler.class, "source", "does not match with the specified record");
                }
                requireNonNull(h, "handler is required");
                final S bean = node.parse(h, o, r.getColumns());
                node.update(bean, r);
                if (v) {
                    node.validate(bean, o, h);
                }
                return bean;
            }
        }
    }

    /**
     * Internal Business ID Accessor.
     */
    abstract sealed static class BAccessor<S extends Segment, K> permits BOAccessor, BUAccessor, BRAccessor, BPAccessor {
        private final Class<K> type;
        private final String locator;

        private BAccessor(Class<K> type, String locator) {
            this.type = type;
            this.locator = locator;
        }

        static BAccessor<Segment, Object> undefined() {
            return BUAccessor.UNDEFINED;
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        static BAccessor<Segment, Object> unknown(Class<? extends Segment> type) {
            if (Referencable.class.isAssignableFrom(type)) {
                final Type ft = getInterfaceArguments(type, Referencable.class)[0];
                final Class<? extends Comparable<?>> rt = (ft instanceof Class<?> c) ? cast(c) : cast(Comparator.class);
                if (BusinessObject.class.isAssignableFrom(type)) {
                    return BOAccessor.CACHE.computeIfAbsent(rt, (k) -> new BOAccessor(rt));
                }
                return BRAccessor.CACHE.computeIfAbsent(rt, (k) -> new BRAccessor(rt));
            }
            return undefined();
        }

        final <P extends Segment> void check(Class<P> type, BAccessor<?, ?> that) throws BeanException {
            if (!(this instanceof BUAccessor) && this.type != that.type) {
                throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
            }
        }

        abstract boolean supports(AccessMode mode);

        abstract String format(S bean) throws AccessException;

        abstract K get(S bean) throws AccessException;

        abstract K set(S bean, K key) throws AccessException;

        /**
         * Internal {@link BusinessObject} accessor.
         */
        static final class BOAccessor<R extends Comparable<R>> extends BAccessor<BusinessObject<R>, R> {
            private static final Map<Class<?>, BAccessor<Segment, Object>> CACHE = new ConcurrentHashMap<>();

            @SuppressWarnings("unchecked")
            private BOAccessor(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(AccessMode mode) {
                return true;
            }

            @Override
            R get(BusinessObject<R> bean) {
                return bean.getReference();
            }

            @Override
            R set(BusinessObject<R> bean, R reference) throws AccessException {
                bean.setReference(reference);
                return reference;
            }

            @Override
            String format(BusinessObject<R> bean) {
                return Optional.ofNullable(bean.getReference()).map(Object::toString).orElse(null);
            }
        }

        /**
         * Internal {@link Referencable} accessor.
         */
        static final class BRAccessor<R extends Comparable<R>> extends BAccessor<Referencable<R>, R> {
            private static final Map<Class<?>, BAccessor<Segment, Object>> CACHE = new ConcurrentHashMap<>();

            @SuppressWarnings("unchecked")
            private BRAccessor(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(AccessMode mode) {
                return mode != WO;
            }

            @Override
            R get(Referencable<R> bean) {
                return bean.getReference();
            }

            @Override
            R set(Referencable<R> bean, R reference) throws AccessException {
                throw new AccessException(BUAccessor.class, "set", "unsupported operation");
            }

            @Override
            String format(Referencable<R> bean) {
                return Optional.ofNullable(bean.getReference()).map(Object::toString).orElse(null);
            }
        }

        /**
         * Internal Undefined Business Accessor.
         */
        static final class BUAccessor extends BAccessor<Segment, Object> {
            private static final BAccessor<Segment, Object> UNDEFINED = new BUAccessor();

            private BUAccessor() {
                super(Object.class, null);
            }

            @Override
            boolean supports(AccessMode mode) {
                return false;
            }

            @Override
            String format(Segment bean) {
                throw new AccessException(BUAccessor.class, "format", "unsupported operation");
            }

            @Override
            Object get(Segment bean) {
                throw new AccessException(BUAccessor.class, "get", "unsupported operation");
            }

            @Override
            Object set(Segment bean, Object ignore) {
                throw new AccessException(BUAccessor.class, "set", "unsupported operation");
            }
        }

        /**
         * Internal Accessor based on managed property.
         */
        abstract sealed static class BPAccessor extends BAccessor<Segment, Object> permits PSProperty, PMProperty {

            protected final PProperty<Object, Object, ?> property;
            protected final PAccessor<?, Object> setter;
            private final boolean settable;

            private BPAccessor(PProperty<Object, Object, ?> property, PAccessor<?, Object> setter) {
                super(property.getType(), property.getName());
                this.property = property;
                this.setter = setter;
                this.settable = !(setter instanceof PNAccessor<?>);
            }

            static BPAccessor of(List<? extends FProperty<Segment, ?, ?>> fs, PProperty<Object, Object, ?> pp, PAccessor<?, Object> ps) {
                if (fs.isEmpty()) {
                    return new PSProperty(pp, ps);
                }
                return new PMProperty(fs, pp, ps);
            }

            @Override
            final boolean supports(AccessMode mode) {
                return settable || mode != WO;
            }

            @Override
            final String format(Segment bean) {
                final Object wrapped = this.value(bean);
                if (wrapped != null) {
                    return property.format(wrapped);
                }
                return null;
            }

            protected abstract Object value(Segment bean);

            static final class PSProperty extends BPAccessor {
                private PSProperty(PProperty<Object, Object, ?> property, PAccessor<?, Object> setter) {
                    super(property, setter);
                }

                @Override
                protected Object value(Segment bean) {
                    return property.value(bean);
                }

                @Override
                Object get(Segment bean) throws AccessException {
                    return property.getValue(bean);
                }

                @Override
                Object set(Segment bean, Object bid) throws AccessException {
                    return property.setValue(setter, bean, bid);
                }
            }

            static final class PMProperty extends BPAccessor {
                private final List<? extends FProperty<Segment, ?, ?>> path;

                private PMProperty(List<? extends FProperty<Segment, ?, ?>> fs, PProperty<Object, Object, ?> pp, PAccessor<?, Object> ps) {
                    super(pp, ps);
                    this.path = fs;
                }

                private Segment from(Segment bean) {
                    for (final BSProperty<Segment, ?, ?> getter : path) {
                        bean = getter.getValue(bean);
                        if (bean == null) {
                            break;
                        }
                    }
                    return bean;
                }

                @Override
                protected Object value(Segment bean) {
                    bean = this.from(bean);
                    if (bean != null) {
                        return property.value(bean);
                    }
                    return null;
                }

                @Override
                Object get(Segment bean) throws AccessException {
                    bean = this.from(bean);
                    if (bean != null) {
                        return property.getValue(bean);
                    }
                    return null;
                }

                @Override
                Object set(Segment bean, Object bid) throws AccessException {
                    bean = this.from(bean);
                    if (bean != null) {
                        return property.setValue(setter, bean, bid);
                    }
                    return null;
                }
            }
        }
    }
}
