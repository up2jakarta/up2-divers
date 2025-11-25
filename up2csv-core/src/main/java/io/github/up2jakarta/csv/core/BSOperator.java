package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSNode.Bean.BC;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSOperator.OPS;
import io.github.up2jakarta.csv.core.BSProperty.Accessor;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.csv.slv.CodeListResolver;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.core.*;
import jakarta.validation.Validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.BSOperator.BId.*;
import static io.github.up2jakarta.csv.core.BSOperator.OPS.Format;
import static io.github.up2jakarta.csv.core.BSOperator.OPS.Mapper;
import static io.github.up2jakarta.csv.core.BeanAccess.RO;
import static io.github.up2jakarta.csv.core.BeanAccess.WO;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static java.lang.String.join;
import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;

/**
 * Internal business-mapping implementation for aggregation and segregation processing.
 */
abstract sealed class BSOperator<B extends DataType<B>, I extends IType<B, I>, P extends OPS<Segment, B, ?>, S extends OPS<Segment, B, ?>> permits BusinessExporter, BusinessImporter {
    protected final I root;
    protected final int offset;
    protected final ModeType mode;

    final List<I> nodes;
    final Up2Factory<B> factory;
    final BId<Segment, Object> bid;
    final Map<IType<B, I>, P> mappers;
    final Map<IType<B, I>, Set<I>> joins;

