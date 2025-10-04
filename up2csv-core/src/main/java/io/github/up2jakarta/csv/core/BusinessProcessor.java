package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

public abstract class BusinessProcessor<B extends DataType<B>, I extends IType<B, I>, T extends Segment, S extends Segment> {

    protected final I root;
    protected final int offset;
    private final Map<IType<B, I>, Set<I>> joins;
    private final Map<IType<B, I>, Mapper<S, B>> mappers;

    protected <E extends Enum<E> & IType<B, I>> BusinessProcessor(MapperFactory<B> factory, E root, Class<T> rootType) throws BeanException {
        //noinspection unchecked
        this(factory, (I) root, ((Class<I>) root.getClass()).getEnumConstants(), rootType);
    }

    protected BusinessProcessor(MapperFactory<B> factory, I root, I[] values, Class<T> rootType) throws BeanException {
        if (root.getClassType() != rootType) {
            throw new BeanException(rootType, "Invalid business mapping");
        }
        this.root = root;
        final Map<IType<B, I>, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(factory, root, values, joins::put);
        this.joins = Collections.unmodifiableMap(joins);
        this.offset = this.getMapper(root).offset;
    }

    private Map<IType<B, I>, Mapper<S, B>> joins(
            MapperFactory<B> factory, I node, I[] values, BiConsumer<IType<B, I>, Set<I>> handler
    ) throws BeanException {
        requireNonNull(node.getBusinessType());
        final Map<IType<B, I>, Mapper<S, B>> result = new LinkedHashMap<>();
        //noinspection unchecked
        result.put(node, (Mapper<S, B>) factory.build(node.getClassType(), node.getBusinessType()));
        final Set<I> children = new LinkedHashSet<>();
        for (final I value : values) {
            if (node.holds(value)) {
                children.add(value);
                final Map<IType<B, I>, Mapper<S, B>> mappers = this.joins(factory, value, values, handler);
                result.putAll(mappers);
            }
        }
        handler.accept(node, Collections.unmodifiableSet(children));
        return result;
    }

    protected final Mapper<S, B> getMapper(IType<B, I> type) {
        return mappers.get(type);
    }

    protected final Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    protected final boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

}
