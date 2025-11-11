package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Listable;
import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Base implementation of {@link EventHandler} that collects events during the mapping, validation and parsing phases.
 * <p>
 * I add new feature {@link #handle(DataType, SeverityType, String, String)} for join cardinalities checking
 * during multi-segments aggregation.
 *
 * @see io.github.up2jakarta.csv.core.BusinessImporter
 */
public abstract sealed class BusinessHandler<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>, B extends IException> extends EventHandler<D> implements Listable<E> permits PropertyCollector, BusinessCollector {

    public static final EventType<PropertyException> PROPERTY_MODE = PropertyMode.INSTANCE;
    public static final EventType<BusinessException> BUSINESS_MODE = BusinessMode.INSTANCE;

    final R source;
    final List<E> events;
    final EventType<B> mode;
    private final Set<Integer> offsets;

    protected BusinessHandler(R source, List<E> collector, EventType<B> mode) {
        this.source = source;
        this.mode = mode;
        this.events = requireNonNull(collector);
        this.offsets = new LinkedHashSet<>();
    }

    /**
     * Handles any error caused by the input, used for cardinality checking.
     *
     * @param type    the data type
     * @param level   the error severity
     * @param code    the error code
     * @param message the error message
     */
    public final void handle(D type, SeverityType level, String code, String message) {
        this.handle(type, null, mode.of(level, code, message));
    }

    @Override
    public final void handle(D data, int offset, SeverityType level, String code, Throwable cause) {
        this.handle(data, offset, mode.of(level, code, cause));
        offsets.add(offset);
    }

    @Override
    public final void handle(D data, int offset, Exception cause, Error config) {
        final String code = code(cause, config);
        final SeverityType level = level(cause, config);
        this.handle(data, offset, level, code, cause);
    }

    @Override
    public final void handle(D data, Integer offset, ConstraintViolation<?> violation, Error config) {
        if (offset == null || !offsets.contains(offset)) {
            //avoid collecting violations on property having parsing error
            final SeverityType level = level(violation, config);
            final String code = code(violation, config);
            this.handle(data, offset, mode.of(level, code, violation.getMessage()));
        }
    }

    public final R getSource() {
        return source;
    }

    @Override
    public final List<E> toList() {
        return events;
    }

    private void handle(D type, Integer offset, B cause) {
        final E event = this.create(type, offset, cause);
        if (event != null) {
            events.add(event);
        }
    }

    abstract E create(D type, Integer offset, B cause);

}
