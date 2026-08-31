package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSContext.VContext;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSOperator.BId;
import io.github.up2jakarta.csv.core.BSOperator.Node;
import io.github.up2jakarta.csv.core.BSOperator.PId;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.MessImporter.Item;
import io.github.up2jakarta.csv.ext.Beans;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.core.BSBuilder.*;
import static io.github.up2jakarta.csv.core.ModeType.NEAT;
import static io.github.up2jakarta.csv.ext.Beans.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.util.stream.Collectors.joining;

/**
 * Internal Business Tree.
 */
final class BSLink<D extends ITerm<D>, I extends Enum<I> & IType<I>, P extends Node<Segment, D, ?>> implements MEP {
    final I key;
    final D term;
    final P computer;
    final Error event;
    final IComputer<D> bid;
    final List<RId<D>> parentIds;
    final List<VId<D>> virtualIds;
    final List<BSLink<D, I, P>> links;
    final int length, index, offset, min, max;
    private final ILinker<Segment, Segment> linker;

    BSLink(BSLink<D, I, ?> source, P computer, List<BSLink<D, I, P>> links) {
        this.virtualIds = source.virtualIds;
        this.parentIds = source.parentIds;
        this.links = List.copyOf(links);
        this.offset = source.offset;
        this.length = source.length;
        this.linker = source.linker;
        this.index = source.index;
        this.event = source.event;
        this.computer = computer;
        this.term = source.term;
        this.key = source.key;
        this.min = source.min;
        this.max = source.max;
        this.bid = source.bid;
    }

    BSLink(I key, int offset, P computer, D term, List<BSLink<D, I, P>> links) throws BeanException {
        this.key = key;
        this.index = 0;
        this.length = 0;
        this.term = term;
        this.linker = null;
        this.offset = offset;
        this.computer = computer;
        this.min = this.max = 1;
        this.parentIds = List.of();
        this.virtualIds = List.of();
        this.links = List.copyOf(links);
        this.event = getError(computer.node.type);
        this.bid = this.isSettable() ? Item::mainId : computer.identifiable ? Item::nodeId : Item::nullId;
    }

    BSLink(Field fp, BusinessLink bl, I st, P mp, D bt, List<BSLink<D, I, P>> ls, LBuilder<D, I> lb) throws BeanException {
        this.key = st;
        this.term = bt;
        this.computer = mp;
        this.offset = lb.offset;
        this.min = lb.getMin(bl);
        this.max = lb.getMax(bl);
        this.links = List.copyOf(ls);
        this.linker = lb.findLinker(fp, bl);
        this.event = getError(fp, mp.node.type);
        if (lb.mode == ModeType.NEAT) {
            this.parentIds = List.of();
            this.virtualIds = List.of();
            this.index = 0;
        } else {
            final Map<I, PId<D>> pids = lb.findParentIds(mp.node);
            if (lb.isAutomatic(bl)) {
                this.virtualIds = lb.findVirtualIds(mp.node.type, pids);
            } else {
                lb.checkParentIds(mp.node.type, pids);
                this.virtualIds = List.of();
            }
            this.index = this.virtualIds.size();
            this.parentIds = pids.entrySet().stream().map(e -> new RId<>(e, index + offset)).toList();
        }
        this.length = this.index + parentIds.size();
        this.bid = computer.identifiable ? Item::nodeId : Item::nullId;
    }

    private boolean isSettable() {
        final BId<Segment, ?, D> bid = computer.businessId;
        return bid.supports(Mode.WO) && bid.isVirtual();
    }

    void visit(int depth, BiConsumer<Integer, BSLink<D, I, P>> handler) {
        handler.accept(depth, this);
        for (final BSLink<D, I, P> link : links) {
            link.visit(depth + 1, handler);
        }
    }

    Map<I, BSLink<D, I, P>> toMapper(Class<I> type) {
        final Map<I, BSLink<D, I, P>> result = new EnumMap<>(type);
        this.visit(0, (d, n) -> result.put(n.key, n));
        return Collections.unmodifiableMap(result);
    }

