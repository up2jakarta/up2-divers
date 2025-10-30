package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSOperator.Processor;
import io.github.up2jakarta.csv.core.hdl.EventCollector;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.core.hdl.PFProperty;
import io.github.up2jakarta.csv.core.hdl.Property;
import io.github.up2jakarta.csv.data.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_VALIDATOR;
import static io.github.up2jakarta.csv.core.ext.Beans.concat;
import static io.github.up2jakarta.csv.core.ext.Path.getOverride;
import static io.github.up2jakarta.csv.slv.CodeListResolver.checkUnique;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Internal business-mapping implementation for aggregation and segregation processing.
 */
abstract sealed class BSOperator<B extends DataType<B>, I extends IType<B, I>, P extends Processor<Segment, B, ?>, S extends Processor<Segment, B, ?>> permits BusinessExporter, BusinessImporter {

    protected final I root;
    protected final int offset;
    protected final ModeType mode;
    protected final List<I> nodes;

    private final Up2Factory<B> factory;
    private final Map<IType<B, I>, P> mappers;
    private final Map<IType<B, I>, Set<I>> joins;

    BSOperator(Up2Factory<B> factory, Class<?> type, ModeType mode, I root, I[] nodes) throws BeanException {
        requireNonNull(factory, "factory is required");
        requireNonNull(root, "root is required");
        checkUnique(type, nodes);
        if (!type.equals(root.getClassType())) {
            throw new BeanException(type, "Invalid business typing");
        }
        this.root = root;
        this.mode = mode;
        this.factory = factory;
        this.nodes = List.of(nodes);
        final Map<I, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(new Stack<>(), root, joins::put);
        this.joins = unmodifiableMap(joins);
        this.offset = offset(mappers.get(root), mode);
    }

    BSOperator(BSOperator<B, I, S, P> source) throws BeanException {
        this.root = source.root;
        this.mode = source.mode;
        this.factory = source.factory;
        this.joins = source.joins;
        this.offset = source.offset;
        this.nodes = source.nodes;
        this.mappers = this.joins(root, source.mappers);
    }

    private static int offset(Processor<?, ?, ?> mapper, ModeType mode) throws BeanException {
        final int min = mode.getBeanIdIndex();
        if (mapper.offset != 0) {
            if (mapper.offset < min) {
                throw new BeanException(mapper.type, "@Truncated[value] must be greater or equals to " + min);
            }
            return mapper.offset;
        }
        return min;
    }

