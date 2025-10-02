package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.Parsed;

import java.util.function.BiConsumer;

public abstract class InputLinker<I extends InputType<?, I>, C extends Parsed<I, ?>, P extends Parsed<I, ?>> extends InputJoiner<C, P> {

    private final BiConsumer<P, C> linker;

    protected InputLinker(Class<P> parentType, Class<C> type, ManyJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    protected InputLinker(Class<P> parentType, Class<C> type, SingleJoin<P, C> getter, BiConsumer<P, C> setter) {
        super(parentType, type, getter);
        this.linker = setter;
    }

    public final void link(P parent, C child) {
        linker.accept(parent, child);
    }

}
