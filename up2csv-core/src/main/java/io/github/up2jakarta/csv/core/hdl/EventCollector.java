package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.hdl.EventCode;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.Listable;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Simple events collector of {@link BusinessHandler} that collects all events in {@link #events}.
 *
 * @param <D> the business term type
 */
public abstract class EventCollector<D extends ITerm<D>, E extends IEvent<D>> extends BusinessHandler<D> implements Listable<E> {

    final List<E> events;
    private final Set<Integer> offsets;

    protected EventCollector(List<E> events) {
        this.events = notNull(events, EventCollector.class, "events");
        this.offsets = new LinkedHashSet<>();
    }

    private E accept(E event) {
        if (event != null) {
            events.add(event);
        }
        return event;
    }

    @Override
    public final void handle(Error config, D term, String message) {
        this.accept(this.newEvent(config.level(), config.value(), term, null, message));
    }

    @Override
    public final void handle(Error config, D term, String message, RuntimeException cause) {
        this.accept(this.newEvent(config.level(), config.value(), term, message, cause));
    }

    @Override
    public final void handle(SeverityType level, String code, D type, int offset, String message) {
        this.accept(this.newEvent(level, code, type, offset, message));
    }

    @Override
    public final void handle(EventLevel level, EventCode code, D data, int offset, Throwable cause) {
        if (this.accept(this.newEvent(level.get(), code.get(), data, offset, cause)) != null) {
            offsets.add(offset);
        }
    }

    @Override
    public final void handle(EventLevel level, EventCode code, D type, Integer offset, ConstraintViolation<?> cause) {
        if (offset == null || !offsets.contains(offset)) {
            //avoid collecting violations on property having parsing error
            this.accept(this.newEvent(level.get(), code.get(), type, offset, cause.getMessage()));
        }
    }

    @Override
    public final List<E> toList() {
        return events;
    }

    protected abstract E newEvent(SeverityType level, String code, D type, String message, Throwable cause);

    protected abstract E newEvent(SeverityType level, String code, D type, Integer offset, Throwable cause);

    protected abstract E newEvent(SeverityType level, String code, D type, Integer offset, String message);

}
