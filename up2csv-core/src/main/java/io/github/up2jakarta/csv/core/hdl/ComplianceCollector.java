package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.hdl.EventCode;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.Listable;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple compliance-handler implementation that collects events in {@link #toList()}.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment)
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment, int)
 */
public final class ComplianceCollector<D extends ITerm<D>> extends ComplianceHandler<D> implements Listable<IComplianceEvent<D>> {

    private final List<IComplianceEvent<D>> events = new LinkedList<>();

    @Override
    public List<IComplianceEvent<D>> toList() {
        return events;
    }

    @Override
    public void handle(EventLevel level, EventCode code, D type, Integer offset, ConstraintViolation<?> cause) {
        events.add(new ComplianceEvent<>(level.get(), code.get(), type, offset, cause));
    }

}
