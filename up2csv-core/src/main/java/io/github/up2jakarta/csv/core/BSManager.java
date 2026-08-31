package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.TermResolver;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSOperator.Node;
import io.github.up2jakarta.csv.core.BSOperator.PId;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BusinessExporter.Format;
import io.github.up2jakarta.csv.core.BusinessImporter.Mapper;
import io.github.up2jakarta.csv.ext.Beans;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.bst.Cache;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.WKCache;
import jakarta.validation.Validator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;

import static io.github.up2jakarta.csv.ext.Beans.getPermittedTypes;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static java.util.regex.Pattern.compile;

/**
 * Internal cache manager.
 */
class BSManager<D extends ITerm<D>, S extends Segment> {
    private static final List<String> EXCLUSIONS = getPermittedTypes(MEP.class, MST.class, Beans.class).toList();
    private static final Map<TermResolver<?>, BSManager<?, ?>> CACHE = new IdentityHashMap<>();
    private static final String MEP_REGEX = "^jdk\\.internal\\.reflect\\.\\w*Accessor\\w*$";
    private static final List<String> CN_ENTRIES = getPermittedTypes(MEP.class).toList();
    private static final Predicate<String> MEP = compile(MEP_REGEX).asMatchPredicate();
    private final Cache<Key<D, S>, Value<D, S>> cache = new WKCache<>();

    private BSManager() {
    }

