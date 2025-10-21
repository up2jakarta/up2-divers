package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.slv.CodeListResolver.checkUnique;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Internal business-mapping implementation for aggregation and segregation processing.
 */
public abstract class BusinessProcessor<B extends DataType<B>, I extends IType<B, I>, V extends BeanValidator<Segment, B>> {

    protected final I root;
    protected final int offset;
    protected final ModeType mode;
    protected final List<I> nodes;

    private final Up2Factory<B> factory;
    private final Map<IType<B, I>, V> mappers;
    private final Map<IType<B, I>, Set<I>> joins;

    BusinessProcessor(Up2Factory<B> factory, Class<?> type, ModeType mode, I root, I[] nodes) throws BeanException {
        requireNonNull(factory, "factory is required");
        requireNonNull(root, "root is required");
        checkUnique(type, nodes);
        if (!type.equals(root.getClassType())) {
            throw new BeanException(type, "Invalid business typing");
        }
        this.root = root;
        this.mode = mode;
        this.factory = factory;
        this.nodes = List.of(nodes);
        final Map<I, Set<I>> joins = new HashMap<>();
        this.mappers = this.joins(new Stack<>(), root, joins::put);
        this.joins = unmodifiableMap(joins);
        this.offset = offset(mappers.get(root), mode);
    }

    BusinessProcessor(BusinessProcessor<B, I, ? extends BeanValidator<Segment, B>> source) throws BeanException {
        this.root = source.root;
        this.mode = source.mode;
        this.factory = source.factory;
        this.joins = source.joins;
        this.offset = source.offset;
        this.nodes = source.nodes;
        this.mappers = this.joins(root, source.mappers);
    }

    private static int offset(BeanValidator<?, ?> mapper, ModeType mode) throws BeanException {
        final int min = mode.getBeanIdIndex();
        if (mapper.offset != 0) {
            if (mapper.offset < min) {
                throw new BeanException(mapper.type, "@Truncated[value] must be greater or equals to " + min);
            }
            return mapper.offset;
        }
        return min;
    }

    private Map<IType<B, I>, V> joins(I rn, Map<IType<B, I>, ? extends BeanValidator<Segment, B>> source) throws BeanException {
        final Map<IType<B, I>, V> result = new LinkedHashMap<>();
        result.put(rn, this.build(factory, rn, source.get(rn)));
        for (final I node : this.getJoins(rn)) {
            final Map<IType<B, I>, V> mappers = this.joins(node, source);
            result.putAll(mappers);
        }
        return unmodifiableMap(result);
    }

    private Map<IType<B, I>, V> joins(Stack<I> cp, I rn, BiConsumer<I, Set<I>> cb) throws BeanException {
        final Map<IType<B, I>, V> result = new LinkedHashMap<>();
        final V pm = this.build(factory, rn, null);
        cp.push(rn);
        result.put(rn, pm);
        final Set<I> children = new LinkedHashSet<>();
        for (final I node : nodes) {
            if (rn.holds(node)) {
                if (cp.contains(node)) {
                    final String p = cp.stream().map(i -> i.joiner().toString()).collect(joining(" > "));
                    throw new BeanException(node.getClass(), node.getCode(), "cyclic segment is not allowed: " + p);
                }
                children.add(node);
                final Map<IType<B, I>, V> mappers = this.joins(cp, node, cb);
                this.check(node.getName(), mappers.get(node), pm);
                result.putAll(mappers);
            }
        }
        cp.pop();
        cb.accept(rn, unmodifiableSet(children));
        return unmodifiableMap(result);
    }

    abstract V build(Up2Factory<B> factory, I type, BeanValidator<Segment, B> source) throws BeanException;

    abstract void check(String name, V node, V parent) throws BeanException;

    V get(IType<B, I> type) {
        return mappers.get(type);
    }

    Set<I> getJoins(I type) {
        return joins.getOrDefault(type, Set.of());
    }

    boolean hasJoins(I type) {
        return joins.containsKey(type);
    }

}
