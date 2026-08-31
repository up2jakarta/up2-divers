package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSContext.VContext;
import io.github.up2jakarta.csv.core.BSLink.LBuilder;
import io.github.up2jakarta.csv.core.BSLink.TContext;
import io.github.up2jakarta.csv.core.BSManager.Key;
import io.github.up2jakarta.csv.core.BSManager.Pod;
import io.github.up2jakarta.csv.core.BSOperator.Node;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BusinessExporter.Format;
import io.github.up2jakarta.csv.core.BusinessImporter.Mapper;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static io.github.up2jakarta.csv.api.ILinker.N;
import static io.github.up2jakarta.csv.core.BSBuilder.name;
import static io.github.up2jakarta.csv.core.BSBuilder.path;
import static io.github.up2jakarta.csv.core.ModeType.MESS;
import static io.github.up2jakarta.csv.ext.Beans.cast;
import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Internal business operator.
 */
abstract sealed class BSOperator<B extends ITerm<B>, I extends Enum<I> & IType<I>, P extends Node<Segment, B, ?>, S extends Node<Segment, B, ?>> permits BusinessExporter, BusinessImporter {
    protected static final String ONLY_ONE = "cardinality must be 1 and only one";
    protected final IMode mode;
    final Up2Factory<B> factory;
    final BSLink<B, I, P> tree;
    final Class<I> type;
    final int length;

    BSOperator(IMode mode, Up2Factory<B> factory, Class<Segment> st, Class<I> it) throws BeanException {
        this.type = it;
        this.mode = mode;
        this.factory = notNull(factory, this.getClass(), "factory");
        final B bt = factory.resolver.get(Optional.empty(), st).orElse(null);
        final TContext<B, I> tc = new TContext<>(mode, it, st);
        final P mapper = this.build(factory, st, null);
        final ICreator<B, I, P> mc = (m, l) -> new BSLink<>(tc.type(), this.offset(m), m, bt, l);
        this.tree = this.tree(st, tc.type(), mapper, tc, mc);
        this.length = this.tree.maxOrdinal();
        tc.finalize(st);
    }

    BSOperator(BSOperator<B, I, S, P> source) throws BeanException {
        this.type = source.type;
        this.mode = source.mode;
        this.length = source.length;
        this.factory = source.factory;
        this.tree = this.tree(source.tree);
    }

    /**
     * @return the mode type
     */
    public final IMode mode() {
        return mode;
    }

    /**
     * @return the business-object type
     */
    public final I type() {
        return tree.key;
    }

    private int offset(P mapper) throws BeanException {
        final int min = mode.getOffset();
        if (mapper.offset != 0) {
            if (mapper.offset < min) {
                throw new BeanException(mapper.node.type, "@Truncated[value] must be greater or equals to " + min);
            }
            return mapper.offset;
        }
        return min;
    }

    private BSLink<B, I, P> tree(Class<Segment> pc, I pt, P pm, TContext<B, I> tc, ICreator<B, I, P> cr) throws BeanException {
        tc.pushNode(pc, pt, pm);
        final LBuilder<B, I> lb = new LBuilder<>(factory.context, tc, mode);
        final List<BSLink<B, I, P>> links = new ArrayList<>(pm.node.bsLinks.size());
        for (final Field field : pm.node.bsLinks) {
            for (final BusinessLink link : field.getAnnotationsByType(BusinessLink.class)) {
                final I ct = tc.parse(field, link);
                if (ct == null) continue;
                tc.pushLink(ct);
                final Class<Segment> sc = tc.type(pc, field, ct, link);
                if (tc.paths().anyMatch(i -> ct.getCode().equals(i.getCode()))) {
                    throw new BeanException(type, pt.getCode(), "cyclic segment is not allowed: " + tc.path());
                }
                final B term = factory.resolver.get(Optional.of(field), cast(sc)).orElse(null);
                final P cm = this.build(factory, sc, null);
                final BSLink<B, I, P> node;
                if (tc.requireKey(ct, link, cm.node.bsLinks)) {
                    if (!cm.identifiable) {
                        throw new BeanException(sc, "must have one property annotated with @BusinessId");
                    }
                    tc.pushKey(ct);
                    node = this.tree(sc, ct, cm, tc, (m, l) -> new BSLink<>(field, link, ct, m, term, l, lb));
                    tc.pollKey();
                } else {
                    node = this.tree(sc, ct, cm, tc, (m, l) -> new BSLink<>(field, link, ct, m, term, l, lb));
                }
                tc.pollLink();
                links.add(node);
            }
        }
        tc.pollNode();
        return cr.apply(pm, links);
    }

