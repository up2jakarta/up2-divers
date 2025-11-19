package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * Simple {@link BusinessCreator} implementation that wraps the business-object and its related events.
 *
 * @param <T> the business-object type
 * @param <E> the event type
 */
public final class Up2Result<T extends Segment, E extends IEvent<?>> implements Supplier<T>, Listable<E> {

    private final T bean;
    private final List<E> events;

    public Up2Result(T bean, List<E> events) {
        this.bean = bean;
        this.events = events;
    }

    @Override
    public T get() {
        return bean;
    }

    @Override
    public List<E> toList() {
        return events;
    }

}