    private static void stackTrace(List<String> cns, Throwable cause, String prefix, PrintWriter printer) {
        printer.println(prefix + cause);
        final StackTraceElement[] traces = cause.getStackTrace();
        for (final StackTraceElement element : traces) {
            final String cn = element.getClassName();
            if (MEP.test(cn)) {
                break;
            }
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

    @SuppressWarnings("unchecked")
    private static <D extends ITerm<D>, S extends Segment> BSManager<D, S> of(TermResolver<D> r) {
        return (BSManager<D, S>) CACHE.computeIfAbsent(r, k -> new BSManager<D, S>());
    }

    @SuppressWarnings("unchecked")
    static <D extends ITerm<D>, S extends Segment> BSManager<D, S> of(Factory<D> factory) {
        return (BSManager<D, S>) factory.manager;
    }

    static <D extends ITerm<D>, S extends Segment> MBuilder<D, S> mp(Pod<S, D, Flat<S, D>> source) {
        final BSManager<D, S> that = source.key.manager;
        return new MBuilder<>() {
            @Override
            public <M extends Pod<S, D, Bean<S, D, ?>>> M build(Validator p, BCreator<D, S, M> c) throws BeanException {
                return that.cache.get(source.key, v -> {
                    if (v instanceof RO<D, S> ro) {
                        return ro.complete();
                    } else if (v == null) {
                        return new RW<>(source.node);
                    }
                    return v;
                }, (k, v) -> c.apply(p, k, v.beanValue()));
            }
        };
    }

    static <D extends ITerm<D>, S extends Segment> FBuilder<D, S> ft(Pod<S, D, Bean<S, D, ?>> source) {
        final BSManager<D, S> that = source.key.manager;
        return new FBuilder<>() {
            @Override
            public <M extends Pod<S, D, Flat<S, D>>> M build(Validator p, FCreator<D, S, M> c) throws BeanException {
                return that.cache.get(source.key, v -> {
                    if (v instanceof WO<D, S> wo) {
                        return wo.complete();
                    } else if (v == null) {
                        return new RW<>(source.node);
                    }
                    return v;
                }, (k, v) -> c.apply(p, k, v.flatValue()));
            }
        };
    }

    MBuilder<D, S> mp(Factory<D> factory, Class<S> type) {
        final Key<D, S> key = new Key<>(type, this);
        return new MBuilder<>() {
            @Override
            public <M extends Pod<S, D, Bean<S, D, ?>>> M build(Validator p, BCreator<D, S, M> c) throws BeanException {
                return cache.get(key, v -> {
                    if (v instanceof RO<D, S> ro) {
                        return ro.complete();
                    } else if (v == null) {
                        return new WO<>(mp(factory, key));
                    }
                    return v;
                }, (k, v) -> c.apply(p, k, v.beanValue()));
            }
        };
    }

    FBuilder<D, S> ft(Factory<D> factory, Class<S> type) {
        final Key<D, S> key = new Key<>(type, this);
        return new FBuilder<>() {
            @Override
            public <M extends Pod<S, D, Flat<S, D>>> M build(Validator p, FCreator<D, S, M> c) throws BeanException {
                return cache.get(key, v -> {
                    if (v instanceof WO<D, S> wo) {
                        return wo.complete();
                    } else if (v == null) {
                        return new RO<>(ft(factory, key));
                    }
                    return v;
                }, (k, v) -> c.apply(p, k, v.flatValue()));
            }
        };
    }

    private Bean<S, D, ?> mp(Factory<D> factory, Key<D, S> key) throws BeanException {
        final Class<S> type = key.type;
        final BSContext<D> mc = new BSContext<>(factory, type, Mode.WO);
        final List<BSProperty<?, D>> ps = mc.build();
        final Constructor<S> cs = BSContext.from(type, ps);
        if (cs == null) {
            if (type.isRecord()) {
                return new Bean.JR<>(type, mc, ps);
            }
            return new Bean.BM<>(type, mc, ps);
        }
        return new Bean.JB<>(type, mc, ps, cs);
    }

    private Flat<S, D> ft(Factory<D> factory, Key<D, S> key) throws BeanException {
        final Class<S> type = key.type;
        final BSContext<D> mc = new BSContext<>(factory, type, Mode.RO);
        return new Flat<>(type, mc, mc.context(), mc.build());
    }

    interface MBuilder<D extends ITerm<D>, S extends Segment> {
        <M extends Pod<S, D, Bean<S, D, ?>>> M build(Validator validator, BCreator<D, S, M> creator) throws BeanException;
    }

    interface FBuilder<D extends ITerm<D>, S extends Segment> {
        <M extends Pod<S, D, Flat<S, D>>> M build(Validator validator, FCreator<D, S, M> creator) throws BeanException;
    }

    @FunctionalInterface
    interface BCreator<D extends ITerm<D>, S extends Segment, M extends Pod<S, D, Bean<S, D, ?>>> {
        M apply(Validator validator, Key<D, S> key, Bean<S, D, ?> node) throws BeanException;
    }

    @FunctionalInterface
    interface FCreator<D extends ITerm<D>, S extends Segment, M extends Pod<S, D, Flat<S, D>>> {
        M apply(Validator validator, Key<D, S> key, Flat<S, D> node) throws BeanException;
    }

    /**
     * Internal Cache Key
     */
    static final class Key<D extends ITerm<D>, S extends Segment> implements Comparable<Key<D, S>> {
        private final BSManager<D, S> manager;
        private final Class<S> type;

        private Key(Class<S> type, BSManager<D, S> manager) {
            this.type = notNull(type, Up2Factory.class, "type");
            this.manager = manager;
        }

        @Override
        public int compareTo(Key<D, S> that) {
            assert manager == that.manager : "same manager";
            // Fine: generic segment is not allowed
            if (this.type == that.type) {
                return 0;
            }
            return this.type.getName().compareTo(that.type.getName());
        }

        @Override
        public String toString() {
            return getTypeName(type);
        }
    }

    /**
     * Internal Business Factory.
     */
    abstract static sealed class Factory<D extends ITerm<D>> permits Up2Factory {
        final Container context;
        final Validator validator;
        final TermResolver<D> resolver;
        private final BSManager<D, ?> manager;

        Factory(Container context, TermResolver<D> resolver, Validator validator) {
            this.resolver = notNull(resolver, Up2Factory.class, "resolver");
            this.context = notNull(context, Up2Factory.class, "context");
            this.manager = of(resolver);
            this.validator = validator;
        }

        /**
         * Builds and returns the stack trace of the cause of the specified event.
         *
         * @param event the source event
         * @return the stack-trace if exists
         */
        public static Optional<String> stackTrace(IException event) {
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
            BSManager.stackTrace(entryPoints, cause, "", new PrintWriter(writer));
            return writer.toString();
        }

        <S extends Segment> Mapper<S, D> mp(Class<S> type) throws BeanException {
            return BSManager.<D, S>of(this).mp(this, type).build(validator, Mapper::new);
        }

        <S extends Segment> Format<S, D> ft(Class<S> type) throws BeanException {
            return BSManager.<D, S>of(this).ft(this, type).build(validator, Format::new);
        }
    }

    /**
     * Internal Cache Value
     */
    abstract static sealed class Value<D extends ITerm<D>, S extends Segment> permits RO, WO, RW {
        abstract Flat<S, D> flatValue();

        abstract Bean<S, D, ?> beanValue();

        abstract Class<S> getType();

        @Override
        public final String toString() {
            return '#' + this.getClass().getSimpleName() + '[' + getTypeName(this.getType()) + ']';
        }
    }

    private static final class RO<D extends ITerm<D>, S extends Segment> extends Value<D, S> {
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

    private static final class WO<D extends ITerm<D>, S extends Segment> extends Value<D, S> {
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

    private static final class RW<D extends ITerm<D>, S extends Segment> extends Value<D, S> {
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
    abstract static sealed class Pod<S extends Segment, D extends ITerm<D>, T extends BSNode<S, D>> implements MEP permits Up2Mapper, Up2Flatter, Node {
        final T node;
        final int length;
        final int offset;
        final boolean validate;
        private final Key<D, S> key;

        Pod(Validator validator, Key<D, S> key, T node) throws BeanException {
            this.key = key;
            this.node = node;
            this.length = max(node) + 1;
            this.validate = node.context.enabled(validator);
            final Truncated truncated = node.type.getAnnotation(Truncated.class);
            this.offset = (truncated != null) ? truncated.value() : 0;
            if (offset < 0) {
                throw new BeanException(node.type, "@Truncated[value] must be positive");
            }
        }

        static int max(BSNode<?, ?> node) {
            int max = -1;
            for (final BSProperty<?, ?> p : node.properties) {
                final int offset;
                if (p instanceof PFragment<?, ?> fp) {
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
            for (final BSProperty<?, ?> p : node.properties) {
                final int offset;
                if (p instanceof PFragment<?, ?> fp) {
                    offset = max(fp.node);
                } else {
                    offset = p.offset;
                }
                min = Math.min(min, offset);
            }
            return min;
        }

        protected void check(Bean<S, D, ?> node) throws BeanException {
            BeanChecker.check(new LinkedList<>(), node);
        }

        /**
         * Returns {@code true} if the specified arguments have the same business identifiers and {@code false} otherwise.
         *
         * @param first  the first segment
         * @param second the second segment
         * @return {@code true} if the arguments have the same business identifiers and {@code false} otherwise
         * @see Object#equals(Object)
         */
        public final boolean equals(S first, S second) {
            if (first == second) {
                return true;
            } else if (first == null || second == null) {
                return false;
            } else if (node.bsIds.isEmpty()) {
                return first.equals(second);
            }
            for (final PId<D> key : node.bsIds) {
                try {
                    if (!Objects.equals(key.get(first), key.get(second))) {
                        return false;
                    }
                } catch (RuntimeException ex) {
                    return false;
                }
            }
            return true;
        }
    }

}