    private BSLink<B, I, P> tree(BSLink<B, I, S> tree) throws BeanException {
        final List<BSLink<B, I, P>> links = new ArrayList<>(tree.links.size());
        final P mapper = this.build(factory, null, tree.computer);
        for (final BSLink<B, I, S> link : tree.links) {
            final BSLink<B, I, P> node = this.tree(link);
            links.add(node);
        }
        return new BSLink<>(tree, mapper, links);
    }

    String validate(BSLink<B, ?, ?> ln, int size) {
        if (ln.min > size || size > ln.max) {
            if (ln.min == 1 && ln.max == 1) {
                return ONLY_ONE;
            } else if (ln.max == N) {
                return "cardinality must be greater than or equal to " + ln.min;
            }
            return "cardinality must be between " + ln.min + " and " + ln.max;
        }
        return null;
    }

    abstract P build(Up2Factory<B> factory, Class<Segment> type, S source) throws BeanException;

    @FunctionalInterface
    private interface ICreator<B extends ITerm<B>, I extends Enum<I> & IType<I>, P extends Node<Segment, B, ?>> {
        BSLink<B, I, P> apply(P mapper, List<BSLink<B, I, P>> nodes) throws BeanException;
    }

    /**
     * Internal Segment identifier
     */
    static final class SId<D extends ITerm<D>> {
        private final PPosition<?, D> pp;
        private final List<Field> fs;

        SId(List<Field> fs, PPosition<?, D> pp) {
            this.fs = fs;
            this.pp = pp;
        }

        @SuppressWarnings("unchecked")
        PId<D> build(List<BSProperty<?, D>> ps, Mode mode) throws BeanException {
            final List<PFragment<?, D>> path = new ArrayList<>(fs.size());
            path(ps, fs, 0, path::addLast);
            return BSProperty.toPId(mode, path.toArray(PFragment[]::new), (PPosition<Object, D>) pp);
        }
    }

    /**
     * Internal Business ID Accessor.
     */
    abstract static sealed class BId<S extends Segment, K, T extends ITerm<T>> implements MEP permits NId, PId {
        protected final Class<K> type;
        protected final String locator;

        private BId(Class<K> type, String locator) {
            this.type = type;
            this.locator = locator;
        }

        final void check(Member source, ReferenceId pid, BId<?, ?, ?> that) throws BeanException {
            if (this.getClass() != NId.class && this.type != that.type) {
                throw new BeanException(source, "must be of type #[" + that.type + "] because " + name(pid));
            }
        }

        abstract boolean supports(Mode mode);

        abstract boolean isVirtual();

        abstract String format(S bean) throws AccessException;

        abstract K parse(String data, int offset, BusinessHandler<T> handler);

        abstract K get(S bean) throws AccessException;

        abstract void set(S bean, K key) throws AccessException;

        @Override
        public final String toString() {
            return locator;
        }
    }

    /**
     * Internal Property BId Accessor.
     */
    abstract static sealed class PId<B extends ITerm<B>> extends BId<Segment, Object, B> permits PId.SP, PId.MP {
        protected final PPosition<Object, B> property;
        protected final BSAccessor<Object> setter;
        private final boolean settable;

        private PId(PPosition<Object, B> property, BSAccessor<Object> setter) {
            super(property.getType(), property.getName());
            this.settable = !property.isFinal();
            this.property = property;
            this.setter = setter;
        }

        static <B extends ITerm<B>> PId<B> of(List<? extends PFragment<Segment, B>> ps, PPosition<Object, B> pp, BSAccessor<Object> st) {
            if (ps.isEmpty()) {
                return new SP<>(pp, st);
            }
            return new MP<>(ps, pp, st);
        }

        @Override
        final boolean supports(Mode mode) {
            return settable || mode != Mode.WO;
        }

        @Override
        final boolean isVirtual() {
            return property.offset < 0;
        }

        @Override
        final String format(Segment bean) {
            final Object value = this.get(bean);
            if (value != null) {
                return property.format(value);
            }
            return null;
        }

        @Override
        final Object parse(String data, int offset, BusinessHandler<B> handler) {
            return property.parse(data, offset, handler);
        }

        abstract boolean isYours(String[] path);

        abstract VContext context(VContext sc);

        /**
         * Internal Accessor based on single property.
         */
        private static final class SP<B extends ITerm<B>> extends PId<B> {
            private SP(PPosition<Object, B> property, BSAccessor<Object> setter) {
                super(property, setter);
            }

