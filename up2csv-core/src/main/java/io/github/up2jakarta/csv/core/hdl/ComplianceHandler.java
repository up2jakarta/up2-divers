package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.AccessException;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Listable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple event handler implementation for JSR-303 validation only.
 *
 * @param <D> the input data type
 * @see io.github.up2jakarta.csv.core.Up2Format#validate(Segment)
 * @see io.github.up2jakarta.csv.core.Up2Format#validate(Segment, int)
 */
public final class ComplianceHandler<D extends DataType<D>> extends EventHandler<D> implements Listable<IComplianceEvent<D>> {

    private final List<IComplianceEvent<D>> events = new LinkedList<>();

    @Override
    public List<IComplianceEvent<D>> toList() {
        return events;
    }

    @Override
    public void handle(D data, int offset, SeverityType level, String code, Throwable cause) {
        throw new AccessException(ComplianceHandler.class, "handle", "unsupported operation");
    }

    @Override
    public void handle(D data, int offset, Exception exception, Error config) {
        throw new AccessException(ComplianceHandler.class, "handle", "unsupported operation");
    }

    @Override
    public void handle(D data, Integer offset, ConstraintViolation<?> violation, Error config) {
        final SeverityType level = level(violation, config);
        final String code = code(violation, config);
        events.add(new ComplianceEvent<>(level, code, data, offset, violation));
    }

}