    private Map<IType<B, I>, P> joins(I rn, Map<IType<B, I>, S> source) throws BeanException {
        final Map<IType<B, I>, P> result = new LinkedHashMap<>();
        result.put(rn, this.build(factory, rn, source.get(rn)));
        for (final I node : this.getJoins(rn)) {
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
                    final String p = cp.stream().map(i -> i.joiner().toString()).collect(joining(" > "));
                    throw new BeanException(node.getClass(), node.getCode(), "cyclic segment is not allowed: " + p);
                }
                children.add(node);
                final Map<IType<B, I>, P> mappers = this.joins(cp, node, cb);
                this.check(node.getName(), mappers.get(node), pm);
                result.putAll(mappers);
            }
        }
        cp.pop();
        cb.accept(rn, unmodifiableSet(children));
        return unmodifiableMap(result);
    }

    abstract P build(Up2Factory<B> factory, I type, S source) throws BeanException;

    abstract void check(String name, P node, P parent) throws BeanException;

    P get(IType<B, I> type) {
        return mappers.get(type);
    }

    Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

    /**
     * Internal segment mapping processor.
     */
    abstract sealed static class Processor<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> permits Up2Mapper, Up2Format {

        final Class<S> type;
        final int length;
        final int offset;
        final T node;

        Processor(Class<S> type, T node) throws BeanException {
            this.type = type;
            this.node = node;
            this.length = max(node) + 1;
            final Truncated truncated = getOverride(type, Truncated.class);
            this.offset = (truncated != null) ? truncated.value() : 0;
            if (offset < 0) {
                throw new BeanException(type, "@Truncated[value] must be positive");
            }
        }

        static int max(BSNode<?, ?> node) {
            int max = -1;
            for (final Property<?, ?> p : node.properties) {
                final int offset;
                if (p instanceof PFProperty<?, ?> fp) {
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
            for (final Property<?, ?> p : node.properties) {
                final int offset;
                if (p instanceof PFProperty<?, ?> fp) {
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
     * Internal business entry.
     */
    static final class Entry<T extends IType<D, T>, R extends IRecord<T>, D extends DataType<D>, E extends IEvent<D>> {

        final T type;
        private final Segment bean;
        private final Up2Mapper<Segment, D> mapper;
        private final EventHandler<R, D, E> handler;

        Entry(Up2Mapper<Segment, D> mapper, T type, Segment bean, EventCollector<R, D, E, ?> handler) {
            this.bean = bean;
            this.type = type;
            this.mapper = mapper;
            this.handler = handler;
        }

        <B extends Segment> B bean() {
            //noinspection unchecked
            return (B) bean;
        }

        void validate(int offset) {
            if ((bean instanceof BusinessObject bo) && bo.getReference() == null) {
                bo.setReference(handler.row.getBusinessReference());
            }
            mapper.node.validate(bean, offset, handler);
        }

        boolean link(Entry<T, R, D, E> parent, IType<D, T> childType) throws BeanException {
            if (childType != this.type) {
                return false;
            }
            if (!Objects.equals(parent.handler.row.getBusinessReference(), handler.row.getBusinessReference())) {
                return false;
            }
            if (mapper.parentId.exists()) {
                final Object key = mapper.parentId.get(bean);
                final Object pid = parent.mapper.businessId.get(parent.bean);
                return Objects.equals(key, pid);
            }
            return true;
        }

        void collect(List<E> target) {
            target.addAll(handler.toCollection());
        }

        void handle(IType<D, T> type, int index, String message) {
            if (type == null) {
                handler.handle(null, index, ERROR, ERROR_VALIDATOR, message);
            } else {
                handler.handle(type.getBusinessType(), index, type.getErrorLevel(), type.getErrorCode(), message);
            }
        }

    }

    /**
     * Internal Business key Getter
     */
    abstract sealed static class Getter<S extends Segment> implements BusinessIdentifier<S> permits BRGetter, BUGetter {

        private static <A extends Annotation> void path(List<? extends Property<?, ?>> ps, Class<A> at, BiConsumer<A, Property<?, ?>[]> c, Property<?, ?>... s) throws BeanException {
            for (final Property<?, ?> property : ps) {
                final AnnotatedElement field = property.getSource();
                final A annotation = field.getAnnotation(at);
                if (property instanceof PFProperty<?, ?> fp) {
                    if (annotation != null) {
                        throw BeanException.of(field, "must not be annotated with @" + at.getSimpleName());
                    }
                    path(fp.node.toList(), at, c, concat(s, property));
                } else if (annotation != null) {
                    c.accept(annotation, concat(s, property));
                }
            }
        }

        private static <A extends Annotation> Property<?, ?>[] path(Class<? extends Segment> st, Class<A> at, List<? extends Property<?, ?>> ps) throws BeanException {
            final Map<A, Property<?, ?>[]> found = new LinkedHashMap<>();
            path(ps, at, found::put);
            if (found.isEmpty()) {
                return null;
            }
            if (found.size() == 1) {
                return found.entrySet().iterator().next().getValue();
            }
            throw new BeanException(st, "multiple @" + at.getSimpleName() + " are found");
        }

        static <S extends Segment> Getter<S> parentId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
            final Property<?, ?>[] pid = path(type, ParentId.class, properties);
            if (pid == null) {
                return BUGetter.getInstance();
            }
            return new BRGetter<>(pid[pid.length - 1], Property.id(pid));
        }

        static <S extends Segment> Getter<S> businessId(Class<S> type, List<? extends Property<?, ?>> properties) throws BeanException {
            final Property<?, ?>[] bid = path(type, BusinessId.class, properties);
            if (bid == null) {
                if (Referencable.class.isAssignableFrom(type)) {
                    final BusinessIdentifier<S> getter = b -> ((Referencable) b).getReference();
                    return new BRGetter<>(String.class, "reference", getter);
                } else {
                    return BUGetter.getInstance();
                }
            } else {
                return new BRGetter<>(bid[bid.length - 1], Property.id(bid));
            }
        }

        abstract <P extends Segment> void check(Class<P> type, Getter<P> other) throws BeanException;

        abstract boolean exists();
    }

    static final class BRGetter<S extends Segment> extends Getter<S> {

        private final BusinessIdentifier<S> getter;
        private final String locator;
        private final Class<?> type;

        private BRGetter(Class<?> type, String locator, BusinessIdentifier<S> getter) {
            this.type = type;
            this.getter = getter;
            this.locator = locator;
        }

        private BRGetter(Property<?, ?> p, BusinessIdentifier<S> getter) {
            this(p.getType(), p.getName(), getter);
        }

        @Override
        boolean exists() {
            return true;
        }

        @Override
        public Object get(S bean) throws BeanException {
            return getter.get(bean);
        }

        public <P extends Segment> void check(Class<P> type, Getter<P> other) throws BeanException {
            if (other instanceof BRGetter<?> that) {
                if (this.type != that.type) {
                    throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
                }
            }
        }

    }

    static final class BUGetter extends Getter<Segment> {
        private static final BUGetter UNDEFINED = new BUGetter();

        private BUGetter() {
        }

        @SuppressWarnings("unchecked")
        static <S extends Segment> Getter<S> getInstance() {
            return (Getter<S>) UNDEFINED;
        }

        @Override
        boolean exists() {
            return false;
        }

        @Override
        <P extends Segment> void check(Class<P> type, Getter<P> other) throws BeanException {
            throw new BeanException(BUGetter.class, "exists", "must be checked");
        }

        @Override
        public Object get(Segment bean) throws BeanException {
            throw new BeanException(BUGetter.class, "exists", "must be checked");
        }

    }
}
