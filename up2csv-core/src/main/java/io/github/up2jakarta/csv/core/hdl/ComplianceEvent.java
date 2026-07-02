package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.validation.ConstraintViolation;

/**
 * Simple event implementation that wraps the JSR-303 {@link ConstraintViolation}.
 *
 * @param <D> the business term type
 * @see ComplianceCollector
 */
final class ComplianceEvent<D extends ITerm<D>> extends SimpleEvent<D> implements IComplianceEvent<D> {

    private final transient ConstraintViolation<?> cause;

    ComplianceEvent(SeverityType level, String code, D type, Integer offset, ConstraintViolation<?> cause) {
        super(type, offset, level, code, cause.getMessage());
        this.cause = cause;
    }

    public ConstraintViolation<?> getCause() {
        return cause;
    }

}
