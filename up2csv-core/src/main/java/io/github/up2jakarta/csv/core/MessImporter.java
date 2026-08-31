package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.*;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.core.BSAccessor.Input;
import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSLink.VId;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.cast;

/**
 * Up2J {@link BusinessImporter} implementation for any mode based on the matching of
 * {@link io.github.up2jakarta.csv.ReferenceId} with {@link io.github.up2jakarta.csv.BusinessId}
 * for each parent-child relationship.
 * <p>
 * This implementation does not require input records to be ordered.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see MessExporter
 */
public abstract non-sealed class MessImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IMessRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    /**
     * Constructor for extended {@link ModeType#MESS} importer without extra meta-data columns.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    protected MessImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.MESS, factory, cast(st), it);
    }

    /**
     * Constructor for extended {@link ModeType#MESS} importer within extra meta-data columns.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @param size    the size for extra-data columns
     * @throws BeanException for any missing or wrong bean configuration
     */
    protected MessImporter(Up2Factory<B> factory, Class<T> st, Class<I> it, int size) throws BeanException {
        super(XMode.of(size), factory, cast(st), it);
    }

    protected MessImporter(MessExporter<B, I, T> exporter) throws BeanException {
        super(exporter);
    }

    @Override
    public MessExporter<B, I, T> toExporter() throws BeanException {
        return new MessExporter<>(this);
    }

    private void link(Object[] ids, Item<B, I> parent, LinkedList<Node<B, I>> rs) {
        final int idx = parent.context.key.ordinal();
        ids[idx] = parent.businessId;
        for (final BSLink<B, I, ?> node : parent.context.links) {
            final LinkedList<Node<B, I>> children = new LinkedList<>();
            for (var it = rs.iterator(); it.hasNext(); ) {
                final Node<B, I> item = it.next();
                if (node == item.context && item.matches(ids)) {
                    children.add(item);
                    it.remove();
                }
            }
            if (this.validate(node, parent.handler, children, Node::handle)) {
                final boolean hasLinks = !node.links.isEmpty();
                for (final Node<B, I> child : children) {
                    node.link(parent.bean, child.bean, parent.context, child.handler);
                    if (hasLinks) {
                        this.link(ids, child, rs);
                    }
                }
            }
        }
        ids[idx] = null;
    }

    private Listable<E> map(Collection<R> records, Consumer<Main<B, I>> root, Consumer<Node<B, I>> node) {
        final IEventBuilder<B, R, E> builder = notNull(this.newBuilder(records.size()), this.getClass(), "builder");
        for (final R record : records) {
            if (record == null) continue;
            final BusinessHandler<B> handler = notNull(builder.of(record), builder.getClass(), "handler");
            final BSLink<B, I, Mapper<Segment, B>> link = this.map(record, handler);
            if (link == null) continue;
            if (this.tree == link) {
                root.accept(new Main<>(factory.validator, handler, link, record));
            } else if (link.length == 0) {
                node.accept(new SNode<>(factory.validator, handler, link, record));
            } else if (record.getData().length < link.index) {
                handler.handle(link.event, link.term, DETACHED);
            } else {
                final CNode<B, I> cn = new CNode<>(mode, factory.validator, handler, link, record);
                if (cn.isDetached()) {
                    cn.handle(DETACHED);
                } else {
                    node.accept(cn);
                }
            }
        }
        return builder;
    }

    @Override
    final <C> C doParse(Collection<R> records, BusinessCreator<C, T, E> creator) {
        final LinkedList<Main<B, I>> roots = new LinkedList<>();
        final LinkedList<Node<B, I>> nodes = new LinkedList<>();
        final Listable<E> store = this.map(records, roots::add, nodes::add);
        final T object = switch (roots.size()) {
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
                roots.forEach(r -> r.handle(ONLY_ONE));
                yield null;
        };
        return creator.apply(object, store.toList());
    }

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
        private SNode(Validator v, BusinessHandler<B> h, BSLink<B, I, Mapper<Segment, B>> l, IMessRecord<I> r) {
            super(v, h, l, r);
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

        private CNode(IMode m, Validator v, BusinessHandler<B> h, BSLink<B, I, Mapper<Segment, B>> l, IMessRecord<I> r) {
            super(v, h, l, r);
            this.parentIds = this.parentIds(m, v, r.getData());
        }

        private List<OId> parentIds(IMode mode, Validator validator, String[] src) {
            final List<OId> pids = new ArrayList<>(context.length);
            if (context.length != context.index) {
                for (final RId<B> rid : context.parentIds) {
                    try {
                        pids.add(new OId(rid.index, rid.reference.get(bean)));
                    } catch (RuntimeException cause) {
                        this.handle("cannot retrieve the @ReferenceId(\"" + rid + "\") value", cause);
                        pids.add(new OId(rid.index, null));
                        break;
                    }
                }
            }
            if (context.index != 0) {
                var i = 0;
                for (final VId<B> vid : context.virtualIds) {
                    final Object pid = vid.parse(src[i], i + mode.getLength(), handler, validator);
                    pids.add(new OId(vid.index, pid));
                    i++;
                }
            }
            return pids;
        }

        private boolean isDetached() {
            for (final OId oid : parentIds) {
                if (oid.value == null) return true;
            }
            return false;
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
    }

    /**
     * Internal Node Entry
     */
    private static abstract sealed class Node<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Item<B, I> permits SNode, CNode {
        private Node(Validator v, BusinessHandler<B> h, BSLink<B, I, Mapper<Segment, B>> l, IMessRecord<I> r) {
            super(v, h, l, r);
        }

        protected abstract boolean matches(Object[] ids);
    }

    /**
     * Internal Main Entry
     */
    private static final class Main<B extends ITerm<B>, I extends Enum<I> & IType<I>> extends Item<B, I> {
        private Main(Validator v, BusinessHandler<B> h, BSLink<B, I, Mapper<Segment, B>> l, IMessRecord<I> r) {
            super(v, h, l, r);
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

        private Item(Validator vld, BusinessHandler<B> hdl, BSLink<B, I, Mapper<Segment, B>> ln, IMessRecord<I> rc) {
            this.context = ln;
            this.handler = hdl;
            final Mapper<Segment, B> mapper = ln.computer;
            final Input in = Input.of(vld, ln, rc.getData());
            this.bean = mapper.node.parse(in, hdl);
            if (mapper.node.recordable) {
                mapper.node.update(bean, rc, e -> this.handle("cannot set the source record", e));
            }
            this.businessId = ln.bid.apply(this, rc);
            if (mapper.validate) {
                mapper.node.validate(vld, bean, in.offset, hdl);
            }
        }

        protected final Object nullId(IMessRecord<?> ignore) {
            return null;
        }

        protected final Object nodeId(IMessRecord<?> ignore) {
            try {
                return context.computer.businessId.get(bean);
            } catch (RuntimeException cause) {
                this.handle("cannot retrieve the business identifier", cause);
                return null;
            }
        }

        protected final Object mainId(IMessRecord<?> source) {
            final String pivot = source.getPivot();
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