    BSOperator(Up2Factory<B> factory, Class<?> type, ModeType mode, I root, List<I> nodes) throws BeanException {
        requireNonNull(factory, "factory is required");
        requireNonNull(root, "root is required");
        this.nodes = unmodifiableList(nodes);
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

    private static int offset(Computer<?, ?, ?> mapper, ModeType mode) throws BeanException {
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
                    throw new BeanException(rn.getClass(), rn.getCode(), "cyclic segment is not allowed: " + p);
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
    abstract sealed static class Computer<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> implements MEP permits Up2Mapper, Up2Flatter, OPS {
        final int length;
        final int offset;
        final T node;

        Computer(T node) throws BeanException {
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
                    var cn = stack.stream().filter(not(BC.class::isInstance)).map(n -> getTypeName(n.type)).toList();
                    throw new BeanException(t, "inner class is not allowed outside enclosing segments: " + join(", ", cn));
                }
                if (parent.get() instanceof Bean.BC<?, ?> n) {
                    throw new BeanException(t, "inner class is not allowed inside enclosing segment: " + getTypeName(n.type));
                }
            }
            stack.push(node);
            for (final BSProperty<?, ?, ?> p : node.properties) {
                if (p instanceof PFragment<?, ?, ?> fp) {
                    check(stack, (Bean<?, ?, ?>) fp.node);
                }
            }
        }

        static int max(BSNode<?, ?> node) {
            int max = -1;
            for (final BSProperty<?, ?, ?> p : node.properties) {
                final int offset;
                if (p instanceof PFragment<?, ?, ?> fp) {
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
                if (p instanceof PFragment<?, ?, ?> fp) {
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
     * Internal Business Factory.
     */
    static sealed abstract class Factory permits Up2Factory {
        private static final List<String> CN_ENTRIES = getPermittedTypes(MEP.class).toList();
        private static final List<String> EXCLUSIONS = getPermittedTypes(MEP.class, MST.class, Beans.class).toList();

        final BeanContext context;
        final Validator validator;

        Factory(BeanContext context, Validator validator) {
            this.validator = requireNonNull(validator);
            this.context = requireNonNull(context);
        }

        /**
         * Builds and returns the stack trace of the cause of the specified event.
         *
         * @param event the source event
         * @return the stack-trace if exists
         */
        public static Optional<String> trace(IException event) {
            while (event.getCause() instanceof IException cause) {
                event = cause;
            }
            return Optional.ofNullable(event.getCause()).map(c -> stackTrace(CN_ENTRIES, c).trim());
        }

        /**
         * Returns the stack trace of the specified <code>cause</code> exception.
         * <p>
         * Notes that the stack elements will be truncated from the given <code>cns</code> entry-point class names
         *
         * @param cause       the cause exception
         * @param entryPoints the list class names of entry-points
         * @return the stack trace
         */
        public static String stackTrace(List<String> entryPoints, Throwable cause) {
            final StringWriter writer = new StringWriter();
            stackTrace(entryPoints, cause, "", new PrintWriter(writer));
            return writer.toString();
        }

        private static void stackTrace(List<String> cns, Throwable cause, String prefix, PrintWriter printer) {
            printer.println(prefix + cause);
            final StackTraceElement[] traces = cause.getStackTrace();
            for (final StackTraceElement element : traces) {
                final String cn = element.getClassName();
                if (!EXCLUSIONS.contains(cn)) {
                    printer.println("\t" + element);
                }
                if (cns.contains(cn)) {
                    break;
                }
            }
            cause = cause.getCause();
            if (cause != null) {
                stackTrace(cns, cause, "Caused by ", printer);
            }
        }

        <S extends Segment, B extends DataType<B>> Mapper<S, B> build(DataTypeResolver<B> dr, Class<S> type) throws BeanException {
            return new Mapper<>(BSContext.build(type, this, dr));
        }

        <S extends Segment, B extends DataType<B>> Format<S, B> format(DataTypeResolver<B> dr, Class<S> type) throws BeanException {
            return new Format<>(BSContext.format(type, this, dr));
        }
    }

    /**
     * Internal Business Processor.
     */
    static abstract sealed class OPS<S extends Segment, D extends DataType<D>, T extends BSNode<S, D>> extends Computer<S, D, T> permits Mapper, Format {
        final BId<Segment, Object> businessId;
        final boolean hasBusinessId;

        OPS(BeanAccess mode, T node) throws BeanException {
            super(node);
            this.businessId = this.id(BusinessId.class, mode).or(() -> unknown(node.type));
            this.hasBusinessId = this.businessId.supports(RO);
        }

        <A extends Annotation> Wrapper<BId<Segment, Object>> id(Class<A> type, BeanAccess mode) throws BeanException {
            final Wrapper<BId<Segment, Object>> result = new Wrapper<>();
            BSBuilder.id(mode, node, type, (fs, pp) -> {
                if (result.isPresent()) {
                    throw new BeanException(node.type, "multiple @" + getTypeName(type) + " are found");
                }
                result.accept(BSProperty.id(fs, pp));
            });
            return result;
        }

        /**
         * Internal Business Format.
         */
        static final class Format<S extends Segment, D extends DataType<D>> extends OPS<S, D, Flat<S, D>> {
            Format(Flat<S, D> node) throws BeanException {
                super(RO, node);
            }
        }

        /**
         * Internal Business Mapper.
         */
        static final class Mapper<S extends Segment, D extends DataType<D>> extends OPS<S, D, Bean<S, D, ?>> {
            final BId<Segment, Object> parentId;
            final boolean hasParentId;

            Mapper(Bean<S, D, ?> node) throws BeanException {
                super(WO, node);
                this.check(node);
                this.parentId = this.id(ParentId.class, WO).or(BId::undefined);
                this.hasParentId = parentId.supports(RO);
            }

            <R extends IRecord<?>> S map(R r, int o, boolean v, BusinessHandler<D> h) {
                var data = r.getData();
                if (data == null) {
                    data = new String[0];
                }
                final S bean = node.parse(h, o, data);
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
    abstract sealed static class BId<S extends Segment, K> implements MEP permits BO, BU, BR, DP {
        protected final Class<K> type;
        protected final String locator;

        private BId(Class<K> type, String locator) {
            this.type = type;
            this.locator = locator;
        }

        static BId<Segment, Object> undefined() {
            return BU.UNDEFINED;
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        static BId<Segment, Object> unknown(Class<? extends Segment> type) {
            if (Referencable.class.isAssignableFrom(type)) {
                final Type ft = getTypeArguments(type, Referencable.class)[0];
                final Class<? extends Comparable<?>> rt = (ft instanceof Class<?> c) ? cast(c) : cast(Comparator.class);
                if (BusinessObject.class.isAssignableFrom(type)) {
                    return BO.CACHE.computeIfAbsent(rt, (k) -> new BO(rt));
                }
                return BR.CACHE.computeIfAbsent(rt, (k) -> new BR(rt));
            }
            return undefined();
        }

        final <P extends Segment> void check(Class<P> type, BId<?, ?> that) throws BeanException {
            if (!(this instanceof BU) && this.type != that.type) {
                throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
            }
        }

        abstract boolean supports(BeanAccess mode);

        abstract String format(S bean) throws AccessException;

        abstract K get(S bean) throws AccessException;

        abstract K set(S bean, K key) throws AccessException;

        /**
         * Internal {@link BusinessObject} accessor.
         */
        static final class BO<R extends Comparable<R>> extends BId<BusinessObject<R>, R> {
            private static final Map<Class<?>, BId<Segment, Object>> CACHE = new ConcurrentHashMap<>();

            @SuppressWarnings("unchecked")
            private BO(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(BeanAccess mode) {
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
        static final class BR<R extends Comparable<R>> extends BId<Referencable<R>, R> {
            private static final Map<Class<?>, BId<Segment, Object>> CACHE = new ConcurrentHashMap<>();

            @SuppressWarnings("unchecked")
            private BR(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(BeanAccess mode) {
                return mode != WO;
            }

            @Override
            R get(Referencable<R> bean) {
                return bean.getReference();
            }

            @Override
            R set(Referencable<R> bean, R reference) throws AccessException {
                throw new AccessException(bean.getClass(), locator, "unsupported write operation");
            }

            @Override
            String format(Referencable<R> bean) {
                return Optional.ofNullable(bean.getReference()).map(Object::toString).orElse(null);
            }
        }

        /**
         * Internal Undefined Business Accessor.
         */
        static final class BU extends BId<Segment, Object> {
            private static final BId<Segment, Object> UNDEFINED = new BU();

            private BU() {
                super(Object.class, null);
            }

            @Override
            boolean supports(BeanAccess mode) {
                return false;
            }

            @Override
            String format(Segment bean) {
                throw new AccessException(bean.getClass(), "reference", "unsupported read operation");
            }

            @Override
            Object get(Segment bean) {
                throw new AccessException(bean.getClass(), "reference", "unsupported read operation");
            }

            @Override
            Object set(Segment bean, Object ignore) {
                throw new AccessException(bean.getClass(), "reference", "unsupported write operation");
            }
        }

        /**
         * Internal Accessor based on defined property.
         */
        abstract sealed static class DP extends BId<Segment, Object> permits SP, MP {

            protected final PPosition<Object, Object, ?> property;
            protected final Accessor<Object> setter;
            private final boolean settable;

            private DP(PPosition<Object, Object, ?> property, Accessor<Object> setter) {
                super(property.getType(), property.getName());
                this.settable = !setter.isFinal();
                this.property = property;
                this.setter = setter;
            }

            static DP of(List<? extends PFragment<Segment, ?, ?>> fs, PPosition<Object, Object, ?> pp, Accessor<Object> ps) {
                if (fs.isEmpty()) {
                    return new SP(pp, ps);
                }
                return new MP(fs, pp, ps);
            }

            @Override
            final boolean supports(BeanAccess mode) {
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
        }

        /**
         * Internal Accessor based on simple property.
         */
        static final class SP extends DP {
            private SP(PPosition<Object, Object, ?> property, Accessor<Object> setter) {
                super(property, setter);
            }

            @Override
            protected Object value(Segment bean) {
                return property.value(bean);
            }

            @Override
            Object get(Segment bean) throws AccessException {
                return property.value(bean);
            }

            @Override
            Object set(Segment bean, Object bid) throws AccessException {
                return property.setValue(setter, bean, bid);
            }
        }

        /**
         * Internal Accessor based on multiple property.
         */
        static final class MP extends DP {
            private final List<? extends PFragment<Segment, ?, ?>> path;

            private MP(List<? extends PFragment<Segment, ?, ?>> fs, PPosition<Object, Object, ?> pp, Accessor<Object> ps) {
                super(pp, ps);
                this.path = fs;
            }

            private Segment from(Segment bean) {
                for (final BSProperty<Segment, ?, ?> getter : path) {
                    bean = getter.value(bean);
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
                    return property.value(bean);
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
