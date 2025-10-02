package io.github.up2jakarta.csv.input;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface SingleJoin<P, T> extends Function<P, T> {

    default ManyJoin<P, T> many() {
        return (p) -> {
            final T value = this.apply(p);
            if (value == null) {
                return List.of();
            }
            return List.of(value);
        };
    }

}
