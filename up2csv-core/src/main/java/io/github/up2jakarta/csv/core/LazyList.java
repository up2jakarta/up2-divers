package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;

final class LazyList<R extends InputRow, E extends InputError<R, ?>> {

    private final List<E> errors;
    private final LazyCounter counter;

    LazyList(final IntSupplier counter) {
        this.errors = new LinkedList<>();
        this.counter = new LazyCounter(counter);
    }

    /**
     * Appends the specified error to the end of this list and set key order.
     *
     * @param error error to be appended to this list
     */
    void addWithOrder(E error) {
        if (error != null) {
            synchronized (errors) {
                final int order = counter.getAsInt() + errors.size();
                if (error.getKey() != null) {
                    error.getKey().setOrder(order);
                }
                errors.add(error);
            }
        }
    }

    void addTo(List<E> list) {
        list.addAll(errors);
    }

    List<E> toList() {
        return new ArrayList<>(errors);
    }

}
