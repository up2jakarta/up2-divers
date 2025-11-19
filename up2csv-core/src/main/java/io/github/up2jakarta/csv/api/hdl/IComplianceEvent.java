package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.ConstraintViolation;

/**
 * Contact interface for an input event with {@link ConstraintViolation} property, useful for JSR-303 validation.
 *
 * @param <D> the business data type
 * @see io.github.up2jakarta.csv.core.hdl.ComplianceCollector
 * @see io.github.up2jakarta.csv.core.Up2Format#validate(Segment)
 * @see io.github.up2jakarta.csv.core.Up2Format#validate(Segment, int)
 */
public interface IComplianceEvent<D extends DataType<D>> extends IEvent<D> {

    /**
     * @return the cause exception
     */
    ConstraintViolation<?> getCause();

    @Override
    default String getMessage() {
        return this.getCause().getMessage();
    }

}
