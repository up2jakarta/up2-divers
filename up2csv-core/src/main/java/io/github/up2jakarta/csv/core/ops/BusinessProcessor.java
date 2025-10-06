package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Beans;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.github.up2jakarta.csv.core.Beans.join;

abstract class BusinessProcessor<B extends DataType<B>, I extends IType<B, I>, T extends BusinessObject> {

    protected final I rootNode;
    protected final BusinessType<T> config;

    private final int rootOffset;
    private final int defaultOffset;
    private final Map<IType<B, I>, Set<I>> joins;
    private final Map<IType<B, I>, Mapper<Segment, B>> mappers;

    BusinessProcessor(MapperFactory<B> factory, BusinessType<T> type, I root, I[] nodes) throws BeanException {
        this.config = type;
        this.rootNode = root;
        final Map<IType<B, I>, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(factory, root, nodes, joins::put);
        this.joins = Collections.unmodifiableMap(joins);
        final Mapper<Segment, B> mapper = this.getMapper(root);
        this.defaultOffset = this.getMapper(root).getOffset();
        this.config.check(root, this.defaultOffset);
        this.rootOffset = Beans.offset(mapper, type);
    }

    private Map<IType<B, I>, Mapper<Segment, B>> joins(MapperFactory<B> mf, I rn, I[] ns, BiConsumer<IType<B, I>, Set<I>> cb) throws BeanException {
        final Map<IType<B, I>, Mapper<Segment, B>> result = new LinkedHashMap<>();
        final Mapper<Segment, B> pm = mf.build(rn.getClassType(), rn.getBusinessType());
        result.put(rn, pm);
        final Set<I> children = new LinkedHashSet<>();
        for (final I node : ns) {
            if (rn.holds(node)) {
                children.add(node);
                final Map<IType<B, I>, Mapper<Segment, B>> mappers = this.joins(mf, node, ns, cb);
                join(node.getName(), mappers.get(node), pm, rn.getClassType());
                result.putAll(mappers);
            }
        }
        cb.accept(rn, Collections.unmodifiableSet(children));
        return result;
    }

    private void format(Segment bean, int offset, I type, Filler<I> consumer) throws BeanException {
        final String[] data = this.getMapper(type).unmap(bean, offset);
        consumer.accept(bean, type, data);
        for (final I node : this.getJoins(type)) {
            final Collection<Segment> values = node.joiner().joins(bean);
            if (values == null) {
                continue;
            }
            for (var value : values) {
                this.format(value, this.defaultOffset, node, consumer);
            }
        }
    }

    final Mapper<Segment, B> getMapper(IType<B, I> type) {
        return mappers.get(type);
    }

    final Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    final boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

    protected int offset(I type) {
        if (type == rootNode) {
            return rootOffset;
        }
        return defaultOffset;
    }

    protected final void format(T bean, Supplier<String> rowId, Consumer<String[]> callback) throws BeanException {
        if (bean == null) {
            return;
        }
        this.format(bean, rootOffset, rootNode, (s, t, d) -> {
            config.filler.accept(d, rowId, t, bean);
            callback.accept(d);
        });
    }

    @FunctionalInterface
    private interface Filler<I extends IType<?, I>> {

        void accept(Segment source, I type, String[] data);

    }

}
