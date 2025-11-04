package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Base implementation of {@link EventHandler} that collects events during the mapping, validation and parsing phases.
 */
public abstract non-sealed class EventCollector<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>, B extends IException> extends EventHandler<R, D, E> {

    public static final EventType<PropertyException> EXCEPTION_TYPE = EventException.INSTANCE;
    public static final EventType<Event> ERROR_TYPE = EventError.INSTANCE;
    protected final EventType<B> mode;
    private final Set<Integer> offsets;

    protected EventCollector(R row, EventType<B> mode) {
        super(row);
        this.mode = mode;
        this.offsets = new LinkedHashSet<>();
    }

    @Override
    public void handle(D type, int offset, SeverityType level, String code, String message) {
        this.accept(type, offset, mode.of(level, code, message));
    }

    @Override
    public void handle(D data, int offset, SeverityType level, String code, Throwable cause) {
        this.accept(data, offset, mode.of(level, code, cause));
        offsets.add(offset);
    }

    @Override
    public final void handle(D data, int offset, Exception cause, Error config) {
        final String code = code(cause, config);
        final SeverityType level = level(cause, config);
        this.handle(data, offset, level, code, cause);
    }

    @Override
    public final void handle(D data, int offset, ConstraintViolation<?> violation, Error config) {
        if (offset == -1 || !offsets.contains(offset)) {
            //avoid collecting violations on property having parsing error
            final SeverityType level = level(violation, config);
            final String code = code(violation, config);
            this.handle(data, offset, level, code, violation.getMessage());
        }
    }

    /**
     * Handles property exception caused by the input at the given offset.
     *
     * @param type   the data type
     * @param offset the input index
     * @param cause  the cause exception
     */
    protected abstract void accept(D type, int offset, B cause);

}
