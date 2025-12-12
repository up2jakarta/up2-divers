package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Beans;
import io.github.up2jakarta.lov.core.WVCache;
import jakarta.validation.Validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.BSAccessor.Mode;
import static io.github.up2jakarta.csv.core.BSManager.*;
import static io.github.up2jakarta.csv.core.BSOperator.BId.*;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.Collections.*;
import static java.util.stream.Collectors.joining;

/**
 * Internal business operator.
 */
abstract sealed class BSOperator<B extends DataType<B>, I extends IType<B, I>, P extends Node<Segment, B, ?>, S extends Node<Segment, B, ?>> permits BusinessExporter, BusinessImporter {
    protected final I root;
    protected final int offset;
    protected final ModeType mode;

    final List<I> nodes;
    final Up2Factory<B> factory;
    final BId<Segment, Object> bid;
    final Map<IType<B, I>, P> mappers;
    final Map<IType<B, I>, Set<I>> joins;

    BSOperator(Up2Factory<B> factory, Class<?> type, ModeType mode, I root, List<I> nodes) throws BeanException {
        this.factory = notNull(factory, BSOperator.class, "factory");
        this.nodes = unmodifiableList(notNull(nodes, BSOperator.class, "nodes"));
        if (root == null || !notNull(type, BSOperator.class, "type").equals(root.getClassType())) {
            throw new BeanException(type, "invalid business typing");
        }
        this.root = root;
        this.mode = mode;
        final Map<I, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(new LinkedList<>(), root, joins::put);
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

    private static int offset(Pod<?, ?, ?> mapper, ModeType mode) throws BeanException {
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

    private Map<IType<B, I>, P> joins(List<I> cp, I rn, BiConsumer<I, Set<I>> cb) throws BeanException {
        final Map<IType<B, I>, P> result = new LinkedHashMap<>();
        final P pm = this.build(factory, rn, null);
        cp.addLast(rn);
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
        cp.removeLast();
        cb.accept(rn, unmodifiableSet(children));
        return unmodifiableMap(result);
    }

    abstract P build(Up2Factory<B> factory, I type, S source) throws BeanException;

    /**
     * Internal Business Factory.
     */
    abstract static sealed class Factory<D extends DataType<D>> permits Up2Factory {
        private static final List<String> CN_ENTRIES = getPermittedTypes(MEP.class).toList();
        private static final List<String> EXCLUSIONS = getPermittedTypes(MEP.class, MST.class, Beans.class).toList();
        final DataResolver<D> resolver;
        final BeanContext context;
        final Validator validator;

        Factory(BeanContext context, DataResolver<D> resolver) {
            this.context = notNull(context, Up2Factory.class, "context");
            this.resolver = notNull(resolver, Up2Factory.class, "resolver");
            this.validator = defaultValidator(context);
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

        private static Validator defaultValidator(BeanContext context) {
            try {
                return context.getBean(Validator.class);
            } catch (Exception ignore) {
            }
            try {
                return Up2Factory.validator(null);
            } catch (Exception ignore) {
                return null;
            }
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

        <S extends Segment> Mapper<S, D> of(Class<S> type) throws BeanException {
            return BSManager.of(this, type, resolver).build(Mapper::new);
        }

        <S extends Segment> Format<S, D> ft(Class<S> type) throws BeanException {
            return BSManager.ft(this, resolver, type).build(Format::new);
        }
    }

    /**
     * Internal Business ID Accessor.
     */
    abstract static sealed class BId<S extends Segment, K> implements MEP permits BO, BU, BR, DP {
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
                final Class<Comparable> rt = getTypeArgument(type, Referencable.class, 0, Comparable.class);
                if (BusinessObject.class.isAssignableFrom(type)) {
                    return BO.CACHE.get(rt, () -> new BO(rt));
                }
                return BR.CACHE.get(rt, () -> new BR(rt));
            }
            return undefined();
        }

        final <P extends Segment> void check(Class<P> type, BId<?, ?> that) throws BeanException {
            if (!(this instanceof BU) && this.type != that.type) {
                throw new BeanException(type, locator, "must be of type #[" + that.type + ']');
            }
        }

        abstract boolean supports(Mode mode);

        abstract String format(S bean) throws AccessException;

        abstract K get(S bean) throws AccessException;

        abstract K set(S bean, K key) throws AccessException;

        /**
         * Internal {@link BusinessObject} accessor.
         */
        static final class BO<R extends Comparable<R>> extends BId<BusinessObject<R>, R> {
            private static final WVCache<Class<?>, BId<Segment, Object>> CACHE = new WVCache<>(Class::getName);

            @SuppressWarnings("unchecked")
            private BO(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(Mode mode) {
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
            private static final WVCache<Class<?>, BId<Segment, Object>> CACHE = new WVCache<>(Class::getName);

            @SuppressWarnings("unchecked")
            private BR(Class<? extends Comparable<?>> type) {
                super((Class<R>) type, "reference");
            }

            @Override
            boolean supports(Mode mode) {
                return mode != Mode.WO;
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
            boolean supports(Mode mode) {
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
        abstract static sealed class DP extends BId<Segment, Object> permits SP, MP {

            protected final PPosition<Object, Object, ?> property;
            protected final BSAccessor<Object> setter;
            private final boolean settable;

            private DP(PPosition<Object, Object, ?> property, BSAccessor<Object> setter) {
                super(property.getType(), property.getName());
                this.settable = !setter.isFinal();
                this.property = property;
                this.setter = setter;
            }

            static DP of(List<? extends PFragment<Segment, ?, ?>> fs, PPosition<Object, Object, ?> pp, BSAccessor<Object> ps) {
                if (fs.isEmpty()) {
                    return new SP(pp, ps);
                }
                return new MP(fs, pp, ps);
            }

            @Override
            final boolean supports(Mode mode) {
                return settable || mode != Mode.WO;
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
            private SP(PPosition<Object, Object, ?> property, BSAccessor<Object> setter) {
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

            private MP(List<? extends PFragment<Segment, ?, ?>> fs, PPosition<Object, Object, ?> pp, BSAccessor<Object> ps) {
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
