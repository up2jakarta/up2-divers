package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.ICreator;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.SeverityType;

import static java.util.Objects.requireNonNull;

/**
 * Base {@link EventCollector} implementation that delegates partial event creation to {@link #mode}.
 *
 * @param <D> the business data type
 */
public abstract class EventModeCollector<D extends DataType<D>, R extends IRecord<?>, E extends IEvent<D>, B extends IException> extends EventCollector<D, E> {

    final R source;
    final EventModeType<B> mode;

    protected EventModeCollector(R source, ICreator<R, E> creator, EventModeType<B> mode) {
        super(creator.apply(source));
        this.source = requireNonNull(source);
        this.mode = requireNonNull(mode);
    }

    public final R getSource() {
        return source;
    }

    @Override
    protected final E newEvent(SeverityType level, String code, D type, String message, Throwable cause) {
        return this.newEvent(type, null, mode.of(level, code, message, cause));
    }

    @Override
    protected final E newEvent(SeverityType level, String code, D type, Integer offset, Throwable cause) {
        return this.newEvent(type, offset, mode.of(level, code, cause));
    }

    @Override
    protected final E newEvent(SeverityType level, String code, D type, Integer offset, String message) {
        return this.newEvent(type, offset, mode.of(level, code, message));
    }

    protected abstract E newEvent(D type, Integer offset, B cause);

}
