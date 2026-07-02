package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IEventBuilder;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.Listable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link IEventBuilder} builder for {@link EventCollector}.
 */
public abstract class EventModeBuilder<D extends ITerm<D>, R extends IRecord<?>, E extends IEvent<D>> implements IEventBuilder<D, R, E> {

    private final List<EventModeCollector<D, R, E, ?>> collectors;

    protected EventModeBuilder(int size) {
        this.collectors = new ArrayList<>(size);
    }

    public final BusinessHandler<D> of(R record) {
        final EventModeCollector<D, R, E, ?> collector = newHandler(record);
        collectors.add(collector);
        return collector;
    }

    protected abstract EventModeCollector<D, R, E, ?> newHandler(R record);

    @Override
    public final List<E> toList() {
        return collectors.stream().map(Listable::toList).flatMap(List::stream).toList();
    }

}
