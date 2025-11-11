package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.hdl.IComplianceEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.ConstraintViolation;

/**
 * Simple event implementation that wraps the JSR-303 {@link ConstraintViolation}.
 *
 * @param <D> the input data type
 * @see ComplianceHandler
 */
final class ComplianceEvent<D extends DataType<D>> implements IComplianceEvent<D> {

    private final D type;
    private final Integer offset;
    private final String code;
    private final SeverityType level;
    private final ConstraintViolation<?> cause;

    public ComplianceEvent(SeverityType level, String code, D type, Integer offset, ConstraintViolation<?> cause) {
        this.offset = offset;
        this.cause = cause;
        this.level = level;
        this.code = code;
        this.type = type;
    }

    public ConstraintViolation<?> getCause() {
        return cause;
    }

    @Override
    public SeverityType getSeverity() {
        return level;
    }

    @Override
    public Integer getOffset() {
        return offset;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public D getType() {
        return type;
    }

}
