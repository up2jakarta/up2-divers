package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.BusinessEntry.join;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Internal business-mapping implementation for aggregation and segregation processing.
 */
abstract class BusinessMapping<B extends DataType<B>, I extends IType<B, I>> {

    final CLParser<I> parser;
    private final MapperFactory<B> factory;
    private final Map<IType<B, I>, Set<I>> joins;
    private final Map<IType<B, I>, Mapper<Segment, B>> mappers;

    protected BusinessMapping(MapperFactory<B> factory, I root, I[] nodes) throws BeanException {
        requireNonNull(factory, "factory is required");
        requireNonNull(root, "root is required");
        //noinspection unchecked
        this.parser = new CLParser<>((Class<I>) root.getClass(), nodes);
        this.factory = factory;
        final Map<I, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(new Stack<>(), root, joins::put);
        this.joins = unmodifiableMap(joins);
    }

    private Map<IType<B, I>, Mapper<Segment, B>> joins(Stack<I> cp, I rn, BiConsumer<I, Set<I>> cb) throws BeanException {
        final Map<IType<B, I>, Mapper<Segment, B>> result = new LinkedHashMap<>();
        final Mapper<Segment, B> pm = factory.build(rn.getClassType(), rn.getBusinessType());
        cp.push(rn);
        result.put(rn, pm);
        final Set<I> children = new LinkedHashSet<>();
        for (final I node : parser.values) {
            if (rn.holds(node)) {
                if (cp.contains(node)) {
                    final String p = cp.stream().map(i -> i.joiner().toString()).collect(joining(" > "));
                    throw new BeanException(node.getClass(), node.getCode(), "cyclic segment is not allowed: " + p);
                }
                children.add(node);
                final Map<IType<B, I>, Mapper<Segment, B>> mappers = this.joins(cp, node, cb);
                join(node.getName(), mappers.get(node), pm);
                result.putAll(mappers);
            }
        }
        cp.pop();
        cb.accept(rn, unmodifiableSet(children));
        return result;
    }

    Mapper<Segment, B> getMapper(IType<B, I> type) {
        return mappers.get(type);
    }

    Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

}