            @Override
            boolean isYours(String[] path) {
                return path.length == 1 && property.getName().equals(path[0]);
            }

            @Override
            VContext context(VContext sc) {
                return sc;
            }

            @Override
            Object get(Segment bean) throws AccessException {
                return property.value(bean);
            }

            @Override
            void set(Segment bean, Object bid) throws AccessException {
                setter.value(bean, bid);
            }
        }

        /**
         * Internal Accessor based on multiple property.
         */
        private static final class MP<B extends ITerm<B>> extends PId<B> {
            private final List<? extends PFragment<Segment, B>> path;

            private MP(List<? extends PFragment<Segment, B>> ps, PPosition<Object, B> pp, BSAccessor<Object> st) {
                super(pp, st);
                this.path = ps;
            }

            private Segment from(Segment bean) {
                for (final BSProperty<Segment, ?> getter : path) {
                    bean = getter.value(bean);
                    if (bean == null) break;
                }
                return bean;
            }

            @Override
            boolean isYours(String[] path) {
                final int size = this.path.size();
                if (path.length == size + 1 && property.getName().equals(path[size])) {
                    return IntStream.range(0, size).allMatch(i -> this.path.get(i).getName().equals(path[i]));
                }
                return false;
            }

            @Override
            VContext context(VContext sc) {
                for (int i = path.size() - 1; i >= 0; i--) {
                    final VContext fc = path.get(i).node.context;
                    if (fc.enabled) {
                        return fc;
                    }
                }
                return sc;
            }

            @Override
            Object get(Segment bean) throws AccessException {
                if ((bean = this.from(bean)) != null) {
                    property.value(bean);
                }
                return null;
            }

            @Override
            void set(Segment bean, Object bid) throws AccessException {
                if ((bean = this.from(bean)) != null) {
                    setter.value(bean, bid);
                }
            }
        }
    }

    /**
     * Internal Undefined BId Accessor.
     */
    private static final class NId<B extends ITerm<B>> extends BId<Segment, Object, B> {
        private static final BId<Segment, Object, ?> INSTANCE = new NId<>();

        private NId() {
            super(Object.class, null);
        }

        @Override
        boolean supports(Mode mode) {
            return false;
        }

        @Override
        boolean isVirtual() {
            throw new UnsupportedOperationException();
        }

        @Override
        String format(Segment bean) {
            throw new UnsupportedOperationException();
        }

        @Override
        Object parse(String data, int offset, BusinessHandler<B> handler) {
            throw new UnsupportedOperationException();
        }

        @Override
        Object get(Segment bean) {
            throw new UnsupportedOperationException();
        }

        @Override
        void set(Segment bean, Object ignore) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Internal Business Processor.
     */
    abstract static sealed class Node<S extends Segment, D extends ITerm<D>, T extends BSNode<S, D>> extends Pod<S, D, T> permits Mapper, Format {
        final BId<Segment, Object, D> businessId;
        final boolean identifiable;

        Node(Validator validator, Key<D, S> key, T node) throws BeanException {
            super(validator, key, node);
            if (node.bsIds.isEmpty()) {
                //noinspection unchecked
                this.businessId = (BId<Segment, Object, D>) NId.INSTANCE;
            } else if (node.bsIds.size() == 1) {
                final PId<D> bid = node.bsIds.getFirst();
                BeanChecker.check(bid.property, BusinessId.class);
                this.businessId = bid;
            } else {
                throw new BeanException(node.type, "multiple @BusinessId are found");
            }
            this.identifiable = this.businessId.supports(Mode.RO);
        }

        @Override
        public String toString() {
            return node.toString();
        }
    }

    /**
     * Extended {@link IMode} for {@link ModeType#MESS}
     */
    static final class XMode implements IMode {
        private static final XMode FULL = new XMode(1);
        private final int index, offset, length;

        private XMode(int size) {
            this.length = MESS.getLength() + size;
            this.offset = MESS.getOffset() + size;
            this.index = MESS.getIndex() + size;
        }

        static IMode of(int size) {
            if (size < 0) {
                throw new AccessException(IMode.class, "size", "must be positive");
            }
            return switch (size) {
                case 0 -> MESS;
                case 1 -> XMode.FULL;
                default -> new XMode(size);
            };
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public int getOffset() {
            return offset;
        }

        @Override
        public int getLength() {
            return length;
        }

        @Override
        public String toString() {
            return "FULL";
        }
    }
}