    TypeConverter<I> toParser(Class<I> type) {
        final List<I> nodes = new LinkedList<>();
        this.visit(0, (d, n) -> nodes.add(n.key));
        return new HashMapAdapter<>(type, ERROR, EC_CODE_LIST, nodes);
    }

    int maxOrdinal() {
        final Wrapper<Integer> max = new Wrapper<>(0);
        this.visit(0, (d, n) -> {
            final int value = n.key.ordinal();
            if (value > max.get()) {
                max.accept(value);
            }
        });
        return max.get() + 1;
    }

    Collection<Segment> from(Segment p) {
        return linker.from(p);
    }

    void link(Segment ps, Segment cs, BSLink<D, ?, ?> pl, BusinessHandler<D> hd) {
        try {
            linker.link(ps, cs);
        } catch (RuntimeException ex) {
            hd.handle(event, term, "cannot link with Segment#[" + pl.key.getCode() + ']', ex);
        }
    }

    @Override
    public String toString() {
        return CodeList.toString(key.getCode(), key.getName());
    }

    @FunctionalInterface
    interface IComputer<B extends ITerm<B>> {
        Object apply(Item<B, ?> item, IMessRecord<?> source);
    }

    /**
     * Internal Link Builder.
     */
    static final class LBuilder<B extends ITerm<B>, I extends Enum<I> & IType<I>> {
        private final List<Class<? extends Segment>> types;
        private final Map<I, Node<?, B, ?>> parents;
        private final BusinessLink override;
        private final List<I> requiredPIds;
        private final Container context;
        private final List<I> paths;
        private final Class<I> type;
        private final int offset;
        private final IMode mode;
        private final I main;

        LBuilder(Container context, TContext<B, I> source, IMode mode) {
            this.context = context;
            this.mode = source.mode;
            this.type = source.type;
            this.main = source.main;
            this.offset = mode.getLength();
            this.paths = List.copyOf(source.paths);
            this.types = List.copyOf(source.types);
            this.parents = Map.copyOf(source.parents);
            this.requiredPIds = List.copyOf(source.requiredPIds);
            final Overlink overlink = source.overrides.remove(source.paths.getLast());
            this.override = Optional.ofNullable(overlink).map(Overlink::value).orElse(null);
        }

        private int getMin(BusinessLink source) {
            if (override != null) {
                return Math.max(override.min(), source.min());
            }
            return source.min();
        }

        private int getMax(BusinessLink source) {
            if (override != null) {
                return Math.min(override.max(), source.max());
            }
            return source.max();
        }

        private boolean isAutomatic(BusinessLink source) {
            if (source.automatic()) {
                return true;
            }
            return (override != null) && override.automatic();
        }

        private ILinker<Segment, Segment> findLinker(Field fp, BusinessLink link) throws BeanException {
            final Linker linker = getLinker(override, link);
            if (linker.value() != BusinessLink.Void.class) {
                return Container.from(context, linker.value(), linker.name());
            }
            final Class<? extends Segment> pc = types.getLast();
            final Class<Segment> ignore = linkerType(pc, fp, this.getMax(link), link);
            return BSAccessor.getLinker(fp, pc);
        }

        private Map<I, PId<B>> findParentIds(BSNode<?, B> node) throws BeanException {
            final Map<I, PId<B>> parentIds = new EnumMap<>(type);
            final Mode mode = (node instanceof Flat<?, B>) ? Mode.RO : Mode.WO;
            id(node, ReferenceId.class, (fs, pp, pa) -> {
                final I type = paths.stream().filter(i -> pa.value().equals(i.getCode())).findAny().orElse(null);
                if (parentIds.containsKey(type)) {
                    throw new BeanException(node.type, "multiple " + name(pa) + "are found");
                }
                final Node<?, B, ?> pn = parents.get(type);
                BeanChecker.check(pn, pp.getSource(), pa);
                final PId<B> parentId = BSProperty.toPId(mode, fs, pp);
                parentId.check(pp.getSource(), pa, pn.businessId);
                parentIds.put(type, parentId);
            });
            for (final Map.Entry<I, PId<B>> pid : parentIds.entrySet()) {
                final I key = pid.getKey();
                if (!requiredPIds.contains(key) && key != main) {
                    final Member type = pid.getValue().property.getSource();
                    throw new BeanException(type, "must not be annotated with @ReferenceId(\"" + key.getCode() + "\")");
                }
            }
            return Collections.unmodifiableMap(parentIds);
        }

