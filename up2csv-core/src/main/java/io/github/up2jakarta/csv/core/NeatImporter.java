package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.data.BusinessCreator;
import io.github.up2jakarta.csv.hdl.BusinessHandler;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.core.BSAccessor.Input;
import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Up2J {@link BusinessImporter} implementation for compact mode without import of
 * {@link io.github.up2jakarta.csv.BusinessId} for each underlying segment when
 * {@link io.github.up2jakarta.csv.BusinessLink#automatic()} is enabled.
 * <p>
 * This implementation requires the input records to be ordered like tree structure.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see NeatExporter
 */
public abstract non-sealed class NeatImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    /**
     * Constructor for {@link ModeType#NEAT} importer.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    protected NeatImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.NEAT, factory, st, it);
    }

    protected NeatImporter(NeatExporter<B, I, T> source) throws BeanException {
        super(source);
    }

    @Override
    public NeatExporter<B, I, T> toExporter() throws BeanException {
        return new NeatExporter<>(this);
    }

    @Override
    final <C> C doParse(Collection<R> records, BusinessCreator<C, T, E> creator) {
        final IEventBuilder<B, R, E> builder = notNull(this.newBuilder(records.size()), this.getClass(), "builder");
        final Context<B, I> context = new Context<>(tree);
        for (final R record : records) {
            if (record == null) continue;
            final BusinessHandler<B> handler = notNull(builder.of(record), builder.getClass(), "handler");
            final BSLink<B, I, Mapper<Segment, B>> link = this.map(record, handler);
            if (link == null) continue;
            final Segment bean = this.parse(link, record, handler);
            if (this.tree == link) {
                context.push(bean, handler);
            } else {
                context.push(link, bean, handler);
            }
        }
        return creator.apply(context.bean(this::link), builder.toList());
    }

    private void link(Node<B> parent) {
        var i = 0;
        for (final BSLink<B, ?, ?> node : parent.context.links) {
            final LinkedList<Node<B>> children = parent.nodes[i];
            if (this.validate(node, parent.handler, children, Node::handle)) {
                final boolean hasLinks = !node.links.isEmpty();
                for (final Node<B> child : children) {
                    node.link(parent.bean, child.bean, parent.context, child.handler);
                    if (hasLinks) {
                        this.link(child);
                    }
                }
            }
            i++;
        }
    }

    private Segment parse(BSLink<B, I, Mapper<Segment, B>> ln, R record, BusinessHandler<B> hd) {
        final Mapper<Segment, B> mapper = ln.computer;
        final Input in = Input.of(factory.validator, ln.offset, record.getData());
        final Segment bean = mapper.node.parse(in, hd);
        if (mapper.node.recordable) {
            mapper.node.update(bean, record, e -> hd.handle(ln.event, ln.term, "cannot set the source record", e));
        }
        if (mapper.validate) {
            mapper.node.validate(factory.validator, bean, in.offset, hd);
        }
        return bean;
    }

    /**
     * Internal Tree Context
     */
    private static final class Context<B extends ITerm<B>, I extends Enum<I> & IType<I>> {
        private final List<BusinessHandler<B>> roots = new LinkedList<>();
        private final LinkedList<Node<B>> nodes = new LinkedList<>();
        private final LinkedList<Node<B>> path = new LinkedList<>();
        private final BSLink<B, I, ?> tree;

        private Context(BSLink<B, I, ?> tree) {
            this.tree = tree;
        }

        <T extends Segment> T bean(Consumer<Node<B>> linker) {
            return switch (roots.size()) {
                case 1:
                    final Node<B> main = path.getFirst();
                    linker.accept(main);
                    //noinspection unchecked
                    yield (T) main.bean;
                case 0:
                    nodes.forEach(n -> n.handle(DETACHED));
                    yield null;
                default:
                    roots.forEach(r -> r.handle(tree.event, tree.term, ONLY_ONE));
                    yield null;
            };
        }

        void push(BSLink<B, I, ?> link, Segment bean, BusinessHandler<B> handler) {
            for (var it = path.listIterator(path.size()); it.hasPrevious(); ) {
                final Node<B> next = it.previous();
                final int index = next.context.links.indexOf(link);
                if (index >= 0) {
                    final Node<B> parent = it.next();
                    for (; it.hasNext(); it.remove()) {
                        it.next();
                    }
                    final Node<B> child = new Node<>(link, bean, handler);
                    parent.nodes[index].add(child);
                    if (!link.links.isEmpty()) {
                        this.path.addLast(child);
                    }
                    this.nodes.add(child);
                    return;
                }
            }
            handler.handle(link.event, link.term, DETACHED);
        }

        void push(Segment bean, BusinessHandler<B> handler) {
            roots.add(handler);
            if (roots.size() == 1) {
                this.path.addLast(new Node<>(tree, bean, handler));
            }
        }
    }

    /**
     * Internal Tree Node
     */
    private static final class Node<B extends ITerm<B>> {
        private final LinkedList<Node<B>>[] nodes;
        private final BusinessHandler<B> handler;
        private final BSLink<B, ?, ?> context;
        private final Segment bean;

        private Node(BSLink<B, ?, ?> context, Segment bean, BusinessHandler<B> handler) {
            this.bean = bean;
            this.handler = handler;
            this.context = context;
            final int length = context.links.size();
            //noinspection unchecked
            this.nodes = new LinkedList[length];
            for (var i = 0; i < length; i++) {
                this.nodes[i] = new LinkedList<>();
            }
        }

        private void handle(String message) {
            handler.handle(context.event, context.term, message);
        }

        @Override
        public String toString() {
            return context.toString();
        }
    }

}