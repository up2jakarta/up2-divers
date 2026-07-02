package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.core.BSAccessor.Input;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSLink.VId;
import io.github.up2jakarta.csv.core.BSOperator.Format;
import io.github.up2jakarta.csv.core.BSOperator.Mapper;
import io.github.up2jakarta.csv.core.hdl.BusinessHandler;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.Listable;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.api.IEvent.EC_COMPLIANCE;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.cast;
import static java.util.Arrays.copyOfRange;

/**
 * Up2J Base Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see FastImporter
 * @see FullImporter
 * @see UnitImporter
 */
public abstract sealed class BusinessImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>>
        extends BSOperator<B, I, Mapper<Segment, B>, Format<Segment, B>>
        permits UnitImporter, FastImporter, FullImporter {

    /**
     * Constant holding the event message for detached segments.
     */
    public static final String DETACHED = "must not be detached";

    private final TypeConverter<I> parser;
    private final Map<I, BSLink<B, I, Mapper<Segment, B>>> mapper;

    BusinessImporter(ModeType mode, Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(mode, factory, cast(st), it);
        this.mapper = tree.toMapper(it);
        this.parser = tree.toParser(it);
    }

    BusinessImporter(BusinessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
        this.mapper = tree.toMapper(type);
        this.parser = tree.toParser(type);
    }

    private void link(Object[] ids, Item<B, I> parent, LinkedList<Node<B, I>> rs) {
        final int idx = parent.context.key.ordinal();
        ids[idx] = parent.businessId;
        for (final BSLink<B, I, Mapper<Segment, B>> node : parent.context.links) {
            final LinkedList<Node<B, I>> children = new LinkedList<>();
            for (var it = rs.iterator(); it.hasNext(); ) {
                final Node<B, I> item = it.next();
                if (node == item.context && item.matches(ids)) {
                    children.add(item);
                    it.remove();
                }
            }
            var size = children.size();
            if (node.notValid(size)) {
                if (size == 0) {
                    parent.handler.handle(node.event, node.term, node.message());
                } else {
                    children.forEach(e -> e.handle(node.message()));
                }
                continue;
            }
            final boolean hasLinks = !node.links.isEmpty();
            for (final Node<B, I> child : children) {
                try {
                    node.link(parent.bean, child.bean);
                } catch (RuntimeException cause) {
                    assert size == size-- : "for linkage issue detection";
                    child.handle("cannot link with Segment#[" + parent.context.key.getCode() + ']', cause);
                }
                if (hasLinks) {
                    this.link(ids, child, rs);
                }
            }
            assert size == node.from(parent.bean).size() : "linkage issue has been detected";
        }
        ids[idx] = null;
    }

    private Listable<E> map(Collection<R> records, Consumer<Main<B, I>> root, Consumer<Node<B, I>> node) {
        final IEventBuilder<B, R, E> builder = notNull(this.newBuilder(records.size()), this.getClass(), "builder");
        for (final R record : records) {
            if (record == null) continue;
            final BusinessHandler<B> handler = notNull(builder.of(record), builder.getClass(), "handler");
            final I type = record.getType();
            if (type == null) {
                handler.handle(ERROR, EC_COMPLIANCE, null, mode.typeIdIndex, "must not be null");
                continue;
            }
            final String[] data = record.getData();
            final BSLink<B, I, Mapper<Segment, B>> link = mapper.get(type);
            if (link == null) {
                handler.handle(WARNING, EC_COMPLIANCE, null, mode.typeIdIndex, DETACHED);
            } else if (data == null) {
                handler.handle(link.event, link.term, "must not be null");
            } else if (this.tree == link) {
                root.accept(new Main<>(handler, link, record));
            } else if (link.length == 0) {
                node.accept(new SNode<>(handler, link, record));
            } else if (data.length < link.index) {
                handler.handle(link.event, link.term, DETACHED);
            } else {
                final CNode<B, I> cn = new CNode<>(mode, handler, link, record);
                if (cn.isDetached()) {
                    cn.handle(DETACHED);
                } else {
                    node.accept(cn);
                }
            }
        }
        return builder;
    }

    /**
     * Parses the segment type then truncates the data columns aka without meta-data.
     *
     * @param creator the record creator
     * @param record  the input record
     * @return the final record instance
     */
    public final R transform(BiFunction<I, String[], R> creator, String... record) {
        final String code = record[mode.typeIdIndex];
        final I key = parser.parse(code);
        final int offset = (key == tree.key) ? tree.offset : mode.length;
        final String[] data = copyOfRange(record, offset, record.length, String[].class);
        return creator.apply(key, data);
    }

    @Override
    final Mapper<Segment, B> build(Up2Factory<B> factory, Class<Segment> type, Format<Segment, B> source) throws BeanException {
        if (source != null) {
            return source.reverse();
        }
        return factory.mp(type);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final <C> C parse(Collection<R> records, BusinessCreator<C, T, E> creator) throws AccessException {
        if (records == null) {
            return null;
        } else if (records.isEmpty()) {
            return creator.apply(null, List.of());
        }
        final LinkedList<Main<B, I>> roots = new LinkedList<>();
        final LinkedList<Node<B, I>> nodes = new LinkedList<>();
        final Listable<E> store = this.map(records, roots::add, nodes::add);
        final T invoice = switch (roots.size()) {
            case 1:
                final Item<B, I> main = roots.getFirst();
                this.link(new Object[length], main, nodes);
                nodes.forEach(n -> n.handle(DETACHED));
                //noinspection unchecked
                yield (T) main.bean;
            case 0:
                nodes.forEach(n -> n.handle(DETACHED));
                yield null;
            default:
                final String msg = this.tree.message();
                roots.forEach(r -> r.handle(msg));
                yield null;
        };
        return creator.apply(invoice, store.toList());
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code>
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final <C> C parse(R[] records, BusinessCreator<C, T, E> creator) throws AccessException {
        if (records == null) {
            return null;
        }
        return this.parse(Arrays.asList(records), creator);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the array of segments
     * @return the business-object with collected errors
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final Up2Result<T, E> parse(R[] records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Parses, validates and aggregates the given records.
     *
     * @param records the collection of segments
     * @return the business-object with collected errors
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final Up2Result<T, E> parse(Collection<R> records) throws AccessException {
        return this.parse(records, Up2Result::new);
    }

    /**
     * Creates and returns a new valid event-builder for the specified record's length.
     *
     * @param length the length of records, it's helpful for collection size initializing.
     * @return a new business event-builder, must not be <code>null</code>
     */
    protected abstract IEventBuilder<B, R, E> newBuilder(int length);

    /**
     * Internal Object Identifier
     */
    private static final class OId {
        private final int index;
        private final Object value;

        private OId(int index, Object value) {
            this.index = index;
            this.value = value;
        }
    }

    /**
     * Internal Simple Node without identifiers
     */
    private static final class SNode<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Node<B, I> {
        private SNode(BusinessHandler<B> handler, BSLink<B, I, Mapper<Segment, B>> link, IRecord<I> record) {
            super(handler, link, record);
        }

        @Override
        protected boolean matches(Object[] ids) {
            return true;
        }
    }

    /**
     * Internal Complex Node within identifiers
     */
    private static final class CNode<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Node<B, I> {
        private final List<OId> parentIds;

        private CNode(ModeType mode, BusinessHandler<B> handler, BSLink<B, I, Mapper<Segment, B>> link, IRecord<I> record) {
            super(handler, link, record);
            this.parentIds = this.parentIds(mode, record.getData());
        }

        private List<OId> parentIds(ModeType mode, String[] src) {
            final List<OId> pids = new ArrayList<>(context.length);
            if (context.length != context.index) {
                for (final RId<B> rid : context.parentIds) {
                    try {
                        pids.add(new OId(rid.index, rid.reference.get(bean)));
                    } catch (RuntimeException cause) {
                        this.handle("cannot retrieve the @ReferenceId(\"" + rid + "\") value", cause);
                        pids.add(new OId(rid.index, null));
                    }
                }
            }
            if (context.index != 0) {
                var i = 0;
                for (final VId<B> vid : context.virtualIds) {
                    final Object pid = vid.parse(src[i], i + mode.length, handler, context.computer.validator);
                    pids.add(new OId(vid.index, pid));
                    i++;
                }
            }
            return pids;
        }

        @Override
        protected boolean matches(Object[] ids) {
            for (final OId oid : parentIds) {
                final Object pid = oid.value;
                if (!pid.equals(ids[oid.index])) {
                    return false;
                }
            }
            return true;
        }

        private boolean isDetached() {
            for (final OId oid : parentIds) {
                if (oid.value == null) return true;
            }
            return false;
        }
    }

    /**
     * Internal Node Entry
     */
    private static abstract sealed class Node<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Item<B, I> permits SNode, CNode {
        private Node(BusinessHandler<B> handler, BSLink<B, I, Mapper<Segment, B>> link, IRecord<I> record) {
            super(handler, link, record);
        }

        protected abstract boolean matches(Object[] ids);
    }

    /**
     * Internal Main Entry
     */
    private static final class Main<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Item<B, I> {
        private Main(BusinessHandler<B> handler, BSLink<B, I, Mapper<Segment, B>> link, IRecord<I> record) {
            super(handler, link, record);
        }
    }

    /**
     * Internal Business Entry
     */
    static abstract sealed class Item<B extends ITerm<B>, I extends Enum<I> & IType<I>> implements MEP permits Main, Node {
        protected final BSLink<B, I, Mapper<Segment, B>> context;
        protected final BusinessHandler<B> handler;
        protected final Object businessId;
        protected final Segment bean;

        private Item(BusinessHandler<B> handler, BSLink<B, I, Mapper<Segment, B>> link, IRecord<I> record) {
            this.context = link;
            this.handler = handler;
            final Mapper<Segment, B> mapper = link.computer;
            final Input in = Input.of(mapper.validator, link, record.getData());
            this.bean = mapper.node.parse(in, handler);
            if (mapper.node.recordable) {
                mapper.node.update(bean, record, e -> this.handle("cannot set the source record", e));
            }
            this.businessId = link.bid.apply(this, record);
            if (mapper.validate) {
                mapper.node.validate(mapper.validator, bean, in.offset, handler);
            }
        }

        protected final Object nullId(IRecord<?> ignore) {
            return null;
        }

        protected final Object nodeId(IRecord<?> ignore) {
            try {
                return context.computer.businessId.get(bean);
            } catch (RuntimeException cause) {
                this.handle("cannot retrieve the business identifier", cause);
                return null;
            }
        }

        protected final Object mainId(IRecord<?> source) {
            final String pivot = ((IFastRecord<?>) source).getPivot();
            final Object id = context.computer.businessId.parse(pivot, context.offset, handler);
            if (id != null) {
                try {
                    context.computer.businessId.set(bean, id);
                } catch (RuntimeException cause) {
                    this.handle("cannot update the business identifier", cause);
                }
            }
            return id;
        }

        protected final void handle(String message, RuntimeException cause) {
            handler.handle(context.event, context.term, message, cause);
        }

        protected final void handle(String message) {
            handler.handle(context.event, context.term, message);
        }

        @Override
        public final String toString() {
            return context.toString();
        }
    }

}
