package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.misc.LazyCounter;

import java.util.*;
import java.util.function.IntSupplier;

final class LazyList<R extends IRecord<?>, E extends IError<R, ?, ?>> {

    private final Set<E> errors;
    private final Set<Integer> offsets;
    private final LazyCounter counter;

    LazyList(final IntSupplier counter) {
        this.errors = new LinkedHashSet<>();
        this.offsets = new LinkedHashSet<>();
        this.counter = new LazyCounter(counter);
    }

    /**
     * Appends the specified error to the end of this list and set key order.
     *
     * @param error error to be appended to this list
     */
    void addWithOrder(Integer offset, E error, boolean unique) {
        if (error != null) {
            synchronized (errors) {
                final int order = counter.getAsInt() + errors.size();
                if (error.getKey() != null) {
                    error.getKey().setOrder(order);
                }
                errors.add(error);
                if (unique) {
                    offsets.add(offset);
                }
            }
        }
    }

    void addTo(Collection<E> target) {
        target.addAll(errors);
    }

    List<E> toList() {
        return new ArrayList<>(errors);
    }

    boolean contains(Integer offset) {
        return offsets.contains(offset);
    }

}
