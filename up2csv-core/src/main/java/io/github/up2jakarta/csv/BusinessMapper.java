package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputType;
import io.github.up2jakarta.csv.misc.BeanException;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

abstract class BusinessMapper<B extends DataType<B>, I extends Enum<I> & InputType<B, I>, T extends Segment, S extends Segment> {

    protected final I root;
    protected final int offset;

    private final Map<InputType<B, I>, Mapper<S, B>> mappers;
    private final Map<InputType<B, I>, Set<I>> joins;

    BusinessMapper(MapperFactory<B> factory, I root, Class<T> rootType) throws BeanException {
        if (root.getClassType() != rootType) {
            throw new BeanException(rootType, "Invalid business mapping");
        }
        this.root = root;
        //noinspection unchecked
        final I[] values = ((Class<I>) root.getClass()).getEnumConstants();
        final Map<InputType<B, I>, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(factory, root, values, joins::put);
        this.joins = Collections.unmodifiableMap(joins);
        this.offset = this.getMapper(root).getOffset();
    }

    private Map<InputType<B, I>, Mapper<S, B>> joins(MapperFactory<B> factory, I node, I[] values, BiConsumer<InputType<B, I>, Set<I>> handler) throws BeanException {
        requireNonNull(node.getBusinessType());
        final Map<InputType<B, I>, Mapper<S, B>> result = new LinkedHashMap<>();
        //noinspection unchecked
        result.put(node, (Mapper<S, B>) factory.build(node.getClassType(), node.getBusinessType()));
        final Set<I> children = new LinkedHashSet<>();
        for (final I value : values) {
            if (node.holds(value)) {
                children.add(value);
                final Map<InputType<B, I>, Mapper<S, B>> mappers = this.joins(factory, value, values, handler);
                result.putAll(mappers);
            }
        }
        handler.accept(node, Collections.unmodifiableSet(children));
        return result;
    }

    protected final Mapper<S, B> getMapper(InputType<B, I> type) {
        return mappers.get(type);
    }

    protected final Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    protected final boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

}
