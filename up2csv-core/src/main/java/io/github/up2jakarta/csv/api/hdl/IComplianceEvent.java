package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.ITerm;
import jakarta.validation.ConstraintViolation;

/**
 * Contact interface for an input event with {@link ConstraintViolation} property, useful for JSR-303 validation.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.core.hdl.ComplianceCollector
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment)
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment, int)
 */
public interface IComplianceEvent<D extends ITerm<D>> extends IEvent<D> {

    /**
     * @return the cause exception
     */
    ConstraintViolation<?> getCause();

    @Override
    default String getMessage() {
        return this.getCause().getMessage();
    }

}
