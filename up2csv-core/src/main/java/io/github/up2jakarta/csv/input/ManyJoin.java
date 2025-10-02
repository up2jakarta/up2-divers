package io.github.up2jakarta.csv.input;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ManyJoin<P, T> extends Function<P, List<T>> {

    static <I> ManyJoin<I, I> none() {
        return (p) -> List.of();
    }

}
