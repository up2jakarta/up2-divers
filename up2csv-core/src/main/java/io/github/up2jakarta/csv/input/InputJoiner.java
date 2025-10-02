package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.Segment;

import java.util.List;
import java.util.function.Function;

public abstract class InputJoiner<T extends Segment, P extends Segment> {

    final Class<T> type;
    final Class<P> parentType;
    private final Function<P, List<T>> joins;

    protected InputJoiner(Class<P> parentType, Class<T> type, ManyJoin<P, T> getter) {
        this.parentType = parentType;
        this.type = type;
        this.joins = getter;
    }

    protected InputJoiner(Class<P> parentType, Class<T> type, SingleJoin<P, T> getter) {
        this(parentType, type, getter.many());
    }

    public final Class<? extends T> getClassType() {
        return type;
    }

    public final Class<? extends P> getParentType() {
        return parentType;
    }

    public final List<T> joins(P parent) {
        return joins.apply(parent);
    }

}