        private void checkParentIds(Class<?> type, Map<I, PId<B>> parentIds) throws BeanException {
            for (final I key : requiredPIds) {
                if (!parentIds.containsKey(key)) {
                    throw new BeanException(type, "must have one property annotated with @ReferenceId(\"" + key.getCode() + "\")");
                }
            }
        }

        private List<VId<B>> findVirtualIds(Class<?> type, Map<I, PId<B>> parentIds) throws BeanException {
            final int size = requiredPIds.size() - parentIds.size();
            if (size == 0) {
                return List.of();
            }
            final List<VId<B>> result = new ArrayList<>(size);
            for (final I code : requiredPIds) {
                if (parentIds.containsKey(code)) {
                    continue;
                }
                final Node<?, B, ?> parent = parents.get(code);
                if (parent.businessId instanceof PId<?> dp) {
                    final VContext best = dp.context(parent.node.context);
                    //noinspection unchecked,RedundantCast
                    result.add(new VId<>(code, ((PId<B>) dp).property, best));
                } else {
                    throw new BeanException(type, "must have one property annotated with @BusinessId");
                }
            }
            return List.copyOf(result);
        }
    }

    /**
     * Internal Tree Context.
     */
    static final class TContext<B extends ITerm<B>, I extends Enum<I> & IType<I>> {
        private final List<List<Sublink>> replaces = new LinkedList<>();
        private final List<List<String>> excludes = new LinkedList<>();
        private final List<Class<? extends Segment>> types;
        private final Map<I, Node<?, B, ?>> parents;
        private final Map<I, Overlink> overrides;
        private final TypeAdapter<I> parser;
        private final List<I> requiredPIds;
        private final List<I> paths;
        private final Class<I> type;
        private final IMode mode;
        private final I main;

        TContext(IMode mode, Class<I> type, Class<Segment> sc) throws BeanException {
            this.mode = mode;
            this.type = type;
            final BusinessObject bo;
            this.types = new LinkedList<>();
            this.paths = new LinkedList<>();
            this.parents = new EnumMap<>(type);
            this.requiredPIds = new LinkedList<>();
            if ((bo = sc.getAnnotation(BusinessObject.class)) == null) {
                throw new BeanException(sc, "must be annotated with @BusinessObject");
            }
            this.parser = new CodeListAdapter<>(type, ERROR, EC_CODE_LIST);
            this.main = parser.parse(bo.value());
            this.overrides = new EnumMap<>(type);
            for (final Overlink c : bo.overrides()) {
                if (overrides.put(parser.parse(c.value().value()), c) != null) {
                    throw new BeanException(sc, "@BusinessObject(overrides = {" + name(c) + "}) must be unique");
                }
            }
            this.pushLink(null);
        }

        I parse(Member src, BusinessLink link) throws BeanException {
            final String key = link.value();
            if (excludes.getLast().contains(key)) {
                return null;
            }
            final I code = parser.parse(key);
            final List<Sublink> mps = replaces.getLast().stream().filter(m -> m.value().equals(key)).toList();
            if (mps.size() > 1) {
                throw new BeanException(src, name(link) + " - multiple mappings for " + code.getCode());
            }
            if (mps.size() == 1) {
                return parser.parse(mps.getFirst().with());
            }
            return code;
        }

