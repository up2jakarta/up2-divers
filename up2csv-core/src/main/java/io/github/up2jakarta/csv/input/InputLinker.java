package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.Parsed;

import java.util.function.BiConsumer;

public abstract class InputLinker<I extends InputType<I>, T extends Parsed<I, ?>, P extends Parsed<I, ?>> {

    final Class<T> type;
    final Class<P> parentType;
    private final BiConsumer<Parsed<I, ?>, Parsed<I, ?>> linker;

    protected InputLinker(Class<P> parentType, Class<T> type, BiConsumer<P, T> linker) {
        this.parentType = parentType;
        this.type = type;
        //noinspection unchecked
        this.linker = (BiConsumer<Parsed<I, ?>, Parsed<I, ?>>) linker;
    }

    public final Class<? extends T> getType() {
        return type;
    }

    public final Class<? extends P> getParentType() {
        return parentType;
    }

    public final void link(Parsed<I, ?> parent, Parsed<I, ?> child) {
        linker.accept(parent, child);
    }

}
