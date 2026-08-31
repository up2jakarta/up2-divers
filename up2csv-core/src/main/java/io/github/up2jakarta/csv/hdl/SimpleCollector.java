package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.lov.SeverityType;

import java.util.LinkedList;

/**
 * Simple events collector of {@link BusinessHandler} that collects events of type {@link SimpleEvent} .
 *
 * @param <D> the business term type
 */
public class SimpleCollector<D extends ITerm<D>> extends EventCollector<D, SimpleEvent<D>> {

    public SimpleCollector() {
        super(new LinkedList<>());
    }

    @Override
    protected SimpleEvent<D> newEvent(SeverityType level, String code, D type, String message, Throwable cause) {
        return new SimpleEvent<>(type, null, level, code, message);
    }

    @Override
    protected SimpleEvent<D> newEvent(SeverityType level, String code, D type, Integer offset, Throwable cause) {
        return new SimpleEvent<>(type, offset, level, code, cause.getMessage());
    }

    @Override
    protected SimpleEvent<D> newEvent(SeverityType level, String code, D type, Integer offset, String message) {
        return new SimpleEvent<>(type, offset, level, code, message);
    }

}