        Class<Segment> type(Class<Segment> pc, Field src, I code, BusinessLink link) throws BeanException {
            final Overlink override = overrides.get(code);
            final Linker linker = getLinker(override, link);
            if (linker.value() == BusinessLink.Void.class) {
                return linkerType(pc, src, 1, link);
            }
            final Type[] types = getTypeArguments(linker.value(), ILinker.class);
            if (!(types[0] instanceof Class<?> hc) || !(types[1] instanceof Class<?> st)) {
                throw new BeanException(src, "@BusinessLink[bean] must not be generic class");
            }
            if (!hc.isAssignableFrom(pc)) {
                final String p = getTypeName(pc);
                throw new BeanException(src, "@BusinessLink[bean] should implements ILinker<" + p + ", ?>");
            }
            final Class<? extends Segment> tc = getTarget(override, link, linker);
            if (tc == Segment.class) {
                return cast(st);
            }
            if (!st.isAssignableFrom(tc)) {
                final String p = getTypeName(pc);
                final String c = getTypeName(tc);
                throw new BeanException(src, "@BusinessLink[bean] should implements ILinker<" + p + ", " + c + ">");
            }
            return cast(tc);
        }

        void pushNode(Class<? extends Segment> sc, I type, Node<?, B, ?> node) {
            types.addLast(sc);
            paths.addLast(type);
            parents.put(type, node);
        }

        void pollNode() {
            types.removeLast();
            final I last = paths.removeLast();
            parents.remove(last);
        }

        void pushLink(I code) {
            final Overlink override = overrides.get(code);
            if (override != null) {
                replaces.add(List.of(override.replaces()));
                excludes.add(List.of(override.excludes()));
            } else {
                replaces.add(List.of());
                excludes.add(List.of());
            }
        }

        void pollLink() {
            replaces.removeLast();
        }

        boolean requireKey(I node, BusinessLink spec, List<?> links) {
            return mode != NEAT && node != main && spec.max() > 1 && !links.isEmpty();
        }

        void pushKey(I type) {
            requiredPIds.addLast(type);
        }

        void pollKey() {
            requiredPIds.removeLast();
        }

        Stream<I> paths() {
            return paths.stream();
        }

        String path() {
            return types.stream().map(Beans::getTypeName).collect(joining(" > "));
        }

        I type() {
            return main;
        }

        void finalize(Class<Segment> bt) throws BeanException {
            if (!overrides.isEmpty()) {
                final String ls = overrides.values().stream().map(BSBuilder::name).collect(joining(", "));
                throw new BeanException(bt, "unknown @BusinessObject(overrides = {" + ls + "})");
            }
        }
    }

    /**
     * Internal Reference Identifier
     */
    static final class RId<B extends ITerm<B>> implements MEP {
        final int index, offset;
        final PId<B> reference;
        private final String code;

        private <I extends Enum<I> & IType<I>> RId(Map.Entry<I, PId<B>> entry, int offset) {
            final I type = entry.getKey();
            this.index = type.ordinal();
            this.code = type.getCode();
            this.reference = entry.getValue();
            this.offset = this.reference.property.offset + offset;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    /**
     * Internal Virtual Identifier
     */
    static final class VId<B extends ITerm<B>> implements MEP, CodeList<VId<B>> {
        final int index;
        private final PPosition<Object, B> property;
        private final VContext context;
        private final String code;

        private <I extends Enum<I> & IType<I>> VId(I type, PPosition<Object, B> property, VContext context) {
            this.index = type.ordinal();
            this.code = type.getCode();
            this.property = property;
            this.context = context;
        }

        Object parse(String data, int offset, BusinessHandler<B> handler, Validator validator) {
            final Object value = property.parse(data, offset, handler);
            if (context.enabled(validator)) {
                final String name = property.getName();
                final Class<?> st = property.getSource().getDeclaringClass();
                for (final ConstraintViolation<?> cv : validator.validateValue(st, name, value, context.groups)) {
                    handler.handle(property.dataType, property.offset + offset, cv, property.error);
                }
            }
            return value;
        }

        @Override
        public String getCode() {
            return Optional.ofNullable(property.dataType).map(ITerm::getCode).orElse("KEY");
        }

        @Override
        public String getName() {
            if (property.dataType == null) {
                return CodeList.toString(code, "Reference");
            }
            return property.dataType.getName();
        }
    }

}
